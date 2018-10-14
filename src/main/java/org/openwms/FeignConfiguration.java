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

import feign.auth.BasicAuthRequestInterceptor;
import io.interface21.cloud.ui.UIPackage;
import org.openwms.common.location.api.LocationGroupApi;
import org.openwms.common.transport.api.TransportUnitApi;
import org.openwms.tms.routing.InputContext;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * A FeignConfiguration.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Configuration
//@ExcludeFromScan
@AutoConfigureOrder(0)
//@AutoConfigureBefore(DataSourceAutoConfiguration.class)
//@AutoConfigureAfter(ModuleConfiguration.class)
//@AutoConfigureBefore(JpaProcessEngineAutoConfiguration.JpaConfiguration.class)
@EnableFeignClients(basePackageClasses = {TransportUnitApi.class, UIPackage.class, LocationGroupApi.class})
public class FeignConfiguration {

    @Bean
    public InputContext in(TransportUnitApi transportUnitApi) {
        System.out.println(transportUnitApi);
        return new InputContext();
    }

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor("user", "sa");
    }
}
