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
import org.openwms.tms.routing.routes.RouteRepository;
import org.openwms.tms.routing.ui.ActionVO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * A ActionMapper.
 *
 * @author Heiko Scherrer
 */
@Mapper
public abstract class ActionMapper {

    @Autowired
    protected RouteRepository routeRepository;

    @Mapping(target = "persistentKey", source = "key")
    @Mapping(target = "actionType", source = "type")
    @Mapping(target = "programKey", source = "program")
    @Mapping(target = "locationKey", source = "location")
    @Mapping(target = "route", expression = "java( super.routeRepository.findByRouteId(vo.getRoute()).orElseThrow() )")
    public abstract Action convertVO(ActionVO vo);

    @Mapping(target = "key", source = "persistentKey")
    @Mapping(target = "type", source = "actionType")
    @Mapping(target = "program", source = "programKey")
    @Mapping(target = "location", source = "locationKey")
    @Mapping(target = "route", source = "route.routeId")
    public abstract ActionVO convertEO(Action eo);

    public abstract List<ActionVO> convertEO(List<Action> eos);

    @Mapping(target = "persistentKey", source = "key")
    @Mapping(target = "actionType", source = "type")
    @Mapping(target = "programKey", source = "program")
    @Mapping(target = "locationKey", source = "location")
    @Mapping(target = "route", expression = "java( super.routeRepository.findByRouteId(vo.getRoute()).orElseThrow() )")
    public abstract Action copy(ActionVO vo, @MappingTarget Action target);
}
