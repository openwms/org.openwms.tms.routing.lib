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
package org.openwms.tms.routing.routes.impl;

import org.ameba.exception.NotFoundException;
import org.openwms.common.location.LocationPK;
import org.openwms.common.location.api.LocationApi;
import org.openwms.common.location.api.LocationGroupVO;
import org.openwms.tms.routing.Route;
import org.openwms.tms.routing.RouteImpl;
import org.openwms.tms.routing.RouteSearchAlgorithm;
import org.openwms.tms.routing.routes.LocationGroupLoader;
import org.openwms.tms.routing.routes.NoRouteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.String.format;

/**
 * A RouteSearchAlgorithmImpl is the extended and standard version of the {@link RouteSearchAlgorithm}.
 *
 * @author Heiko Scherrer
 */
@Profile("!SIMPLE")
@Component
class RouteSearchAlgorithmImpl implements RouteSearchAlgorithm {

    private static final Logger LOGGER = LoggerFactory.getLogger(RouteSearchAlgorithmImpl.class);
    public static final String MSG = "No route found for TransportOrder with sourceLocation [%s], targetLocation [%s] and targetLocationGroup [%s]";
    private final RouteRepository repository;
    private final LocationApi locationApi;
    private final LocationGroupLoader locationGroupLoader;

    private final Map<String, String> mappingLocationToParent = new ConcurrentHashMap<>();
    private Collection<LocationGroupVO> allLocationGroups;

    RouteSearchAlgorithmImpl(RouteRepository repository, LocationApi locationApi, LocationGroupLoader locationGroupLoader) {
        this.repository = repository;
        this.locationApi = locationApi;
        this.locationGroupLoader = locationGroupLoader;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Cacheable("routes")
    public Route findBy(String sourceLocation, String targetLocation, String targetLocationGroup) {
        if (allLocationGroups == null) {
            allLocationGroups = locationGroupLoader.loadLocGroups();
        }
        Assert.hasText(sourceLocation, "The sourceLocation must be given when searching for a Route");
        var targetLocExists = StringUtils.hasText(targetLocation);
        var targetLgExists = StringUtils.hasText(targetLocationGroup);

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

            // No direct match
            if (targetLgExists) {
                result = repository.findBySourceLocation_LocationIdAndTargetLocationGroupNameAndEnabled(sourceLocation, targetLocationGroup, true);
                if (result.isPresent()) {
                    LOGGER.debug("Route with sourceLocation and targetLocation direct match found: {}", result.get());
                    return result.get();
                }

                // (2) We don't find a Route for either 2 Locations nor source Location and target LocationGroup -> climb up the target
                result = findInHierarchy(sourceLocation, targetLocation, targetLocationGroup, allLocationGroups);
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

                throw new NoRouteException(format(MSG, sourceLocation, targetLocation, targetLocationGroup));
            } else {

                // No targetLocationGroup, search for the targetLocation upwards:

                final String targetLocationLocationGroupName = mappingLocationToParent.computeIfAbsent(targetLocation, k -> locationApi.findById(k).orElseThrow(()->new NotFoundException(format("TargetLocation with coordinate [%s] does not exist", targetLocation))).getLocationGroupName());
                result = findInTargetGroupHierarchy(sourceLocation, targetLocationLocationGroupName, allLocationGroups);
                if (result.isPresent()) {
                    LOGGER.debug("Route found 3: {}", result.get());
                    return result.get();
                }

                final String sourceLocationGroupName = mappingLocationToParent.computeIfAbsent(sourceLocation, k -> locationApi.findById(k).orElseThrow(()->new NotFoundException(format("SourceLocation with locationId [%s] does not exist", sourceLocation))).getLocationGroupName());
                result = repository.findBySourceLocationGroupNameAndTargetLocation_LocationIdAndEnabled(sourceLocationGroupName, targetLocation, true);
                if (result.isPresent()) {
                    LOGGER.debug("(1) Route found SourceLG -> TargetLoc: {}", result.get());
                    return result.get();
                }
                result = findInSourceGroupHierarchyWithTargetLocation(sourceLocationGroupName, targetLocation, targetLocationGroup, allLocationGroups);
                if (result.isPresent()) {
                    LOGGER.debug("(2) Route found SourceLG -> TargetLoc: {}", result.get());
                    return result.get();
                }
                throw new NoRouteException(format(MSG, sourceLocation, targetLocation, targetLocationGroup));
            }
        }
        Assert.hasText(targetLocationGroup, "The targetLocation did not find a match, hence a TargetLocationGroup is required");

        result = findInTargetGroupHierarchy(sourceLocation, targetLocationGroup, allLocationGroups);
        if (result.isPresent()) {
            return result.get();
        }

        result = findInHierarchy2(sourceLocation, targetLocationGroup, allLocationGroups);
        if (result.isPresent()) {
            return result.get();
        }

        throw new NoRouteException(format(MSG, sourceLocation, targetLocation, targetLocationGroup));
    }

