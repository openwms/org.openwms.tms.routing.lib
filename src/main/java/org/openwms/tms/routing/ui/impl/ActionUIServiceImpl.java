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
import org.ameba.annotation.Measured;
import org.ameba.annotation.TxService;
import org.ameba.exception.NotFoundException;
import org.openwms.tms.routing.routes.RouteService;
import org.openwms.tms.routing.ui.api.ActionVO;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * A ActionUIServiceImpl.
 *
 * @author Heiko Scherrer
 */
@TxService
class ActionUIServiceImpl implements ActionUIService {

    private final ActionRepository actionRepository;
    private final ActionMapper mapper;
    private final RouteService routeService;

    ActionUIServiceImpl(ActionRepository actionRepository, ActionMapper mapper, RouteService routeService) {
        this.actionRepository = actionRepository;
        this.mapper = mapper;
        this.routeService = routeService;
    }

    /**
     * {@inheritDoc}
     */
    @Measured
    @Override
    public @NotNull List<ActionVO> findAll(@NotNull Sort sort) {
        return mapper.convertEO(actionRepository.findAll(sort));
    }

    /**
     * {@inheritDoc}
     */
    @Measured
    @Override
    public void delete(@NotBlank String pKey) {
        actionRepository.deleteBypKey(pKey);
    }

    /**
     * {@inheritDoc}
     */
    @Measured
    @Override
    public @NotNull ActionVO create(@NotNull ActionVO vo) {
        var action = mapper.convertVO(vo);
        action.setRoute(routeService.findByRouteId(vo.getRoute()).orElseThrow(NotFoundException::new));
        return mapper.convertEO(actionRepository.save(action));
    }

    /**
     * {@inheritDoc}
     */
    @Measured
    @Override
    public @NotNull ActionVO save(@NotNull ActionVO vo) {
        var eo = actionRepository.findBypKey(vo.getKey()).orElseThrow(NotFoundException::new);
        var action = mapper.copy(vo, eo);
        action.setRoute(routeService.findByRouteId(vo.getRoute()).orElseThrow(NotFoundException::new));
        return mapper.convertEO(actionRepository.save(action));
    }
}
