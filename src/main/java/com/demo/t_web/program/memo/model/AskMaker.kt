package com.demo.t_web.program.memo.model

data class AskMaker (
    var question : String,
    var memos : MutableList<String> = mutableListOf(),
    var askType : Int,
    var referenceYn : Boolean,
    var conversationId : String
){
    fun getAsk() : String {
        val str : StringBuilder = StringBuilder()
        str.append("\n[\n")

        memos.forEach {
            memo -> str.append("------------------\n")
                .append(memo)
                .append("\n")
        }

        str.append("]\n")
        if(askType == 2){
            str.append("question : ")
                .append(question)
        }

        return str.toString()
    }

    fun addMemoList(memoList : List<String>) {
        memos.addAll(memoList)
    }

    fun getPrompt() : String {
        return if(referenceYn) prompt2 else prompt1
    }

    private val prompt1 = """
            이전 답변을 참고해서 짧고 간결하게 대답.        
            """.trimIndent();
    private val prompt2 = """
            Make response quickly with short and clear sentence.
            Analyze the content inside the brackets [ ] and answer the question based on the given information only.
            Do not modify or paraphrase any words inside the brackets.
            Provide only the answer to the question without any additional explanations or context.
            The answer must be provided in Korean.
            Answer should be correct with content inside the brackets mentions.
            Only use the information from the brackets to form the answer, and do not include any unrelated or additional responses.
            Response must be short and clear.
            """.trimIndent();
}