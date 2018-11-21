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

/**
 * A InputContext.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
public class InputContext {

    private Map<String, Object> msg = new ConcurrentHashMap<>();

    public void addBeanToMsg(String name, Object bean) {
        msg.put(name, bean);
    }

    public Map<String, Object> getMsg() {
        return msg;
    }

    public <T extends Object> Optional<T> get(String key, Class<T> clazz) {
        return (Optional<T>) Optional.ofNullable(this.msg.get(key));
    }

    public void setMsg(Map<String, Object> msg) {
        this.msg = msg;
    }

    public void put(String key, Object val) {
        this.msg.put(key, val);
    }

    public void putAll(Map<String, Object> msg) {
        this.msg.putAll(msg);
    }
}
