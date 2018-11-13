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
package org.openwms.tms.api;

import org.openwms.tms.TransportOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * A TransportOrderApi.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@FeignClient(name = "tms-service", qualifier = "transportOrderApi", decode404 = true)
public interface TransportOrderApi {

    @PostMapping(value = "/transportorders")
    @ResponseStatus(HttpStatus.CREATED)
    void createTO(@RequestParam(value = "barcode") String barcode, @RequestParam(value = "target") String target);

    @PostMapping(value = "/transportorders")
    @ResponseStatus(HttpStatus.CREATED)
    void createTO(@RequestParam(value = "barcode") String barcode, @RequestParam(value = "target") String target, @RequestParam(value = "priority", required = false) String priority);

    @PostMapping(value = "/transportorders/{id}", params = {"state"})
    void changeState(@PathVariable(value = "id") String id, @RequestParam(value = "state") String state);

    @GetMapping(value = "/transportorders", params = {"barcode", "state"})
    List<TransportOrder> findBy(@RequestParam(value = "barcode") String barcode, @RequestParam(value = "state") String state);

    @GetMapping(value = "/transportorders", params ={"sourceLocation", "state", "searchTargetLocationGroupNames"})
    TransportOrder getNextInfeed(@RequestParam("sourceLocation") String sourceLocation, @RequestParam("state") String state, @RequestParam("searchTargetLocationGroupNames") String searchTargetLocationGroups);

    @GetMapping(value = "/transportorders", params ={"sourceLocationGroupName", "targetLocationGroupName", "state"})
    TransportOrder getNextInAisle(@RequestParam("sourceLocationGroupName") String sourceLocationGroupName, @RequestParam("targetLocationGroupName") String targetLocationGroupName, @RequestParam("state") String state);

    @GetMapping(value = "/transportorders", params ={"state", "sourceLocationGroupName"})
    TransportOrder getNextOutfeed(@RequestParam("state") String state, @RequestParam("sourceLocationGroupName") String sourceLocationGroupName);
}
