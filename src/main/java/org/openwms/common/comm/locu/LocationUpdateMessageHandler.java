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
package org.openwms.common.comm.locu;

import org.ameba.exception.NotFoundException;
import org.openwms.common.comm.ConsiderOSIPCondition;
import org.openwms.common.location.api.LocationApi;
import org.openwms.common.location.api.LocationGroupApi;
import org.openwms.common.location.api.LocationGroupVO;
import org.openwms.common.location.api.LocationVO;
import org.openwms.tms.routing.InputContext;
import org.openwms.tms.routing.Matrix;
import org.openwms.tms.routing.ProgramExecutor;
import org.openwms.tms.routing.routes.RouteImpl;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import static java.lang.String.format;

/**
 * A LocationUpdateMessageHandler.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Component
@Conditional(ConsiderOSIPCondition.class)
class LocationUpdateMessageHandler {

    private final LocationGroupApi locationGroupApi;
    private final LocationApi locationApi;
    private final Matrix matrix;
    private final ProgramExecutor executor;

    LocationUpdateMessageHandler(LocationGroupApi locationGroupApi, LocationApi locationApi, Matrix matrix, ProgramExecutor executor) {
        this.locationGroupApi = locationGroupApi;
        this.locationApi = locationApi;
        this.matrix = matrix;
        this.executor = executor;
    }

    void handle(LocationUpdateVO msg) {
        LocationGroupVO locationGroupName = locationGroupApi.findByName(msg.getLocationGroupName()).orElseThrow(()-> new NotFoundException(format("No LocationGroup with name [%s] exists, can't process LOCU", msg.getLocationGroupName())));
        LocationVO location = locationApi.findLocationByCoordinate(msg.getLocation()).orElseThrow(() -> new NotFoundException(format("No Location with coordinate [%s] exists, can't process LOCU", msg.getLocation())));
        executor.execute(matrix.findBy(msg.getType(), RouteImpl.NO_ROUTE, location, locationGroupName), new InputContext(msg.getAll()).getMsg());
    }
}
