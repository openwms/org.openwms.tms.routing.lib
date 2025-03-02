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
import org.openwms.tms.routing.ui.api.ActionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.is;
import static org.openwms.tms.routing.RoutingConstants.API_ACTIONS;
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
 * A ActionResourceDocumentation.
 *
 * @author Heiko Scherrer
 */
@RoutingApplicationTest
@Sql(scripts = "classpath:testdata.sql")
class ActionResourceDocumentation {

    private static final String EXISTING_ACTION_PKEY = "1000";
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
                        get(API_ACTIONS)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)))
                .andDo(document("action-findall", preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    void shall_find_one_by_pKey() throws Exception {
        mockMvc
                .perform(
                        get(API_ACTIONS + "/{pKey}", EXISTING_ACTION_PKEY)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("A0001")))
                .andExpect(jsonPath("$.description", is("Common action for the whole project on REQ telegrams")))
                .andExpect(jsonPath("$.locationGroupName", is("ZILE")))
                .andExpect(jsonPath("$.location").doesNotExist())
                .andExpect(jsonPath("$.enabled", is(true)))
                .andExpect(jsonPath("$.program", is("CP001")))
                .andExpect(jsonPath("$.route", is("_DEFAULT")))
                .andExpect(jsonPath("$.ol", is(0)))
                .andExpect(jsonPath("$.createDt").exists())
                .andExpect(jsonPath("$.lastModifiedDt").doesNotExist())
                .andDo(document("action-findbypkey", preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    void shall_delete_a_action() throws Exception {
        mockMvc
                .perform(
                        delete(API_ACTIONS + "/{pKey}", EXISTING_ACTION_PKEY)
                )
                .andExpect(status().isNoContent())
                .andDo(document("action-delete", preprocessResponse(prettyPrint())))
        ;
        mockMvc
                .perform(
                        get(API_ACTIONS + "/{pKey}", EXISTING_ACTION_PKEY)
                )
                .andExpect(status().isNotFound())
                .andDo(document("action-delete-notfound", preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    void shall_update_a_action() throws Exception {
        mockMvc
                .perform(
                        put(API_ACTIONS).content(mapper.writeValueAsString(createAction("A0003", EXISTING_ACTION_PKEY))).contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("A0003")))
                .andExpect(jsonPath("$.description", is("Description (Updated)")))
                .andExpect(jsonPath("$.location", is("STCK/0001/0001/0000/0000")))
                .andExpect(jsonPath("$.locationGroupName", is("UNKNOWN")))
                .andExpect(jsonPath("$.route", is("CONVCONV")))
                .andExpect(jsonPath("$.program", is("CP010")))
                .andExpect(jsonPath("$.type", is("UPD")))
                .andExpect(jsonPath("$.enabled", is(false)))
                .andExpect(jsonPath("$.ol").exists())
                .andExpect(jsonPath("$.createDt").exists())
                .andExpect(jsonPath("$.lastModifiedDt").exists())
                .andDo(document("action-save", preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    void shall_create_a_action() throws Exception {
        mockMvc
                .perform(
                        post(API_ACTIONS).content(mapper.writeValueAsString(createAction("A0003", "new"))).contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andDo(document("action-create", preprocessResponse(prettyPrint())))
        ;
    }

    @Test
    void shall_not_create_an_existing_action() throws Exception {
        mockMvc
                .perform(
                        post(API_ACTIONS).content(mapper.writeValueAsString(createAction("A0002", "new"))).contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isConflict())
                .andDo(document("route-create-409", preprocessResponse(prettyPrint())))
        ;
    }

    private ActionVO createAction(String name, String pKey) {
        var actionVO = new ActionVO();
        actionVO.setKey(pKey);
        actionVO.setName(name);
        actionVO.setDescription("Description (Updated)");
        actionVO.setLocation("STCK/0001/0001/0000/0000");
        actionVO.setLocationGroupName("UNKNOWN");
        actionVO.setRoute("CONVCONV");
        actionVO.setProgram("CP010");
        actionVO.setType("UPD");
        actionVO.setEnabled(false);
        return actionVO;
    };
}