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
package org.openwms.common.comm.req;

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
import java.util.Optional;

/**
 * A RequestMessageHandler.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@TxService
class RequestMessageHandler {

    private final Matrix matrix;
    private final ProgramExecutor executor;
    private final InputContext in;
    private final RouteSearchAlgorithm routeSearch;
    private final LocationApi locationApi;
    private final LocationGroupApi locationGroupApi;
    private final TransportOrderApi transportOrderApi;

    RequestMessageHandler(Matrix matrix, ProgramExecutor executor, InputContext in, RouteSearchAlgorithm routeSearch, LocationApi locationApi, LocationGroupApi locationGroupApi, TransportOrderApi transportOrderApi) {
        this.matrix = matrix;
        this.executor = executor;
        this.in = in;
        this.routeSearch = routeSearch;
        this.locationApi = locationApi;
        this.locationGroupApi = locationGroupApi;
        this.transportOrderApi = transportOrderApi;
    }

    /**
     * Takes the passed message, and hands over to the service.
     */
    public void handleREQ(RequestVO req) {
        Assert.notNull(req, "handleREQ called with null message");
        Assert.notNull(req.getHeader(), "handleREQ called without message header");
        in.putAll(req.getAll());
        in.putAll(req.getHeader().getAll());

        LocationVO location = locationApi.findLocationByCoordinate(req.getActualLocation()).orElse(LocationVO.REQUEST_LOCATION);
        Optional<LocationGroupVO> locationGroup = req.hasLocationGroupName() ? locationGroupApi.findByName(req.getLocationGroupName()) : locationGroupApi.findByName(location.getLocationGroupName());
        if (!locationGroup.isPresent()) {
            throw new NotFoundException("No LocationGroup exists for handling REQ message in routing");
        }
        Route route = Route.NO_ROUTE;
        List<TransportOrder> transportOrders = transportOrderApi.findBy(req.getBarcode(), "STARTED");
        if (transportOrders != null && !transportOrders.isEmpty()) {
            TransportOrder transportOrder = transportOrders.get(0);
            in.putAll(transportOrder.getAll());
            try {
                route = routeSearch.findBy(transportOrder.getSourceLocation(), transportOrder.getTargetLocation(), transportOrder.getTargetLocationGroup());
            } catch (NoRouteException nre) {
                // perfectly fine here
            }
        }
        executor.execute(matrix.findBy("REQ_", route, location, locationGroup.get()), in.getMsg());
    }
}
