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
package org.openwms.tms;

import org.ameba.exception.NotFoundException;
import org.openwms.core.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.function.Function;

/**
 * A FetchStartedTransportOrder.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Component
public class FetchStartedTransportOrder implements Function<String, TransportOrder> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FetchStartedTransportOrder.class);
    @Autowired
    private RestTemplate aLoadBalanced;
    @Autowired
    private DiscoveryClient dc;

    @Override
    public TransportOrder apply(String barcode) {
        List<ServiceInstance> list = dc.getInstances("tms-service");
        if (list == null || list.size() == 0) {
            throw new RuntimeException("No deployed service with name tms-service found");
        }
        ServiceInstance si = list.get(0);
        String endpoint = si.getMetadata().get("protocol") + "://tms-service/transportorders?barcode=" + barcode + "&state=STARTED";
        LOGGER.debug("Calling tms-service URL [{}]", endpoint);
        ResponseEntity<List<TransportOrder>> exchange =
                aLoadBalanced.exchange(
                        endpoint,
                        HttpMethod.GET,
                        new HttpEntity<>(SecurityUtils.createHeaders(si.getMetadata().get("username"), si.getMetadata().get("password"))),
                        new ParameterizedTypeReference<List<TransportOrder>>() {}
                        );
        if (exchange.getBody().size() == 0) {
            throw new NotFoundException(String.format("No started TransportOrders for TransportUnit [%s] found, no routing possible", barcode));
        }
        return exchange.getBody().get(0);
    }
}
