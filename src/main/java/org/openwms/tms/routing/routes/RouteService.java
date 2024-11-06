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

import jakarta.validation.constraints.NotBlank;

/**
 * A RouteService operates on {@code Route}s.
 *
 * @author Heiko Scherrer
 */
public interface RouteService {

    /**
     * Checks if a {@code Route} exists that uses a {@code Location} with the given {@code locationId}.
     *
     * @param locationId The location ID to check for.
     * @return {@code true} if a route exists with the given location ID, {@code false} otherwise.
     * @throws IllegalArgumentException if the parameter 'locationId' is blank.
     */
    boolean routeExistsWithLocationId(@NotBlank String locationId);

    /**
     * Send a RES_ telegram to the next location that is defined in the static Route Details.
     */
    void sendToNextLocation();

    /**
     * Changes the determined route due to error etc.
     *
     * @param routeId The route id
     */
    void changeRoute(String routeId);
}
