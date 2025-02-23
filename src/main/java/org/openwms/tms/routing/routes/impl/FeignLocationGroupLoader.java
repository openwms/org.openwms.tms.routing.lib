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

import org.ameba.annotation.Measured;
import org.openwms.common.location.api.LocationGroupApi;
import org.openwms.common.location.api.LocationGroupVO;
import org.openwms.tms.routing.routes.LocationGroupLoader;
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
class FeignLocationGroupLoader implements LocationGroupLoader {

    private final LocationGroupApi locationGroupApi;

    FeignLocationGroupLoader(LocationGroupApi locationGroupApi) {
        this.locationGroupApi = locationGroupApi;
    }

    /**
     * {@inheritDoc}
     */
    @Measured
    @Retryable(backoff = @Backoff(delay = 5000L))
    @Override
    public Collection<LocationGroupVO> loadLocGroups() {
        return locationGroupApi.findAll();
    }
}
