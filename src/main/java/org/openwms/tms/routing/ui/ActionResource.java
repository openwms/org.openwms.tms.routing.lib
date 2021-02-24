/*
 * Copyright 2005-2021 the original author or authors.
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
package org.openwms.tms.routing.ui;

import org.ameba.exception.NotFoundException;
import org.ameba.mapping.BeanMapper;
import org.openwms.tms.routing.Action;
import org.openwms.tms.routing.ActionRepository;
import org.openwms.tms.routing.routes.RouteRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static org.openwms.tms.routing.RoutingConstants.API_ACTIONS;

/**
 * A ActionResource.
 *
 * @author Heiko Scherrer
 */
@RestController
class ActionResource {

    private final ActionRepository actionRepository;
    private final RouteRepository routeRepository;
    private final BeanMapper mapper;

    ActionResource(ActionRepository actionRepository, RouteRepository routeRepository, BeanMapper mapper) {
        this.actionRepository = actionRepository;
        this.routeRepository = routeRepository;
        this.mapper = mapper;
    }

    @GetMapping(API_ACTIONS)
    public List<ActionVO> getAll() {
        return mapper.map(actionRepository.findAll(Sort.by("name")), ActionVO.class);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    @DeleteMapping(API_ACTIONS + "/{persistentKey}")
    public void delete(@PathVariable("persistentKey") String persistentKey) {
        actionRepository.findBypKey(persistentKey).ifPresent(actionRepository::delete);
    }

    @PutMapping(API_ACTIONS)
    @Transactional
    public ActionVO save(@RequestBody ActionVO actionVO) {
        Action eo = actionRepository.findBypKey(actionVO.getKey()).orElseThrow(NotFoundException::new);
        Action action = mapper.mapFromTo(actionVO, eo);
        action.setRoute(routeRepository.findByRouteId(actionVO.getRoute()).orElseThrow(NotFoundException::new));
        return mapper.map(actionRepository.save(action), ActionVO.class);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    @PostMapping(API_ACTIONS)
    public void create(@RequestBody ActionVO actionVO, HttpServletRequest req, HttpServletResponse resp) {
        Action action = mapper.map(actionVO, Action.class);
        action.setRoute(routeRepository.findByRouteId(actionVO.getRoute()).orElseThrow(NotFoundException::new));
        action = actionRepository.save(action);
        resp.addHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS,HttpHeaders.LOCATION);
        resp.addHeader(HttpHeaders.LOCATION, getCreatedResourceURI(req, action.getPersistentKey()));
    }

    private String getCreatedResourceURI(HttpServletRequest req, String objId) {
        StringBuffer url = req.getRequestURL();
        UriTemplate template = new UriTemplate(url.append("/{objId}").toString());
        return template.expand(objId).toASCIIString();
    }
}
