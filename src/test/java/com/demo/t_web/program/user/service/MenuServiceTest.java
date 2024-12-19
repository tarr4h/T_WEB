package com.demo.t_web.program.user.service;

import com.demo.t_web.program.sys.model.Menu;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local")
@Slf4j
public class MenuServiceTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @Transactional
    @Rollback(false)
    public void menuTest() throws Exception {

        Map<String, String> map = new HashMap<>();
        map.put("id", "test1");
        map.put("name", "harry");
        map.put("password", "abcde1234");

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(map);

        MvcResult loginResult = this.mvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Cookie cookie = loginResult.getResponse().getCookie("jwt");
        Menu menu = new Menu();
        menu.setMenuNm("테스트메뉴1");

        ObjectMapper objectMapper2 = new ObjectMapper();
        String json2 = objectMapper2.writeValueAsString(menu);

        MvcResult menuResult = this.mvc.perform(post("/menu/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json2)
                .cookie(cookie)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        log.debug("menuResult = {}", menuResult);
    }
}