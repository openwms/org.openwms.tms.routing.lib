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
    public Route findBy(String sourceLocation, String targetLocation, String targetLocationGroup) {
        Assert.hasText(sourceLocation, "The sourceLocation must be given when searching for a Route");
        final boolean targetLocExists = StringUtils.hasText(targetLocation);

        Optional<Route> result;
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

    private Optional<Route> findInGroup(String sourceLocation, String targetLocationGroup) {
        return repository.findBySourceLocation_LocationIdAndTargetLocationGroupNameAndEnabled(sourceLocation, targetLocationGroup, true);
    }
}
