/*
 * openwms.org, the Open Warehouse Management System.
 * Copyright (C) 2014 Heiko Scherrer
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
package org.openwms.common.comm.res;

import java.io.Serializable;
import java.util.Date;

/**
 * A OSIP ResponseMessage responds to an processed {@code RequestMessage}.
 *
 * See https://interface21-io.gitbook.io/osip/messaging-between-layer-n-and-layer-n-1#response-telegram-res_
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
// ajc has a problem here with lombok
public class ResponseMessage implements Serializable {

    private ResponseHeader header;
    private String barcode;
    private String actualLocation;
    private String targetLocation;
    private String targetLocationGroup;
    private String errorCode;
    private Date created;

    public ResponseMessage() {
    }

    public ResponseHeader getHeader() {
        return header;
    }

    public void setHeader(ResponseHeader header) {
        this.header = header;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getActualLocation() {
        return actualLocation;
    }

    public void setActualLocation(String actualLocation) {
        this.actualLocation = actualLocation;
    }

    public String getTargetLocation() {
        return targetLocation;
    }

    public void setTargetLocation(String targetLocation) {
        this.targetLocation = targetLocation;
    }

    public String getTargetLocationGroup() {
        return targetLocationGroup;
    }

    public void setTargetLocationGroup(String targetLocationGroup) {
        this.targetLocationGroup = targetLocationGroup;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    private ResponseMessage(Builder builder) {
        header = builder.header;
        barcode = builder.barcode;
        actualLocation = builder.actualLocation;
        targetLocation = builder.targetLocation;
        targetLocationGroup = builder.targetLocationGroup;
        errorCode = builder.errorCode;
        created = builder.created;
    }

    public static Builder newBuilder() {
        return new Builder();
    }


    public static final class Builder {
        private ResponseHeader header;
        private String barcode;
        private String actualLocation;
        private String targetLocation;
        private String targetLocationGroup;
        private String errorCode;
        private Date created;

        private Builder() {
        }

        public Builder header(ResponseHeader val) {
            header = val;
            return this;
        }

        public Builder barcode(String val) {
            barcode = val;
            return this;
        }

        public Builder actualLocation(String val) {
            actualLocation = val;
            return this;
        }

        public Builder targetLocation(String val) {
            targetLocation = val;
            return this;
        }

        public Builder targetLocationGroup(String val) {
            targetLocationGroup = val;
            return this;
        }

        public Builder errorCode(String val) {
            errorCode = val;
            return this;
        }

        public Builder created(Date val) {
            created = val;
            return this;
        }

        public ResponseMessage build() {
            if (created == null) {
                created = new Date();
            }
            return new ResponseMessage(this);
        }
    }
}
