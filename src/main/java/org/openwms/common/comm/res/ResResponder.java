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

/**
 * A ResResponder is sending messages to trigger a OSIP RES_ telegram to a given target.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
public interface ResResponder {

    /**
     * Send a message to fire a OSIP RES_ telegram to the given {@code target} location.
     *
     * @param targetLocation The target location
     */
    void sendToLocation(String targetLocation);

    /**
     * Send a message to fire a OSIP RES_ telegram to the given {@code target} location.
     *
     * @param targetLocation The target location
     */
    void sendToLocation(String barcode, String sourceLocation, String targetLocation);
}
