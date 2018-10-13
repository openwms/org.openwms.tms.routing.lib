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
import org.openwms.common.LocationGroupVO;
import org.openwms.common.LocationVO;
import org.openwms.core.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
 * A ActivitiMatrix.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Component
class ActivitiMatrix implements Matrix {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivitiMatrix.class);

    @Autowired
    private ActionRepository repository;
    @Autowired
    @Qualifier("simpleRestTemplate")
    private RestTemplate restTemplate;
    @Autowired
    private DiscoveryClient dc;

    @Override
    public Action findBy(@NotNull String actionType, @NotNull Route route, LocationVO location, LocationGroupVO locationGroup) {
        // search explicitly...
        Optional<Action> prg = Optional.empty();
        if (null != location) {

            // First explicitly search for the Location and Route
            prg = repository.findByRouteAndLocationKey(route.getRouteId(), location.getCoordinate());
            if (!prg.isPresent()) {

                // When Location is set but no Action exists, check by LocationGroup
                prg = findByLocationGroupByName(route, location.getLocationGroupName());
                if (!prg.isPresent()) {

                    // search the LocationGroup hierarchy the way up...
                    prg = findByLocationGroup(route, locationGroup);
                    if (!prg.isPresent()) {
                        String message = String.format("No Action found for Route [%s] on Location [%s] and LocationGroup [%s]", route.getRouteId(), location.getCoordinate(), location.getLocationGroupName());
                        LOGGER.info(message);
                        throw new NoRouteException(message);
                    }
                }
            }
        }

        // search for locgroup...
        if (!prg.isPresent()) {
            if (null == locationGroup) {
                String message = String.format("No Action found for Route [%s] and Location [%s] without LocationGroup", route.getRouteId(), location);
                LOGGER.info(message);
                throw new NoRouteException(message);
            }
            prg = findByLocationGroup(route, locationGroup);
        }
        return prg.orElseThrow(() -> {
            String message = String.format("No Action found for Route [%s], Location [%s], LocationGroup [%s]", route.getRouteId(), location, locationGroup);
            LOGGER.info(message);
            return new NoRouteException(message);
        });
    }

    private Optional<Action> findByLocationGroup(Route route, LocationGroupVO locationGroup) {
        Optional<Action> cp = repository.findByRouteAndLocationGroupName(route.getRouteId(), locationGroup.getName());
        if (!cp.isPresent() && locationGroup.hasLink("_parent")) {
            cp = findByLocationGroup(route, findLocationGroup(locationGroup.getLink("_parent")));
        }
        return cp;
    }

    private LocationGroupVO findLocationGroup(Link parent) {
        List<ServiceInstance> list = dc.getInstances("common-service");
        if (list == null || list.size() == 0) {
            throw new RuntimeException("No deployed service with name common-service found");
        }
        ServiceInstance si = list.get(0);
        LOGGER.debug("Calling common-service URL [{}]", parent.getHref());
        ResponseEntity<LocationGroupVO> lg = restTemplate.exchange(
                parent.getHref(),
                HttpMethod.GET,
                new HttpEntity<>(SecurityUtils.createHeaders(si.getMetadata().get("username"), si.getMetadata().get("password"))),
                LocationGroupVO.class
                );
        if (lg == null) {
            throw new NotFoundException(String.format("No LocationGroup found at [%s]", parent.getHref()));
        }
        return lg.getBody();
    }

    private Optional<Action> findByLocationGroupByName(Route route, String locationGroupName) {
        return repository.findByRouteAndLocationGroupName(route.getRouteId(), locationGroupName);
    }
}
