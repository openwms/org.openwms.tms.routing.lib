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

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * A ActionRepository.
 *
 * @author Heiko Scherrer
 */
public interface ActionRepository extends JpaRepository<Action, Long> {

    Optional<Action> findBypKey(String pKey);

    @Query("select a from Action a where a.actionType = :actionType and a.route.routeId = :routeId and a.locationKey is not null and a.locationKey = :locationKey and a.enabled = true")
    Optional<Action> findByActionTypeAndRouteAndLocationKey(@Param("actionType") String actionType, @Param("routeId") String routeId, @Param("locationKey") String locationKey);

    @Query("select a from Action a where a.actionType = :actionType and a.route.routeId = :routeId and a.locationGroupName is not null and a.locationGroupName = :locationGroupName and a.enabled = true")
    Optional<Action> findByActionTypeAndRouteAndLocationGroupName(@Param("actionType") String actionType, @Param("routeId") String routeId, @Param("locationGroupName") String locationGroupName);

}
