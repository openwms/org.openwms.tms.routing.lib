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
package org.openwms.tms.routing.app;

import jakarta.servlet.Filter;
import org.ameba.app.SpringProfiles;
import org.ameba.http.PermitAllCorsConfigurationSource;
import org.openwms.core.app.JSONConfiguration;
import org.openwms.tms.routing.InputContext;
import org.openwms.tms.routing.config.OwmsProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.CorsFilter;

/**
 * A RoutingModuleConfiguration.
 *
 * @author Heiko Scherrer
 */
@Configuration
@Import(JSONConfiguration.class)
public class RoutingModuleConfiguration {

    @LoadBalanced @Bean RestTemplate aLoadBalanced() {
        return new RestTemplate();
    }

    @Bean RestTemplate simpleRestTemplate() {
        return new RestTemplate();
    }

    @Profile(SpringProfiles.DEVELOPMENT_PROFILE)
    public @Bean
    Filter corsFiler() {
        return new CorsFilter(new PermitAllCorsConfigurationSource());
    }

    @Bean
    InputContext in() {
        return new InputContext();
    }

    @Bean OwmsProperties owmsProperties() {
        return new OwmsProperties();
    }
}
