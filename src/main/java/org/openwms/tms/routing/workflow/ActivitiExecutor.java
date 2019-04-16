/*
 * Copyright 2018 Heiko Scherrer
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
package org.openwms.tms.routing.workflow;

import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.repository.ProcessDefinition;
import org.openwms.tms.routing.Action;
import org.openwms.tms.routing.ProgramExecutor;
import org.openwms.tms.routing.ProgramResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

import static java.lang.String.format;

/**
 * A ActivitiExecutor delegates to Activiti for program execution.
 *
 * @author <a href="mailto:hscherrer@openwms.org">Heiko Scherrer</a>
 */
@Component
class ActivitiExecutor implements ProgramExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivitiExecutor.class);
    private final RuntimeService runtimeService;
    private final RepositoryService repositoryService;

    ActivitiExecutor(RuntimeService runtimeService, RepositoryService repositoryService) {
        this.runtimeService = runtimeService;
        this.repositoryService = repositoryService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ProgramResult> execute(Action program, Map<String, Object> runtimeVariables) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Executing program : {}", program);
        }
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey(program.getProgramKey()).active().latestVersion().singleResult();
        if (null == processDefinition) {
            throw new IllegalStateException(format("No active process with programkey %s found", program.getProgramKey()));
        }
        runtimeService.startProcessInstanceById(processDefinition.getId(), runtimeVariables);
        return Optional.empty();
    }
}
