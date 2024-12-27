package com.demo.t_web.program.memo.service;

import com.demo.t_web.comn.model.Tmap;
import com.demo.t_web.program.memo.model.AskMaker;
import com.demo.t_web.program.memo.model.Memo;
import com.demo.t_web.program.memo.model.MemoChat;
import com.demo.t_web.program.memo.repository.MemoChatRepository;
import com.demo.t_web.program.memo.repository.MemoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * com.demo.t_web.program.memo.service.MemoService
 *   - MemoService.java
 * </pre>
 *
 * @author : tarr4h
 * @className : MemoService
 * @description :
 * @date : 12/27/24
 */
@Service
@Slf4j
public class MemoService {

    @Autowired
    MemoRepository memoRepository;

    @Autowired
    MemoChatRepository memoChatRepository;

    @PersistenceContext
    private EntityManager em;

    private final ChatClient chatClient;

    public MemoService(ChatClient.Builder builder){
        this.chatClient = builder.build();
    }

    public Object search(Map<String, Object> param) {
        String userInput = (String) param.get("userInput");

        AskMaker preset = AskMaker.builder()
                .askType(1)
                .build();
        preset.addMemo(userInput);

        String ready = aiAsk(preset.getAsk(), preset);
        if(ready.contains("json")){
            ready = ready.replaceAll("```", "").replaceAll("json", "");
        }
        log.debug("ask1 = {}", ready);
        Map<String, List<String>> wordsMap = new HashMap<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            wordsMap = mapper.readValue(ready, new TypeReference<Map<String, List<String>>>() {
            });
        } catch (JsonProcessingException e) {
            log.error("json error -----", e);
        }

        List<String> words = wordsMap.get("words") == null ? new ArrayList<>() : wordsMap.get("words");
        List<String> importantWords = wordsMap.get("importantWords") == null ? new ArrayList<>() : wordsMap.get("importantWords");

        List<Memo> searchMemoList = getMemos(words, importantWords);
        log.debug("memoList = {}", searchMemoList.size());

        AskMaker resultset = AskMaker.builder()
                .askType(2)
                .question(userInput)
                .build();
        resultset.addMemoList(searchMemoList.stream()
                .map(m -> m.getTitle() + "\n" + m.getContent())
                .toList()
        );

        String result = aiAsk(resultset.getAsk(), resultset);

        MemoChat askChat = MemoChat.builder()
                .type(1)
                .content(userInput)
                .build();
        MemoChat resChat = MemoChat.builder()
                .type(2)
                .content(result)
                .build();
        memoChatRepository.save(askChat);
        memoChatRepository.save(resChat);

        return new Tmap()
                .direct("success", true)
                .direct("memoList", searchMemoList)
                .direct("q", askChat)
                .direct("r", resChat);
    }

    public List<Memo> getMemos(List<String> words, List<String> importantWords) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Memo> cq = cb.createQuery(Memo.class);

        Root<Memo> memoRoot = cq.from(Memo.class);
        List<Predicate> titlePredicates = new ArrayList<>();
        List<Predicate> contentPredicates = new ArrayList<>();

        words.forEach(w -> {
            titlePredicates.add(cb.like(memoRoot.get("title"), "%" + w + "%"));
            contentPredicates.add(cb.like(memoRoot.get("content"), "%" + w + "%"));
        });
        importantWords.forEach(w -> {
            titlePredicates.add(cb.like(memoRoot.get("title"), "%" + w + "%"));
            contentPredicates.add(cb.like(memoRoot.get("content"), "%" + w + "%"));
        });

        Predicate titlePredicate = cb.or(titlePredicates.toArray(new Predicate[0]));
        Predicate contentPredicate = cb.or(contentPredicates.toArray(new Predicate[0]));

        cq.where(cb.or(titlePredicate, contentPredicate));

        return em.createQuery(cq).getResultList();
    }

    public String aiAsk(String input, AskMaker am){
        Message message = new UserMessage(input);
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(am.getPrompt());
        Message sysMsg = systemPromptTemplate.createMessage();

        Prompt prompt = new Prompt(List.of(message, sysMsg));
        return chatClient.prompt(prompt)
                .user(input)
                .call()
                .content();
    }

    public Object add(Memo memo) {
        log.debug("memos = {}, {}", memo.getTitle(), memo.getContent());
        memoRepository.save(memo);
        return new Tmap().direct("success", true);
    }

    public List<Memo> selectMemoList() {
        return memoRepository.findAll();
    }
}
