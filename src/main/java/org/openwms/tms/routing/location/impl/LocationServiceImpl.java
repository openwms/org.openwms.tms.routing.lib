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
package org.openwms.tms.routing.location.impl;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.ameba.annotation.Measured;
import org.ameba.annotation.TxService;
import org.openwms.core.listener.RemovalNotAllowedException;
import org.openwms.tms.routing.location.LocationService;
import org.openwms.tms.routing.routes.RouteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * A LocationServiceImpl.
 *
 * @author Heiko Scherrer
 */
@TxService
class LocationServiceImpl implements LocationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocationServiceImpl.class);
    private final LocationRepository locationRepository;
    private final RouteService routeService;

    LocationServiceImpl(LocationRepository locationRepository, RouteService routeService) {
        this.locationRepository = locationRepository;
        this.routeService = routeService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Measured
    public boolean isRemovalAllowed(@NotNull List<String> foreignPKeys) {
        boolean result = true;
        for (var foreignPKey : foreignPKeys) {
            if (!isRemovalAllowedInternal(foreignPKey)) {
                result = false;
            }
        }
        return result;
    }

    private boolean isRemovalAllowedInternal(String foreignPKey) {
        var optLocation = locationRepository.findByForeignPKey(foreignPKey);
        if (optLocation.isEmpty()) {
            LOGGER.warn("Location with foreignPKey [{}] does not exist and therefor removal is allowed", foreignPKey);
            return true;
        }
        if (routeService.routeExistsWithLocationId(optLocation.get().getLocationId())) {
            LOGGER.error("Location with foreignPKey [{}] is used in a route and cannot be removed", foreignPKey);
            return false;
        }
        LOGGER.info("Location with foreignPKey [{}] is not used in any route definitions and is allowed to be deleted", foreignPKey);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Measured
    public void markForRemoval(@NotNull List<String> foreignPKeys) {
        for (var foreignPKey : foreignPKeys) {
            var optLocation = locationRepository.findByForeignPKey(foreignPKey);
            if (optLocation.isEmpty()) {
                LOGGER.warn("Location with foreignPKey [{}] does not exist and therefor mark for removal is fails", foreignPKey);
                continue;
            }
            if (isRemovalAllowedInternal(foreignPKey)) {
                optLocation.get().setMarkForDeletion(true);
                locationRepository.save(optLocation.get());
                continue;
            }
            throw new RemovalNotAllowedException("Not allowed to delete Location with pKey [%s]".formatted(foreignPKey));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Measured
    @Override
    public void delete(@NotBlank String foreignPKey) {
        // Only allow to delete those who where previously marked for deletion
        var locationOpt = locationRepository.findByForeignPKeyAndDeleted(foreignPKey, true);
        if (locationOpt.isEmpty()) {
            LOGGER.warn("Location to delete does not exist, foreignPKey [{}]", foreignPKey);
            return;
        }
        locationRepository.delete(locationOpt.get());
        LOGGER.info("Deleted Location with foreignPKey [{}]", foreignPKey);
    }

    /**
     * {@inheritDoc}
     */
    @Measured
    @Override
    public void rollbackDeletionMark(@NotBlank String foreignPKey) {
        var locationOpt = locationRepository.findByForeignPKeyAndDeleted(foreignPKey, true);
        if (locationOpt.isEmpty()) {
            LOGGER.warn("Location with foreignPKey [{}] does either not exist or is not marked for deletion", foreignPKey);
            return;
        }
        locationOpt.get().setMarkForDeletion(false);
        locationRepository.save(locationOpt.get());
    }
}
