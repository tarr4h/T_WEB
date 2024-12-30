package com.demo.t_web.program.memo.service;

import com.demo.t_web.comn.model.Tmap;
import com.demo.t_web.program.ai.component.ChatClientComponent;
import com.demo.t_web.program.memo.model.AskMaker;
import com.demo.t_web.program.memo.model.Memo;
import com.demo.t_web.program.memo.model.MemoChat;
import com.demo.t_web.program.memo.repository.MemoChatRepository;
import com.demo.t_web.program.memo.repository.MemoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY;

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
@AllArgsConstructor
public class MemoService {

    MemoRepository memoRepository;
    MemoChatRepository memoChatRepository;
    private ChatClientComponent chatClient;

    @PersistenceContext
    private EntityManager em;

    public Flux<String> search(Tmap param) {
        String userInput = param.getString("i");
        String conversationId = param.getString("c");
        log.debug("conversationId : {}", conversationId);

        List<String> words = getNouns(userInput);
        List<Memo> searchMemoList = getMemos(words);

        AskMaker resultset = AskMaker.builder()
                .askType(2)
                .question(userInput)
                .conversationId(conversationId)
                .referenceYn(!searchMemoList.isEmpty())
                .build();
        resultset.addMemoList(searchMemoList);
        return aiStream(resultset.getAsk(), resultset);
    }

    public Tmap saveChatHistory(Tmap param){
        String q = param.getString("q");
        String r = param.getString("r");

        MemoChat askChat = MemoChat.builder()
                .type(1)
                .content(q)
                .build();
        MemoChat resChat = MemoChat.builder()
                .type(2)
                .content(r)
                .build();
        memoChatRepository.save(askChat);
        memoChatRepository.save(resChat);

        List<String> words = getNouns(q);
        List<Memo> searchMemoList = getMemos(words);
        resChat.setMemos(searchMemoList);

        return new Tmap()
                .direct("success", true)
                .direct("r", resChat);
    }

    public List<Memo> getMemos(List<String> words) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Memo> cq = cb.createQuery(Memo.class);

        Root<Memo> memoRoot = cq.from(Memo.class);
        List<Predicate> titlePredicates = new ArrayList<>();
        List<Predicate> contentPredicates = new ArrayList<>();

        words.forEach(w -> {
            titlePredicates.add(cb.like(memoRoot.get("title"), "%" + w + "%"));
            contentPredicates.add(cb.like(memoRoot.get("content"), "%" + w + "%"));
        });

        Predicate titlePredicate = cb.or(titlePredicates.toArray(new Predicate[0]));
        Predicate contentPredicate = cb.or(contentPredicates.toArray(new Predicate[0]));

        cq.where(cb.or(titlePredicate, contentPredicate));

        return em.createQuery(cq).getResultList();
    }

    public String aiAsk(String input, AskMaker am){
        return chatClient.getChatClient()
                .prompt()
                .system(am.getPrompt())
                .user(input)
                .advisors(a -> a
                        .param(CHAT_MEMORY_CONVERSATION_ID_KEY, am.getConversationId())
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 100)
                )
                .call()
                .content();
    }

    public Flux<String> aiStream(String input, AskMaker am) {
        log.debug("prompt ? {}", am.getPrompt());
        log.debug("input ? {}", input);
        return chatClient.getChatClient()
                .prompt()
                .system(am.getPrompt())
                .user(input)
                .advisors(a -> a
                        .param(CHAT_MEMORY_CONVERSATION_ID_KEY, am.getConversationId())
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 100)
                )
                .stream()
                .content();
    }

    public Object add(Memo memo) {
        memoRepository.save(memo);
        return new Tmap().direct("success", true);
    }

    public Object delete(Memo memo) {
        memoRepository.delete(memo);
        return new Tmap().direct("success", true);
    }

    public List<Memo> selectMemoList() {
        return memoRepository.findAll();
    }

    public List<String> getNouns(String sentence){
        Komoran komoran = new Komoran(DEFAULT_MODEL.LIGHT);
        KomoranResult result = komoran.analyze(sentence);

        if(result != null){
            return result.getNouns();
        } else {
            return new ArrayList<>();
        }
    }
}
