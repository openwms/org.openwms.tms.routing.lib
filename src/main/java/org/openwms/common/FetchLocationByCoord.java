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

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * A FetchLocationGroupByName.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Component
public class FetchLocationByCoord implements Function<String, LocationVO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FetchLocationByCoord.class);

    @Autowired
    private RestTemplate aLoadBalanced;
    @Value("${owms.common-service.service-id}")
    private String serviceId;
    @Value("${owms.common-service.protocol}")
    private String protocol;
    @Value("${owms.common-service.username}")
    private String username;
    @Value("${owms.common-service.password}")
    private String password;
    private String endpoint;
    @Autowired
    private LoadBalancerClient loadBalancer;

    @Override
    public LocationVO apply(String coordinate) {
        Map<String, Object> maps = new HashMap<>();
        maps.put("locationPK", coordinate);
        ServiceInstance instance = loadBalancer.choose(serviceId);
        //instance.getMetadata().entrySet().forEach(p -> LOGGER.debug("Entry/Value:" + p.getKey() + p.getValue()));
        URI storesUri = URI.create(String.format("https://%s:%s", instance.getHost(), instance.getPort()));
        endpoint = protocol + "://" + username + ":" + password + "@" + instance.getHost()+ instance.getPort();
        try {
            LOGGER.debug(endpoint + CommonConstants.API_LOCATIONS + "?locationPK=" + coordinate);
            ResponseEntity<LocationVO> exchange =
                    aLoadBalanced.getForEntity(
                            endpoint + CommonConstants.API_LOCATIONS + "?locationPK=" + coordinate,
                            LocationVO.class,
                            maps);
            return exchange.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
