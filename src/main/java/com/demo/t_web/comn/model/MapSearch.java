package com.demo.t_web.comn.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 * com.demo.t_web.comn.model.MaxLatLng
 *   - MaxLatLng.java
 * </pre>
 *
 * @author : 한태우
 * @className : MaxLatLng
 * @description :
 * @date : 4/29/24
 */
@Getter
@Setter
public class MapSearch implements Serializable {

    public double lat;
    public double lng;
    public int radius;
    public double maxLat;
    public double maxLng;
    public double minLat;
    public double minLng;

    public String name;
    public String mcid;
    public String addr1;
    public String addr2;
    public List<String> placeName;

    public String cacheKey;

    public String getCacheKey(){
        StringBuilder sb = new StringBuilder();
        sb.append((String.valueOf(lat)).replace(".", ""))
                .append(String.valueOf(lng).replace(".", ""))
                .append(radius);
        if(mcid != null){
            sb.append(mcid);
        }
        if(addr1 != null){
            sb.append(addr1);
        }
        if(placeName != null){
            for(String place : placeName){
                sb.append(place);
            }
        }

        return sb.toString();
    }
}
