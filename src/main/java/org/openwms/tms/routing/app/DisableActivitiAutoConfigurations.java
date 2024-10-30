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
package org.openwms.tms.routing.app;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * A DisableActivitiAutoConfigurations disables all Activiti Spring AutoConfigurations the are present on the classpath in case either
 * {@literal CAMUNDA} or {@literal FLOWABLE} profiles are active.
 *
 * @author Heiko Scherrer
 */
@Profile("!FLOWABLE && !ACTIVITI")
@Configuration
@EnableAutoConfiguration(excludeName = {
        "org.activiti.application.conf.ApplicationProcessAutoConfiguration",
        "org.activiti.spring.boot.ActivitiMethodSecurityAutoConfiguration",
        "org.activiti.spring.boot.EndpointAutoConfiguration",
        "org.activiti.spring.boot.ProcessEngineAutoConfiguration",
        "org.activiti.runtime.api.conf.CommonRuntimeAutoConfiguration",
        "org.activiti.runtime.api.conf.ConnectorsAutoConfiguration",
        "org.activiti.runtime.api.conf.ProcessRuntimeAutoConfiguration",
        "org.activiti.runtime.api.conf.TaskRuntimeAutoConfiguration",
        "org.activiti.spring.process.conf.ProcessExtensionsAutoConfiguration",
        "org.activiti.spring.process.conf.ProcessExtensionsConfiguratorAutoConfiguration"
})
class DisableActivitiAutoConfigurations {
}
