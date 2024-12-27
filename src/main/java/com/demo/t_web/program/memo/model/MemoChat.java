package com.demo.t_web.program.memo.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * <pre>
 * com.demo.t_web.program.memo.model.MemoChat
 *   - MemoChat.java
 * </pre>
 *
 * @author : tarr4h
 * @className : MemoChat
 * @description :
 * @date : 12/27/24
 */
@Entity
@Table(name = "s_memo_chat")
@SequenceGenerator(name = "chat_generator", sequenceName = "chat_seq", initialValue = 1, allocationSize = 1)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MemoChat {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chat_generator")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "type", nullable = false)
    private int type;

    @Column(name = "content", length = 1000)
    private String content;
}
