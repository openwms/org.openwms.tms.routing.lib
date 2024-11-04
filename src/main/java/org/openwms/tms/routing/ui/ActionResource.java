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
package org.openwms.tms.routing.ui;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.ameba.exception.NotFoundException;
import org.ameba.http.MeasuredRestController;
import org.openwms.core.http.AbstractWebController;
import org.openwms.tms.routing.Action;
import org.openwms.tms.routing.ActionMapper;
import org.openwms.tms.routing.ActionRepository;
import org.openwms.tms.routing.routes.RouteRepository;
import org.springframework.cache.annotation.CacheEvict;
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
import org.springframework.web.util.UriTemplate;

import java.util.List;

import static org.openwms.tms.routing.RoutingConstants.API_ACTIONS;

/**
 * A ActionResource.
 *
 * @author Heiko Scherrer
 */
@MeasuredRestController
class ActionResource extends AbstractWebController {

    private final ActionRepository actionRepository;
    private final RouteRepository routeRepository;
    private final ActionMapper mapper;

    ActionResource(ActionRepository actionRepository, RouteRepository routeRepository, ActionMapper mapper) {
        this.actionRepository = actionRepository;
        this.routeRepository = routeRepository;
        this.mapper = mapper;
    }

    @GetMapping(API_ACTIONS)
    public List<ActionVO> getAll() {
        return mapper.convertEO(actionRepository.findAll(Sort.by("name")));
    }

    @DeleteMapping(API_ACTIONS + "/{persistentKey}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict("actions")
    @Transactional
    public void delete(@PathVariable("persistentKey") String persistentKey) {
        actionRepository.findBypKey(persistentKey).ifPresent(actionRepository::delete);
    }

    @PutMapping(API_ACTIONS)
    @CacheEvict("actions")
    @Transactional
    public ActionVO save(@RequestBody ActionVO actionVO) {
        Action eo = actionRepository.findBypKey(actionVO.getKey()).orElseThrow(NotFoundException::new);
        Action action = mapper.copy(actionVO, eo);
        action.setRoute(routeRepository.findByRouteId(actionVO.getRoute()).orElseThrow(NotFoundException::new));
        return mapper.convertEO(actionRepository.save(action));
    }

    @PostMapping(API_ACTIONS)
    @ResponseStatus(HttpStatus.CREATED)
    @CacheEvict("actions")
    @Transactional
    public void create(@RequestBody ActionVO actionVO, HttpServletRequest req, HttpServletResponse resp) {
        Action action = mapper.convertVO(actionVO);
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
