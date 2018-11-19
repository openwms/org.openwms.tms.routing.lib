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
package org.openwms.common.comm.upd;

import org.ameba.annotation.TxService;
import org.ameba.exception.NotFoundException;
import org.openwms.common.LocationGroupVO;
import org.openwms.common.LocationVO;
import org.openwms.common.location.api.LocationApi;
import org.openwms.common.location.api.LocationGroupApi;
import org.openwms.tms.TransportOrder;
import org.openwms.tms.api.TransportOrderApi;
import org.openwms.tms.routing.InputContext;
import org.openwms.tms.routing.Matrix;
import org.openwms.tms.routing.NoRouteException;
import org.openwms.tms.routing.ProgramExecutor;
import org.openwms.tms.routing.Route;
import org.openwms.tms.routing.RouteSearchAlgorithm;
import org.springframework.util.Assert;

import java.util.List;

/**
 * A UpdateMessageHandler.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@TxService
class UpdateMessageHandler {

    private final TransportOrderApi transportOrderApi;
    private final Matrix matrix;
    private final ProgramExecutor executor;
    private final InputContext in;
    private final RouteSearchAlgorithm routeSearch;
    private final LocationApi locationApi;
    private final LocationGroupApi locationGroupApi;

    UpdateMessageHandler(TransportOrderApi transportOrderApi, Matrix matrix, ProgramExecutor executor, InputContext in, RouteSearchAlgorithm routeSearch, LocationApi locationApi, LocationGroupApi locationGroupApi) {
        this.transportOrderApi = transportOrderApi;
        this.matrix = matrix;
        this.executor = executor;
        this.in = in;
        this.routeSearch = routeSearch;
        this.locationApi = locationApi;
        this.locationGroupApi = locationGroupApi;
    }

    public void handle(UpdateVO msg) {
        Assert.notNull(msg, "handle called with null message");
        Assert.notNull(msg.getHeader(), "handle called without message header");
        in.putAll(msg.getAll());
        in.putAll(msg.getHeader().getAll());

        LocationVO location = locationApi.findLocationByCoordinate(msg.getActualLocation()).orElseThrow(NotFoundException::new);
        LocationGroupVO locationGroup = locationGroupApi.findByName(location.getLocationGroupName()).orElseThrow(NotFoundException::new);
        Route route = Route.NO_ROUTE;
        List<TransportOrder> transportOrders = transportOrderApi.findBy(msg.getBarcode(), "STARTED");
        if (transportOrders != null && !transportOrders.isEmpty()) {
            TransportOrder transportOrder = transportOrders.get(0);
            in.putAll(transportOrder.getAll());
            try {
                route = routeSearch.findBy(transportOrder.getSourceLocation(), transportOrder.getTargetLocation(), transportOrder.getTargetLocationGroup());
            } catch (NoRouteException nfe) {
                // perfectly fine here
            }
        }
        executor.execute(matrix.findBy("UPD_", route, location, locationGroup), in.getMsg());
    }

}
