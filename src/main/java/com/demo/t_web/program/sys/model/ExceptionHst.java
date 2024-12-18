package com.demo.t_web.program.sys.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

/**
 * <pre>
 * com.demo.t_web.program.sys.model.ExceptionHst
 *   - ExceptionHst.java
 * </pre>
 *
 * @author : tarr4h
 * @className : ExceptionHst
 * @description :
 * @date : 12/18/24
 */
@Entity
@Table(name = "exception_hst")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "exception_hst_generator", sequenceName = "exception_hst_seq", initialValue = 1, allocationSize = 1)
public class ExceptionHst {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "exception_hst_generator")
    @Column(name = "id")
    private Long id;

    @Column(name = "uri", length = 100)
    private String uri;

    @Column(name = "exception", length = 100)
    private String exception;

    @Column(name = "msg", length = 4000)
    private String msg;

    @Column(name = "params", length = 4000)
    private String params;

    @Column(name = "reg_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date regDt;

    @PrePersist
    public void prePersist(){
        if(regDt == null){
            regDt = new Date();
        }
    }
}
