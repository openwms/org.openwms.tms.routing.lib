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
package org.openwms.tms.routing.ui;

import org.ameba.http.AbstractBase;

import java.io.Serializable;
import java.util.Objects;

/**
 * A RouteVO.
 *
 * @author Heiko Scherrer
 */
// ajc has a problem here with lombok
public class RouteVO extends AbstractBase<RouteVO> implements Serializable {

    private String name;
    private String description;
    private String sourceLocationName;
    private String sourceLocationGroupName;
    private String targetLocationName;
    private String targetLocationGroupName;
    private String key;
    private boolean enabled;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSourceLocationName() {
        return sourceLocationName;
    }

    public void setSourceLocationName(String sourceLocationName) {
        this.sourceLocationName = sourceLocationName;
    }

    public boolean hasSourceLocationName() {
        return sourceLocationName != null && !sourceLocationName.isEmpty();
    }

    public String getSourceLocationGroupName() {
        return sourceLocationGroupName;
    }

    public void setSourceLocationGroupName(String sourceLocationGroupName) {
        this.sourceLocationGroupName = sourceLocationGroupName;
    }

    public String getTargetLocationName() {
        return targetLocationName;
    }

    public void setTargetLocationName(String targetLocationName) {
        this.targetLocationName = targetLocationName;
    }

    public boolean hasTargetLocationName() {
        return targetLocationName != null && !targetLocationName.isEmpty();
    }

    public String getTargetLocationGroupName() {
        return targetLocationGroupName;
    }

    public void setTargetLocationGroupName(String targetLocationGroupName) {
        this.targetLocationGroupName = targetLocationGroupName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        RouteVO routeVO = (RouteVO) o;
        return enabled == routeVO.enabled && Objects.equals(name, routeVO.name) && Objects.equals(description, routeVO.description) && Objects.equals(sourceLocationName, routeVO.sourceLocationName) && Objects.equals(sourceLocationGroupName, routeVO.sourceLocationGroupName) && Objects.equals(targetLocationName, routeVO.targetLocationName) && Objects.equals(targetLocationGroupName, routeVO.targetLocationGroupName) && Objects.equals(key, routeVO.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, description, sourceLocationName, sourceLocationGroupName, targetLocationName, targetLocationGroupName, key, enabled);
    }
}
