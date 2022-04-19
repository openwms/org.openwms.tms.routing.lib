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
package org.openwms.tms.routing.routes;

import org.ameba.annotation.Measured;
import org.openwms.common.comm.NoRouteException;
import org.openwms.core.SpringProfiles;
import org.openwms.tms.api.MessageVO;
import org.openwms.tms.api.TransportOrderApi;
import org.openwms.tms.api.TransportOrderVO;
import org.openwms.tms.api.requests.state.StateChangeRequest;
import org.openwms.tms.api.requests.state.StateChangeResponse;
import org.openwms.tms.routing.Route;
import org.openwms.tms.routing.RouteSearchAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import static java.lang.String.format;

/**
 * A ExternalStartListener.
 *
 * @author Heiko Scherrer
 */
@Profile({SpringProfiles.ASYNCHRONOUS_PROFILE})
@Component
class ExternalStartListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExternalStartListener.class);
    public static final String RESPONSE_STATE_CHANGE = "response.state.change";
    private final TransportOrderApi transportOrderApi;
    private final RouteSearchAlgorithm routeSearch;
    private final AmqpTemplate amqpTemplate;
    private final String exchangeName;

    ExternalStartListener(TransportOrderApi transportOrderApi, RouteSearchAlgorithm routeSearch, AmqpTemplate amqpTemplate,
            @Value("${owms.requests.routing.to.exchange-name}") String exchangeName) {
        this.transportOrderApi = transportOrderApi;
        this.routeSearch = routeSearch;
        this.amqpTemplate = amqpTemplate;
        this.exchangeName = exchangeName;
    }

    @Measured
    @RabbitListener(queues = "${owms.requests.routing.to.queue-name}")
    public void onRequest(StateChangeRequest request) {
        if ("STARTED".equals(request.getRequestedState())) {
            try {
                TransportOrderVO vo = transportOrderApi.findByPKey(request.getTransportOrderPkey());
                Route route = routeSearch.findBy(vo.getSourceLocation(), vo.getTargetLocation(), vo.getTargetLocationGroup());
                LOGGER.debug("TransportOrder to start has a Route [{}] ", route.getRouteId());
                amqpTemplate.convertAndSend(exchangeName, RESPONSE_STATE_CHANGE, new StateChangeResponse(request, "STARTED", null));
            } catch (NoRouteException ex) {

                // not that bad we know no Route exists...
                String msg = format("A TransportOrder was requested to start that has no Route. PKey: [%s]", request.getTransportOrderPkey());
                LOGGER.warn(msg, msg);
                amqpTemplate.convertAndSend(exchangeName, RESPONSE_STATE_CHANGE, new StateChangeResponse(request, "XX",
                        MessageVO.newBuilder()
                                .message(msg)
                                .build()
                ));
            } catch (Exception ex) {
                String msg = format("Generic exception. Route for TransportOrder [%s] could not be determined", request.getTransportOrderPkey());
                LOGGER.warn(msg, msg);
                amqpTemplate.convertAndSend(exchangeName, RESPONSE_STATE_CHANGE, new StateChangeResponse(request, "XX",
                        MessageVO.newBuilder()
                                .message(msg)
                                .build()
                ));
            }
        }
    }
}
