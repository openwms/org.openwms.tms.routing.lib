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

import feign.auth.BasicAuthRequestInterceptor;
import io.interface21.cloud.ui.UIPackage;
import org.openwms.common.location.api.LocationApi;
import org.openwms.common.location.api.LocationGroupApi;
import org.openwms.common.transport.api.TransportUnitApi;
import org.openwms.tms.api.TransportOrderApi;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * A RoutingFeignConfiguration activates client stubs using Feign to connect to other services. This is deactivated when the service is
 * packaged and deployed as part of a whole application.
 *
 * @author Heiko Scherrer
 */
@Profile("!INMEM")
@Configuration
@AutoConfigureOrder
@EnableFeignClients(
        basePackageClasses = {
                TransportUnitApi.class,
                UIPackage.class,
                LocationGroupApi.class,
                LocationApi.class,
                TransportOrderApi.class
        })
public class RoutingFeignConfiguration {

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor("user", "sa");
    }
}
