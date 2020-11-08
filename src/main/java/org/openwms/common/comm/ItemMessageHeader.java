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
package org.openwms.common.comm;

import org.openwms.common.comm.osip.CommConstants;
import org.openwms.tms.routing.InputContext;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A ItemMessageHeader.
 *
 * @author Heiko Scherrer
 */
public class ItemMessageHeader implements Serializable {

    private String sequenceNo;
    private String sender;
    private String receiver;

    public String getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(String sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Map<String, Object> getAll() {
        Map<String, Object> result = new HashMap<>(3);
        result.put(CommConstants.SEQUENCE_NO, sequenceNo);
        result.put(CommConstants.SENDER, sender);
        result.put(CommConstants.RECEIVER, receiver);
        return Collections.unmodifiableMap(result);
    }

    void addFields(InputContext in) {
        in.addBeanToMsg(CommConstants.SEQUENCE_NO, sequenceNo);
        in.addBeanToMsg(CommConstants.SENDER, sender);
        in.addBeanToMsg(CommConstants.RECEIVER, receiver);
    }
}
