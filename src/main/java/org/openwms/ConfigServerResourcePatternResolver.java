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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.lang.String.format;
import static org.openwms.SecurityUtils.createHeaders;

/**
 * A ConfigServerResourcePatternResolver.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
public class ConfigServerResourcePatternResolver extends PathMatchingResourcePatternResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigServerResourcePatternResolver.class);
    public static final String CONFIG_URL_PREFIX = "config:";
    private final DiscoveryClient dc;
    private final RestTemplate restTemplate;
    private final String configServerId;

    public ConfigServerResourcePatternResolver(DiscoveryClient dc, RestTemplate restTemplate, @Value("${spring.cloud.config.discovery.service-id}") String configServerId) {
        this.configServerId = configServerId;
        this.restTemplate = restTemplate;
        this.dc = dc;
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
        List<ServiceInstance> instances = dc.getInstances(configServerId);
        if (instances == null || instances.isEmpty()) {
            throw new IllegalArgumentException("No instances available");
        }
        Map<String, Object> maps = new HashMap<>();
        ServiceInstance si = instances.get(0);
        String name = locationPattern; // Filename (first part)
        String profile = "default"; // Spring Profile
        String label = "master"; // Git Branch
        String endpoint = format("%s://%s/%s/%s/%s",si.getMetadata().get("protocol"), si.getServiceId(), name, profile, label);
        ResponseEntity<Resource> exchange =
                restTemplate.exchange(
                        endpoint,
                        HttpMethod.GET,
                        new HttpEntity<Resource>(createHeaders(si.getMetadata().get("username"), si.getMetadata().get("password"))),
                        Resource.class,
                        maps);

        return Optional.of(exchange.getBody());
    }
}
