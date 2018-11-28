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
package org.openwms.common.comm.sysu;

import org.ameba.annotation.Measured;
import org.openwms.common.comm.ConsiderOSIPCondition;
import org.openwms.core.SpringProfiles;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * A SystemUpdateMessageListener.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Profile(SpringProfiles.ASYNCHRONOUS_PROFILE)
@Conditional(ConsiderOSIPCondition.class)
@Component
class SystemUpdateMessageListener {

    private final SystemUpdateMessageHandler handler;

    SystemUpdateMessageListener(SystemUpdateMessageHandler handler) {
        this.handler = handler;
    }

    @Measured
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${owms.driver.sysu.queue-name}", durable = "true"),
            exchange = @Exchange(value = "${owms.driver.sysu.exchange-mapping}", ignoreDeclarationExceptions = "true"))
    )
    void handle(@Payload SystemUpdateVO msg) {
        try {
            handler.handle(msg);
        } catch (Exception e) {
            throw new AmqpRejectAndDontRequeueException(e.getMessage(), e);
        }
    }
}
