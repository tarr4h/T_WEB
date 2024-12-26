package com.demo.t_web.program.memo.model;

import com.demo.t_web.program.sys.model.BaseVo;
import jakarta.persistence.*;
import lombok.*;

/**
 * <pre>
 * com.demo.t_web.program.memo.model.Memo
 *   - Memo.java
 * </pre>
 *
 * @author : tarr4h
 * @className : Memo
 * @description :
 * @date : 12/27/24
 */
@Entity
@Table(name = "s_memo")
@SequenceGenerator(name = "memo_generator", sequenceName = "memo_seq", initialValue = 1, allocationSize = 1)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Memo extends BaseVo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "memo_generator")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Column(name = "content", length = 1000)
    private String content;

}
