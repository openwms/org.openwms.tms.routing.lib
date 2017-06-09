/*
 * openwms.org, the Open Warehouse Management System.
 * Copyright (C) 2014 Heiko Scherrer
 *
 * This file is part of openwms.org.
 *
 * openwms.org is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as 
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * openwms.org is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this software. If not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.openwms;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static java.lang.String.format;
import static org.openwms.SecurityUtils.createHeaders;

/**
 * A ConfigServerResourcePatternResolver.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Primary
@Component
public class ConfigServerResourcePatternResolver extends PathMatchingResourcePatternResolver {

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
        String path = locationPattern.substring(locationPattern.indexOf(":")+1, locationPattern.length());
        ResponseEntity<Resource> exchange =
                restTemplate.exchange(
                        format("%s://%s/%s/%s/%s", configServerProtocol, configServerId, path, "default", "master"),
                        HttpMethod.GET,
                        new HttpEntity<Resource>(createHeaders(configServerUsername, configServerPassword)),
                        Resource.class
                );

        return Optional.of(exchange.getBody());
    }
}
