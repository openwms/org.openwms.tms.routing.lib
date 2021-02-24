/*
 * Copyright 2005-2021 the original author or authors.
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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openwms.tms.routing.RoutingServiceRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * A ConfigServerResourcePatternResolverIT.
 *
 * @author Heiko Scherrer
 */
@Disabled("work in progress")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = RoutingServiceRunner.class)
public class ConfigServerResourcePatternResolverIT {

    @TestConfiguration
    @ComponentScan("org.openwms")
    static class TestConfig {

    }

    @Autowired
    private RestTemplate restTemplate;

    private ConfigServerResourcePatternResolver testee;

    @BeforeEach
    public void onBefore() {
        testee = new ConfigServerResourcePatternResolver(restTemplate, "openwms-config", "http", "user", "sa");
    }

    @Disabled("work in progress")
    public final
    @Test
    void shall_resolve_from_GitHub() {
        Resource resource = testee.getResource("config:routing-service");
        assertThat(resource.exists()).isTrue();
    }
}
