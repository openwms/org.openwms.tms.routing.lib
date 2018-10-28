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
package org.openwms.common.comm.upd;

import org.openwms.tms.routing.InputContext;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * A UpdateHeaderVO.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
public class UpdateHeaderVO implements Serializable {

    private String sequenceNo, sender, receiver;

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
        result.put("sequenceNo", sequenceNo);
        result.put("sender", sender);
        result.put("receiver", receiver);
        return result;
    }

    void addFields(InputContext in) {
        in.addBeanToMsg("sequenceNo", sequenceNo);
        in.addBeanToMsg("sender", sender);
        in.addBeanToMsg("receiver", receiver);
    }
}
