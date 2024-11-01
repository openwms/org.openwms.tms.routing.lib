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
package org.openwms.tms.routing;

import org.ameba.integration.jpa.ApplicationEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * A LocationEO is an Entity Object (EO) that represents a Location in a simple way.
 *
 * @author Heiko Scherrer
 */
@Entity
@Table(name = "TMS_RSRV_LOCATION")
public class LocationEO extends ApplicationEntity {

    /** The foreign persistent key of the Location. */
    @Column(name = "C_FOREIGN_PID")
    private String foreignPKey;

    /** The unique business key. */
    @NotNull
    @Column(name = "C_LOCATION_ID")
    private String locationId;

    /** The name of the LocationGroup this Location belongs to. */
    @Column(name = "C_LOCATION_GROUP_NAME")
    private String locationGroupName;

    /** Flag to determine if this Location is marked for deletion and cannot be used anymore. */
    @Column(name = "C_MARKED_DELETION")
    private boolean markForDeletion;

    protected LocationEO() {}

    public String getForeignPKey() {
        return foreignPKey;
    }

    public void setForeignPKey(String foreignPKey) {
        this.foreignPKey = foreignPKey;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getLocationGroupName() {
        return locationGroupName;
    }

    public void setLocationGroupName(String locationGroupName) {
        this.locationGroupName = locationGroupName;
    }

    public boolean isMarkForDeletion() {
        return markForDeletion;
    }

    public void setMarkForDeletion(boolean markForDeletion) {
        this.markForDeletion = markForDeletion;
    }

    @Override
    public String toString() {
        return locationId;
    }

    /**
     * {@inheritDoc}
     *
     * All fields.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LocationEO that)) return false;
        if (!super.equals(o)) return false;
        return markForDeletion == that.markForDeletion && Objects.equals(foreignPKey, that.foreignPKey) && Objects.equals(locationId, that.locationId) && Objects.equals(locationGroupName, that.locationGroupName);
    }

    /**
     * {@inheritDoc}
     *
     * All fields.
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), foreignPKey, locationId, locationGroupName, markForDeletion);
    }
}
