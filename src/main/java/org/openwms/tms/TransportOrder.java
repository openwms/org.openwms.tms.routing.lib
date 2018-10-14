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
package org.openwms.tms;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A TransportOrder.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransportOrder {

    @JsonProperty
    private String id;
    @JsonProperty
    private String transportUnitId;
    @JsonProperty
    private String routeId;
    @JsonProperty
    private String sourceLocation;
    @JsonProperty
    private String targetLocation;
    @JsonProperty
    private String targetLocationGroup;

    public TransportOrder() {

    }

    public TransportOrder(String id, String transportUnitId, String routeId) {
        this.id = id;
        this.transportUnitId = transportUnitId;
        this.routeId = routeId;
    }

    public String getId() {
        return id;
    }

    public String getTransportUnitId() {
        return transportUnitId;
    }

    public String getRouteId() {
        return routeId;
    }

    public String getSourceLocation() {
        return sourceLocation;
    }

    public String getTargetLocation() {
        return targetLocation;
    }

    public String getTargetLocationGroup() {
        return targetLocationGroup;
    }
}
