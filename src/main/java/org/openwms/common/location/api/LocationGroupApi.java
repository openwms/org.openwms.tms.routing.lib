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
package org.openwms.common.location.api;

import org.openwms.common.LocationGroupVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.Optional;

/**
 * A LocationGroupApi.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@FeignClient(name = "common-service", qualifier = "locationGroupApi", decode404 = true)
public interface LocationGroupApi {

    @GetMapping(value = "/v1/locationgroups", params = {"name"})
    Optional<LocationGroupVO> findByName(@RequestParam("name") String name);
    /**
     * This method is used to update the state of a LocationGroup with an errorCode String, usually coming from a SYSU telegram.
     *
     * @param locationGroupName The name of the LocationGroup
     * @param errorCode The error code as String
     */
    @PatchMapping(value = "/v1/locationgroups", params = {"name"})
    void updateState(@RequestParam(name = "name") String locationGroupName, @RequestBody ErrorCodeVO errorCode);

    /**
     * Find and return all existing {@code LocationGroup} representations.
     *
     * @return Never {@literal null}
     */
    @GetMapping("/v1/locationgroups")
    Collection<LocationGroupVO> findAll();
}
