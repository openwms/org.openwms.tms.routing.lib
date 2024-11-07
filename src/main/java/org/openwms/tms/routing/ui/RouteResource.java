/*
 * Copyright 2005-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openwms.tms.routing.ui;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.ameba.exception.NotFoundException;
import org.ameba.http.MeasuredRestController;
import org.openwms.core.http.AbstractWebController;
import org.openwms.tms.routing.RouteMapper;
import org.openwms.tms.routing.location.impl.LocationRepository;
import org.openwms.tms.routing.routes.RouteRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriTemplate;

import java.util.List;

import static org.openwms.tms.routing.RoutingConstants.API_ROUTES;


/**
 * A RouteResource.
 *
 * @author Heiko Scherrer
 */
@MeasuredRestController
class RouteResource extends AbstractWebController {

    private final LocationRepository locationRepository;
    private final RouteRepository routeRepository;
    private final RouteMapper mapper;

    RouteResource(LocationRepository locationRepository, RouteRepository routeRepository, RouteMapper mapper) {
        this.locationRepository = locationRepository;
        this.routeRepository = routeRepository;
        this.mapper = mapper;
    }

    @GetMapping(API_ROUTES)
    public List<RouteVO> getAll() {
        return mapper.convertEO(routeRepository.findAll(Sort.by("pk")));
    }

    @DeleteMapping(API_ROUTES + "/{persistentKey}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict("routes")
    @Transactional
    public void delete(@PathVariable("persistentKey") String persistentKey) {
        routeRepository.findBypKey(persistentKey).ifPresent(routeRepository::delete);
    }

    @PutMapping(API_ROUTES)
    @CacheEvict("routes")
    @Transactional
    public RouteVO save(@RequestBody RouteVO routeVO) {
        var eo = routeRepository.findBypKey(routeVO.getKey()).orElseThrow(NotFoundException::new);
        var route = mapper.copy(routeVO, eo);
        route.setSourceLocation(routeVO.hasSourceLocationName()
                ? locationRepository.findByLocationId(routeVO.getSourceLocationName()).orElseThrow(NotFoundException::new)
                : null);
        route.setTargetLocation(routeVO.hasTargetLocationName()
                ? locationRepository.findByLocationId(routeVO.getTargetLocationName()).orElseThrow(NotFoundException::new)
                : null);
        return mapper.convertEO(routeRepository.save(route));
    }

    @PostMapping(API_ROUTES)
    @ResponseStatus(HttpStatus.CREATED)
    @CacheEvict("routes")
    @Transactional
    public void create(@RequestBody RouteVO routeVO, HttpServletRequest req, HttpServletResponse resp) {
        var route = mapper.convertVO(routeVO);
        if (routeVO.hasSourceLocationName()) {
            route.setSourceLocation(locationRepository.findByLocationId(routeVO.getSourceLocationName()).orElseThrow(NotFoundException::new));
        }
        if (routeVO.hasTargetLocationName()) {
            route.setTargetLocation(locationRepository.findByLocationId(routeVO.getTargetLocationName()).orElseThrow(NotFoundException::new));
        }
        route = routeRepository.save(route);
        resp.addHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS,HttpHeaders.LOCATION);
        resp.addHeader(HttpHeaders.LOCATION, getCreatedResourceURI(req, route.getPersistentKey()));
    }

    private String getCreatedResourceURI(HttpServletRequest req, String objId) {
        var url = req.getRequestURL();
        var template = new UriTemplate(url.append("/{objId}").toString());
        return template.expand(objId).toASCIIString();
    }
}
