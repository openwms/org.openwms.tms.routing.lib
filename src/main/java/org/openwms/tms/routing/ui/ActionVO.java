/*
 * Copyright 2005-2021 the original author or authors.
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
package org.openwms.tms.routing.ui;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.ameba.http.AbstractBase;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ActionVO.
 *
 * @author Heiko Scherrer
 */
public class ActionVO extends AbstractBase implements Serializable {

    @NotNull
    @JsonProperty("name")
    private String name;
    @JsonProperty("type")
    private String type;
    @JsonProperty("description")
    private String description;
    @JsonProperty("route")
    private String route;
    @JsonProperty("program")
    private String program;
    @JsonProperty("locationGroupName")
    private String locationGroupName;
    @JsonProperty("location")
    private String location;
    @JsonProperty("key")
    private String key;
    @JsonProperty("enabled")
    private boolean enabled;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getLocationGroupName() {
        return locationGroupName;
    }

    public void setLocationGroupName(String locationGroupName) {
        this.locationGroupName = locationGroupName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean hasRoute() {
        return route != null && !route.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        ActionVO actionVO = (ActionVO) o;
        return enabled == actionVO.enabled && Objects.equals(name, actionVO.name) && Objects.equals(type, actionVO.type) && Objects.equals(description, actionVO.description) && Objects.equals(route, actionVO.route) && Objects.equals(program, actionVO.program) && Objects.equals(locationGroupName, actionVO.locationGroupName) && Objects.equals(location, actionVO.location) && Objects.equals(key, actionVO.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, type, description, route, program, locationGroupName, location, key, enabled);
    }
}
