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
package org.openwms.common.comm.osip.locu;

import org.ameba.exception.NotFoundException;
import org.openwms.common.comm.osip.OSIPComponent;
import org.openwms.common.location.api.LocationApi;
import org.openwms.common.location.api.LocationGroupApi;
import org.openwms.common.location.api.LocationGroupVO;
import org.openwms.common.location.api.LocationVO;
import org.openwms.core.process.execution.RouteImpl;
import org.openwms.core.process.execution.spi.ProgramExecutor;
import org.openwms.tms.routing.InputContext;
import org.openwms.tms.routing.Matrix;
import org.springframework.util.Assert;

import java.util.Optional;

import static java.lang.String.format;

/**
 * A LocationUpdateMessageHandler.
 *
 * @author Heiko Scherrer
 */
@OSIPComponent
class LocationUpdateMessageHandler {

    private final LocationGroupApi locationGroupApi;
    private final LocationApi locationApi;
    private final InputContext in;
    private final Matrix matrix;
    private final ProgramExecutor executor;

    LocationUpdateMessageHandler(LocationGroupApi locationGroupApi, LocationApi locationApi, InputContext in, Matrix matrix, ProgramExecutor executor) {
        this.locationGroupApi = locationGroupApi;
        this.locationApi = locationApi;
        this.in = in;
        this.matrix = matrix;
        this.executor = executor;
    }

    void handle(LocationUpdateVO msg) {
        Assert.notNull(msg, "handle called with null message");
        in.clear();
        in.putAll(msg.getAll());
        Optional<LocationGroupVO> locationGroupOpt = locationGroupApi.findByName(msg.getLocationGroupName());
        Optional<LocationVO> locationOpt = locationApi.findById(msg.getLocation());

        if (locationOpt.isEmpty() && locationGroupOpt.isEmpty()) {
            throw new NotFoundException(format("Either the Location [%s] or the LocationGroup [%s] must exists! Can't process LOCU", msg.getLocation(), msg.getLocationGroupName()));
        }

        var action = matrix.findBy(msg.getType(), RouteImpl.NO_ROUTE, locationOpt.orElse(null), locationGroupOpt.orElse(null));
        in.putAll(action.getFlexVariables());
        executor.execute(action.getProgramKey(), in.getMsg());
    }
}
