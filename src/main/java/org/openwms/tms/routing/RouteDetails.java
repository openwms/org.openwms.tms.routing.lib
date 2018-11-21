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

import org.ameba.integration.jpa.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;

/**
 * A RouteDetails.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Entity
@Table(name = "RSRV_ROUTE_DETAILS", uniqueConstraints = {@UniqueConstraint(name = "UC_ROUTE_POS", columnNames = {RouteDetails.COLUMN_ROUTE_ID, RouteDetails.COLUMN_POS})})
class RouteDetails extends BaseEntity implements Serializable {

    public static final String COLUMN_ROUTE_ID = "C_ROUTE_ID";
    @Column(name = COLUMN_ROUTE_ID)
    private Route route;

    public static final String COLUMN_POS = "C_POS";
    @Column(name = COLUMN_POS)
    private int pos;

    @Column(name = "C_SOURCE_LOCATION")
    private String source;

    @Column(name = "C_NEXT_LOCATION")
    private String next;

    /*~ ----------------------------- constructors ------------------- */

    /** Dear JPA ... */
    protected RouteDetails() {
    }

    public RouteDetails(Route route, int pos) {
        this.route = route;
        this.pos = pos;
    }

    /*~ ----------------------------- accessors ------------------- */
    public Route getRoute() {
        return route;
    }

    public int getPos() {
        return pos;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }
}
