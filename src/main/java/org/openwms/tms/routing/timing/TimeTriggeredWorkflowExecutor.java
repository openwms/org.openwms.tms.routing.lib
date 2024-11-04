/*
 * Copyright 2005-2024 the original author or authors.
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
package org.openwms.tms.routing.timing;

import jakarta.annotation.PostConstruct;
import org.openwms.tms.routing.Action;
import org.openwms.tms.routing.ProgramExecutor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * A TimeTriggeredWorkflowExecutor.
 *
 * @author Heiko Scherrer
 */
@Service
class TimeTriggeredWorkflowExecutor {

    private final ProgramExecutor programExecutor;
    private final TaskScheduler taskScheduler;
    private final TimerConfigurationService timerConfigurationService;

    TimeTriggeredWorkflowExecutor(ProgramExecutor programExecutor, TaskScheduler taskScheduler, TimerConfigurationService timerConfigurationService) {
        this.programExecutor = programExecutor;
        this.taskScheduler = taskScheduler;
        this.timerConfigurationService = timerConfigurationService;
    }

    @PostConstruct
    public void execute() {
        var timerConfigurations = timerConfigurationService.loadConfigurations();
        timerConfigurations.forEach(tc -> {
            var cronTrigger
                    = new CronTrigger(tc.getCronExpression());
            taskScheduler.schedule(
                    () -> {
                        var action = new Action(tc.getName(), tc.getDescription());
                        var result = programExecutor.execute(action, convertMap(tc.getRuntimeVariables()));
                    },
                    cronTrigger);
        });
    }

    public static Map<String, Object> convertMap(Map<String, String> stringMap) {
        Map<String, Object> objectMap = new HashMap<>();
        for (Map.Entry<String, String> entry : stringMap.entrySet()) {
            objectMap.put(entry.getKey(), entry.getValue());
        }
        return objectMap;
    }
}
