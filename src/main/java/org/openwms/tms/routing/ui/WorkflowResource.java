/*
 * Copyright 2005-2026 the original author or authors.
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

import org.ameba.http.MeasuredRestController;
import org.openwms.core.http.AbstractWebController;
import org.openwms.core.process.execution.api.WorkflowDefinition;
import org.openwms.core.process.execution.spi.WorkflowFinder;
import org.openwms.core.process.execution.spi.WorkflowUpdater;
import org.openwms.tms.routing.ui.api.WorkflowVO;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static org.openwms.tms.routing.RoutingConstants.API_WORKFLOWS;

/**
 * A WorkflowResource.
 *
 * @author Heiko Scherrer
 */
@Profile("ACTIVITI")
@MeasuredRestController
class WorkflowResource extends AbstractWebController {

    private final WorkflowFinder workflowFinder;
    private final WorkflowUpdater workflowUpdater;

    WorkflowResource(WorkflowFinder workflowFinder, WorkflowUpdater workflowUpdater) {
        this.workflowFinder = workflowFinder;
        this.workflowUpdater = workflowUpdater;
    }

    @PutMapping(value = API_WORKFLOWS + "/{pKey}")
    public ResponseEntity<WorkflowVO> save(@RequestBody WorkflowVO workflow) {
        var workflowDefinition = new WorkflowDefinition();
        workflowDefinition.setId(workflow.getId());
        workflowDefinition.setName(workflow.getName());
        workflowDefinition.setpKey(workflow.getpKey());
        workflowDefinition.setBpmnXml(workflow.getBpmnXml());

        var saved = workflowUpdater.save(workflowDefinition);

        var w = new WorkflowVO();
        w.setName(saved.getName());
        w.setpKey(saved.getpKey());
        w.setId(saved.getId());
        w.setBpmnXml(saved.getBpmnXml());
        return ResponseEntity.ok(w);
    }

    @GetMapping(value = API_WORKFLOWS)
    public ResponseEntity<List<WorkflowVO>> findAll() {
        var processDefinitions = workflowFinder.findAll();
        return ResponseEntity.ok(processDefinitions.stream().map(wd -> {
            var w = new WorkflowVO();
            w.setName(wd.getName());
            w.setpKey(wd.getpKey());
            w.setId(wd.getId());
            w.setBpmnXml(wd.getBpmnXml());
            return w;
        }).toList());
    }

    @GetMapping(value = API_WORKFLOWS, params = "program")
    public ResponseEntity<String> getWorkflow(@RequestParam("program") String workflowDefinitionKey) {
        var processDefinition = workflowFinder.loadProcessFile(workflowDefinitionKey);
        return ResponseEntity.ok(processDefinition);
    }
}
