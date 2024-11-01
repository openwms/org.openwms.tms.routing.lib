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
package org.openwms.tms.routing;

import org.ameba.integration.jpa.ApplicationEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * An Action declares what needs to be done (executed) in case an event or trigger occurs at a given Location or LocationGroup.
 *
 * @author Heiko Scherrer
 */
@Entity
@Table(name = "TMS_RSRV_ACTION")
public class Action extends ApplicationEntity {

    @NotNull
    @ManyToOne
    @JoinColumn(name = "C_ROUTE_ID")
    private RouteImpl route;
    @NotNull
    @Column(name = "C_PROGRAM_NAME")
    private String programKey;
    @NotNull
    @Column(name = "C_NAME", unique = true)
    private String name;
    @Column(name = "C_LOCATION_KEY")
    private String locationKey;
    @Column(name = "C_LOCATION_GROUP_NAME")
    private String locationGroupName;
    @NotNull
    @Column(name = "C_ACTION_TYPE")
    private String actionType;
    @NotNull
    @Column(name = "C_DESCRIPTION")
    private String description;
    @Column(name = "C_ENABLED")
    private boolean enabled = true;
    @Column(name = "C_FLEX_1")
    private String flexField1;
    @Column(name = "C_FLEX_2")
    private String flexField2;
    @Column(name = "C_FLEX_3")
    private String flexField3;
    @Column(name = "C_FLEX_4")
    private String flexField4;
    @Column(name = "C_FLEX_5")
    private String flexField5;

    /** Dear JPA... */
    protected Action() {
    }

    public Action(String programKey, String description) {
        this.name = programKey;
        this.programKey = programKey;
        this.description = description;
    }

    public Action(RouteImpl route, String name, String locationKey, String locationGroupName, String actionType, String programKey, String description) {
        this.route = route;
        this.name = name;
        this.locationKey = locationKey;
        this.locationGroupName = locationGroupName;
        this.actionType = actionType;
        this.programKey = programKey;
        this.description = description;
    }

    @Override
    public void setPersistentKey(String pKey) {
        super.setPersistentKey(pKey);
    }

    public Map<String, Object> getFlexVariables() {
        Map<String, Object> result = new HashMap<>(5);
        result.put("flexField1", flexField1);
        result.put("flexField2", flexField2);
        result.put("flexField3", flexField3);
        result.put("flexField4", flexField4);
        result.put("flexField5", flexField5);
        return result;
    }

    public RouteImpl getRoute() {
        return route;
    }

    public void setRoute(RouteImpl route) {
        this.route = route;
    }

    public String getProgramKey() {
        return programKey;
    }

    public void setProgramKey(String programKey) {
        this.programKey = programKey;
    }

    public String getName() {
        return name;
    }

    public String getLocationKey() {
        return locationKey;
    }

    public void setLocationKey(String locationKey) {
        this.locationKey = locationKey;
    }

    public String getLocationGroupName() {
        return locationGroupName;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getDescription() {
        return description;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getFlexField1() {
        return flexField1;
    }

    public String getFlexField2() {
        return flexField2;
    }

    public String getFlexField3() {
        return flexField3;
    }

    public String getFlexField4() {
        return flexField4;
    }

    public String getFlexField5() {
        return flexField5;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Action.class.getSimpleName() + "[", "]").add("route=" + route).add("programKey='" + programKey + "'").add("name='" + name + "'").add("locationKey='" + locationKey + "'").add("locationGroupName='" + locationGroupName + "'").add("actionType='" + actionType + "'").add("description='" + description + "'").add("enabled=" + enabled).add("flexField1='" + flexField1 + "'").add("flexField2='" + flexField2 + "'").add("flexField3='" + flexField3 + "'").add("flexField4='" + flexField4 + "'").add("flexField5='" + flexField5 + "'").toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Action action = (Action) o;
        return enabled == action.enabled && Objects.equals(route, action.route) && Objects.equals(programKey, action.programKey)
                && Objects.equals(name, action.name) && Objects.equals(locationKey, action.locationKey)
                && Objects.equals(locationGroupName, action.locationGroupName) && Objects.equals(actionType, action.actionType)
                && Objects.equals(description, action.description) && Objects.equals(flexField1, action.flexField1)
                && Objects.equals(flexField2, action.flexField2) && Objects.equals(flexField3, action.flexField3)
                && Objects.equals(flexField4, action.flexField4) && Objects.equals(flexField5, action.flexField5);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), route, programKey, name, locationKey, locationGroupName, actionType, description, enabled, flexField1, flexField2, flexField3, flexField4, flexField5);
    }
}
