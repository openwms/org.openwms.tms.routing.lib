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
package org.openwms.tms.routing.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

/**
 * A CacheJanitor is used to evict caches.
 *
 * @author Heiko Scherrer
 */
@Component
class CacheJanitor {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheJanitor.class);

    @CacheEvict(cacheNames = "locationGroups", allEntries = true)
    public void evictLocationGroupCache() {
        LOGGER.debug("LocationGroup cache evicted");
    }

    @CacheEvict(cacheNames = "locations", allEntries = true)
    public void evictLocationCache() {
        LOGGER.debug("Location cache evicted");
    }
}
