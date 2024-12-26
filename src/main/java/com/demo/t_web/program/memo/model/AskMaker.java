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
        str.append(getPrompt()).append("\n[\n");

        memos.forEach(memo -> {
            str.append("------------------\n")
                    .append(memo)
                    .append("\n");
        });

        str.append("]\n");
        if(this.askType == 2){
            str.append("질문 : ")
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

    private String getPrompt(){
        if(this.askType == 1){
            return prompt1;
        } else if(this.askType == 2){
            return prompt2;
        } else {
            return "";
        }
    }

    private final String prompt1 = """
                requirement :
                'Extract only the nouns from the given sentence and return them as a JSON formatted string.
                      It should consist of two arrays.
                      One containing all the nouns,
                      Another containing the nouns that are contextually important.
                      The format should be as follows:
                {
                    "words" : ["햇빛", "창가", "바람"],
                    "importantWords" : ["햇빛", "창가"]
                }
                Never translate into English, especially Korean into English.
                Only return the JSON string in the exact format requested above.
                Response string must be start with "{" and end with "}".
                No explaination, greeting.
                Do not explain about json string and process.
                Do not contain any other word or sentence without object.
                Do not response what u did.
                '
            """;

    private final String prompt2 = """
            Review the content within the brackets and answer the question based on it.
            Only refer to the information within the brackets to find the answer.
            Provide an exact answer from the content without adding additional information.
            Answer only with the required content; no prefix or suffix should be included.
            Identify the key terms in the question to locate the relevant answer in the content.
            If the content within the brackets matches the question, ensure it is included in the answer.
            Key terms refer to the central or most important words in the question.
            Do not modify the words from the content. Always use the exact wording as provided in the brackets.
            For example:
                             [
                    미네랄페이퍼
                    - 방수가능종이
                    - UV인쇄 권장
                    - 옵셋인쇄시 건조가 잘 안될 수 있음
                 ]
                 질문 : 방수 가능한 종이 있어?

            Answer must be like '미네랄페이퍼는 방수 가능한 종이입니다.'.
            Do not paraphrase or alter the wording in the response. Use the existing words in the content.
            Must response using Korean.
            """;

    private final String prompt3 = """
                아래 대괄호 안의 내용을 확인해서 질문에 답변.\

                 내용 안에서만 답변의 결과를 찾는다.\

                 내용의 정확한 내용만 찾아서 답변.\

                 추가적으로 답변을 생성할 필요 없음.\
                
                 답변 내용만 답변할것. prefix, suffix 필요 없음\

                 질문의 핵심 단어를 파악해서, 내용에서 해당 내용에 대한 결과값을 찾는다.\
                
                 대괄호 안에 해당하는 내용이 있다면 답변에 반드시 포함할 것.\

                 핵심 단어라 함은 질문 내에서 나타난 질문의 중심이 되는 단어.
                
                 do not change word,
                 반드시 대괄호 안 단어를 쓸 것(use exist word).
                
                 예를 들어,
                
                 [
                    미네랄페이퍼
                    - 방수가능종이
                    - UV인쇄 권장
                    - 옵셋인쇄시 건조가 잘 안될 수 있음
                 ]
                 질문 : 방수 가능한 종이 있어?
                
                 이렇게 질문이 올 때, '미네랄페이퍼는 방수 가능한 종이입니다.'
                 이런식으로 대답한다.
                
                 그리고 내용의 단어를 바꿔서 대답하지마
                """;
}
