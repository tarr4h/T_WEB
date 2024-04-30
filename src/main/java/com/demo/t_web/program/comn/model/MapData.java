package com.demo.t_web.program.comn.model;

import lombok.Getter;
import lombok.Setter;

/**
 * <pre>
 * com.demo.t_web.program.comn.model.MapData
 *   - MapData.java
 * </pre>
 *
 * @author : 한태우
 * @className : MapData
 * @description :
 * @date : 4/29/24
 */
@Getter
@Setter
public class MapData implements Comparable<MapData>{

    private String id;
    private String name;
    private String px;
    private String py;
    private String address;
    private String addr1;
    private String addr2;
    private String addr3;
    private String mcid;
    private String mcidName;
    private String available;
    private String updatedDt;

    private double centerDistance;

    @Override
    public int compareTo(MapData o) {
        if(this.centerDistance < o.centerDistance){
            return -1;
        } else if(this.centerDistance == o.centerDistance){
            return 0;
        } else {
            return 1;
        }
    }
}
