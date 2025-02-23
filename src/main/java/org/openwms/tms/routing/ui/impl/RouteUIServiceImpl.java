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
import org.ameba.exception.ResourceExistsException;
import org.openwms.tms.routing.RouteImpl;
import org.openwms.tms.routing.location.LocationService;
import org.openwms.tms.routing.routes.impl.RouteRepository;
import org.openwms.tms.routing.ui.api.RouteVO;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

/**
 * A RouteUIServiceImpl.
 *
 * @author Heiko Scherrer
 */
@TxService
class RouteUIServiceImpl implements RouteUIService {

    private final RouteRepository routeRepository;
    private final RouteMapper mapper;
    private final LocationService locationService;

    RouteUIServiceImpl(RouteRepository routeRepository, RouteMapper mapper, LocationService locationService) {
        this.routeRepository = routeRepository;
        this.mapper = mapper;
        this.locationService = locationService;
    }

    /**
     * {@inheritDoc}
     */
    @Measured
    @Override
    public @NotNull List<RouteVO> findAll(@NotNull Sort sort) {
        return mapper.convertEO(routeRepository.findAll(sort));
    }

    /**
     * {@inheritDoc}
     */
    @Measured
    @Override
    public Optional<RouteVO> findBypKey(@NotBlank String pKey) {
        return routeRepository.findBypKey(pKey).map(mapper::convertEO);
    }

    /**
     * {@inheritDoc}
     */
    @Measured
    @Override
    public void delete(@NotBlank String pKey) {
        routeRepository.findBypKey(pKey).ifPresent(routeRepository::delete);
    }

    /**
     * {@inheritDoc}
     */
    @Measured
    @Override
    public @NotNull RouteVO create(@NotNull RouteVO vo) {
        routeRepository.findByRouteId(vo.getName()).ifPresent(r -> {
            throw new ResourceExistsException("Route with name [%s] already exists".formatted(vo.getName()));
        });
        var route = mapper.convertVO(vo);
        return saveInternal(vo, route);
    }

    /**
     * {@inheritDoc}
     */
    @Measured
    @Override
    public @NotNull RouteVO save(@NotNull RouteVO vo) {
        var eo = routeRepository.findBypKey(vo.getKey()).orElseThrow(NotFoundException::new);
        var route = mapper.copy(vo, eo);
        return saveInternal(vo, route);
    }

    private RouteVO saveInternal(RouteVO vo, RouteImpl route) {
        route.setSourceLocation(vo.hasSourceLocationName()
                ? locationService.findByLocationId(vo.getSourceLocationName()).orElseThrow(NotFoundException::new)
                : null);
        route.setTargetLocation(vo.hasTargetLocationName()
                ? locationService.findByLocationId(vo.getTargetLocationName()).orElseThrow(NotFoundException::new)
                : null);
        return mapper.convertEO(routeRepository.save(route));
    }
}
