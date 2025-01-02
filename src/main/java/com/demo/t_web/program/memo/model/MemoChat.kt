package com.demo.t_web.program.memo.model;

import com.demo.t_web.program.sys.model.BaseVo;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
public class MemoChat extends BaseVo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chat_generator")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "type", nullable = false)
    private int type;

    @Column(name = "content", length = 1000)
    private String content;

    @Transient
    public List<Memo> memos = new ArrayList<>();
}
