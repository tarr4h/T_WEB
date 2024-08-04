package com.demo.t_web.comn.model;

import lombok.Getter;
import lombok.Setter;

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
public class MapSearch extends BaseVo {

    private double lat;
    private double lng;
    private int radius;
    private double maxLat;
    private double maxLng;
    private double minLat;
    private double minLng;

    private String name;
    private String mcid;
    private String addr1;
    private String addr2;
    private List<String> placeName;

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
