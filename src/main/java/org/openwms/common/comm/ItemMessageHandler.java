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
package org.openwms.common.comm;

import org.ameba.annotation.TxService;
import org.ameba.exception.NotFoundException;
import org.openwms.common.location.api.LocationApi;
import org.openwms.common.location.api.LocationGroupApi;
import org.openwms.core.process.execution.spi.ProgramExecutor;
import org.openwms.tms.api.TransportOrderApi;
import org.openwms.tms.routing.InputContext;
import org.openwms.tms.routing.Matrix;
import org.openwms.tms.routing.RouteImpl;
import org.openwms.tms.routing.RouteSearchAlgorithm;
import org.springframework.util.Assert;

import static java.lang.String.format;

/**
 * A ItemMessageHandler.
 *
 * @author Heiko Scherrer
 */
@TxService
public class ItemMessageHandler {

    private final Matrix matrix;
    private final ProgramExecutor executor;
    private final InputContext in;
    private final RouteSearchAlgorithm routeSearch;
    private final LocationApi locationApi;
    private final LocationGroupApi locationGroupApi;
    private final TransportOrderApi transportOrderApi;

    ItemMessageHandler(Matrix matrix, ProgramExecutor executor, InputContext in, RouteSearchAlgorithm routeSearch, LocationApi locationApi, LocationGroupApi locationGroupApi, TransportOrderApi transportOrderApi) {
        this.matrix = matrix;
        this.executor = executor;
        this.in = in;
        this.routeSearch = routeSearch;
        this.locationApi = locationApi;
        this.locationGroupApi = locationGroupApi;
        this.transportOrderApi = transportOrderApi;
    }

    public void handle(ItemMessage msg) {
        Assert.notNull(msg, "handle called with null message");
        Assert.notNull(msg.getHeader(), "handle called without message header");
        in.clear();
        in.putAll(msg.getAll());
        in.putAll(msg.getHeader().getAll());

        var actualLocation = locationApi
                .findById(msg.getActualLocation())
                .orElseThrow(() -> new NotFoundException(format("Location with coordinate [%s] does not exist", msg.getActualLocation())));

        var locationGroup =
                msg.hasLocationGroupName() ?
                        locationGroupApi.findByName(msg.getLocationGroupName())
                                .orElseThrow(() -> new NotFoundException(format("LocationGroup with name [%s] does not exist", msg.getLocationGroupName()))) :
                        locationGroupApi.findByName(actualLocation.getLocationGroupName())
                                .orElseThrow(() -> new NotFoundException(format("LocationGroup with name [%s] does not exist", actualLocation.getLocationGroupName())));

        var route = RouteImpl.NO_ROUTE;
        var transportOrders = transportOrderApi.findBy(msg.getBarcode(), "STARTED");
        if (transportOrders != null && !transportOrders.isEmpty()) {
            var transportOrder = transportOrders.get(0);
            in.putAll(transportOrder.getAll());
            try {
                route = routeSearch.findBy(transportOrder.getSourceLocation(), transportOrder.getTargetLocation(), transportOrder.getTargetLocationGroup());
            } catch (NoRouteException nre) {
                // perfectly fine here
            }
        }
        in.put("route", route);
        var action = matrix.findBy(msg.getType(), route, actualLocation, locationGroup);
        in.putAll(action.getFlexVariables());
        executor.execute(action.getProgramKey(), in.getMsg());
    }
}
