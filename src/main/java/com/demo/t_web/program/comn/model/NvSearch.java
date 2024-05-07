package com.demo.t_web.program.comn.model;

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
public class NvSearch {

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
        public double getLat(){
            String pre = getMapy().substring(0, 3);
            String suf = getMapy().substring(3);
            String comb = pre + "." + suf;
            return Utilities.parseDouble(comb);
        }

        @JsonIgnore
        public double getLng(){
            String pre = getMapx().substring(0, 3);
            String suf = getMapx().substring(3);
            String comb = pre + "." + suf;
            return Utilities.parseDouble(comb);
        }
    }
}