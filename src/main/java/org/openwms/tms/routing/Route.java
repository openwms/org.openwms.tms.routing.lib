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
package org.openwms.tms.routing;

import org.ameba.integration.jpa.ApplicationEntity;
import org.openwms.common.LocationEO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Route.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Entity
@Table(name = "RSRV_ROUTE")
public class Route extends ApplicationEntity implements Serializable {

    /** For TransportUnits without active TransportOrder. */
    public static final Route NO_ROUTE = new Route("_NO_ROUTE");
    /** For all TransportOrders with no explicitly defined Route. */
    public static final Route DEF_ROUTE = new Route("_DEFAULT");
    @NotNull
    @Column(name = "C_NAME", unique = true)
    private String routeId;
    @Column(name = "C_DESCRIPTION")
    private String description;
    @ManyToOne
    @JoinColumn(name = "C_SOURCE_LOCATION", referencedColumnName = "C_PK")
    private LocationEO sourceLocation;
    @ManyToOne
    @JoinColumn(name = "C_TARGET_LOCATION", referencedColumnName = "C_PK")
    private LocationEO targetLocation;
    @Column(name = "C_SOURCE_LOC_GROUP_NAME")
    private String sourceLocationGroupName;
    @Column(name = "C_TARGET_LOC_GROUP_NAME")
    private String targetLocationGroupName;
    @Column(name = "C_ENABLED")
    private boolean enabled = true;

    /** Dear JPA ... */
    protected Route() {
    }

    public Route(String routeId) {
        this.routeId = routeId;
    }

    public String getRouteId() {
        return routeId;
    }

    public String getDescription() {
        return description;
    }

    public boolean hasSourceLocation() {
        return sourceLocation != null;
    }

    public LocationEO getSourceLocation() {
        return sourceLocation;
    }

    public void setSourceLocation(LocationEO sourceLocation) {
        this.sourceLocation = sourceLocation;
    }

    public boolean hasTargetLocation() {
        return targetLocation != null;
    }

    public LocationEO getTargetLocation() {
        return targetLocation;
    }

    public void setTargetLocation(LocationEO targetLocation) {
        this.targetLocation = targetLocation;
    }

    public String getSourceLocationGroupName() {
        return sourceLocationGroupName;
    }

    public String getTargetLocationGroupName() {
        return targetLocationGroupName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public static Route of(String routeId) {
        if (routeId == null || routeId.isEmpty())
            return DEF_ROUTE;
        return new Route(routeId);
    }

    @Override
    public String toString() {
        return routeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Route route = (Route) o;
        return Objects.equals(routeId, route.routeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(routeId);
    }
}
