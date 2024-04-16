/*
 * Copyright 2005-2024 the original author or authors.
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

import org.ameba.http.MeasuredRestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * A LocationController.
 *
 * @author Heiko Scherrer
 */
@Validated
@MeasuredRestController
class LocationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocationController.class);
    private final LocationService locationService;

    LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping("/v1/locations/remove")
    public ResponseEntity<Boolean> requestDeleteLocation(@RequestBody List<String> pKeys) {
        LOGGER.debug("Request deleting Locations with pKeys [{}]", pKeys);
        return ResponseEntity.ok(locationService.isRemovalAllowed(pKeys));
    }

    @DeleteMapping("/v1/locations")
    public ResponseEntity<Void> deleteLocation(@RequestBody List<String> pKeys) {
        LOGGER.debug("Deleting Locations with pKeys [{}]", pKeys);
        locationService.markForRemoval(pKeys);
        return ResponseEntity.noContent().build();
    }
}
