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
package org.openwms.common.comm.res;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * A ResponseHeader.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
// ajc has a problem here with lombok
public class ResponseHeader implements Serializable {
    @JsonProperty
    private String sender, receiver, sequenceNo;

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
