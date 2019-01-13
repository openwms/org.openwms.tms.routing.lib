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
package org.openwms.tms.routing;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * A InputContext.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
public class InputContext {

    private InheritableThreadLocal<Map<String, Object>> msg = new InheritableThreadLocal<Map<String, Object>>() {
        @Override
        protected Map<String, Object> initialValue() {
            return new ConcurrentHashMap<>();
        }
    };

    public void addBeanToMsg(String name, Object bean) {
        msg.get().put(name, bean);
    }

    public void clear() {
        this.msg.get().clear();
    }

    public Map<String, Object> getMsg() {
        return msg.get();
    }

    public <T> Optional<T> get(String key, Class<T> routeClass) {
        return (Optional<T>) Optional.ofNullable(this.msg.get().get(key));
    }

    public InputContext setMsg(Map<String, Object> msg) {
        this.msg.set(msg);
        return this;
    }

    public Object put(String key, Object val) {
        if (val != null) {
            this.msg.get().put(key, val);
        }
        return val;
    }

    public void putAll(Map<String, Object> msg) {
        if (msg != null) {
            this.msg.get().putAll(msg.entrySet().stream().filter(e -> e.getKey() != null && e.getValue() != null).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
        }
    }
}
