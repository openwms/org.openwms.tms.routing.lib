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

import org.ameba.annotation.EnableAspects;
import org.ameba.mapping.BeanMapper;
import org.ameba.mapping.DozerMapperImpl;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

/**
 * A ModuleConfiguration.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Configuration
@EnableDiscoveryClient
@EnableTransactionManagement
@EnableAspects(propagateRootCause = true)
@EnableConfigurationProperties
@EnableJpaRepositories(basePackages = "org.openwms")
@EntityScan(basePackages = "org.openwms")
public class ModuleConfiguration {

    public
    @LoadBalanced
    @Bean
    RestTemplate aLoadBalanced() {
        return new RestTemplate();
    }

    public
    @Bean
    RestTemplate simpleRestTemplate() {
        return new RestTemplate();
    }

    public
    @Bean
    BeanMapper beanMapper() {
        return new DozerMapperImpl("META-INF/dozer/tms-bean-mappings.xml");
    }

    @Bean OwmsProperties owmsProperties() {
        return new OwmsProperties();
    }
}
