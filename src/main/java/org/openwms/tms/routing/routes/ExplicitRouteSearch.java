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
package org.openwms.tms.routing.routes;

import org.openwms.tms.routing.NoRouteException;
import org.openwms.tms.routing.RouteSearchAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Optional;

import static java.lang.String.format;

/**
 * A ExplicitRouteSearch.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Profile("SIMPLE")
@Component
class ExplicitRouteSearch implements RouteSearchAlgorithm {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExplicitRouteSearch.class);
    private final RouteRepository repository;

    ExplicitRouteSearch(RouteRepository repository) {
        this.repository = repository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RouteImpl findBy(String sourceLocation, String targetLocation, String targetLocationGroup) {
        Assert.hasText(sourceLocation, "The sourceLocation must be given when searching for a Route");
        final boolean targetLocExists = StringUtils.hasText(targetLocation);

        Optional<RouteImpl> result;
        // First try explicit declaration
        if (targetLocExists) {

            // (1) Have both. Search for a match:
            result = repository.findBySourceLocation_LocationIdAndTargetLocation_LocationIdAndEnabled(sourceLocation, targetLocation, true);
            if (result.isPresent()) {
                // Match with locations
                LOGGER.debug("Route in direct location match found: {}", result.get());
                return result.get();
            }
            throw new NoRouteException(format("No route found for TransportOrder with sourceLocation [%s] and targetLocation [%s]", sourceLocation, targetLocation));
        }
        Assert.hasText(targetLocationGroup, "The targetLocation did not find a match, hence a TargetLocationGroup is required");
        return findInGroup(sourceLocation, targetLocationGroup).orElseThrow(() -> new NoRouteException(format("No route found for TransportOrder with sourceLocation [%s] and targetLocationGroup [%s]", sourceLocation, targetLocationGroup)));
    }

    private Optional<RouteImpl> findInGroup(String sourceLocation, String targetLocationGroup) {
        return repository.findBySourceLocation_LocationIdAndTargetLocationGroupNameAndEnabled(sourceLocation, targetLocationGroup, true);
    }
}
