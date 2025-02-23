/*
 * Copyright 2005-2025 the original author or authors.
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
package org.openwms.tms.routing.routes.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openwms.tms.routing.routes.NoRouteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * A ExplicitRouteSearchIT.
 *
 * @author Heiko Scherrer
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "org.openwms.tms.routing")
@EntityScan(basePackages = "org.openwms.tms.routing")
@Sql(scripts = "classpath:testdata.sql")
@ActiveProfiles("SIMPLE")
class ExplicitRouteSearchIT {

    @Autowired
    private ExplicitRouteSearch testee;

    @Test
    void shall_fail_when_no_source_is_given() {
        try {
            testee.findBy(null, "IPUNKT", "LAGER");
            fail("Should fail because no source is given");
        } catch (IllegalArgumentException iae) {
            assertTrue(iae.getMessage().contains("sourceLocation"));
            // ok
        }
    }

    @Test
    void shall_fail_when_no_target_is_given() {
        try {
            testee.findBy("STCK/0001/0001/0000/0000", null, null);
            fail("Should fail because no target is given at all");
        } catch (IllegalArgumentException iae) {
            assertTrue(iae.getMessage().contains("target"));
            // ok
        }
    }

    @Test
    void shall_fail_when_no_target_is_given3() {
        try {
            testee.findBy("STCK/0001/0001/0000/0000", " ", null);
            fail("Should fail because no target is given");
        } catch (IllegalArgumentException iae) {
            assertTrue(iae.getMessage().contains("target"));
            // ok
        }
    }

    @Test
    void shall_fail_when_no_target_is_given4() {
        try {
            testee.findBy("STCK/0001/0001/0000/0000", null, " ");
            fail("Should fail because no target is given");
        } catch (IllegalArgumentException iae) {
            assertTrue(iae.getMessage().contains("targetLocation"));
            // ok
        }
    }

    @Test
    void shall_fail_with_invalid_target() {
        try {
            testee.findBy("STCK/0001/0001/0000/0000", "", "STOCK");
            fail("Should fail because no route exists");
        } catch (NoRouteException iae) {
            assertTrue(iae.getMessage().contains("No route"));
            // ok
        }
    }

    @Test
    void shall_pass_when_at_least_one_target_is_given() {
        var result = testee.findBy("STCK/0001/0001/0000/0000", "STCK/0001/0002/0000/0000", null);
        assertNotEquals("", result.getRouteId());
    }

    @Test
    void shall_pass_when_at_least_one_target_is_given2() {
        var result = testee.findBy("STCK/0001/0001/0000/0000", null, "FGRECEIVING");
        assertEquals("REC_CONV", result.getRouteId());
    }

    public @Test
    void shall_pass_when_all_targets_are_given() {
        var result = testee.findBy("STCK/0001/0001/0000/0000", "STCK/0001/0002/0000/0000", "FGRECEIVING");
        assertEquals("SRC_TRG", result.getRouteId());
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        ExplicitRouteSearch explicitRouteSearch(RouteRepository routeRepository) {
            return new ExplicitRouteSearch(routeRepository);
        }
    }
}
