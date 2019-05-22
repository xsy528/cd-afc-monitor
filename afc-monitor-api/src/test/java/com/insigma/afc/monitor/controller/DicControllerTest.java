/*
 * 日期：2018年12月10日
 *
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.afc.monitor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.insigma.afc.security.test.WithMockAfcUser;
import com.insigma.commons.model.dto.Result;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * Ticket: 字典接口单元测试
 *
 * @author xuzhemin
 * 2019/3/20 15:26
 */
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
//@RunWith(SpringRunner.class)
//@AutoConfigureMockMvc
public class DicControllerTest {

//    @Autowired
    private MockMvc mockMvc;

//    @MockBean
    RestTemplate restTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

//    @Before
    public void before() {

    }

//    @Test
    public void getModeTypeListTest() throws Exception {
        mockMvc.perform(post("/monitor/dic/modeTypeList").header("Afc-User-Id", 1))
                .andExpect((mvcResult) ->
                        Assert.assertEquals(0, objectMapper
                                .readValue(mvcResult.getResponse().getContentAsByteArray(), Result.class).getCode())
                );
    }
}
