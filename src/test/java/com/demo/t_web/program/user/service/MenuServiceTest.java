package com.demo.t_web.program.user.service;

import com.demo.t_web.program.sys.model.Menu;
import com.demo.t_web.program.user.model.User;
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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
        menu.setMenuNm("MAP");
        Menu menu2 = new Menu();
        menu2.setMenuNm("SETTING");

        List<Menu> menuList = Arrays.asList(menu, menu2);

        menuList.forEach(m -> {
            try {
                ObjectMapper objectMapper2 = new ObjectMapper();
                String json2 = objectMapper2.writeValueAsString(m);

                MvcResult menuResult = this.mvc.perform(post("/menu/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json2)
                                .cookie(cookie)
                        )
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andReturn();
                log.debug("menuResult = {}", menuResult);
            } catch (Exception e){
                log.error("menuInsert exception--", e);
            }
        });

//        ObjectMapper objectMapper2 = new ObjectMapper();
//        String json2 = objectMapper2.writeValueAsString(menu);
//
//        MvcResult menuResult = this.mvc.perform(post("/menu/add")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(json2)
//                .cookie(cookie)
//                )
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andReturn();
    }


    @Test
    @Transactional
    @Rollback(false)
    public void getUserMenuTest() throws Exception {
        User user = User.builder()
                .id("test1")
                .build();


        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(user);

        MvcResult loginResult = this.mvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Cookie cookie = loginResult.getResponse().getCookie("jwt");

        MvcResult menuResult = this.mvc.perform(post("/user/selectUser")
//                        .contentType(MediaType.APPLICATION_JSON)
                        .cookie(cookie)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseData = menuResult.getResponse().getContentAsString();
        log.debug("resopnseData = {}", responseData);
        log.debug("menuResult = {}", menuResult);

    }
}