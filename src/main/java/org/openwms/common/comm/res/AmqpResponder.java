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

import org.ameba.annotation.Measured;
import org.openwms.core.SpringProfiles;
import org.openwms.tms.routing.InputContext;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * A AmqpResponder.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Profile(SpringProfiles.ASYNCHRONOUS_PROFILE)
@Service("responder")
public class AmqpResponder implements ResResponder {

    private final InputContext in;
    private final AmqpTemplate amqpTemplate;
    private final String queueName;

    @Autowired
    public AmqpResponder(InputContext in, AmqpTemplate amqpTemplate, @Value("${owms.driver.res.queue-name}") String queueName) {
        this.in = in;
        this.amqpTemplate = amqpTemplate;
        this.queueName = queueName;
    }

    @Measured
    @Override
    public void sendToLocation(String target) {
        ResponseHeader header = ResponseHeader.newBuilder()
                .sender("" + in.getMsg().get("receiver"))
                .receiver(""+in.getMsg().get("sender"))
                .sequenceNo(""+in.getMsg().get("sequenceNo"))
                .build();

        ResponseMessage.Builder builder = ResponseMessage
                .newBuilder()
                .header(header)
                .barcode(""+in.getMsg().get("barcode"))
                .actualLocation(""+in.getMsg().get("actualLocation"))
                .targetLocation(target)
                .errorCode(""+in.getMsg().get("errorCode"))
                ;
        amqpTemplate.convertAndSend(queueName, builder.build());
    }

}
