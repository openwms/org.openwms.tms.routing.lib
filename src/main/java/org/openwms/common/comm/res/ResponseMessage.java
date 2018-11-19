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
