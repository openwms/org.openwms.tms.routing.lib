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
package org.openwms.tms.routing.ui.impl;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.openwms.tms.routing.ui.api.RouteVO;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

/**
 * A RouteUIService.
 *
 * @author Heiko Scherrer
 */
public interface RouteUIService {

    @NotNull List<RouteVO> findAll(@NotNull Sort sort);

    Optional<RouteVO> findBypKey(@NotBlank String pKey);

    void delete(@NotBlank String pKey);

    @NotNull RouteVO create(@NotNull RouteVO vo);

    @NotNull RouteVO save(@NotNull RouteVO vo);
}
