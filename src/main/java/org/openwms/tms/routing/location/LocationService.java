/*
 * Copyright 2005-2025 the original author or authors.
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
package org.openwms.tms.routing.location;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.openwms.tms.routing.LocationEO;

import java.util.List;
import java.util.Optional;

/**
 * A LocationService.
 *
 * @author Heiko Scherrer
 */
public interface LocationService {

    /**
     * Requests the removal of {@code Location}s identified by the foreign persistent keys.
     *
     * @param foreignPKeys The foreign persistent keys of the {@code Location}s to be deleted
     * @return {@literal true} if deletion of {@code Location}s is allowed
     */
    boolean isRemovalAllowed(@NotNull List<String> foreignPKeys);

    /**
     * Mark {@code Location}s identified by the given keys for removal.
     *
     * @param foreignPKeys The foreign persistent keys of the {@code Location}s to be removed
     */
    void markForRemoval(@NotNull List<String> foreignPKeys);

    /**
     * Deletes a {@code Location} with the given foreign persistent key.
     *
     * @param foreignPKey The foreign persistent key of the {@code Location} instance.
     * @throws IllegalArgumentException If the parameter 'foreignPKey' is blank.
     */
    void delete(@NotBlank String foreignPKey);

    /**
     * Unset a previously set removal marker on the {@code Location} with the given foreign persistent key.
     *
     * @param foreignPKey The foreign persistent key of the {@code Location} instance.
     */
    void rollbackDeletionMark(@NotBlank String foreignPKey);

    /**
     * Find a Location by its given {@code locationId}.
     *
     * @param locationId The ID of the Location must not be blank
     * @return Optional instance of the Location
     */
    Optional<LocationEO> findByLocationId(@NotBlank String locationId);
}
