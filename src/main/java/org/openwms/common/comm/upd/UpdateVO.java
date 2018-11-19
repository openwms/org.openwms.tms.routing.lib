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
package org.openwms.common.comm.upd;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * A UpdateVO.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
// ajc has a problem here with lombok
public class UpdateVO implements Serializable {

    private String actualLocation, barcode, errorCode;

    public void setActualLocation(String actualLocation) {
        this.actualLocation = actualLocation;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public void setHeader(UpdateHeaderVO header) {
        this.header = header;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    private UpdateHeaderVO header;

    public Map<String, Object> getAll() {
        Map<String, Object> result = new HashMap<>(4);
        result.put("actualLocation", actualLocation);
        result.put("barcode", barcode);
        result.put("errorCode", errorCode);
        result.put("reqHeader", header);
        return result;
    }

    public String getActualLocation() {
        return actualLocation;
    }

    public String getBarcode() {
        return barcode;
    }

    public UpdateHeaderVO getHeader() {
        return header;
    }
}
