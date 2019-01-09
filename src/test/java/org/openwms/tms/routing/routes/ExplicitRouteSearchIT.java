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
package org.openwms.tms.routing;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openwms.tms.routing.routes.ExplicitRouteSearch;
import org.openwms.tms.routing.routes.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * A ExplicitRouteSearchIT.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "org.openwms")
@EntityScan(basePackages = "org.openwms")
@ActiveProfiles("SIMPLE")
public class ExplicitRouteSearchIT {

    @Autowired
    private ExplicitRouteSearch testee;


    public @Test
    void shall_fail_when_no_source_is_given() {
        try {
            testee.findBy(null, "IPUNKT", "LAGER");
            fail("Should fail because no source is given");
        } catch (IllegalArgumentException iae) {
            assertTrue(iae.getMessage().contains("sourceLocation"));
            // ok
        }
    }

    public @Test
    void shall_fail_when_no_target_is_given() {
        try {
            testee.findBy("STCK/0001/0001/0000/0000", null, null);
            fail("Should fail because no target is given at all");
        } catch (IllegalArgumentException iae) {
            assertTrue(iae.getMessage().contains("target"));
            // ok
        }
    }

    public @Test
    void shall_fail_when_no_target_is_given3() {
        try {
            testee.findBy("STCK/0001/0001/0000/0000", " ", null);
            fail("Should fail because no target is given");
        } catch (IllegalArgumentException iae) {
            assertTrue(iae.getMessage().contains("target"));
            // ok
        }
    }

    public @Test
    void shall_fail_when_no_target_is_given4() {
        try {
            testee.findBy("STCK/0001/0001/0000/0000", null, " ");
            fail("Should fail because no target is given");
        } catch (IllegalArgumentException iae) {
            assertTrue(iae.getMessage().contains("targetLocation"));
            // ok
        }
    }

    public @Test
    void shall_fail_with_invalid_target() {
        try {
            testee.findBy("STCK/0001/0001/0000/0000", "", "STOCK");
            fail("Should fail because no route exists");
        } catch (NoRouteException iae) {
            assertTrue(iae.getMessage().contains("No route"));
            // ok
        }
    }

    public @Test
    void shall_pass_when_at_least_one_target_is_given() {
        Route result = testee.findBy("STCK/0001/0001/0000/0000", "STCK/0001/0002/0000/0000", null);
        Assert.assertNotEquals("", result.getRouteId());
    }

    public @Test
    void shall_pass_when_at_least_one_target_is_given2() {
        Route result = testee.findBy("STCK/0001/0001/0000/0000", null, "FGRECEIVING");
        Assert.assertEquals("REC_CONV", result.getRouteId());
    }

    public @Test
    void shall_pass_when_all_targets_are_given() {
        Route result = testee.findBy("STCK/0001/0001/0000/0000", "STCK/0001/0002/0000/0000", "FGRECEIVING");
        Assert.assertEquals("SRC_TRG", result.getRouteId());
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        ExplicitRouteSearch explicitRouteSearch(RouteRepository routeRepository) {
            return new ExplicitRouteSearch(routeRepository);
        }
    }
}
