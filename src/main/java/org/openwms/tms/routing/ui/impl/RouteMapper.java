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

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.openwms.tms.routing.RouteImpl;
import org.openwms.tms.routing.location.LocationService;
import org.openwms.tms.routing.ui.api.RouteVO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * A RouteMapper.
 *
 * @author Heiko Scherrer
 */
@Mapper
abstract class RouteMapper {

    protected LocationService srv;

    @Autowired
    public void setSrv(LocationService srv) {
        this.srv = srv;
    }

    @Mapping(target = "persistentKey", source = "key")
    @Mapping(target = "routeId", source = "name")
    @Mapping(target = "sourceLocation", expression = "java( vo.hasSourceLocationName() ? srv.findByLocationId(vo.getSourceLocationName()).orElseThrow() : null )")
    @Mapping(target = "targetLocation", expression = "java( vo.hasTargetLocationName() ? srv.findByLocationId(vo.getTargetLocationName()).orElseThrow() : null )")
    public abstract RouteImpl convertVO(RouteVO vo);

    @Mapping(target = "key", source = "persistentKey")
    @Mapping(target = "name", source = "routeId")
    @Mapping(target = "sourceLocationName", source = "sourceLocation.locationId")
    @Mapping(target = "targetLocationName", source = "targetLocation.locationId")
    public abstract RouteVO convertEO(RouteImpl eo);

    public abstract List<RouteVO> convertEO(List<RouteImpl> eos);

    @Mapping(target = "persistentKey", source = "key")
    @Mapping(target = "routeId", source = "name")
    @Mapping(target = "sourceLocation", expression = "java( vo.hasSourceLocationName() ? srv.findByLocationId(vo.getSourceLocationName()).orElseThrow() : null )")
    @Mapping(target = "targetLocation", expression = "java( vo.hasTargetLocationName() ? srv.findByLocationId(vo.getTargetLocationName()).orElseThrow() : null )")
    public abstract RouteImpl copy(RouteVO vo, @MappingTarget RouteImpl target);
}
