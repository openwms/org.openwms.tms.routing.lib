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

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * A RouteRepository.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
interface RouteRepository extends JpaRepository<Route, Long> {

    Optional<Route> findByPKey(String pKey);

    Optional<Route> findBySourceLocation_LocationIdAndEnabled(String sourceLocation, boolean enabled);

    Optional<Route> findBySourceLocation_LocationIdAndTargetLocation_LocationIdAndEnabled(String sourceLocation, String targetLocation, boolean enabled);

    Optional<Route> findBySourceLocation_LocationIdAndTargetLocationGroupNameAndEnabled(String sourceLocation, String targetLocationGroupName, boolean enabled);

    Optional<Route> findBySourceLocationGroupNameAndTargetLocationGroupNameAndEnabled(String sourceLocationGroupName, String targetLocationGroupName, boolean enabled);
}
