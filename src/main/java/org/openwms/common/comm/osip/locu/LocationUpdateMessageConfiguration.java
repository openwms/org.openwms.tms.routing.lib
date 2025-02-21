/*
 * Copyright 2005-2025 the original author or authors.
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
package org.openwms.common.comm.osip.locu;

import org.openwms.common.comm.osip.OSIP;
import org.openwms.core.SpringProfiles;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * A LocationUpdateMessageConfiguration.
 *
 * @author Heiko Scherrer
 */
@Profile(SpringProfiles.ASYNCHRONOUS_PROFILE)
@OSIP
@Configuration
class LocationUpdateMessageConfiguration {

    @Bean
    TopicExchange locuExchange(@Value("${owms.driver.osip.locu.exchange-name}") String exchangeName) {
        return new TopicExchange(exchangeName, true, false);
    }

    @Bean
    Queue locuQueue(@Value("${owms.driver.osip.locu.queue-name}") String queueName) {
        return new Queue(queueName);
    }

    @Bean
    Binding locuBinding(
            TopicExchange locuExchange, Queue locuQueue,
            @Value("${owms.driver.osip.locu.routing-key-in}") String routingKey) {
        return BindingBuilder
                .bind(locuQueue)
                .to(locuExchange)
                .with(routingKey);
    }
}
