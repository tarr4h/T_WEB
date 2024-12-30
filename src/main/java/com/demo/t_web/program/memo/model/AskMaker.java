package com.demo.t_web.program.memo.model;

import lombok.Builder;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * com.demo.t_web.program.memo.model.AskVo
 *   - AskVo.java
 * </pre>
 *
 * @author : tarr4h
 * @className : AskVo
 * @description :
 * @date : 12/27/24
 */
@Setter
@Builder
public class AskMaker {

    private String question;

    private String ask;

    private List<Memo> memos;

    private int askType;

    private boolean referenceYn;

    private String conversationId;

    public String getConversationId() {
        return this.conversationId;
    }

    public boolean isReferenceYn() {
        return this.referenceYn;
    }

    public String getAsk(){
        StringBuilder str = new StringBuilder();
        str.append("\n[\n");

        memos.forEach(memo -> {
            str.append("------------------\n")
                    .append("title : ")
                    .append(memo.getTitle())
                    .append("\n")
                    .append("content : \n")
                    .append(memo.getContent())
                    .append("\n")
                    .append("------------------\n");
        });

        str.append("]\n");
        if(this.askType == 2){
            str.append("question : ")
                .append(this.question);
        }
        return str.toString();
    }

    public void addMemo(Memo memo){
        if(this.memos == null) {
            this.memos = new ArrayList<>();
        }
        this.memos.add(memo);
    }

    public void addMemoList(List<Memo> memoList){
        if(this.memos == null) this.memos = new ArrayList<>();
        memos.addAll(memoList);
    }

    public String getPrompt(){
        return isReferenceYn() ? prompt2 : prompt1;
    }

    private final String prompt1 = """
            이전 답변을 참고해서 짧고 간결하게 대답.
            """;
    private final String prompt2 = """
            Make responses quickly with short and clear sentences. \s
            Analyze the content inside the brackets [ ] and answer the question based only on the given information. \s
            Do not infer or mix relationships such as "competitor product" and "counterpart product" unless explicitly stated in the document. \s
            For terms with similar meanings within the same context (e.g., "counterpart product" and "comparable product"), infer only if the relationship is explicitly stated or directly implied in the document. \s
            Do not modify or paraphrase any words inside the brackets. \s
            When multiple products are listed as counterparts to a single product, treat each listed product as a counterpart to the main product only, not to the other listed products. \s
            Each product relationship is specific to the context provided and should not extend beyond the explicitly mentioned information. \s
            Do not assume any implied relationship between listed products unless explicitly stated in the document. \s
            The relationship between products must be understood as directly related to the subject (e.g., "A's counterpart product is B"). \s
            Provide only the answer to the question in Korean, without additional explanations or context. \s
            Use only the information inside the brackets to form the answer, and do not include unrelated or additional responses. \s
            If there is a document stating "A's counterpart products are B, C" then B and C can also be considered A's counterpart. For such inference-based answers, analyze relationships to respond appropriately. \s
            Ensure the response is accurate, even if analysis is required, but keep it short and clear.
            """;
}
