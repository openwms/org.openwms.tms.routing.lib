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

import java.util.Map;
import java.util.Optional;

/**
 * A ProgramExecutor is responsible for the execution of material flow programs.
 *
 * @author Heiko Scherrer
 */
public interface ProgramExecutor {

    /**
     * Execute the Action also called {@code program} with the given input parameters {@code runtimeVariables}.
     *
     * @param program The program to execute
     * @param runtimeVariables A map of input values, passed to the program
     * @return The program result
     */
    Optional<ProgramResult> execute(Action program, Map<String, Object> runtimeVariables);
}
