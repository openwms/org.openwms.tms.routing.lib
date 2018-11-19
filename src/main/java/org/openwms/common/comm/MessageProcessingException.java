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

/**
 * A MessageProcessingException is a general exception that indicates a fault situation during message processing.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
public class MessageProcessingException extends RuntimeException {

    /**
     * Create a new MessageProcessingException.
     *
     * @param message Detail message
     * @param cause Cause to be propagated
     */
    public MessageProcessingException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Create a new MessageProcessingException.
     *
     * @param message Detail message
     */
    public MessageProcessingException(String message) {
        super(message);
    }
}
