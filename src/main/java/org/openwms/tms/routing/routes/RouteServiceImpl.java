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

import org.ameba.annotation.TxService;
import org.ameba.exception.NotFoundException;
import org.openwms.common.comm.NoRouteException;
import org.openwms.common.comm.Responder;
import org.openwms.tms.routing.InputContext;
import org.openwms.tms.routing.Route;
import org.openwms.tms.routing.RouteImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static java.lang.String.format;

/**
 * A RouteServiceImpl is a transactional Spring managed bean that operated on {@code Route}s.
 *
 * @author Heiko Scherrer
 */
@TxService("routing")
class RouteServiceImpl implements RouteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RouteServiceImpl.class);
    private final Responder responder;
    private final RouteRepository routeRepository;
    private final RouteDetailsRepository routeDetailsRepository;
    private final InputContext in;

    RouteServiceImpl(
            @Autowired(required = false) Responder responder,
            RouteRepository routeRepository,
            RouteDetailsRepository routeDetailsRepository,
            InputContext in
    ) {
        this.responder = responder;
        this.routeRepository = routeRepository;
        this.routeDetailsRepository = routeDetailsRepository;
        this.in = in;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendToNextLocation() {
        if (responder == null) {
            LOGGER.warn("Please provide a bean instance of Responder. Default implementations are disabled. If property 'owms.osip.enabled' is set to false a custom Responder is expected");
            return;
        }
        Route route = in.get("route", Route.class).orElseThrow(() -> new NoRouteException("No Route information in current context, can't load the next Location from the RouteDetails"));
        String actualLocation = in.get("actualLocation", String.class).orElseThrow(() -> new NoRouteException("No information about the actual Location in the current context, can't load the next Location from the RouteDetails"));
        Optional<RouteDetails> optAsNext = routeDetailsRepository.findByRoute_RouteId_OrderByPos(route.getRouteId())
                .stream()
                .filter(r -> r.getSource().equals(actualLocation))
                .findFirst();
        String asNext;
        if (optAsNext.isPresent()) {
            asNext = optAsNext.get().getNext();
        } else {
            asNext = routeDetailsRepository.findByRoute_RouteId_OrderByPos(RouteImpl.DEF_ROUTE.getRouteId())
                    .stream()
                    .filter(r -> r.getSource().equals(actualLocation))
                    .findFirst()
                    .orElseThrow(() -> new NoRouteException(format("No entry in RouteDetails that matches the sourceLocation [%s] and route [%s]", actualLocation, route.getRouteId())))
                    .getNext();
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Sending to next Location [{}]", asNext);
        }
        responder.sendToLocation(asNext);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeRoute(String routeId) {
        Route route = routeRepository.findByRouteId(routeId).orElseThrow(() -> new NotFoundException(format("Route with routeId [%s] was not found", routeId)));
        in.put("route", route);
        LOGGER.debug("Set Route with routeId [{}] in current call context", routeId);
    }
}
