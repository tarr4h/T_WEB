package com.demo.t_web.program.memo.service

import com.demo.t_web.comn.model.Tmap
import com.demo.t_web.program.ai.component.ChatClientComponent
import com.demo.t_web.program.memo.model.AskMaker
import com.demo.t_web.program.memo.model.Memo
import com.demo.t_web.program.memo.model.MemoChat
import com.demo.t_web.program.memo.repository.MemoChatRepository
import com.demo.t_web.program.memo.repository.MemoRepository
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL
import kr.co.shineware.nlp.komoran.core.Komoran
import kr.co.shineware.nlp.komoran.model.KomoranResult
import lombok.extern.slf4j.Slf4j
import org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
@Slf4j
class MemoService @Autowired constructor(
    private val memoRepository: MemoRepository,
    private val memoChatRepository: MemoChatRepository,
    private val chatClientComponent : ChatClientComponent
){

    @PersistenceContext
    lateinit var em : EntityManager

    fun search(param : Tmap) : Flux<String> {
        val userInput = param.getString("i")
        val conversationId = param.getString("c")

        val searchMemoList = getMemos(getNouns(userInput))

        val resultset = AskMaker(
            askType = 2,
            question = userInput,
            conversationId = conversationId,
            referenceYn = searchMemoList.isNotEmpty(),
        )
        resultset.addMemoList(searchMemoList.stream().map{
            m -> m.title + "\n" + m.content
        }.toList())

        return aiStream(resultset.getAsk(), resultset)
    }

    fun saveChatHistory(param : Tmap) : Tmap {
        val q = param.getString("q");
        val r = param.getString("r")
        val askChat = MemoChat(
            type = 1,
            content = q
        )
        val resChat = MemoChat(
            type = 2,
            content = r
        )

        memoChatRepository.save(askChat)
        memoChatRepository.save(resChat)

        val words = getNouns(q)
        val searchMemoList = getMemos(words)
        resChat.memos.addAll(searchMemoList)

        return Tmap()
            .direct("success", true)
            .direct("r", resChat)
    }


    fun getMemos(words : List<String>) : List<Memo> {
        val cb : CriteriaBuilder? = em!!.criteriaBuilder
        val cq : CriteriaQuery<Memo>? = cb!!.createQuery(
            Memo::class.java)

        val memoRoot : Root<Memo> = cq!!.from(Memo::class.java)
        val titlePredicates = ArrayList<Predicate>()
        val contentPredicates = ArrayList<Predicate>()

        words.forEach{ w ->
            titlePredicates.add(cb.like(memoRoot.get("title"), "%$w%"))
            contentPredicates.add(cb.like(memoRoot.get("content"), "%$w%"))
        }

        cq.where(cb.or(cb.or(*titlePredicates.toTypedArray<Predicate>())
            , cb.or(*contentPredicates.toTypedArray<Predicate>())))

        return em.createQuery(cq).resultList ?: emptyList()
    }


    fun aiAsk(input : String, am : AskMaker) : String? {
        return chatClientComponent.chatClient
            .prompt()
            .system(am.getPrompt())
            .user(input)
            .advisors { a -> a
                .param(AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY, am.conversationId)
                .param(AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY, 100)

            }
            .call()
            .content()
    }

    fun aiStream(input : String, am : AskMaker) : Flux<String> {
        return chatClientComponent.chatClient
            .prompt()
            .system(am.getPrompt())
            .user(input)
            .advisors { a -> a
                .param(AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY, am.conversationId)
                .param(AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY, 100)

            }
            .stream()
            .content()
    }

    fun add(memo : Memo) : Tmap {
        memoRepository.save(memo)
        return Tmap().direct("success", true);
    }

    fun delete(memo : Memo) : Tmap {
        memoRepository.delete(memo)
        return Tmap().direct("success", true);
    }

    fun selectMemoList() : List<Memo> = memoRepository.findAll()

    fun getNouns(sentence : String) : List<String> {
        val komoran = Komoran(DEFAULT_MODEL.LIGHT)
        val result : KomoranResult? = komoran.analyze(sentence);

        return result?.nouns ?: emptyList()
    }

}