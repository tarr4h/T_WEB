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

    private List<String> memos;

    private int askType;

    public String getAsk(){
        StringBuilder str = new StringBuilder();
        str.append("\n[\n");

        memos.forEach(memo -> {
            str.append("------------------\n")
                    .append(memo)
                    .append("\n");
        });

        str.append("]\n");
        if(this.askType == 2){
            str.append("question : ")
                .append(this.question);
        }
        return str.toString();
    }

    public void addMemo(String memo){
        if(this.memos == null) {
            this.memos = new ArrayList<>();
        }
        this.memos.add(memo);
    }

    public void addMemoList(List<String> memoList){
        if(this.memos == null) this.memos = new ArrayList<>();
        memos.addAll(memoList);
    }

    public String getPrompt(){
        return """
            Analyze the content inside the brackets [ ] and answer the question based on the given information only.
            Do not modify or paraphrase any words inside the brackets.
            Provide only the answer to the question without any additional explanations or context.
            The answer must be provided in Korean.
            Answer should be correct with conteint inside the brackets mentions.
            Only use the information from the brackets to form the answer, and do not include any unrelated or additional responses.
            """;
    }
}
