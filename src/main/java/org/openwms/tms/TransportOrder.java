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
package org.openwms.tms;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * A TransportOrder.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
// ajc has a problem here with lombok
public class TransportOrder implements Serializable {

    @JsonProperty("persistentKey")
    private String id;
    @JsonProperty("transportUnitBK")
    private String transportUnitId;
    @JsonProperty
    private String routeId;
    @JsonProperty
    private String sourceLocation;
    @JsonProperty
    private String targetLocation;
    @JsonProperty
    private String targetLocationGroup;

    @JsonCreator
    public TransportOrder() {
    }

    public String getId() {
        return id;
    }

    public String getTransportUnitId() {
        return transportUnitId;
    }

    public String getRouteId() {
        return routeId;
    }

    public String getSourceLocation() {
        return sourceLocation;
    }

    public String getTargetLocation() {
        return targetLocation;
    }

    public String getTargetLocationGroup() {
        return targetLocationGroup;
    }

    public Map<String, Object> getAll() {
        Map<String, Object> result = new HashMap<>(6);
        result.put("toId", id);
        result.put("to-barcode", transportUnitId);
        result.put("to-sourceLocation", sourceLocation);
        result.put("to-targetLocation", targetLocation);
        result.put("to-targetLocationGroup", targetLocationGroup);
        return result;
    }
}
