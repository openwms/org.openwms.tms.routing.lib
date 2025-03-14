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
package org.openwms.tms.routing.routes;

import org.openwms.common.location.api.LocationGroupVO;

import java.util.Collection;

/**
 * A LocationGroupLoader.
 *
 * @author Heiko Scherrer
 */
public interface LocationGroupLoader {

    /**
     * This public method is surrounded with a retry advice in order to retry the remote operation in case of an error.
     *
     * @return All LocationGroups as List implementation
     */
    Collection<LocationGroupVO> loadLocGroups();
}
