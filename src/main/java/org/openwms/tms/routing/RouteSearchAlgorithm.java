/*
 * Copyright 2005-2020 the original author or authors.
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

/**
 * A RouteSearchAlgorithm.
 *
 * @author Heiko Scherrer
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
