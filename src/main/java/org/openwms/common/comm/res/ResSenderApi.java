/*
 * openwms.org, the Open Warehouse Management System.
 * Copyright (C) 2018 Heiko Scherrer
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
package org.openwms.common.comm.res;

import org.ameba.annotation.Measured;
import org.openwms.OwmsProperties;
import org.openwms.common.comm.MessageProcessingException;
import org.openwms.core.SecurityUtils;
import org.openwms.core.SpringProfiles;
import org.openwms.core.exception.IllegalConfigurationValueException;
import org.openwms.tms.routing.InputContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static java.lang.String.format;

/**
 * A ResSenderApi.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Profile("!"+ SpringProfiles.ASYNCHRONOUS_PROFILE)
@Service("responder")
class ResSenderApi implements ResResponder {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResSenderApi.class);
    private final InputContext in;
    private final RestTemplate aLoadBalanced;
    private final DiscoveryClient dc;
    private final OwmsProperties owmsProperties;

    public ResSenderApi(InputContext in, RestTemplate aLoadBalanced, DiscoveryClient dc, OwmsProperties owmsProperties) {
        this.in = in;
        this.aLoadBalanced = aLoadBalanced;
        this.dc = dc;
        this.owmsProperties = owmsProperties;
    }

    @Override
    @Measured
    public void sendToLocation(String target) {
        String sender = owmsProperties.getPartner(""+in.getMsg().get("sender")).orElseThrow(() -> new IllegalConfigurationValueException(format("No partner service with name [%s] configured in property owms.driver.partners", ""+in.getMsg().get("sender"))));
        List<ServiceInstance> list = dc.getInstances(sender);
        if (list == null || list.isEmpty()) {
            throw new RuntimeException(format("No deployed service with name [%s] found", ""+in.getMsg().get("sender")));
        }
        // FIXME [openwms]: 19.10.18 encode this into bean
        ResponseHeader header = ResponseHeader.newBuilder()
                .sender("" + in.getMsg().get("receiver"))
                .receiver(""+in.getMsg().get("sender"))
                .build();

        ResponseMessage.Builder builder = ResponseMessage
                .newBuilder()
                .header(header)
                .barcode(""+in.getMsg().get("barcode"))
                .actualLocation(""+in.getMsg().get("actualLocation"))
                .errorCode(""+in.getMsg().get("errorCode"))
                .targetLocation(target);

        ServiceInstance si = list.get(0);
        String endpoint = si.getMetadata().get("protocol") + "://" + si.getServiceId() + "/res";
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Calling driver URL [{}]", endpoint);
        }
        HttpHeaders headers = SecurityUtils.createHeaders(si.getMetadata().get("username"), si.getMetadata().get("password"));
        try {
            aLoadBalanced.exchange(
                endpoint,
                HttpMethod.POST,
                new HttpEntity<>(builder.build(), headers),
                Void.class
            );
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new MessageProcessingException(e.getMessage(), e);
        }
    }

    /**
     * Send a message to fire a OSIP RES_ telegram to the given {@code target} location.
     *
     * @param barcode
     * @param sourceLocation
     * @param targetLocation The target location
     */
    @Override
    public void sendToLocation(String barcode, String sourceLocation, String targetLocation) {

    }
}
