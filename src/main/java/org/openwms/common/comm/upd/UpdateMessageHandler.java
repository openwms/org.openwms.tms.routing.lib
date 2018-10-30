/*
 * openwms.org, the Open Warehouse Management System.
 * Copyright (C) 2018 Heiko Scherrer
 *
 * This file is part of openwms.org.
 *
 * openwms.org is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * openwms.org is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this software. If not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.openwms.common.comm.upd;

import org.ameba.annotation.TxService;
import org.ameba.exception.NotFoundException;
import org.openwms.common.FetchLocationByCoord;
import org.openwms.common.LocationGroupVO;
import org.openwms.common.LocationVO;
import org.openwms.common.location.api.LocationApi;
import org.openwms.common.location.api.LocationGroupApi;
import org.openwms.tms.FetchStartedTransportOrder;
import org.openwms.tms.TransportOrder;
import org.openwms.tms.routing.InputContext;
import org.openwms.tms.routing.Matrix;
import org.openwms.tms.routing.NoRouteException;
import org.openwms.tms.routing.ProgramExecutor;
import org.openwms.tms.routing.Route;
import org.openwms.tms.routing.RouteSearchAlgorithm;
import org.springframework.util.Assert;

/**
 * A UpdateMessageHandler.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@TxService
class UpdateMessageHandler {

    private final FetchLocationByCoord fetchLocationByCoord;
    private final FetchStartedTransportOrder fetchTransportOrder;
    private final Matrix matrix;
    private final ProgramExecutor executor;
    private final InputContext in;
    private final RouteSearchAlgorithm routeSearch;
    private final LocationApi locationApi;
    private final LocationGroupApi locationGroupApi;

    UpdateMessageHandler(FetchLocationByCoord fetchLocationByCoord, FetchStartedTransportOrder fetchTransportOrder, Matrix matrix, ProgramExecutor executor, InputContext in, RouteSearchAlgorithm routeSearch, LocationApi locationApi, LocationGroupApi locationGroupApi) {
        this.fetchLocationByCoord = fetchLocationByCoord;
        this.fetchTransportOrder = fetchTransportOrder;
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
        Route route;
        try {
            TransportOrder transportOrder = fetchTransportOrder.apply(msg.getBarcode());
            in.putAll(transportOrder.getAll());
            route = routeSearch.findBy(transportOrder.getSourceLocation(), transportOrder.getTargetLocation(), transportOrder.getTargetLocationGroup());
        } catch (NoRouteException nfe) {
            route = Route.NO_ROUTE;
        }
        executor.execute(matrix.findBy("UPD_", route, location, locationGroup), in.getMsg());
    }

}
