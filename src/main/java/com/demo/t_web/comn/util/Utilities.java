package com.demo.t_web.comn.util;

import com.demo.t_web.comn.model.MapSearch;
import com.demo.t_web.comn.model.Tmap;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * <pre>
 * com.web.trv.comn.util.Utilities
 *   - Utilities.java
 * </pre>
 *
 * @author : 한태우
 * @ClassName : Utilities
 * @description :
 * @date : 2023/07/19
 */
@Slf4j
@Component
public class Utilities {

    private static ConfigurableApplicationContext context;
    private static ObjectMapper objectMapper;

    @Autowired
    private ConfigurableApplicationContext ctx;

    @PostConstruct
    public void init(){
        context = this.ctx;
        objectMapper = (ObjectMapper) context.getBean("jacksonObjectMapper");
    }


    public static HttpSession getSession() throws Exception {
        ServletRequestAttributes servletContainer = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        if(servletContainer != null){
            HttpServletRequest request = servletContainer.getRequest();
            return request.getSession();
        } else {
            throw new Exception("SERVLETCONTAINER NULL");
        }
    }

    public static int sec2min(int sec){
        double doubleRange = (double) sec / 60;
        return (int) (Math.ceil(doubleRange));
    }

    public static int miliSec2min(int milisec){
        double doubleRange = (double) milisec / 1000;
        int double2int = (int) (Math.ceil(doubleRange));
        return sec2min(double2int);
    }

    public static String addMinToTimeFmt(String timeFormat, int min){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        try{
            Date date = sdf.parse(timeFormat);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.MINUTE, min);
            timeFormat = sdf.format(cal.getTime());
        } catch (ParseException e){
            log.error("PARSING EXCEPTION = {}", e.getMessage());
        }

        return timeFormat;
    }

    public static int minBetweenTimeStr(String timeFormat1, String timeFormat2){
        int result = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        try{
            Date date1 = sdf.parse(timeFormat1);
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(date1);

            Date date2 = sdf.parse(timeFormat2);
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(date2);

            if(cal1.getTimeInMillis() > cal2.getTimeInMillis()){
                throw new Exception("timeFormat1이 2보다 큽니다.");
            }

            int gap = (int) (cal2.getTimeInMillis() - cal1.getTimeInMillis());
            result = miliSec2min(gap);
        } catch (Exception e){
            log.error("PARSING EXCEPTION = {}", e.getMessage());
        }

        return result;
    }

    public static int getRandom6Number(){
        return (int) Math.floor(Math.random() * 1000000);
    }

    public static int parseInt(Object obj){
        try {
            if(obj instanceof String){
                return Integer.parseInt(String.valueOf(obj));
            } else {
                return (int) obj;
            }
        } catch (NullPointerException e){
            throw new NullPointerException("PARSE INT >> OBJ == NULL");
        }
    }

    public static Map<String, Object> beanToMap(Object obj) {
        if(obj == null) return null;
        return objectMapper.convertValue(obj, Tmap.class);
    }

    public static MapSearch getMaxLatLng(double lat, double lng, int distance){
        MapSearch ret = new MapSearch();

        double R = 6371;

        double aRad = distance / R;

        double latDeg = Math.toDegrees(aRad);
        double maxLat = lat + latDeg;
        double minLat = lat - latDeg;

        double lngDeg = Math.toDegrees(aRad / Math.cos(Math.toRadians(lat)));
        double maxLng = lng + lngDeg;
        double minLng = lng - lngDeg;

        ret.setMaxLat(maxLat);
        ret.setMinLat(minLat);
        ret.setMaxLng(maxLng);
        ret.setMinLng(minLng);

        return ret;
    }
}
