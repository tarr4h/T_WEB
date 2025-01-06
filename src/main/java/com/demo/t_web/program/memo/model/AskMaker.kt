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
            memo -> str
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
            Only use the information from the brackets to form the answer, and do not include any unrelated or additional responses.
            Response must be short and clear.
            
            Define relationships:
            - '<->' means "counterproductive relationship."
            - Analyze ONLY the part of the sentence containing '<->'.
            - Items on the left of '<->' are counterproductive to all items on the right.
            - Do not infer or expand relationships beyond what is specified by <->.
            """.trimIndent();
}
//Relationships(example):
//A <-> B
//A <-> C
//D <-> B
//E <-> B
//F <-> B
//...
//
//Important:
//- Do NOT infer or expand relationships across <->.
//Relationships must remain directly across <-> as explicitly stated.
//- <-> 간의 관계를 절대 확장하면 안됨.
//- A is counterproductive to B.
//- A is counterproductive to C.
//- B is counterproductive to A.
//- C is counterproductive to A.
//- D is counterproductive to B.
//- B is counterproductive to D.
//- D has no relationship with A.
//- B is counterproductive to E.
//- E is counterproductive to B.
//- F is counterproductive to B.
//- B is counterproductive to F.
//- for example, A is counterproduct of (B, C), B is counterproduct of (A, D, E), C is counterproduct of (A), D is counterproduct of (B), E is counterproduct of (B).


//- B is NOT counterproductive to C.
//- C is NOT counterproductive to B.
//A(word) <-> B(word)
//A(word) <-> C(word)
//D(word) <-> B(word)
//E(word) <-> B(word)
//            - Items separated by commas on the same side (e.g., B, C) are *not* counterproductive to each other and should NOT be linked in any way.
