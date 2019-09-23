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
package org.openwms.common.comm.osip.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.openwms.common.comm.osip.CommConstants;

import java.io.Serializable;

/**
 * A ResponseHeader.
 *
 * @author <a href="mailto:hscherrer@openwms.org">Heiko Scherrer</a>
 */
public class ResponseHeader implements Serializable {
    @JsonProperty(CommConstants.SENDER)
    private String sender;
    @JsonProperty(CommConstants.RECEIVER)
    private String receiver;
    @JsonProperty(CommConstants.SEQUENCE_NO)
    private String sequenceNo;

    public ResponseHeader() {
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setSequenceNo(String sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    private ResponseHeader(Builder builder) {
        sender = builder.sender;
        receiver = builder.receiver;
        sequenceNo = builder.sequenceNo;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getSequenceNo() {
        return sequenceNo;
    }


    public static final class Builder {
        private String sender;
        private String receiver;
        private String sequenceNo;

        private Builder() {
        }

        public Builder sender(String val) {
            sender = val;
            return this;
        }

        public Builder receiver(String val) {
            receiver = val;
            return this;
        }

        public Builder sequenceNo(String val) {
            sequenceNo = val;
            return this;
        }

        public ResponseHeader build() {
            return new ResponseHeader(this);
        }
    }
}