    private Optional<RouteImpl> findInTargetGroupHierarchy(String sourceLocation, String targetLocationGroup, Collection<LocationGroupVO> allLocationGroups) {
        // climb up group
        Optional<RouteImpl> route = repository.findBySourceLocation_LocationIdAndTargetLocationGroupNameAndEnabled(sourceLocation, targetLocationGroup, true);
        if (!route.isPresent()) {
            LocationGroupVO current = allLocationGroups.stream().filter(lg -> lg.getName().equals(targetLocationGroup)).findFirst().orElseThrow(() -> new NotFoundException(format("The LocationGroup with name [%s] does not exist", targetLocationGroup)));
            if (current.getParent() == null || current.getParent().isEmpty()) {
                return Optional.empty();
            }
            route = findInTargetGroupHierarchy(sourceLocation, current.getParent(), allLocationGroups);
        } return route;
    }

    private Optional<RouteImpl> findInSourceGroupHierarchyWithTargetLocation(String sourceLocationGroup, String targetLocation, String targetLocationGroup, Collection<LocationGroupVO> allLocationGroups) {
        // climb up group
        Optional<RouteImpl> route = repository.findBySourceLocationGroupNameAndTargetLocation_LocationIdAndEnabled(sourceLocationGroup, targetLocation, true);
        if (!route.isPresent()) {
            LocationGroupVO currentSource = allLocationGroups.stream().filter(lg -> lg.getName().equals(sourceLocationGroup)).findFirst().orElseThrow(() -> new NotFoundException(format("The LocationGroup with name [%s] does not exist", sourceLocationGroup)));
            if (currentSource.getParent() == null || currentSource.getParent().isEmpty()) {
                return Optional.empty();
            }
            route = findInSourceGroupHierarchyWithTargetLocation(currentSource.getParent(), targetLocation, targetLocationGroup, allLocationGroups);
            if (route.isPresent()) {
                return route;
            }

            LocationGroupVO currentTarget = allLocationGroups.stream().filter(lg -> lg.getName().equals(targetLocationGroup)).findFirst().orElseThrow(() -> new NotFoundException(format("The LocationGroup with name [%s] does not exist", targetLocationGroup)));
            if (currentTarget.getParent() == null || currentTarget.getParent().isEmpty()) {
                return Optional.empty();
            }
            route = findInSourceGroupHierarchyWithTargetLocation(sourceLocationGroup, targetLocation, currentTarget.getParent(), allLocationGroups);
        } return route;
    }

    private Optional<RouteImpl> findInSourceGroupHierarchy(String sourceLocationGroup, String targetLocationGroup, Collection<LocationGroupVO> allLocationGroups) {
        // climb up group
        Optional<RouteImpl> route = repository.findBySourceLocationGroupNameAndTargetLocationGroupNameAndEnabled(sourceLocationGroup, targetLocationGroup, true);
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

    private Optional<RouteImpl> findInHierarchy(String sourceLocation, String targetLocation, String targetLocationGroupName, Collection<LocationGroupVO> allLocationGroups) {
        Optional<RouteImpl> route = repository.findBySourceLocation_LocationIdAndTargetLocation_LocationIdAndEnabled(sourceLocation, targetLocation, true);
        if (route.isPresent()) {
            return route;
        }

        // The targetLocation may also contain a LocationGroup!! (for CREATED and INITIALIZED orders)
        if (LocationPK.isValid(targetLocation)) {

            // first step is target then source...
            String targetLocationLocationGroupName = mappingLocationToParent.computeIfAbsent(targetLocation, k -> locationApi.findById(k).orElseThrow(()->new NotFoundException(format("TargetLocation with coordinate [%s] does not exist", targetLocation))).getLocationGroupName());
            route = findInTargetGroupHierarchy(sourceLocation, targetLocationLocationGroupName, allLocationGroups);
            if (route.isPresent()) {
                return route;
            }
        }

        final String sourceLocationGroupName = mappingLocationToParent.computeIfAbsent(sourceLocation, k -> locationApi.findById(k).orElseThrow(()->new NotFoundException(format("SourceLocation with locationId [%s] does not exist", sourceLocation))).getLocationGroupName());
        route = findInSourceGroupHierarchy(sourceLocationGroupName, targetLocationGroupName, allLocationGroups);
        return route;

    }

    private Optional<RouteImpl> findInHierarchy2(String sourceLocation, String targetLocationGroupName, Collection<LocationGroupVO> allLocationGroups) {
        // first step is target then source...
        Optional<RouteImpl> route = findInTargetGroupHierarchy(sourceLocation, targetLocationGroupName, allLocationGroups);
        if (route.isPresent()) {
            return route;
        }

        final String sourceLocationGroupName = mappingLocationToParent.computeIfAbsent(sourceLocation, k -> locationApi.findById(k).orElseThrow(()->new NotFoundException(format("SourceLocation with locationId [%s] does not exist", sourceLocation))).getLocationGroupName());
        route = findInSourceGroupHierarchy(sourceLocationGroupName, targetLocationGroupName, allLocationGroups);
        return route;

    }
}
