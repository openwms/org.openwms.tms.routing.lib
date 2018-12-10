/*
 * Copyright 2018 Heiko Scherrer
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

import org.ameba.exception.NotFoundException;
import org.ameba.mapping.BeanMapper;
import org.openwms.tms.routing.LocationRepository;
import org.openwms.tms.routing.RouteImpl;
import org.openwms.tms.routing.RouteRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * A RouteResource.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@CrossOrigin(origins = "*")
@RestController
class RouteResource {

    private final LocationRepository locationRepository;
    private final RouteRepository routeRepository;
    private final BeanMapper mapper;

    RouteResource(LocationRepository locationRepository, RouteRepository routeRepository, BeanMapper mapper) {
        this.locationRepository = locationRepository;
        this.routeRepository = routeRepository;
        this.mapper = mapper;
    }

    @GetMapping("/routes")
    public List<RouteVO> getAll() {
        return mapper.map(routeRepository.findAll(Sort.by("pk")), RouteVO.class);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    @DeleteMapping("/routes/{persistentKey}")
    public void delete(@PathVariable("persistentKey") String persistentKey) {
        routeRepository.findByPKey(persistentKey).ifPresent(routeRepository::delete);
    }

    @PutMapping("/routes")
    @Transactional
    public RouteVO save(@RequestBody RouteVO routeVO) {
        RouteImpl eo = routeRepository.findByPKey(routeVO.getKey()).orElseThrow(NotFoundException::new);
        RouteImpl route = mapper.mapFromTo(routeVO, eo);

        route.setSourceLocation(routeVO.hasSourceLocationName() ? locationRepository.findByLocationId(routeVO.getSourceLocationName()).orElseThrow(NotFoundException::new) : null);

        route.setTargetLocation(routeVO.hasTargetLocationName() ? locationRepository.findByLocationId(routeVO.getTargetLocationName()).orElseThrow(NotFoundException::new) : null);

        return mapper.map(routeRepository.save(route), RouteVO.class);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    @PostMapping("/routes")
    public void create(@RequestBody RouteVO routeVO, HttpServletRequest req, HttpServletResponse resp) {
        RouteImpl route = mapper.map(routeVO, RouteImpl.class);
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
        StringBuffer url = req.getRequestURL();
        UriTemplate template = new UriTemplate(url.append("/{objId}").toString());
        return template.expand(objId).toASCIIString();
    }
}
