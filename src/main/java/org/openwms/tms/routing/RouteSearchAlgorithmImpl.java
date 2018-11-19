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

import org.ameba.exception.NotFoundException;
import org.openwms.common.LocationGroupVO;
import org.openwms.common.location.api.LocationApi;
import org.openwms.common.location.api.LocationGroupApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
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
@Profile("!SIMPLE")
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
    void onPostConstruct() {
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

        result = findInHierarchy2(sourceLocation, targetLocationGroup, allLocationGroups);
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
        return route;

    }

    private Optional<Route> findInHierarchy2(String sourceLocation, String targetLocationGroupName, Collection<LocationGroupVO> allLocationGroups) {
        // first step is target then source...
        Optional<Route> route = findInTargetGroupHierarchy(sourceLocation, targetLocationGroupName, allLocationGroups);
        if (route.isPresent()) {
            return route;
        }

        final String sourceLocationGroupName = mappingLocationToParent.computeIfAbsent(sourceLocation, (k) -> locationApi.findLocationByCoordinate(k).orElseThrow(()->new NotFoundException(format("SourceLocation with locationId %s does not exist", sourceLocation))).getLocationGroupName());
        route = findInSourceGroupHierarchy(sourceLocationGroupName, targetLocationGroupName, allLocationGroups);
        return route;

    }
}
