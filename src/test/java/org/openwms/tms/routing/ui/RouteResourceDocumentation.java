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
package org.openwms.tms.routing.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openwms.tms.routing.RoutingApplicationTest;
import org.openwms.tms.routing.ui.api.RouteVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.is;
import static org.openwms.tms.routing.RoutingConstants.API_ROUTES;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * A RouteResourceDocumentation.
 *
 * @author Heiko Scherrer
 */
@RoutingApplicationTest
@Sql(scripts = "classpath:testdata.sql")
class RouteResourceDocumentation {

    private static final String EXISTING_ROUTE_PKEY = "709957655206";
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private ObjectMapper mapper;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation)).build();
    }

    @Test
    void shall_return_all_existing() throws Exception {
        mockMvc
                .perform(
                        get(API_ROUTES)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(9)))
                .andDo(document("route-findall", preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    void shall_find_one_by_pKey() throws Exception {
        mockMvc
                .perform(
                        get(API_ROUTES + "/{pKey}", EXISTING_ROUTE_PKEY)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("SRC_TRG")))
                .andExpect(jsonPath("$.description", is("Source -> Target")))
                .andExpect(jsonPath("$.sourceLocationName", is("STCK/0001/0001/0000/0000")))
                .andExpect(jsonPath("$.targetLocationName", is("STCK/0001/0002/0000/0000")))
                .andExpect(jsonPath("$.key", is(EXISTING_ROUTE_PKEY)))
                .andExpect(jsonPath("$.enabled", is(true)))
                .andExpect(jsonPath("$.ol", is(0)))
                .andExpect(jsonPath("$.createDt").exists())
                .andExpect(jsonPath("$.lastModifiedDt").exists())
                .andDo(document("route-findbypkey", preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    void shall_delete_a_route() throws Exception {
        mockMvc
                .perform(
                        delete(API_ROUTES + "/{pKey}", EXISTING_ROUTE_PKEY)
                )
                .andExpect(status().isNoContent())
                .andDo(document("route-delete", preprocessResponse(prettyPrint())))
        ;
        mockMvc
                .perform(
                        get(API_ROUTES + "/{pKey}", EXISTING_ROUTE_PKEY)
                )
                .andExpect(status().isNotFound())
                .andDo(document("route-delete-notfound", preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    void shall_update_a_route() throws Exception {
        var routeVO = new RouteVO();
        routeVO.setKey(EXISTING_ROUTE_PKEY);
        routeVO.setName("SRC_TRG_UPDATED");
        routeVO.setDescription("Source -> Target (Updated)");
        routeVO.setSourceLocationGroupName("SOURCE GROUP");
        routeVO.setTargetLocationGroupName("TARGET GROUP");
        routeVO.setEnabled(false);
        mockMvc
                .perform(
                        put(API_ROUTES).content(mapper.writeValueAsString(routeVO)).contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("SRC_TRG_UPDATED")))
                .andExpect(jsonPath("$.description", is("Source -> Target (Updated)")))
                .andExpect(jsonPath("$.sourceLocationName").doesNotExist())
                .andExpect(jsonPath("$.targetLocationName").doesNotExist())
                .andExpect(jsonPath("$.key", is(EXISTING_ROUTE_PKEY)))
                .andExpect(jsonPath("$.enabled", is(false)))
                .andExpect(jsonPath("$.ol").exists())
                .andExpect(jsonPath("$.createDt").exists())
                .andExpect(jsonPath("$.lastModifiedDt").exists())
                .andDo(document("route-save", preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    void shall_create_a_route() throws Exception {
        var routeVO = new RouteVO();
        routeVO.setKey("NEW");
        routeVO.setName("SRC_TRG_NEW");
        routeVO.setDescription("Source -> Target");
        routeVO.setSourceLocationGroupName("SOURCE GROUP");
        routeVO.setTargetLocationGroupName("TARGET GROUP");
        routeVO.setEnabled(false);
        mockMvc
                .perform(
                        post(API_ROUTES).content(mapper.writeValueAsString(routeVO)).contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andDo(document("route-create", preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    void shall_not_create_an_existing_route() throws Exception {
        var routeVO = new RouteVO();
        routeVO.setKey("NEW");
        routeVO.setName("SRC_TRG");
        routeVO.setDescription("Source -> Target");
        routeVO.setSourceLocationGroupName("SOURCE GROUP");
        routeVO.setTargetLocationGroupName("TARGET GROUP");
        routeVO.setEnabled(false);
        mockMvc
                .perform(
                        post(API_ROUTES).content(mapper.writeValueAsString(routeVO)).contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isConflict())
                .andDo(document("route-create-409", preprocessResponse(prettyPrint())))
        ;
    }
}