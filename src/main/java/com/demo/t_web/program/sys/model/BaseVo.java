package com.demo.t_web.program.sys.model;

import jakarta.persistence.*;

import java.util.Date;

/**
 * <pre>
 * com.demo.t_web.program.sys.model.BaseVo
 *   - BaseVo.java
 * </pre>
 *
 * @author : tarr4h
 * @className : BaseVo
 * @description :
 * @date : 12/19/24
 */
@MappedSuperclass
public class BaseVo {

    @Column(name = "reg_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date regDt;

    @Column(name = "upt_dt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date uptDt;

    @PrePersist
    public void prePersist(){
        if(regDt == null){
            regDt = new Date();
        }
        if(uptDt == null){
            uptDt = new Date();
        }
    }
}
