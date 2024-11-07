/*
 * Copyright 2005-2024 the original author or authors.
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
package org.openwms.common.comm.osip.err;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.openwms.common.comm.osip.CommConstants;
import org.openwms.common.location.api.ErrorCodeVO;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * A ErrorMessageVO.
 *
 * @author Heiko Scherrer
 */
class ErrorMessageVO implements Serializable {

    @JsonProperty
    private String type = "ERR";
    @JsonProperty
    private String errorCode;
    @JsonProperty
    private String locationGroupName;
    @JsonProperty
    private Date created;

    /*~-------------------- Methods --------------------*/
    public Map<String, Object> getAll() {
        Map<String, Object> result = new HashMap<>(4);
        result.put(CommConstants.LOCATION_GROUP, locationGroupName);
        result.put(CommConstants.ERROR_CODE, new ErrorCodeVO(errorCode));
        result.put(CommConstants.CREATED, created);
        return Collections.unmodifiableMap(result);
    }

    /*~-------------------- Accessors --------------------*/
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getLocationGroupName() {
        return locationGroupName;
    }

    public void setLocationGroupName(String locationGroupName) {
        this.locationGroupName = locationGroupName;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getCreated() {
        return this.created;
    }

    /*~-------------------- Overrides --------------------*/
    /**
     * {@inheritDoc}
     *
     * All fields.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ErrorMessageVO that = (ErrorMessageVO) o;
        return Objects.equals(type, that.type) && Objects.equals(errorCode, that.errorCode) && Objects.equals(locationGroupName, that.locationGroupName) && Objects.equals(created, that.created);
    }

    /**
     * {@inheritDoc}
     *
     * All fields.
     */
    @Override
    public int hashCode() {
        return Objects.hash(type, errorCode, locationGroupName, created);
    }

    /**
     * {@inheritDoc}
     *
     * All fields.
     */
    @Override
    public String toString() {
        return new StringJoiner(", ", ErrorMessageVO.class.getSimpleName() + "[", "]").add("type='" + type + "'").add("errorCode='" + errorCode + "'").add("locationGroupName='" + locationGroupName + "'").add("created=" + created).toString();
    }
}