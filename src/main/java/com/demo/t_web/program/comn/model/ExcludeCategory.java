package com.demo.t_web.program.comn.model;

import com.demo.t_web.comn.model.BaseVo;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * <pre>
 * com.demo.t_web.program.comn.model.ExcludeCategory
 *   - ExcludeCategory.java
 * </pre>
 *
 * @author : 한태우
 * @className : ExcludeCategory
 * @description :
 * @date : 7/21/24
 */
@Getter
@Setter
public class ExcludeCategory extends BaseVo {

    private String categoryName;
    private String relateMcid;
    private Date updatedAt;
}
