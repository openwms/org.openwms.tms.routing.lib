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

    /**
     * {@inheritDoc}
     */
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
     * {@inheritDoc}
     */
    @Override
    public void sendToLocation(String barcode, String sourceLocation, String targetLocation) {
        throw new UnsupportedOperationException("Currently not implemented");
    }
}
