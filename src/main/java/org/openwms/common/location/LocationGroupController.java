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
package org.openwms.common.location;

import org.openwms.common.location.api.LocationGroupApi;
import org.openwms.common.location.api.LocationGroupVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * A LocationGroupController.
 *
 * @author Heiko Scherrer
 */
@RestController
class LocationGroupController {

    private final LocationGroupApi locationGroupApi;

    LocationGroupController(LocationGroupApi locationGroupApi) {
        this.locationGroupApi = locationGroupApi;
    }

    @GetMapping("/api/location-groups")
    public List<LocationGroupVO> findAll() {
        return locationGroupApi.findAll();
    }
}
