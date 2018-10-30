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

import org.ameba.exception.NotFoundException;
import org.openwms.common.LocationGroupVO;
import org.openwms.common.location.api.LocationApi;
import org.openwms.common.location.api.LocationGroupApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.String.format;

/**
 * A RouteSearchAlgorithmImpl.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Component
class RouteSearchAlgorithmImpl implements RouteSearchAlgorithm {

    private static final Logger LOGGER = LoggerFactory.getLogger(RouteSearchAlgorithmImpl.class);
    private final RouteRepository repository;
    private final LocationGroupApi locationGroupApi;
    private final LocationApi locationApi;

    private Map<String, String> mappingLocationToParent = new ConcurrentHashMap<>();
    private Collection<LocationGroupVO> allLocationGroups;

    RouteSearchAlgorithmImpl(RouteRepository repository, LocationGroupApi locationGroupApi, LocationApi locationApi) {
        this.repository = repository;
        this.locationGroupApi = locationGroupApi;
        this.locationApi = locationApi;
    }

    @PostConstruct
    void onPOstConstruct() {
        allLocationGroups = locationGroupApi.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Route findBy(String sourceLocation, String targetLocation, String targetLocationGroup) {
        Assert.hasText(sourceLocation, "The sourceLocation must be given when searching for a Route");
        boolean targetLocExists = StringUtils.hasText(targetLocation);
        boolean targetLgExists = StringUtils.hasText(targetLocationGroup);

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

            // No direct match
            if (targetLgExists) {
                result = repository.findBySourceLocation_LocationIdAndTargetLocationGroupNameAndEnabled(sourceLocation, targetLocationGroup, true);
                if (result.isPresent()) {
                    LOGGER.debug("Route with sourceLocation and targetLocation direct match found: {}", result.get());
                    return result.get();
                }

                // (2) We don't find a Route for either 2 Locations nor source Location and target LocationGroup -> climb up the target
                result = findInHierarchy(sourceLocation, targetLocation, allLocationGroups);
                if (result.isPresent()) {
                    LOGGER.debug("Route found 1: {}", result.get());
                    return result.get();
                }

                // strange but we haven't found something above the target Location, try LocationGroup at last...
                result = findInTargetGroupHierarchy(sourceLocation, targetLocationGroup, allLocationGroups);
                if (result.isPresent()) {
                    LOGGER.debug("Route found 2: {}", result.get());
                    return result.get();
                }

                throw new NoRouteException(format("No route found for TransportOrder with sourceLocation [%s], targetLocation [%s] and targetLocationGroup [%s]", sourceLocation, targetLocation, targetLocationGroup));
            } else {

                // No targetLocationGroup, search for the targetLocation upwards:
                result = findInHierarchy(sourceLocation, targetLocation, allLocationGroups);
                if (result.isPresent()) {
                    LOGGER.debug("Route found 3: {}", result.get());
                    return result.get();
                }
                throw new NoRouteException(format("No route found for TransportOrder with sourceLocation [%s], targetLocation [%s] and targetLocationGroup [%s]", sourceLocation, targetLocation, targetLocationGroup));
            }
        }
        Assert.hasText(targetLocationGroup, "The targetLocation did not find a match, hence a TargetLocationGroup is required");

        result = findInTargetGroupHierarchy(sourceLocation, targetLocationGroup, allLocationGroups);
        if (result.isPresent()) {
            return result.get();
        }

        result = findInTargetGroupHierarchy(sourceLocation, targetLocationGroup, allLocationGroups);
        if (result.isPresent()) {
            return result.get();
        }

        throw new NoRouteException(format("No route found for TransportOrder with sourceLocation [%s], targetLocation [%s] and targetLocationGroup [%s]", sourceLocation, targetLocation, targetLocationGroup));
    }

    private Optional<Route> findInTargetGroupHierarchy(String sourceLocation, String targetLocationGroup, Collection<LocationGroupVO> allLocationGroups) {
        // climb up group
        Optional<Route> route = repository.findBySourceLocation_LocationIdAndTargetLocationGroupNameAndEnabled(sourceLocation, targetLocationGroup, true);
        if (!route.isPresent()) {
            LocationGroupVO current = allLocationGroups.stream().filter(lg -> lg.getName().equals(targetLocationGroup)).findFirst().orElseThrow(() -> new NotFoundException(format("The LocationGroup with name [%s] does not exist", targetLocationGroup)));
            if (current.getParent() == null || current.getParent().isEmpty()) {
                return Optional.empty();
            }
            route = findInTargetGroupHierarchy(sourceLocation, current.getParent(), allLocationGroups);
        } return route;
    }

    private Optional<Route> findInSourceGroupHierarchy(String sourceLocationGroup, String targetLocationGroup, Collection<LocationGroupVO> allLocationGroups) {
        // climb up group
        Optional<Route> route = repository.findBySourceLocationGroupNameAndTargetLocationGroupNameAndEnabled(sourceLocationGroup, targetLocationGroup, true);
        if (!route.isPresent()) {
            LocationGroupVO currentSource = allLocationGroups.stream().filter(lg -> lg.getName().equals(sourceLocationGroup)).findFirst().orElseThrow(() -> new NotFoundException(format("The LocationGroup with name [%s] does not exist", sourceLocationGroup)));
            if (currentSource.getParent() == null || currentSource.getParent().isEmpty()) {
                return Optional.empty();
            }
            route = findInSourceGroupHierarchy(currentSource.getParent(), targetLocationGroup, allLocationGroups);
            if (route.isPresent()) {
                return route;
            }

            LocationGroupVO currentTarget = allLocationGroups.stream().filter(lg -> lg.getName().equals(targetLocationGroup)).findFirst().orElseThrow(() -> new NotFoundException(format("The LocationGroup with name [%s] does not exist", targetLocationGroup)));
            if (currentTarget.getParent() == null || currentTarget.getParent().isEmpty()) {
                return Optional.empty();
            }
            route = findInSourceGroupHierarchy(sourceLocationGroup, currentTarget.getParent(), allLocationGroups);
        } return route;
    }

    private Optional<Route> findInHierarchy(String sourceLocation, String targetLocation, Collection<LocationGroupVO> allLocationGroups) {
        Optional<Route> route = repository.findBySourceLocation_LocationIdAndTargetLocation_LocationIdAndEnabled(sourceLocation, targetLocation, true);
        if (route.isPresent()) {
            return route;
        }
        // first step is target then source...
        String targetLocationGroupName = mappingLocationToParent.computeIfAbsent(targetLocation, (k) -> locationApi.findLocationByCoordinate(k).orElseThrow(()->new NotFoundException(format("TargetLocation with locationId %s does not exist", targetLocation))).getLocationGroupName());
        route = findInTargetGroupHierarchy(sourceLocation, targetLocationGroupName, allLocationGroups);
        if (route.isPresent()) {
            return route;
        }

        final String sourceLocationGroupName = mappingLocationToParent.computeIfAbsent(sourceLocation, (k) -> locationApi.findLocationByCoordinate(k).orElseThrow(()->new NotFoundException(format("SourceLocation with locationId %s does not exist", sourceLocation))).getLocationGroupName());
        route = findInSourceGroupHierarchy(sourceLocationGroupName, targetLocationGroupName, allLocationGroups);
        if (route.isPresent()) {
            return route;
        }

        return Optional.empty();
    }
}
