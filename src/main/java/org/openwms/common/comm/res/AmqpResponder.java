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
import org.openwms.core.SpringProfiles;
import org.openwms.core.exception.IllegalConfigurationValueException;
import org.openwms.tms.routing.InputContext;
import org.openwms.tms.routing.OwmsProperties;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

/**
 * A AmqpResponder.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Profile(SpringProfiles.ASYNCHRONOUS_PROFILE)
@Service("responder")
public class AmqpResponder implements ResResponder {

    private final InputContext in;
    private final AmqpTemplate amqpTemplate;
    private final String exchangeMapping;
    private final OwmsProperties owmsProperties;

    @Autowired
    public AmqpResponder(InputContext in, AmqpTemplate amqpTemplate, @Value("${owms.driver.res.exchange-mapping}") String exchangeMapping, OwmsProperties owmsProperties) {
        this.in = in;
        this.amqpTemplate = amqpTemplate;
        this.exchangeMapping = exchangeMapping;
        this.owmsProperties = owmsProperties;
    }

    /**
     * {@inheritDoc}
     */
    @Measured
    @Override
    public void sendToLocation(String target) {
        ResponseHeader header = ResponseHeader.newBuilder()
                .sender("" + in.getMsg().get("receiver"))
                .receiver(""+in.getMsg().get("sender"))
                .sequenceNo(""+in.getMsg().get("sequenceNo"))
                .build();

        ResponseMessage.Builder builder = ResponseMessage
                .newBuilder()
                .header(header)
                .barcode(""+in.getMsg().get("barcode"))
                .actualLocation(""+in.getMsg().get("actualLocation"))
                .targetLocation(target)
                .errorCode(""+in.getMsg().get("errorCode"))
                ;
        String routingKey = owmsProperties.getPartner(""+in.getMsg().get("sender")).orElseThrow(() -> new IllegalConfigurationValueException(format("No partner service with name [%s] configured in property owms.driver.partners", ""+in.getMsg().get("sender"))));
        amqpTemplate.convertAndSend(exchangeMapping, routingKey, builder.build());
    }

    /**
     * {@inheritDoc}
     */
    @Measured
    @Override
    public void sendToLocation(String barcode, String sourceLocation, String targetLocation) {
        ResponseHeader header = ResponseHeader.newBuilder()
                .sender("" + in.getMsg().get("receiver"))
                .receiver(""+in.getMsg().get("sender"))
                .sequenceNo(""+in.getMsg().get("sequenceNo"))
                .build();

        ResponseMessage.Builder builder = ResponseMessage
                .newBuilder()
                .header(header)
                .barcode(barcode)
                .actualLocation(sourceLocation)
                .targetLocation(targetLocation)
                .errorCode(""+in.getMsg().get("errorCode"))
                ;
        String routingKey = owmsProperties.getPartner(""+in.getMsg().get("sender")).orElseThrow(() -> new IllegalConfigurationValueException(format("No partner service with name [%s] configured in property owms.driver.partners", ""+in.getMsg().get("sender"))));
        amqpTemplate.convertAndSend(exchangeMapping, routingKey, builder.build());
    }

}
