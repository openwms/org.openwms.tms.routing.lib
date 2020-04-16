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
package org.openwms.tms.routing.app;

import org.camunda.bpm.engine.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;

/**
 * A RoutingModuleConfiguration.
 *
 * @author Heiko Scherrer
 */
@Configuration
//@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "org.camunda",
        entityManagerFactoryRef = "camundaEntityManagerFactory",
        transactionManagerRef = "camundaTransactionManager"
)
//@EntityScan
public class CamundaConfiguration {

    @Bean
    public SpringProcessEngineConfiguration processEngineConfiguration (
            ResourcePatternResolver resourceLoader,
            DataSource camundaDataSource,
            PlatformTransactionManager camundaTransactionManager,
            @Value("${camunda.bpm.history-level:none}") String historyLevel
    ) throws IOException {
        SpringProcessEngineConfiguration config = new SpringProcessEngineConfiguration();

        config.setDataSource(camundaDataSource);
        config.setDatabaseSchemaUpdate("true");

        config.setTransactionManager(camundaTransactionManager);

        config.setHistory(historyLevel);

        config.setJobExecutorActivate(true);
        config.setMetricsEnabled(false);

        // deploy all processes from folder 'processes'
        Resource[] resources = resourceLoader.getResources("classpath:/camunda/processes/*.bpmn");
        config.setDeploymentResources(resources);

        return config;
    }

    @Bean
    @ConfigurationProperties(prefix = "owms.camunda.datasource")
    public DataSource camundaDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean camundaEntityManagerFactory(@Qualifier("camundaDataSource") DataSource camundaDataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(camundaDataSource);
        em.setPackagesToScan(new String[] { "org.camunda" });

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "create");
        properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        em.setJpaPropertyMap(properties);
        return em;
    }
    @Bean
    public PlatformTransactionManager camundaTransactionManager (
            @Qualifier("camundaEntityManagerFactory") EntityManagerFactory camundaEntityManagerFactory
    ) {
        return new JpaTransactionManager(camundaEntityManagerFactory);
    }
}
