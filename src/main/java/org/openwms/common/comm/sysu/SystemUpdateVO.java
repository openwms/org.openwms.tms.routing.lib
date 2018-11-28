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
package org.openwms.common.comm.sysu;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.openwms.common.location.api.ErrorCodeVO;

import java.beans.ConstructorProperties;
import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * A SystemUpdateVO.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
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

    @ConstructorProperties({"created", "locationGroupName", "errorCode"})
    public SystemUpdateVO(Date created, String locationGroupName, String errorCode) {
        this.created = created;
        this.locationGroupName = locationGroupName;
        this.errorCode = errorCode;
    }

    public SystemUpdateVO() {
    }

    public Map<String, Object> getAll() {
        Map<String, Object> result = new HashMap<>(3);
        result.put("created", created);
        result.put("locationGroupName", locationGroupName);
        result.put("errorCode", new ErrorCodeVO(errorCode));
        return Collections.unmodifiableMap(result);
    }

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

    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof SystemUpdateVO))
            return false;
        final SystemUpdateVO other = (SystemUpdateVO) o;
        if (!other.canEqual((Object) this))
            return false;
        final Object this$created = this.getCreated();
        final Object other$created = other.getCreated();
        if (this$created == null ? other$created != null : !this$created.equals(other$created))
            return false;
        final Object this$locationGroupName = this.getLocationGroupName();
        final Object other$locationGroupName = other.getLocationGroupName();
        if (this$locationGroupName == null ? other$locationGroupName != null : !this$locationGroupName.equals(other$locationGroupName))
            return false;
        final Object this$errorCode = this.getErrorCode();
        final Object other$errorCode = other.getErrorCode();
        if (this$errorCode == null ? other$errorCode != null : !this$errorCode.equals(other$errorCode))
            return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $created = this.getCreated();
        result = result * PRIME + ($created == null ? 43 : $created.hashCode());
        final Object $locationGroupName = this.getLocationGroupName();
        result = result * PRIME + ($locationGroupName == null ? 43 : $locationGroupName.hashCode());
        final Object $errorCode = this.getErrorCode();
        result = result * PRIME + ($errorCode == null ? 43 : $errorCode.hashCode());
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof SystemUpdateVO;
    }
}