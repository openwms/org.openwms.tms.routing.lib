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
package org.openwms.common.comm.osip.sysu;

import org.ameba.exception.NotFoundException;
import org.openwms.common.comm.osip.OSIPComponent;
import org.openwms.common.location.api.LocationGroupApi;
import org.openwms.common.location.api.LocationGroupVO;
import org.openwms.core.process.execution.spi.ProgramExecutor;
import org.openwms.tms.routing.InputContext;
import org.openwms.tms.routing.Matrix;
import org.openwms.tms.routing.RouteImpl;

import static java.lang.String.format;

/**
 * A SystemUpdateHandler.
 *
 * @author Heiko Scherrer
 */
@OSIPComponent
class SystemUpdateMessageHandler {

    private final LocationGroupApi locationGroupApi;
    private final Matrix matrix;
    private final ProgramExecutor executor;
    private final InputContext in;

    SystemUpdateMessageHandler(LocationGroupApi locationGroupApi, Matrix matrix, ProgramExecutor executor, InputContext in) {
        this.locationGroupApi = locationGroupApi;
        this.matrix = matrix;
        this.executor = executor;
        this.in = in;
    }

    void handle(SystemUpdateVO msg) {
        in.clear();
        LocationGroupVO locationGroupName = locationGroupApi.findByName(msg.getLocationGroupName()).orElseThrow(()->new NotFoundException(format("LocationGroup with name [%s] does not exists!", msg.getLocationGroupName())));
        in.getMsg().putAll(msg.getAll());
        executor.execute(matrix.findBy(msg.getType(), RouteImpl.NO_ROUTE, null, locationGroupName).getProgramKey(), in.getMsg());
    }
}
