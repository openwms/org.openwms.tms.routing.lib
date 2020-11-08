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
import org.openwms.common.comm.Responder;
import org.openwms.common.comm.osip.CommConstants;
import org.openwms.common.comm.osip.OSIP;
import org.openwms.core.SpringProfiles;
import org.openwms.core.exception.IllegalConfigurationValueException;
import org.openwms.tms.routing.InputContext;
import org.openwms.tms.routing.config.OwmsProperties;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

/**
 * A AmqpResponder.
 *
 * @author Heiko Scherrer
 */
@Profile(SpringProfiles.ASYNCHRONOUS_PROFILE)
@OSIP
@Service("responder")
class AmqpResponder implements Responder {

    private final InputContext in;
    private final AmqpTemplate amqpTemplate;
    private final String exchangeName;
    private final OwmsProperties owmsProperties;

    AmqpResponder(InputContext in, AmqpTemplate amqpTemplate,
            @Value("${owms.driver.osip.res.exchange-name}") String exchangeName,
            OwmsProperties owmsProperties) {
        this.in = in;
        this.amqpTemplate = amqpTemplate;
        this.exchangeName = exchangeName;
        this.owmsProperties = owmsProperties;
    }

    /**
     * {@inheritDoc}
     *
     * Send a message to fire a OSIP RES_ telegram to the given {@code target} location.
     */
    @Measured
    @Override
    public void sendToLocation(String target) {
        ResponseHeader header = ResponseHeader.newBuilder()
                .sender("" + in.getMsg().get(CommConstants.RECEIVER))
                .receiver(""+in.getMsg().get(CommConstants.SENDER))
                .sequenceNo(""+in.getMsg().get(CommConstants.SEQUENCE_NO))
                .build();

        ResponseMessage.Builder builder = ResponseMessage
                .newBuilder()
                .header(header)
                .barcode(""+in.getMsg().get(CommConstants.BARCODE))
                .actualLocation(""+in.getMsg().get(CommConstants.ACTUAL_LOCATION))
                .targetLocation(target)
                .errorCode(""+in.getMsg().get(CommConstants.ERROR_CODE))
                ;
        String routingKey = owmsProperties
                .getPartner(""+in.getMsg().get(CommConstants.SENDER))
                .orElseThrow(() -> new IllegalConfigurationValueException(format("No partner service with name [%s] configured in property owms.driver.partners", ""+in.getMsg().get(CommConstants.SENDER))));

        amqpTemplate.convertAndSend(
                exchangeName,
                routingKey,
                builder.build(),
                m -> {
                    m.getMessageProperties().getHeaders().put(CommConstants.SENDER, "" + in.getMsg().get(CommConstants.RECEIVER));
                    m.getMessageProperties().getHeaders().put(CommConstants.RECEIVER, "" + in.getMsg().get(CommConstants.SENDER));
                    return m;
                }
        );
    }

    /**
     * {@inheritDoc}
     *
     * Send a message to fire a OSIP RES_ telegram to the given {@code target} location.
     */
    @Measured
    @Override
    public void sendToLocation(String barcode, String sourceLocation, String targetLocation) {
        ResponseHeader header = ResponseHeader.newBuilder()
                .sender("" + in.getMsg().get(CommConstants.RECEIVER))
                .receiver(""+in.getMsg().get(CommConstants.SENDER))
                .sequenceNo(""+in.getMsg().get(CommConstants.SEQUENCE_NO))
                .build();

        ResponseMessage.Builder builder = ResponseMessage
                .newBuilder()
                .header(header)
                .barcode(barcode)
                .actualLocation(sourceLocation)
                .targetLocation(targetLocation)
                .errorCode(""+in.getMsg().get(CommConstants.ERROR_CODE))
                ;
        String routingKey = owmsProperties.getPartner(""+in.getMsg().get(CommConstants.SENDER)).orElseThrow(() -> new IllegalConfigurationValueException(format("No partner service with name [%s] configured in property owms.driver.partners", ""+in.getMsg().get(CommConstants.SENDER))));
        amqpTemplate.convertAndSend(exchangeName, routingKey, builder.build());
    }

}
