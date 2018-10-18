/*
 * openwms.org, the Open Warehouse Management System.
 * Copyright (C) 2014 Heiko Scherrer
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

import org.openwms.common.FetchLocationGroupByName;
import org.openwms.common.LocationGroupVO;
import org.openwms.common.location.api.ErrorCodeVO;
import org.openwms.tms.FetchStartedTransportOrder;
import org.openwms.tms.routing.InputContext;
import org.openwms.tms.routing.Matrix;
import org.openwms.tms.routing.ProgramExecutor;
import org.openwms.tms.routing.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * A SystemUpdateMessageController.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@RestController
class SystemUpdateMessageController {

    @Autowired
    private FetchLocationGroupByName fetchLocationGroupByName;
    @Autowired
    private FetchStartedTransportOrder fetchTransportOrder;
    @Autowired
    private Matrix matrix;
    @Autowired
    private ProgramExecutor executor;
    @Autowired
    private InputContext in;

    @PostMapping("/sysu")
    public void handleSYSU(@RequestBody SystemUpdateVO sysu) {

        LocationGroupVO locationGroup = fetchLocationGroupByName.apply(sysu.locationGroupName);
        Map<String, Object> runtimeVariables = new HashMap<>(3);
        in.getMsg().put("locationGroupName", sysu.locationGroupName);
        ErrorCodeVO vo = new ErrorCodeVO();
        vo.setErrorCode(sysu.errorCode);
        in.getMsg().put("errorCode", vo);
        in.getMsg().put("created", sysu.created);

        runtimeVariables.put("locationGroupName", sysu.locationGroupName);
        runtimeVariables.put("errorCode", sysu.errorCode);
        runtimeVariables.put("created", sysu.created);
        executor.execute(matrix.findBy("SYSU", Route.NO_ROUTE, null, locationGroup), runtimeVariables);
    }
}
