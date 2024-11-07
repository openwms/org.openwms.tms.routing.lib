/*
 * Copyright 2005-2024 the original author or authors.
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
package org.openwms.common.comm.osip.upd;

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
 * A UpdateMessageConfiguration.
 *
 * @author Heiko Scherrer
 */
@Profile(SpringProfiles.ASYNCHRONOUS_PROFILE)
@OSIP
@Configuration
class UpdateMessageConfiguration {

    @Bean
    TopicExchange updExchange(@Value("${owms.driver.osip.upd.exchange-name}") String exchangeName) {
        return new TopicExchange(exchangeName, true, false);
    }

    @Bean
    Queue updQueue(@Value("${owms.driver.osip.upd.queue-name}") String queueName) {
        return new Queue(queueName);
    }

    @Bean
    Binding updBinding(
            TopicExchange updExchange,
            Queue updQueue,
            @Value("${owms.driver.osip.upd.routing-key-in}") String routingKey
    ) {
        return BindingBuilder
                .bind(updQueue)
                .to(updExchange)
                .with(routingKey);
    }

    @Bean
    TopicExchange updxExchange(@Value("${owms.driver.osip.updx.exchange-name}") String exchangeName) {
        return new TopicExchange(exchangeName, true, false);
    }

    @Bean
    Queue updxQueue(@Value("${owms.driver.osip.updx.queue-name}") String queueName) {
        return new Queue(queueName);
    }

    @Bean
    Binding updxBinding(
            TopicExchange updxExchange,
            Queue updxQueue,
            @Value("${owms.driver.osip.updx.routing-key-in}") String routingKey
    ) {
        return BindingBuilder
                .bind(updxQueue)
                .to(updxExchange)
                .with(routingKey);
    }
}
