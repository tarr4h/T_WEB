package com.demo.t_web.program.ai.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <pre>
 * com.demo.t_web.program.ai.controller.OllamaController
 *   - OllamaController.java
 * </pre>
 *
 * @author : tarr4h
 * @className : OllamaController
 * @description :
 * @date : 12/26/24
 */
@RestController
@RequestMapping("/ai/ollama")
@Slf4j
public class OllamaController {

    private final ChatClient chatClient;

    public OllamaController(ChatClient.Builder builder){
        this.chatClient = builder.build();
    }

    @PostMapping("/test")
    public ResponseEntity<String> test(@RequestBody Map<String, Object> param){
        String input = param.get("input").toString();
        log.debug("input : {}", input);

        String response = chatClient.prompt()
                .user(input)
                .call()
                .content();

        return ResponseEntity.ok().body(response);
    }
}
