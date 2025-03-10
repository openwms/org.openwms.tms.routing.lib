/*
 * Copyright 2005-2025 the original author or authors.
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
import org.openwms.tms.routing.ui.api.RouteVO;
import org.openwms.tms.routing.ui.impl.RouteUIService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

import static org.openwms.tms.routing.RoutingConstants.API_ROUTES;


/**
 * A RouteResource.
 *
 * @author Heiko Scherrer
 */
@MeasuredRestController
class RouteResource extends AbstractWebController {

    private final RouteUIService routeUIService;

    RouteResource(MessageSource messageSource, RouteUIService routeUIService) {
        super(messageSource);
        this.routeUIService = routeUIService;
    }

    @GetMapping(API_ROUTES)
    public List<RouteVO> getAll() {
        return routeUIService.findAll(Sort.by("pk"));
    }

    @GetMapping(API_ROUTES + "/{pKey}")
    public RouteVO findByPKey(@PathVariable String pKey) {
        return routeUIService.findBypKey(pKey).orElseThrow(() -> new NotFoundException("Route with pKey [%s] not found".formatted(pKey)));
    }

    @DeleteMapping(API_ROUTES + "/{pKey}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict("routes")
    public void delete(@PathVariable("pKey") String pKey) {
        routeUIService.delete(pKey);
    }

    @PutMapping(API_ROUTES)
    @CacheEvict("routes")
    public RouteVO save(@RequestBody RouteVO routeVO) {
        return routeUIService.save(routeVO);
    }

    @PostMapping(API_ROUTES)
    @ResponseStatus(HttpStatus.CREATED)
    @CacheEvict("routes")
    public void create(@RequestBody RouteVO routeVO, HttpServletRequest req, HttpServletResponse resp) {
        resp.addHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS,HttpHeaders.LOCATION);
        resp.addHeader(HttpHeaders.LOCATION, super.getLocationForCreatedResource(req, routeUIService.create(routeVO).getKey()));
    }
}
