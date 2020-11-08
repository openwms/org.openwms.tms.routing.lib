/*
 * Copyright 2019 Heiko Scherrer
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
package org.openwms.common.comm.osip;

/**
 * A CommConstants.
 *
 * @author Heiko Scherrer
 */
public final class CommConstants {

    public static final String PREFIX = "osip_";
    public static final String SENDER = PREFIX + "sender";
    public static final String RECEIVER = PREFIX + "receiver";
    public static final String SEQUENCE_NO = PREFIX +"sequenceno";
    public static final String ERROR_CODE = "errorCode";
    public static final String BARCODE = "barcode";
    public static final String ACTUAL_LOCATION = "actualLocation";
    public static final String CREATED = "created";
    public static final String LOCATION_GROUP = "locationGroup";

    private CommConstants() {}
}
