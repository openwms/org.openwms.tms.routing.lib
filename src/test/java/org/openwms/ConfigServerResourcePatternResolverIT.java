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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openwms.tms.routing.RoutingServiceRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * A ConfigServerResourcePatternResolverIT.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RoutingServiceRunner.class)
public class ConfigServerResourcePatternResolverIT {

    @TestConfiguration
    @ComponentScan("org.openwms")
    static class TestConfig {

    }

    @Autowired
    private RestTemplate restTemplate;

    private ConfigServerResourcePatternResolver testee;

    @Before
    public void onBefore() {
        testee = new ConfigServerResourcePatternResolver(restTemplate, "openwms-config", "http", "user", "sa");
    }

    public final
    @Test
    void shall_resolve_from_GitHub() {
        Resource resource = testee.getResource("config:routing-service");
        assertThat(resource.exists()).isTrue();
    }
}
