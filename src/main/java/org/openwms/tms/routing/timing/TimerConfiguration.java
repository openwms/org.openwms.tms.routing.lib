/*
 * Copyright 2005-2024 the original author or authors.
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
package org.openwms.tms.routing.timing;

import org.ameba.integration.jpa.ApplicationEntity;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;

/**
 * A TimerConfiguration stores timer configuration when a workflow should be executed.
 *
 * @author Heiko Scherrer
 */
@Entity
@Table(name = "TMS_RSRV_TIMER_CONFIG")
public class TimerConfiguration extends ApplicationEntity {

    /** Name of the workflow to execute. */
    @NotBlank
    @Column(name = "C_NAME")
    private String name;
    /** A descriptive text for the configuration. */
    @Column(name = "C_DESCRIPTION")
    private String description;
    /** A 6-digit Spring Cron expression, like {@literal 10 * * * * ?}. */
    @NotBlank
    @Column(name = "C_CRON_EXPRESSION")
    private String cronExpression;
    /** An arbitrary map of runtime variables that are passed to the workflow execution. */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "TMS_RSRV_TIMER_CONFIG_VARS",
            joinColumns = {
                    @JoinColumn(name = "C_TC_PK", referencedColumnName = "C_PK")
            },
            foreignKey = @ForeignKey(name = "FK_TIMER_CFG_VAR")
    )
    @MapKeyColumn(name = "C_KEY")
    @Column(name = "C_VALUE")
    private Map<String, String> runtimeVariables = new HashMap<>();

    protected TimerConfiguration() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public Map<String, String> getRuntimeVariables() {
        return runtimeVariables;
    }

    public void setRuntimeVariables(Map<String, String> runtimeVariables) {
        this.runtimeVariables = runtimeVariables;
    }
}
