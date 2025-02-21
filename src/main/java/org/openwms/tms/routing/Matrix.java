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
package org.openwms.tms.routing;

import org.openwms.common.location.api.LocationGroupVO;
import org.openwms.common.location.api.LocationVO;
import org.springframework.lang.Nullable;

/**
 * A Matrix.
 *
 * @author Heiko Scherrer
 */
public interface Matrix {

    /**
     * Find and return an {@code Action}.
     *
     * @param actionType The type of action is often the type of triggering event (REQ_, SYSU, etc.)
     * @param route The {@code TransportOrder}s {@code Route}
     * @param location The actual {@code Location}
     * @param locationGroup The corresponding actual {@code LocationGroup}
     * @return The Action
     * @throws org.ameba.exception.NotFoundException in case no Action was found
     */
    Action findBy(String actionType, Route route, @Nullable LocationVO location, @Nullable LocationGroupVO locationGroup);
}
