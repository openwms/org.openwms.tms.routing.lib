/*
 * Copyright 2005-2022 the original author or authors.
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

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.openwms.core.process.execution.RouteImpl;
import org.openwms.tms.routing.routes.RouteRepository;
import org.openwms.tms.routing.ui.RouteVO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * A RouteMapper.
 *
 * @author Heiko Scherrer
 */
@Mapper
public abstract class RouteMapper {

    @Autowired
    protected RouteRepository routeRepository;

    @Mapping(target = "persistentKey", source = "key")
    @Mapping(target = "routeId", expression = "java( super.routeRepository.findByRouteId(vo.getName()).orElseThrow().getRouteId() )")
    @Mapping(target = "sourceLocation.locationId", source = "sourceLocationName")
    @Mapping(target = "targetLocation.locationId", source = "targetLocationName")
    public abstract RouteImpl convertVO(RouteVO vo);

    @Mapping(target = "key", source = "persistentKey")
    @Mapping(target = "name", source = "routeId")
    @Mapping(target = "sourceLocationName", source = "sourceLocation.locationId")
    @Mapping(target = "targetLocationName", source = "targetLocation.locationId")
    public abstract RouteVO convertEO(RouteImpl eo);

    public abstract List<RouteVO> convertEO(List<RouteImpl> eos);

    @Mapping(target = "persistentKey", source = "key")
    @Mapping(target = "routeId", expression = "java( super.routeRepository.findByRouteId(vo.getName()).orElseThrow().getRouteId() )")
    @Mapping(target = "sourceLocation.locationId", source = "sourceLocationName")
    @Mapping(target = "targetLocation.locationId", source = "targetLocationName")
    public abstract RouteImpl copy(RouteVO vo, @MappingTarget RouteImpl target);
}
