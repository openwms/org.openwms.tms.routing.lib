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
package org.openwms.tms.routing.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * A OwmsProperties.
 *
 * @author Heiko Scherrer
 */
@Configuration
@ConfigurationProperties("owms")
public class OwmsProperties {

    private Map<String, String> partners = new HashMap<>();
    private Routing routing;

    public Map<String, String> getPartners() {
        return this.partners;
    }

    public Optional<String> getPartner(String key) {
        return partners
                .entrySet()
                .stream()
                .filter(e -> e.getKey().equalsIgnoreCase(key))
                .findFirst()
                .map(Map.Entry::getValue);
    }

    public Routing getRouting() {
        return routing;
    }

    public void setRouting(Routing routing) {
        this.routing = routing;
    }

    public static class Routing {
        private String serialization;

        public String getSerialization() {
            return serialization;
        }

        public void setSerialization(String serialization) {
            this.serialization = serialization;
        }
    }
}
