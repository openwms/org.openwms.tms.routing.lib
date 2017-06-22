/*
 * openwms.org, the Open Warehouse Management System.
 * Copyright (C) 2014 Heiko Scherrer
 *
 * This file is part of openwms.org.
 *
 * openwms.org is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as 
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * openwms.org is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this software. If not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.openwms.tms.routing;

import org.ameba.exception.NotFoundException;
import org.ameba.mapping.BeanMapper;
import org.openwms.common.LocationRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
        return mapper.map(routeRepository.findAll(), RouteVO.class);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/routes/{persistentKey}")
    public void delete(@PathVariable("persistentKey") String persistentKey) {
        routeRepository.findByPKey(persistentKey).ifPresent(routeRepository::delete);
    }

    @PutMapping("/routes")
    public RouteVO save(@RequestBody RouteVO routeVO) {
        Route route = routeRepository.findByPKey(routeVO.getKey()).orElseThrow(NotFoundException::new);
        return mapper.map(routeRepository.save(mapper.mapFromTo(routeVO, route)), RouteVO.class);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/routes")
    public void create(@RequestBody RouteVO routeVO, HttpServletRequest req, HttpServletResponse resp) {
        Route route = mapper.map(routeVO, Route.class);
        if (route.hasSourceLocation()) {
            route.setSourceLocation(locationRepository.findByLocationId(route.getSourceLocation().getLocationId()).orElseThrow(NotFoundException::new));
        }
        if (route.hasTargetLocation()) {
            route.setTargetLocation(locationRepository.findByLocationId(route.getTargetLocation().getLocationId()).orElseThrow(NotFoundException::new));
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
