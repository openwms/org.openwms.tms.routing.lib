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
package org.openwms.common.comm.osip.res;

import org.ameba.annotation.Measured;
import org.ameba.exception.NotFoundException;
import org.openwms.common.comm.MessageProcessingException;
import org.openwms.common.comm.Responder;
import org.openwms.common.comm.osip.CommConstants;
import org.openwms.common.comm.osip.OSIP;
import org.openwms.core.SecurityUtils;
import org.openwms.core.SpringProfiles;
import org.openwms.core.exception.IllegalConfigurationValueException;
import org.openwms.tms.routing.InputContext;
import org.openwms.tms.routing.config.OwmsProperties;
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
 * @author Heiko Scherrer
 */
@Profile("!"+ SpringProfiles.ASYNCHRONOUS_PROFILE)
@OSIP
@Service("responder")
class ResSenderApi implements Responder {

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

    /**
     * {@inheritDoc}
     *
     * Send a message to fire a OSIP RES_ telegram to the given {@code target} location.
     */
    @Override
    @Measured
    public void sendToLocation(String target) {
        String sender = owmsProperties.getPartner(""+in.getMsg().get(CommConstants.SENDER)).orElseThrow(() -> new IllegalConfigurationValueException(format("No partner service with name [%s] configured in property owms.driver.partners", ""+in.getMsg().get(CommConstants.SENDER))));
        List<ServiceInstance> list = dc.getInstances(sender);
        if (list == null || list.isEmpty()) {
            throw new NotFoundException(format("No deployed service with name [%s] found", sender));
        }
        ResponseHeader header = ResponseHeader.newBuilder()
                .sender("" + in.getMsg().get(CommConstants.RECEIVER))
                .receiver(""+in.getMsg().get(CommConstants.SENDER))
                .build();

        ResponseMessage.Builder builder = ResponseMessage
                .newBuilder()
                .header(header)
                .barcode(""+in.getMsg().get(CommConstants.BARCODE))
                .actualLocation(""+in.getMsg().get(CommConstants.ACTUAL_LOCATION))
                .errorCode(""+in.getMsg().get(CommConstants.ERROR_CODE))
                .targetLocation(target);

        ServiceInstance si = list.get(0);
        String endpoint = si.getMetadata().get("protocol") + "://" + si.getServiceId() + "/res";
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Calling driver URL [{}]", endpoint);
        }
        HttpHeaders headers = SecurityUtils.createHeaders(si.getMetadata().get("username"), si.getMetadata().get("password"));
        headers.add(CommConstants.RECEIVER, header.getReceiver());
        try {
            aLoadBalanced.exchange(
                endpoint,
                HttpMethod.POST,
                new HttpEntity<>(builder.build(), headers),
                Void.class
            );
        } catch (Exception e) {
            throw new MessageProcessingException(e.getMessage(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendToLocation(String barcode, String sourceLocation, String targetLocation) {
        throw new UnsupportedOperationException("Currently not implemented");
    }
}
