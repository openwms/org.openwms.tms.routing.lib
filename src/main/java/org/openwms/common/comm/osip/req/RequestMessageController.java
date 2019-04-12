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
package org.openwms.common.comm.osip.req;

import org.openwms.common.comm.ItemMessage;
import org.openwms.common.comm.ItemMessageHandler;
import org.openwms.common.comm.osip.OSIP;
import org.openwms.core.SpringProfiles;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * A RequestMessageController is the http endpoint of the routing service component to
 * process REQ_ messages.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Profile("!"+ SpringProfiles.ASYNCHRONOUS_PROFILE)
@OSIP
@RestController
class RequestMessageController {

    private final ItemMessageHandler handler;

    RequestMessageController(ItemMessageHandler handler) {
        this.handler = handler;
    }

    @PostMapping("/req")
    void handle(@RequestBody ItemMessage msg) {
        handler.handle(msg);
    }
}
