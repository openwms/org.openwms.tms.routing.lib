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
package org.openwms.common;

import org.ameba.annotation.Measured;
import org.openwms.core.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.lang.String.format;

/**
 * A FetchLocationGroupByName.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@RefreshScope
@Component
public class FetchLocationGroupByName implements Function<String, LocationGroupVO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FetchLocationGroupByName.class);
    private final String commonServiceName;
    private final RestTemplate aLoadBalanced;
    private final DiscoveryClient dc;

    FetchLocationGroupByName(@Value("${owms.routing.common-service-name:common-service}") String commonServiceName, RestTemplate aLoadBalanced, DiscoveryClient dc) {
        this.commonServiceName = commonServiceName;
        this.aLoadBalanced = aLoadBalanced;
        this.dc = dc;
    }

    @Measured
    @Override
    public LocationGroupVO apply(String name) {
        List<ServiceInstance> list = dc.getInstances(commonServiceName.toUpperCase());
        if (list == null || list.size() == 0) {
            throw new RuntimeException(format("No deployed service with name [%s] found", commonServiceName.toUpperCase()));
        }
        Map<String, Object> maps = new HashMap<>();
        maps.put("name", name);
        ServiceInstance si = list.get(0);
        String endpoint = si.getMetadata().get("protocol") + "://" + si.getServiceId() + "/v1/locationgroups" + "?name=" + name;
        LOGGER.debug("Calling common-service URL [{}]", endpoint);
        ResponseEntity<LocationGroupVO> exchange = aLoadBalanced.exchange(endpoint, HttpMethod.GET, new HttpEntity<LocationGroupVO>(SecurityUtils.createHeaders(si.getMetadata().get("username"), si.getMetadata().get("password"))), LocationGroupVO.class, maps);
        return exchange.getBody();
    }
}
