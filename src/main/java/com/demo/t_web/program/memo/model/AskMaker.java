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
        str
//                .append(getPrompt())
                .append("\n[\n");

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
        if(this.askType == 1){
            return prompt1;
        } else if(this.askType == 2){
            return prompt2;
        } else {
            return "";
        }
    }

    private final String prompt1 = """
                Extract only the nouns from the given sentence and return them as a JSON formatted string.
                Do not include words that are a single character in length.
                Ensure that only nouns with more than one character are included.
                      It should consist of two arrays.
                      One containing all the nouns,
                      Another containing the nouns that are contextually important.
                      each key is "words", "importantWords".
                      and each value must be array object.
                      Must escape " char as \\"
                Never translate into English, especially Korean into English.
                Only return the JSON string in the exact format requested above.
                No explaination, greeting.
                Do not explain about json string and process.
                Do not contain any other word or sentence without object.
                Do not response what u did.
                Do not prefix the response with "```json" or any other formatting marker.
            """;

    private final String prompt2 = """
            Analyze the content inside the brackets [ ] and answer the question based on the given information only.
            Do not modify or paraphrase any words inside the brackets.
            Provide only the answer to the question without any additional explanations or context.
            The answer must be provided in Korean.
            Answer should be correct with conteint inside the brackets mentions.
            Only use the information from the brackets to form the answer, and do not include any unrelated or additional responses.
            """;
}
