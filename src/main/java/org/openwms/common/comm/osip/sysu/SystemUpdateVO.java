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
package org.openwms.common.comm.osip.sysu;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.openwms.common.location.api.ErrorCodeVO;

import java.beans.ConstructorProperties;
import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * A SystemUpdateVO.
 *
 * @author <a href="mailto:hscherrer@openwms.org">Heiko Scherrer</a>
 */
class SystemUpdateVO implements Serializable {

    @JsonProperty
    private String type;
    @JsonProperty
    private String locationGroupName;
    @JsonProperty
    private String errorCode;
    @JsonProperty
    private Date created;

    /*~-------------------- Constructors --------------------*/
    @ConstructorProperties({"created", "locati  onGroupName", "errorCode"})
    public SystemUpdateVO(Date created, String locationGroupName, String errorCode) {
        this.created = created;
        this.locationGroupName = locationGroupName;
        this.errorCode = errorCode;
    }

    public SystemUpdateVO() {
    }

    /*~-------------------- Methods --------------------*/
    public Map<String, Object> getAll() {
        Map<String, Object> result = new HashMap<>(3);
        result.put("created", created);
        result.put("locationGroupName", locationGroupName);
        result.put("errorCode", new ErrorCodeVO(errorCode));
        return Collections.unmodifiableMap(result);
    }

    /*~-------------------- Accessors --------------------*/
    public Date getCreated() {
        return this.created;
    }

    public String getLocationGroupName() {
        return this.locationGroupName;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public void setLocationGroupName(String locationGroupName) {
        this.locationGroupName = locationGroupName;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /*~-------------------- Overrides --------------------*/
    @Override
    public String toString() {
        return new StringJoiner(", ", SystemUpdateVO.class.getSimpleName() + "[", "]").add("type='" + type + "'").add("locationGroupName='" + locationGroupName + "'").add("errorCode='" + errorCode + "'").add("created=" + created).toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        SystemUpdateVO that = (SystemUpdateVO) o;
        return Objects.equals(type, that.type) && Objects.equals(locationGroupName, that.locationGroupName) && Objects.equals(errorCode, that.errorCode) && Objects.equals(created, that.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, locationGroupName, errorCode, created);
    }
}