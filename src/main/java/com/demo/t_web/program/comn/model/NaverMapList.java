package com.demo.t_web.program.comn.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 * com.demo.t_web.program.comn.model.NaverMapList
 *   - NaverMapList.java
 * </pre>
 *
 * @author : 한태우
 * @className : NaverMapList
 * @description :
 * @date : 4/28/24
 */
@Getter
@Setter
@ToString
public class NaverMapList {

    @JsonIgnore
    private Map<String, Object> folder;
    @JsonProperty("bookmarkList")
    private List<NaverMap> bookmarkList;
    @JsonIgnore
    private int unavailableCount;
    @JsonIgnore
    private boolean removed;
}
