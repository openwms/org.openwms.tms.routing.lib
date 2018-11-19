/*
 * Copyright 2018 Heiko Scherrer
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
package org.openwms;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * A OwmsProperties.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Configuration
@ConfigurationProperties("owms")
public class OwmsProperties {

    private Map<String, String> partners = new HashMap<>();

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
}
