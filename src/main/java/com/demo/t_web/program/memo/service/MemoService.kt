package com.demo.t_web.program.memo.service

import com.demo.t_web.comn.model.Tmap
import com.demo.t_web.program.ai.component.ChatClientComponent
import com.demo.t_web.program.memo.model.AskMaker
import com.demo.t_web.program.memo.model.Memo
import com.demo.t_web.program.memo.model.MemoChat
import com.demo.t_web.program.memo.repository.MemoChatRepository
import com.demo.t_web.program.memo.repository.MemoRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL
import kr.co.shineware.nlp.komoran.core.Komoran
import kr.co.shineware.nlp.komoran.corpus.model.Dictionary
import kr.co.shineware.nlp.komoran.model.KomoranResult
import lombok.extern.slf4j.Slf4j
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.xssf.usermodel.XSSFCell
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import java.io.File
import java.io.FileInputStream
import java.nio.charset.StandardCharsets
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

@Service
@Slf4j
class MemoService @Autowired constructor(
    private val memoRepository: MemoRepository,
    private val memoChatRepository: MemoChatRepository,
    private val chatClientComponent : ChatClientComponent
){

    private val log = KotlinLogging.logger {}

    @PersistenceContext
    lateinit var em : EntityManager

    fun search(param : Tmap) : Flux<String> {
        val userInput = param.getString("i")
        val conversationId = param.getString("c")

        val refineList = getRefineList(userInput)
        val resultset = AskMaker(
            askType = 2,
            question = userInput,
            conversationId = conversationId,
            referenceYn = refineList.isNotEmpty(),
        )
        resultset.addMemoList(refineList.stream().map{
            m -> m.title + "\n" + m.content
        }.toList())

        return aiStream(resultset.getAsk(), resultset)
    }

    fun getRefineList(userInput : String) : MutableList<Memo>{
        val nouns = getNouns(userInput)
        log.debug { "nouns : $nouns" }
        val searchMemoList = getMemos(nouns)
        val refineList = mutableListOf<Memo>()

        for(smemo in searchMemoList){
            val splitWords = smemo.content?.split(" ") ?: mutableListOf()
            for(word in splitWords){
                for(noun in nouns){
                    if (word.trimIndent() == noun) {
                        if(!refineList.contains(smemo)){
                            refineList.add(smemo)
                        }
                        break
                    }
                }
            }
        }
        log.debug { "searchList size = ${searchMemoList.size}" }
        log.debug { "refineList size = ${refineList.size}" }
        return refineList
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

        val refineList = getRefineList(q)
        resChat.memos.addAll(refineList)

        return Tmap()
            .direct("success", true)
            .direct("r", resChat)
    }


    fun getMemos(words : List<String>) : List<Memo> {
        val cb : CriteriaBuilder? = em.criteriaBuilder
        val cq : CriteriaQuery<Memo>? = cb!!.createQuery(
            Memo::class.java)

        val memoRoot : Root<Memo> = cq!!.from(Memo::class.java)
        val titlePredicates = ArrayList<Predicate>()
        val contentPredicates = ArrayList<Predicate>()

        words.forEach{ w ->
            titlePredicates.add(cb.like(memoRoot.get("title"), "%$w%"))
            contentPredicates.add(cb.like(memoRoot.get("content"), "%$w%"))
        }

//        cq.where(cb.and(cb.or(*titlePredicates.toTypedArray<Predicate>())
//            , cb.or(*contentPredicates.toTypedArray<Predicate>())))
        cq.where(cb.or(*contentPredicates.toTypedArray<Predicate>()))

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

        val path = "/Users/taewoohan/WorkFolder/komo_words.txt"
        komoran.setUserDic(path)

        val result : KomoranResult? = komoran.analyze(sentence);

        return result?.nouns ?: emptyList()
    }

    fun insertDicWords(words: List<String>) {
        val path = "/Users/taewoohan/WorkFolder/komo_words.txt"
        val file = File(path)
        val exists = file.readLines()
        for(word in words) {
            if(word.trimIndent() != "" && !exists.contains(word.trimIndent())){
                file.appendText(word.trimIndent() + "\n")
            }
        }
    }

    fun insertVs() {
        val list = readExcel()

        for((ind, i) in list.withIndex()){
            if(ind == 0) continue
            log.debug { "row separation -------------" }
            val m = Memo()
            var dProducts = i[2]
            if(dProducts.contains(".")){
                dProducts = dProducts.replace(".", ",")
            }

            var cProductStr = i[1]
            m.title = if(dProducts.trimIndent() == ""){
                cProductStr.trimIndent()
            } else {
                dProducts.trimIndent()
            }
            cProductStr = cProductStr.replace(".", ",")
            val counterProducts = if(cProductStr.contains(",")) {
                cProductStr.split(",")
            } else {
                listOf(cProductStr)
            }

            var content = ""
            if(dProducts.trimIndent() == ""){
                content += " $cProductStr <-> 없음\n"
            } else {
                if(dProducts.contains(",")) {
                    for (products in dProducts.split(",")) {
                        for(c in counterProducts){
                            if(products.trimIndent() != ""){
                                content += " $c <-> $products\n"
                            }
                            insertDicWords(listOf(c))
                        }
                        if(products.trimIndent() != ""){
                            insertDicWords(listOf(products))
                        }
                    }
                } else {
                    for(c in counterProducts){
                        if(dProducts.trimIndent() != ""){
                            content += " $c <-> $dProducts\n"
                            insertDicWords(listOf(c))
                            insertDicWords(listOf(dProducts))
                        }
                    }
                }
            }

            if("" != i[3]){
                content += "\n비고 : ${i[3]}"
            }
            m.content = content
            log.debug { "title = ${m.title}" }
            log.debug { "content = $content" }
            log.debug { "----------------------------" }

            memoRepository.save(m)
        }
    }

    fun readExcel() : MutableList<MutableList<String>> {
        val filePath = "/Users/taewoohan/WorkFolder/doosung.xlsx"
        val file = FileInputStream(filePath)
        val workbook = XSSFWorkbook(file)

        val sheetNum = workbook.numberOfSheets
        log.debug { "sheetNum = $sheetNum" }

        val list = mutableListOf<MutableList<String>>()
        for(i in 0 until sheetNum){
            if(i != 0) continue
            val sheet: XSSFSheet = workbook.getSheetAt(i)
            val rows = sheet.physicalNumberOfRows

            for(j in 0 until rows){
                val rowList = mutableListOf<String>()
                val row = sheet.getRow(j)

                val cells = row.physicalNumberOfCells
                for(c in 0 until cells){
                    val cell = row.getCell(c)
                    val value = getExcelCellValue(cell)
                    if(c <= 3){
                        rowList.add(value.toString().trimIndent())
                    }
                }
                list.add(rowList)
            }
        }

        return list
    }

    fun getExcelCellValue(cell : XSSFCell?): Any? {
        val f = NumberFormat.getInstance(Locale.US)
        f.isGroupingUsed = false
        var value : Any? = null

        if(cell != null){
            when(cell.cellType.toString()){
                CellType.STRING.toString() -> value = cell.stringCellValue
                CellType.NUMERIC.toString() -> value = cell.numericCellValue
                CellType.BLANK.toString() -> value = ""
                CellType.ERROR.toString() -> value = cell.errorCellValue
            }
        }

        return value
    }
}