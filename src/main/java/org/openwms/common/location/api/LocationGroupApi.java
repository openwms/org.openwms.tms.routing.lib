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
package org.openwms.common.location.api;

import org.openwms.common.LocationGroupVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;

/**
 * A LocationGroupApi.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@FeignClient(name = "common-service", qualifier = "locationGroupApi")
public interface LocationGroupApi {

    /**
     * This method is used to update the state of a LocationGroup with an errorCode String, usually coming from a SYSU telegram.
     *
     * @param locationGroupName The name of the LocationGroup
     * @param errorCode The error code as String
     */
    @PatchMapping(value = "/v1/locationgroups", params = {"name"})
    void updateState(@RequestParam(name = "name") String locationGroupName, @RequestBody ErrorCodeVO errorCode);

    /**
     * Find and return all existing {@code LocationGroup} representations.
     *
     * @return Never {@literal null}
     */
    @GetMapping("/v1/locationgroups")
    Collection<LocationGroupVO> findAll();
}
