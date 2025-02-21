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
package org.openwms.tms.routing.routes;

import org.openwms.tms.routing.RouteImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * A RouteRepository.
 *
 * @author Heiko Scherrer
 */
public interface RouteRepository extends JpaRepository<RouteImpl, Long> {

    @Query("select count(r) > 0 from RouteImpl r where r.sourceLocation.locationId = :locationId or r.targetLocation.locationId = :locationId")
    boolean existsWithLocation(@Param("locationId") String locationId);

    Optional<RouteImpl> findBypKey(String persistentKey);

    Optional<RouteImpl> findByRouteId(String routeId);

    Optional<RouteImpl> findBySourceLocation_LocationIdAndTargetLocation_LocationIdAndEnabled(String sourceLocation, String targetLocation, boolean enabled);

    Optional<RouteImpl> findBySourceLocation_LocationIdAndTargetLocationGroupNameAndEnabled(String sourceLocation, String targetLocationGroupName, boolean enabled);

    Optional<RouteImpl> findBySourceLocationGroupNameAndTargetLocation_LocationIdAndEnabled(String sourceLocationGroupName, String targetLocation, boolean enabled);

    Optional<RouteImpl> findBySourceLocationGroupNameAndTargetLocationGroupNameAndEnabled(String sourceLocationGroupName, String targetLocationGroupName, boolean enabled);
}
