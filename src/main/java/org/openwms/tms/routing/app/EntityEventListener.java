/*
 * Copyright 2005-2022 the original author or authors.
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

import org.ameba.annotation.Measured;
import org.openwms.common.location.api.messages.LocationGroupMO;
import org.openwms.common.location.api.messages.LocationMO;
import org.openwms.core.SpringProfiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * A EntityEventListener listens on changes on entities managed on foreign services.
 *
 * @author Heiko Scherrer
 */
@Profile(SpringProfiles.ASYNCHRONOUS_PROFILE)
@Component
class EntityEventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(EntityEventListener.class);
    private final CacheJanitor janitor;

    EntityEventListener(CacheJanitor janitor) {
        this.janitor = janitor;
    }

    /** Listens on LocationGroup messages. */
    @Measured
    @RabbitListener(queues = "${owms.events.common.lg.queue-name}")
    public void onLocationGroupEvent(@Payload LocationGroupMO msg, @Header("amqp_receivedRoutingKey") String routingKey) {
        if (msg == null) {
            LOGGER.warn("Got an event for a LocationGroup with empty body and routingKey [{}]", routingKey);
            return;
        }
        switch (routingKey) {
            case "lg.event.boot" -> {
                LOGGER.debug("[Cache] Location(Group) master data source has restarted -> evicting cache");
                janitor.evictLocationGroupCache();
                janitor.evictLocationCache();
            }
            case "lg.event.changed" -> {
                LOGGER.debug("[Cache] LocationGroup has changed -> evicting cache");
                janitor.evictLocationGroupCache();
            }
            case "lg.event.deleted" -> {
                LOGGER.debug("[Cache] LocationGroup was deleted -> evicting cache");
                janitor.evictLocationGroupCache();
            }
            case "lg.event.state-changed" -> {
                LOGGER.debug("[Cache] LocationGroup has changed state -> evicting cache");
                janitor.evictLocationGroupCache();
            }
            case "lg.event.changed-operation-mode" -> {
                LOGGER.debug("[Cache] LocationGroup Operation Mode has changed -> evicting cache");
                janitor.evictLocationGroupCache();
            }
            default -> LOGGER.warn("Eventtype [{}] currently not supported", routingKey);
        }
    }

    /** Listens on Location messages. */
    @Measured
    @RabbitListener(queues = "${owms.events.common.loc.queue-name}")
    public void onLocationEvent(@Payload LocationMO msg, @Header("amqp_receivedRoutingKey") String routingKey) {
        if (msg == null) {
            LOGGER.warn("Got an event for a Location with empty body and routingKey [{}]", routingKey);
            return;
        }
        switch (routingKey) {
            case "loc.event.changed" -> {
                LOGGER.debug("[Cache] Location has changed -> evicting cache");
                janitor.evictLocationCache();
            }
            case "loc.event.deleted" -> {
                LOGGER.debug("[Cache] Location deleted -> evicting cache");
                janitor.evictLocationCache();
            }
            case "loc.event.state-changed" -> {
                LOGGER.debug("[Cache] Location State has changed state -> evicting cache");
                janitor.evictLocationCache();
            }
            default -> LOGGER.warn("Eventtype [{}] currently not supported", routingKey);
        }
    }
}
