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
package org.openwms.tms.routing;

/**
 * A ProgramResult.
 *
 * @author Heiko Scherrer
 */
public class ProgramResult {

    private String barcode;
    private String actualLocation;
    private String targetLocation;
    private String locationGroupName;
    private String error;

    public String getBarcode() {
        return barcode;
    }

    public String getActualLocation() {
        return actualLocation;
    }

    public String getTargetLocation() {
        return targetLocation;
    }

    public String getLocationGroupName() {
        return locationGroupName;
    }

    public String getError() {
        return error;
    }
}
