/*
 * Copyright 2005-2022 the original author or authors.
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
package org.openwms.common.comm.osip.sysu;

import org.ameba.annotation.Measured;
import org.openwms.common.comm.osip.OSIP;
import org.openwms.core.SpringProfiles;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * A SystemUpdateMessageController.
 *
 * @author Heiko Scherrer
 */
@Profile("!"+SpringProfiles.ASYNCHRONOUS_PROFILE)
@OSIP
@RestController
class SystemUpdateMessageController {

    private final SystemUpdateMessageHandler handler;

    SystemUpdateMessageController(SystemUpdateMessageHandler handler) {
        this.handler = handler;
    }

    @Measured
    @PostMapping("/sysu")
    public void handleSYSU(@RequestBody SystemUpdateVO sysu) {
        handler.handle(sysu);
    }
}
