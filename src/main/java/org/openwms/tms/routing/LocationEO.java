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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A LocationEO.
 *
 * @author <a href="mailto:hscherrer@openwms.org">Heiko Scherrer</a>
 */
@Entity
@Table(name = "RSRV_LOCATION")
public class LocationEO extends ApplicationEntity implements Serializable {

    @NotNull
    @Column(name = "C_LOCATION_ID")
    private String locationId;
    @Column(name = "C_LOCATION_GROUP_NAME")
    private String locationGroupName;

    protected LocationEO() {
    }

    public String getLocationId() {
        return locationId;
    }

    public String getLocationGroupName() {
        return locationGroupName;
    }
}
