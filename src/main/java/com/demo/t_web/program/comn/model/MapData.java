package com.demo.t_web.program.comn.model;

import com.demo.t_web.comn.util.Utilities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

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
public class MapData implements Comparable<MapData>, Serializable {

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
    @JsonIgnore
    private DrivingVo driving;

    public double getLat(){
        return Utilities.parseDouble(getPy());
    }

    public double getLng(){
        return Utilities.parseDouble(getPx());
    }

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
