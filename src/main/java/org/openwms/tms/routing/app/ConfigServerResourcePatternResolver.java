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
package org.openwms.tms.routing.app;

import org.openwms.core.SecurityUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static java.lang.String.format;

/**
 * A ConfigServerResourcePatternResolver.
 *
 * @author Heiko Scherrer
 */
@Primary
@Component
class ConfigServerResourcePatternResolver extends PathMatchingResourcePatternResolver {

    public static final String CONFIG_URL_PREFIX = "config:";
    private  RestTemplate restTemplate;
    private  String configServerId;
    private  String configServerProtocol;
    private  String configServerUsername;
    private  String configServerPassword;

    ConfigServerResourcePatternResolver(){}

    public ConfigServerResourcePatternResolver(@Qualifier("aLoadBalanced") RestTemplate restTemplate,
                                               @Value("${spring.cloud.config.discovery.service-id}") String configServerId,
                                               @Value("${spring.cloud.config.headers.protocol:http}") String configServerProtocol,
                                               @Value("${spring.cloud.config.username}") String configServerUsername,
                                               @Value("${spring.cloud.config.password}") String configServerPassword
                                               ) {
        this.restTemplate = restTemplate;
        this.configServerId = configServerId;
        this.configServerProtocol = configServerProtocol;
        this.configServerUsername = configServerUsername;
        this.configServerPassword = configServerPassword;
    }

    @Override
    public Resource getResource(String location) {
        Assert.notNull(location, "Location pattern must not be null");
        if (location.startsWith(CONFIG_URL_PREFIX)) {
            return resolveConfiguredValue(location).orElseThrow(RuntimeException::new);
        }
        return super.getResource(location);
    }

    private Optional<Resource> resolveConfiguredValue(String locationPattern) {
        var path = locationPattern.substring(locationPattern.indexOf(':')+1, locationPattern.length());
        var exchange =
                restTemplate.exchange(
                        format("%s://%s/%s/%s/%s", configServerProtocol, configServerId, path, "default", "master"),
                        HttpMethod.GET,
                        new HttpEntity<Resource>(SecurityUtils.createHeaders(configServerUsername, configServerPassword)),
                        Resource.class
                );

        return Optional.of(exchange.getBody());
    }
}
