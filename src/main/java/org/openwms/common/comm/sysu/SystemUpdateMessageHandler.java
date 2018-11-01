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
package org.openwms.common.comm.sysu;

import org.ameba.exception.NotFoundException;
import org.openwms.common.LocationGroupVO;
import org.openwms.common.location.api.LocationGroupApi;
import org.openwms.tms.routing.InputContext;
import org.openwms.tms.routing.Matrix;
import org.openwms.tms.routing.ProgramExecutor;
import org.openwms.tms.routing.Route;
import org.springframework.stereotype.Component;

import static java.lang.String.format;

/**
 * A SystemUpdateHandler.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Component
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

    void handleSYSU(SystemUpdateVO sysu) {
        LocationGroupVO locationGroupName = locationGroupApi.findByName(sysu.getLocationGroupName()).orElseThrow(()-> new NotFoundException(format("No LocationGroup with name [%s] exists, can't process SYSU", sysu.getLocationGroupName())));
        in.getMsg().putAll(sysu.getAll());
        executor.execute(matrix.findBy("SYSU", Route.NO_ROUTE, null, locationGroupName), in.getMsg());
    }
}
