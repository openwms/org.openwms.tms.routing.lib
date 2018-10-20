/*
 * openwms.org, the Open Warehouse Management System.
 * Copyright (C) 2018 Heiko Scherrer
 *
 * This file is part of openwms.org.
 *
 * openwms.org is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * openwms.org is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this software. If not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.openwms.common.comm.sysu;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * A SystemUpdateVO.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
class SystemUpdateVO implements Serializable {

    @JsonProperty
    Date created;
    @JsonProperty
    String locationGroupName, errorCode;

    @java.beans.ConstructorProperties({"created", "locationGroupName", "errorCode"})
    public SystemUpdateVO(Date created, String locationGroupName, String errorCode) {
        this.created = created;
        this.locationGroupName = locationGroupName;
        this.errorCode = errorCode;
    }

    public SystemUpdateVO() {
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

    public String toString() {
        return "SystemUpdateVO(created=" + this.getCreated() + ", locationGroupName=" + this.getLocationGroupName() + ", errorCode=" + this.getErrorCode() + ")";
    }
}