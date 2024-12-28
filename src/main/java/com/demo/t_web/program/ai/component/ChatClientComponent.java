package com.demo.t_web.program.ai.component;

import lombok.Getter;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * com.demo.t_web.program.ai.component.ChatClientService
 *   - ChatClientService.java
 * </pre>
 *
 * @author : tarr4h
 * @className : ChatClientService
 * @description :
 * @date : 12/28/24
 */
@Component
@Getter
public class ChatClientComponent {

    private final ChatClient chatClient;

    public ChatClientComponent(ChatClient.Builder builder){
        this.chatClient = builder.build();
    }
}
