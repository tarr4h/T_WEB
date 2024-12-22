package com.demo.t_web.comn.filter;

import com.demo.t_web.comn.enums.JwtEnum;
import com.demo.t_web.program.user.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * <pre>
 * com.demo.t_web.comn.filter.JwtTest
 *   - JwtTest.java
 * </pre>
 *
 * @author : tarr4h
 * @className : JwtTest
 * @description :
 * @date : 12/18/24
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local")
@Slf4j
public class TestClass {

    @Autowired
    private MockMvc mvc;

    @Test
    @Transactional
    @Rollback(false)
    public void addUser() throws Exception {
        User user = User.builder()
                .id("test1")
                .name("harry")
                .password("abcd1234")
                .email("asfelis@naver.com")
                .phone("01011111111")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(user);

        MvcResult joinResult = this.mvc.perform(post("/user/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        log.debug("response = {}", joinResult.getResponse().getContentAsString());
    }

    @Test
    @Transactional
    @Rollback(false)
    public void jwtLoginTest() throws Exception {
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

        log.debug("response = {}", loginResult.getResponse().getContentAsString());

        String jwtToken = loginResult.getResponse().getContentAsString();
        log.debug("jwt token -- {}", jwtToken);

        mvc.perform(get("/login/jwtTest").header(JwtEnum.HEADER_STRING.getValue(),
                                                            JwtEnum.HEADER_PRE.getValue() + " " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Transactional
    @Rollback(false)
    public void adminTest() throws Exception{
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

        String jwtToken = loginResult.getResponse().getContentAsString();
        log.debug("jwt token -- {}", jwtToken);

        mvc.perform(post("/admin/test").header(JwtEnum.HEADER_STRING.getValue(),
                        JwtEnum.HEADER_PRE.getValue() + " " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
