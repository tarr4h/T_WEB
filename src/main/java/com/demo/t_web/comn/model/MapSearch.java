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
public class MapSearch {

    private double lat;
    private double lng;
    private int radius;
    private double maxLat;
    private double maxLng;
    private double minLat;
    private double minLng;

    private String mcid;
    private List<String> placeName;
}
