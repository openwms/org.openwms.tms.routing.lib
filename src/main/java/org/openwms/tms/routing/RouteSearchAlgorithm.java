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
package org.openwms.tms.routing;

/**
 * A RouteSearchAlgorithm.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
public interface RouteSearchAlgorithm {

    /**
     * Find and return a Route from the given {@code sourceLocation} to either the
     * {@code targetLocation} or to the {@code targetLocationGroup}.
     *
     * @param sourceLocation The start point of the TransportOrder
     * @param targetLocation The target of the TransportOrder as Location
     * @param targetLocationGroup The target of the TransportOrder as LocationGroup
     * @return A Route, never {@literal null}
     */
    Route findBy(String sourceLocation, String targetLocation, String targetLocationGroup);
}
