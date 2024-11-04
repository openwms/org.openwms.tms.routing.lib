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
package org.openwms.tms.routing.routes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import org.ameba.integration.jpa.BaseEntity;
import org.openwms.tms.routing.Route;
import org.openwms.tms.routing.RouteImpl;

import java.io.Serializable;

/**
 * A RouteDetails.
 *
 * @author Heiko Scherrer
 */
@Entity
@Table(name = "TMS_RSRV_ROUTE_DETAILS", uniqueConstraints = {@UniqueConstraint(name = "UC_ROUTE_POS", columnNames = {RouteDetails.COLUMN_ROUTE_ID, RouteDetails.COLUMN_POS})})
public class RouteDetails extends BaseEntity implements Serializable {

    public static final String COLUMN_ROUTE_ID = "C_ROUTE_ID";
    /** The Route this details entry belongs to.*/
    @ManyToOne
    @JoinColumn(name = COLUMN_ROUTE_ID)
    private RouteImpl route;

    public static final String COLUMN_POS = "C_POS";
    /** A sequence number unique within the referenced Route. */
    @Column(name = COLUMN_POS)
    private int pos;

    /** The source Location where the path starts from. */
    @Column(name = "C_SOURCE_LOCATION")
    private String source;

    /** The next Location is the next hop where to move to. */
    @Column(name = "C_NEXT_LOCATION")
    private String next;

    /*~ ----------------------------- constructors ------------------- */

    /** Dear JPA ... */
    protected RouteDetails() {
    }

    public RouteDetails(RouteImpl route, int pos) {
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
