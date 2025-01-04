package com.demo.t_web.program.comn.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * com.demo.t_web.program.comn.model.NaverMap
 *   - NaverMap.java
 * </pre>
 *
 * @author : 한태우
 * @className : NaverMap
 * @description :
 * @date : 4/28/24
 */
@Getter
@Setter
public class NaverMap {

    @JsonProperty
    private String bookmarkId;
    @JsonProperty
    private String name;
    @JsonProperty
    private double px;
    @JsonProperty
    private double py;
    @JsonProperty
    private String type;
    @JsonProperty
    private String address;
    @JsonProperty
    private String mcid;
    @JsonProperty
    private String mcidName;
    @JsonProperty
    private boolean available;

    @JsonIgnore
    private String displayName;
    @JsonIgnore
    private String useTime;
    @JsonIgnore
    private String lastUpdateTime;
    @JsonIgnore
    private String creationTime;
    @JsonIgnore
    private int order;
    @JsonIgnore
    private String sid;
    @JsonIgnore
    private String memo;
    @JsonIgnore
    private String url;
    @JsonIgnore
    private String rcode;
    @JsonIgnore
    private String[] cidPath;
    @JsonIgnore
    private String folderMappings;
    @JsonIgnore
    private Object placeInfo;
    @JsonIgnore
    private Object isIndoor;

}
