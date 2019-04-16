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
package org.openwms.tms.routing;

import org.ameba.annotation.EnableAspects;
import org.ameba.mapping.BeanMapper;
import org.ameba.mapping.DozerMapperImpl;
import org.openwms.tms.routing.config.OwmsProperties;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

/**
 * A RoutingModuleConfiguration.
 *
 * @author <a href="mailto:hscherrer@openwms.org">Heiko Scherrer</a>
 */
@Configuration
@EnableAspects(propagateRootCause = true)
@EnableConfigurationProperties
@EnableTransactionManagement
@EnableJpaRepositories
@EnableJpaAuditing
@EntityScan
public class RoutingModuleConfiguration {

    @LoadBalanced
    @Bean
    RestTemplate aLoadBalanced() {
        return new RestTemplate();
    }

    @Bean
    RestTemplate simpleRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    BeanMapper beanMapper() {
        return new DozerMapperImpl("META-INF/dozer/tms-bean-mappings.xml");
    }

    @Bean
    InputContext in() {
        return new InputContext();
    }

    @Bean
    OwmsProperties owmsProperties() {
        return new OwmsProperties();
    }
}
