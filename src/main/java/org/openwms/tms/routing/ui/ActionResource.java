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
package org.openwms.tms.routing.ui;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.ameba.http.MeasuredRestController;
import org.openwms.core.http.AbstractWebController;
import org.openwms.tms.routing.ui.api.ActionVO;
import org.openwms.tms.routing.ui.impl.ActionUIService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

import static org.openwms.tms.routing.RoutingConstants.API_ACTIONS;

/**
 * A ActionResource.
 *
 * @author Heiko Scherrer
 */
@MeasuredRestController
class ActionResource extends AbstractWebController {

    private final ActionUIService actionUIService;

    ActionResource(MessageSource messageSource, ActionUIService actionUIService) {
        super(messageSource);
        this.actionUIService = actionUIService;
    }

    @GetMapping(API_ACTIONS)
    public List<ActionVO> getAll() {
        return actionUIService.findAll(Sort.by("name"));
    }

    @DeleteMapping(API_ACTIONS + "/{pKey}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict("actions")
    public void delete(@PathVariable("pKey") String pKey) {
        actionUIService.delete(pKey);
    }

    @PutMapping(API_ACTIONS)
    @CacheEvict("actions")
    public ActionVO save(@RequestBody ActionVO actionVO) {
        return actionUIService.save(actionVO);
    }

    @PostMapping(API_ACTIONS)
    @ResponseStatus(HttpStatus.CREATED)
    @CacheEvict("actions")
    public void create(@RequestBody ActionVO actionVO, HttpServletRequest req, HttpServletResponse resp) {
        var action = actionUIService.create(actionVO);
        resp.addHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.LOCATION);
        resp.addHeader(HttpHeaders.LOCATION, super.getLocationForCreatedResource(req, action.getKey()));
    }
}
