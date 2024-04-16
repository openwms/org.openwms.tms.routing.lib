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
package org.openwms.tms.routing.app;

import org.openwms.common.location.api.commands.LocationReplicaRegistration;
import org.openwms.core.SpringProfiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * A Registrator.
 *
 * @author Heiko Scherrer
 */
@Profile(SpringProfiles.ASYNCHRONOUS_PROFILE)
@RefreshScope
@Component
class Registrator {

    private static final Logger LOGGER = LoggerFactory.getLogger(Registrator.class);
    private final String exchangeName;
    private final LocationReplicaRegistration locationReplicaRegistration;
    private final AmqpTemplate amqpTemplate;

    Registrator(@Value("${owms.commands.common.registration.exchange-name}") String exchangeName,
            @Value("${spring.application.name}") String applicationName,
            @Value("${owms.routing.registration.location-request-removal-path}") String locationRequestRemovalPath,
            @Value("${owms.routing.registration.location-removal-path}") String locationRemovalPath,
            AmqpTemplate amqpTemplate) {
        this.exchangeName = exchangeName;
        this.locationReplicaRegistration = new LocationReplicaRegistration(
                applicationName,
                locationRequestRemovalPath,
                locationRemovalPath
        );
        this.amqpTemplate = amqpTemplate;
    }

    @EventListener
    public void onContextStarted(ContextStartedEvent cse) {
        LOGGER.info("Register at business services");
        amqpTemplate.convertAndSend(exchangeName, "common.registration.command.in.register.loc", locationReplicaRegistration);
    }

    @EventListener
    public void onContextStopped(ContextClosedEvent cse) {
        LOGGER.info("Stopping ApplicationContext");
        //amqpTemplate.convertAndSend(exchangeName, "common.registration.command.in.unregister.loc", locationReplicaRegistration);
    }

    @Scheduled(fixedDelay = 30000L)
    public void onTimer() {
        amqpTemplate.convertAndSend(exchangeName, "common.registration.command.in.register.loc", locationReplicaRegistration);
    }
}
