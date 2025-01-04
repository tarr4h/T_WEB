package com.demo.t_web.program.comn.oldFiles.model;

import com.demo.t_web.comn.util.Utilities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * <pre>
 * com.demo.t_web.program.comn.model.NvSearch
 *   - NvSearch.java
 * </pre>
 *
 * @author : 한태우
 * @className : NvSearch
 * @description :
 * @date : 5/7/24
 */
@Getter
@Setter
public class NvSearch_old {

    private int display;
    private String lastBuildDate;
    private int start;
    private int total;
    private List<NvSearchDetail> items;

    @Getter
    @Setter
    public static class NvSearchDetail{
        private String address;
        private String category;
        private String description;
        private String link;
        private String mapx;
        private String mapy;
        private String roadAddress;
        private String telephone;
        private String title;

        @JsonIgnore
        public String getLatStr(){
            return new StringBuilder(getMapy()).insert(2, ".").toString();
        }

        @JsonIgnore
        public String getLngStr(){
            return new StringBuilder(getMapx()).insert(3, ".").toString();
        }

        @JsonIgnore
        public double getLat(){
            return Utilities.parseDouble(getLatStr());
        }

        @JsonIgnore
        public double getLng(){
            return Utilities.parseDouble(getLngStr());
        }
    }
}