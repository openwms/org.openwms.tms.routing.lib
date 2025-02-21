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
package org.openwms.tms.routing.app;

import org.ameba.annotation.Measured;
import org.ameba.app.SpringProfiles;
import org.openwms.common.location.api.commands.RevokeLocationRemoveCommand;
import org.openwms.common.location.api.messages.LocationMO;
import org.openwms.tms.routing.location.LocationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * A LocationMessageListener listens on changes on entities managed on foreign services.
 *
 * @author Heiko Scherrer
 */
@Profile(SpringProfiles.ASYNCHRONOUS_PROFILE)
@Component
@RabbitListener(queues = "${owms.events.common.loc.queue-name}")
class LocationMessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocationMessageListener.class);
    private final CacheJanitor janitor;
    private final LocationService locationService;

    LocationMessageListener(CacheJanitor janitor, LocationService locationService) {
        this.janitor = janitor;
        this.locationService = locationService;
    }

    /** Listens on Location messages. */
    @Measured
    @RabbitHandler
    public void onLocationEvent(@Payload LocationMO obj, @Header("amqp_receivedRoutingKey") String routingKey) {
        if (obj == null) {
            LOGGER.warn("Got an event for a Location with empty body and routingKey [{}]", routingKey);
            return;
        }
        switch (routingKey) {
            case "loc.event.created" -> {
                LOGGER.debug("[Cache] Location has been created -> evicting cache");
                janitor.evictLocationCache();
            }
            case "loc.event.changed" -> {
                LOGGER.debug("[Cache] Location has changed -> evicting cache");
                janitor.evictLocationCache();
            }
            case "loc.event.deleted" -> {
                LOGGER.debug("[Cache] Location deleted -> evicting cache");
                locationService.delete(obj.pKey());
                janitor.evictLocationCache();
            }
            case "loc.event.state-changed" -> {
                LOGGER.debug("[Cache] Location State has changed state -> evicting cache");
                janitor.evictLocationCache();
            }
            default -> LOGGER.warn("Eventtype [{}] currently not supported", routingKey);
        }
    }

    /** Listens on Location messages. */
    @Measured
    @RabbitHandler
    public void onLocationEvent(@Payload RevokeLocationRemoveCommand obj, @Header("amqp_receivedRoutingKey") String routingKey) {
        if (obj == null) {
            LOGGER.warn("Got an event for a Location with empty body and routingKey [{}]", routingKey);
            return;
        }
        if (routingKey.equals("loc.event.deletion-rollback")) {
            LOGGER.debug("[Cache] Rollback Location deletion marker -> evicting cache");
            locationService.rollbackDeletionMark(obj.getpKey());
            janitor.evictLocationCache();
        } else {
            LOGGER.warn("Eventtype [{}] currently not supported", routingKey);
        }
    }
}
