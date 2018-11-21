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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A ItemMessage.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
public class ItemMessage {

    private String type, actualLocation, locationGroupName, barcode, errorCode;
    private ItemMessageHeader header;

    /*~ ----------------------------- accessors ------------------- */
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getActualLocation() {
        return actualLocation;
    }

    public void setActualLocation(String actualLocation) {
        this.actualLocation = actualLocation;
    }

    public String getLocationGroupName() {
        return locationGroupName;
    }

    public void setLocationGroupName(String locationGroupName) {
        this.locationGroupName = locationGroupName;
    }

    public boolean hasLocationGroupName() {
        return locationGroupName != null && !locationGroupName.isEmpty();
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getBarcode() {
        return barcode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public ItemMessageHeader getHeader() {
        return header;
    }

    public void setHeader(ItemMessageHeader header) {
        this.header = header;
    }

    /*~ ----------------------------- methods ------------------- */
    public Map<String, Object> getAll() {
        Map<String, Object> result = new HashMap<>(4);
        result.put("actualLocation", actualLocation);
        if (hasLocationGroupName()) {
            result.put("locationGroupName", locationGroupName);
        }
        result.put("barcode", barcode);
        result.put("errorCode", errorCode);
        result.put("reqHeader", header);
        return Collections.unmodifiableMap(result);
    }
}
