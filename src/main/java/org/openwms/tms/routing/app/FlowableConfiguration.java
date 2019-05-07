/*
 * Copyright 2019 Heiko Scherrer
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

import com.zaxxer.hikari.util.DriverDataSource;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Properties;

/**
 * A FlowableConfiguration is active if not explicitly CAMUNDA (profile) is demanded and
 * configures the datasource used by the Flowable engine. We need to create a new
 * DataSource instance and do not depend on the applications one in order to separate the
 * database schema.
 *
 * @author <a href="mailto:hscherrer@interface21.io">Heiko Scherrer</a>
 */
@Profile("!CAMUNDA")
@Configuration
class FlowableConfiguration {

    @ConditionalOnProperty("${flowable.datasource}")
    @Bean
    EngineConfigurationConfigurer<SpringProcessEngineConfiguration> EngineConfigurationConfigurer(
            @Value("${flowable.datasource.url}") String jdbcUrl,
            @Value("${flowable.datasource.driver-class-name}") String driverClassname,
            @Value("${flowable.datasource.username}") String username,
            @Value("${flowable.datasource.password}") String password

    ) {
        return engineConfiguration -> engineConfiguration.setDataSource(new DriverDataSource(jdbcUrl, driverClassname, new Properties(), username, password));
    }
}
