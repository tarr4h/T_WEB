package com.demo.t_web.program.ai.controller;

import com.demo.t_web.comn.model.Tmap;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local")
@Slf4j
public class OllamaTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void aiAskTest() throws Exception {
        String prompt = """
                아래 대괄호 안의 내용을 확인해서 질문에 답변.\

                 내용 안에서만 답변의 결과를 찾는다.\

                 내용의 정확한 내용만 찾아서 답변.\

                 추가적으로 답변을 생성할 필요 없음.\
                
                 답변 내용만 답변할것. prefix, suffix 필요 없음\

                 질문의 핵심 단어를 파악해서, 내용에서 해당 내용에 대한 결과값을 찾는다.\

                 핵심 단어라 함은 질문 내에서 나타난 질문의 중심이 되는 단어.""";

        String prevInfo = """
                엔젤클로스 종이 특성\

                 - 천질감이다\

                 - 옵셋인쇄""";

        String fakeInfo = """
                엔젤클로스 종이 가격\
                
                - 5000원\
                
                - 증감요소있음""";

        String fakeInfo2 = """
                호롤롤로 종이 특성\

                - 플라스틱이다\

                - 그냥인쇄""";

        String info3 = """
                미네랄페이퍼\
                
                - 방수가능종이\
                
                - UV인쇄 권장\
                
                - 옵셋인쇄시 건조가 잘 안될 수 있음
                """;


        StringBuilder str = new StringBuilder();
        str.append(prompt)
                .append("\n")
                .append("[")
                .append("\n")
                .append(prevInfo)
                .append("\n")
                .append(fakeInfo)
                .append("\n")
                .append(fakeInfo2)
                .append("\n")
                .append(info3)
                .append("\n")
                .append("]")
                .append("\n")
            .append("질문 : 방수가능한 종이 있어 ?");


        Tmap test = new Tmap().direct("input", str.toString());

        MvcResult result = this.mvc.perform(post("/ai/ollama/test")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(test)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                ;

        log.debug("response = {}", result.getResponse().getContentAsString());
    }
}