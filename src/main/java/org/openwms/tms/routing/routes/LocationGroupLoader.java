/*
 * Copyright 2019 Heiko Scherrer
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

import org.openwms.common.location.api.LocationGroupApi;
import org.openwms.common.location.api.LocationGroupVO;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * A LocationGroupLoader.
 *
 * @author Heiko Scherrer
 */
@Component
class LocationGroupLoader {

    private final LocationGroupApi locationGroupApi;

    LocationGroupLoader(LocationGroupApi locationGroupApi) {
        this.locationGroupApi = locationGroupApi;
    }

    @Retryable(backoff = @Backoff(delay = 5000L))
    public Collection<LocationGroupVO> loadLocGroups() {
        return locationGroupApi.findAll();
    }
}
