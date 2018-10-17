/*
 * openwms.org, the Open Warehouse Management System.
 * Copyright (C) 2018 Heiko Scherrer
 *
 * This file is part of openwms.org.
 *
 * openwms.org is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * openwms.org is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this software. If not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.openwms.tms.routing;

import org.ameba.exception.NotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

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
public class ExplicitRouteSearchIT {

    @Autowired
    private ExplicitRouteSearch testee;


    public @Test(expected = IllegalArgumentException.class)
    void shall_fail_when_no_source_is_given() {
        testee.findBy(null, "IPUNKT", "LAGER");
    }

    public @Test(expected = IllegalArgumentException.class)
    void shall_fail_when_no_targetLoc_is_given() {
        testee.findBy(" ", "", " ");
    }

    public @Test(expected = IllegalArgumentException.class)
    void shall_fail_when_no_targetLG_is_given() {
        testee.findBy(" ", " ", "");
    }

    public @Test(expected = IllegalArgumentException.class)
    void shall_fail_when_no_target_is_given() {
        testee.findBy(" ", null, null);
    }

    public @Test(expected = IllegalArgumentException.class)
    void shall_fail_when_no_target_is_given2() {
        testee.findBy(" ", null, null);
    }

    public @Test(expected = IllegalArgumentException.class)
    void shall_fail_when_no_target_is_given3() {
        testee.findBy("STCK/0001/0001/0000/0000", null, null);
    }

    public @Test(expected = IllegalArgumentException.class)
    void shall_fail_when_no_target_is_given4() {
        testee.findBy("STCK/0001/0001/0000/0000", null, " ");
    }

    public @Test(expected = IllegalArgumentException.class)
    void shall_fail_when_no_target_is_given5() {
        testee.findBy("STCK/0001/0001/0000/0000", " ", null);
    }

    public @Test
    void shall_pass_when_at_least_one_target_is_given() {
        Route result = testee.findBy("STCK/0001/0001/0000/0000", "STCK/0001/0002/0000/0000", null);
        Assert.assertNotEquals("", result.getRouteId());
    }

    public @Test(expected = NotFoundException.class)
    void shall_fail_with_invalid_target() {
        Route result = testee.findBy("STCK/0001/0001/0000/0000", "", "STOCK");
        Assert.assertNotEquals("", result.getRouteId());
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        ExplicitRouteSearch explicitRouteSearch(RouteRepository routeRepository) {
            return new ExplicitRouteSearch(routeRepository);
        }
    }
}
