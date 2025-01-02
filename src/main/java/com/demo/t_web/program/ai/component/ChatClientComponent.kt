package com.demo.t_web.program.ai.component;

import lombok.Getter;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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
        InMemoryChatMemory memory = new InMemoryChatMemory();
        this.chatClient = builder
                .defaultAdvisors(new MessageChatMemoryAdvisor(memory))
                .build();
    }
}
