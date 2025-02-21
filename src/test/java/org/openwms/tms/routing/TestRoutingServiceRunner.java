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
package org.openwms.tms.routing;

import org.ameba.app.SolutionApp;
import org.openwms.common.comm.CommPackage;
import org.openwms.core.process.execution.RuntimeConfiguration;
import org.openwms.core.process.execution.app.DisableActivitiAutoConfigurations;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

/**
 * A TestRoutingServiceRunner.
 *
 * @author Heiko Scherrer
 */
@SpringBootApplication(
        scanBasePackageClasses = {
                SolutionApp.class,
                DisableActivitiAutoConfigurations.class,
                RoutingModuleConfiguration.class,
                RuntimeConfiguration.class,
                TestRoutingServiceRunner.class,
                CommPackage.class
        })
@EnableRetry
public class TestRoutingServiceRunner {

    /**
     * Boot up!
     *
     * @param args Some args
     */
    public static void main(String[] args) {
        var ctx = SpringApplication.run(TestRoutingServiceRunner.class, args);
        ctx.start();
    }
}
