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
package org.openwms.common.comm.req;

import org.ameba.annotation.Measured;
import org.openwms.common.comm.ItemMessage;
import org.openwms.common.comm.ItemMessageHandler;
import org.openwms.core.SpringProfiles;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * A RequestMessageListener.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Profile(SpringProfiles.ASYNCHRONOUS_PROFILE)
@Component
class RequestMessageListener {

    private final ItemMessageHandler handler;

    RequestMessageListener(ItemMessageHandler handler) {
        this.handler = handler;
    }

    @Measured
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${owms.driver.req.queue-name}", durable = "true"),
            exchange = @Exchange(value = "${owms.driver.req.exchange-mapping}", ignoreDeclarationExceptions = "true"))
    )
    void handle(@Payload ItemMessage msg) {
        try {
            handler.handle(msg);
        } catch (Exception e) {
            throw new AmqpRejectAndDontRequeueException(e.getMessage(), e);
        }
    }
}
