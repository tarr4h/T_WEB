package com.demo.t_web.comn.util;

import com.demo.t_web.comn.enums.ErrorType;
import com.demo.t_web.comn.model.MapSearch;
import com.demo.t_web.comn.model.Tmap;
import com.demo.t_web.program.comn.model.DrivingVo;
import com.demo.t_web.program.comn.model.NvSearch;
import com.demo.t_web.program.sys.model.BaseVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.kotlin.KotlinModule;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    private static final double EARTH_RADIUS = 6371;

    private static String naverApiKeyId;
    private static String naverApiKey;
    private static String naverDrivingUrl;
    private static String naverClientId;
    private static String naverClientSecret;

    @Value("${naver.apiKeyId}")
    private void setNaverApiKeyId(String value){
        naverApiKeyId = value;
    }
    @Value("${naver.apiKey}")
    private void setNaverApiKey(String value){
        naverApiKey = value;
    }
    @Value("${naver.drivingUrl}")
    private void setNaverDrivingUrl(String value){
        naverDrivingUrl = value;
    }
    @Value("${naver.clientId}")
    private void setNaverClientId(String value){
        naverClientId = value;
    }
    @Value("${naver.clientSecret}")
    private void setNaverClientSecret(String value){
        naverClientSecret = value;
    }

    @Autowired
    private ConfigurableApplicationContext ctx;

    @PostConstruct
    public void init(){
        context = this.ctx;
        objectMapper = (ObjectMapper) context.getBean("jacksonObjectMapper");
    }

    public static ResponseEntity<?> retValue(Object obj){
        return ResponseEntity.ok().body(obj);
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
        } catch (ClassCastException e){
            throw new ClassCastException("PARSE INT >> OBJ IS NOT SUITABLE TYPE");
        }
    }

    public static double parseDouble(Object obj){
        try {
            if(obj instanceof String){
                return Double.parseDouble(String.valueOf(obj));
            } else {
                return (double) obj;
            }
        } catch (ClassCastException e){
            throw new ClassCastException("PARSE DOUBLE >> OBJ IS NOT SUITABLE TYPE");
        }
    }

    public static long parseLong(Object obj){
        try {
            if(obj instanceof String){
                return Long.parseLong(String.valueOf(obj));
            } else {
                return (long) obj;
            }
        } catch (ClassCastException e){
            throw new ClassCastException("PARSE LONG >> OBJ IS NOT SUITABLE TYPE");
        }
    }

    public static boolean parseBoolean(Object obj){
        try {
            if(obj instanceof String){
                return Boolean.parseBoolean(String.valueOf(obj));
            } else {
                return (boolean) obj;
            }
        } catch (ClassCastException e){
            throw new ClassCastException("PARSE BOOLEAN >> OBJ IS NOT SUITABLE TYPE");
        }
    }

    public static Map<String, Object> beanToMap(Object obj) {
        if(obj == null) return null;
        return objectMapper.convertValue(obj, Tmap.class);
    }

    public static MapSearch getMaxLatLng(double lat, double lng, int radius){
        MapSearch ret = new MapSearch();

        double R = 6371;

        double aRad = radius / R;

        double latDeg = Math.toDegrees(aRad);
        double maxLat = lat + latDeg;
        double minLat = lat - latDeg;

        double lngDeg = Math.toDegrees(aRad / Math.cos(Math.toRadians(lat)));
        double maxLng = lng + lngDeg;
        double minLng = lng - lngDeg;

        ret.setLat(lat);
        ret.setLng(lng);
        ret.setRadius(radius);

        ret.setMaxLat(maxLat);
        ret.setMinLat(minLat);
        ret.setMaxLng(maxLng);
        ret.setMinLng(minLng);

        return ret;
    }

    // X, Y : 기준 lat, lng - A, B : 비교할 lat, lng
    public static double calculateArea(double X, double Y, double A, double B) {
        if(X == A && Y == B) {
            return 0;
        }

        return getSqrtDistance(X, Y, A, B);
    }

    public static double getSqrtDistance(double X, double Y, double A, double B){
        double latDistance = Math.toRadians(A - X);
        double lonDistance = Math.toRadians(B - Y);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(X)) * Math.cos(Math.toRadians(A))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }

    public static DrivingVo getDriving(double startLat, double startLng, double goalLat, double goalLng){
        RestTemplate restTemplate = new RestTemplate();

        String apiKeyId = naverApiKeyId;
        String apiKey = naverApiKey;
        String drivingUrl = naverDrivingUrl;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Accept", "*/*");
        headers.add("User-Agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Mobile Safari/537.36");
        headers.set("X-NCP-APIGW-API-KEY-ID", apiKeyId);
        headers.set("X-NCP-APIGW-API-KEY", apiKey);

        HttpEntity<HttpHeaders> request = new HttpEntity<>(headers);

        URI uri = UriComponentsBuilder.fromHttpUrl(drivingUrl)
                .queryParam("start", startLng + "," + startLat)
                .queryParam("goal", goalLng + "," + goalLat)
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUri();

        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, request, String.class);
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new KotlinModule());
        try{
            return objectMapper.readValue(response.getBody(), DrivingVo.class);
        } catch(JsonProcessingException e){
            log.error("NAVERMAP API RESPONSE ERROR = {}", e.getMessage());
            return null;
        }
    }

    public static NvSearch getNvSearch(Tmap param){
        String text = param.getString("searchTxt");
        if(text == null) return null;

        URI uri = UriComponentsBuilder.fromUriString("https://openapi.naver.com")
                .path("/v1/search/local.json")
                .queryParam("query", text)
                .queryParam("display", 10)
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUri();

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", naverClientId);
        requestHeaders.put("X-Naver-Client-Secret", naverClientSecret);
        String responseBody = nvGet(uri.toString(),requestHeaders);

        log.debug("responseBody = {}", responseBody);

        ObjectMapper objMapper = new ObjectMapper().registerModule(new KotlinModule());
        try{
            return objMapper.readValue(responseBody, NvSearch.class);
        } catch (JsonProcessingException e){
            log.error("NAVER SEARCH RESPONSE ERROR = {}", e.getMessage());
            return null;
        }
    }


    private static String nvGet(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = nvConnect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return nvReadBody(con.getInputStream());
            } else {
                return nvReadBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("NAVER API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    private static HttpURLConnection nvConnect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("NAVER API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("NAVER 연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    public static String nvReadBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body, StandardCharsets.UTF_8);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }


            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("NAVER API 응답을 읽는 데 실패했습니다.", e);
        }
    }

    @SuppressWarnings("unchecked")
    public static String listToSeparatedString(List<?> list, String separator, String field){
        StringBuilder sb = new StringBuilder();
        int ind = 0;
        for(Object o : list){
            if(o instanceof BaseVo){
                o = Utilities.beanToMap(o);
            } else if(!(o instanceof LinkedHashMap<?,?>)){
                break;
            }
            sb.append(((Map<String, Object>) o).get(field));
            if(ind++ < list.size() - 1){
                sb.append(separator);
            }
        }

        return sb.toString();
    }

    public static String listToSeparatedString(String[] list, String separator){
        return listToSeparatedString(Arrays.asList(list), separator);
    }

    public static String listToSeparatedString(List<String> list, String separator){
        StringBuilder sb = new StringBuilder();
        int ind = 0;
        for(String o : list){
            sb.append(o);
            if(ind++ < list.size() - 1){
                sb.append(separator);
            }
        }
        return sb.toString();
    }

    public static void sendHandleError(HttpServletResponse response, ErrorType errorType, String msg){
        response.setStatus(errorType.getCode());
        response.setContentType(MediaType.APPLICATION_JSON.toString());
        try {
            Map<String, String> obj = new HashMap<>();
            obj.put("errorType", errorType.getType());
            obj.put("msg", msg);
            obj.put("title", errorType.getTitle());

            String json = new ObjectMapper().writeValueAsString(obj);
            response.getWriter().write(json);
            response.getWriter().flush();
        } catch (Exception e){
            log.error("handle exception error : ", e);
        }
    }

    public static void sendHandleError(HttpServletResponse response, ErrorType errorType, Exception ex){
        sendHandleError(response, errorType, ex.getMessage());
    }

    public static void sendHandleError(HttpServletResponse response, ErrorType errorType){
        sendHandleError(response, errorType, errorType.getMsg());
    }

    public static ServletRequestAttributes getContainer(){
        ServletRequestAttributes container = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(container == null){
            throw new NullPointerException("연결이 종료되었습니다.");
        }

        return container;
    }

    public static HttpServletResponse getResponse(){
        return getContainer().getResponse();
    }

    public static HttpServletRequest getRequest(){
        return getContainer().getRequest();
    }

    public static Cookie getCookie(String name){
        return getCookie(Utilities.getRequest(), name);
    }

    public static Cookie getCookie(HttpServletRequest request, String name){
        Cookie[] cookies = request.getCookies();
        if(cookies == null){
            return null;
        }
        Optional<Cookie> findCookie = Arrays.stream(cookies)
                                        .filter(cookie -> cookie.getName().equals(name))
                                        .findAny();
        return findCookie.orElse(null);
    }

    public static void deleteCookie(String name){
        deleteCookie(Utilities.getRequest(), name);
    }

    public static void deleteCookie(HttpServletRequest request, String name){
        Cookie cookie = getCookie(request, name);
        if(cookie != null){
            ResponseCookie responseCookie = ResponseCookie.from(cookie.getName(), "")
                    .path("/")
                    .sameSite("None")
                    .httpOnly(true)
                    .secure(true)
                    .maxAge(0)
                    .build();

            Utilities.getResponse().addHeader("Set-Cookie", responseCookie.toString());
        }
    }

    public static void addCookie(String name, String value){
        ResponseCookie jwtCookie = ResponseCookie.from(name, value)
                .path("/")
                .sameSite("None")
                .httpOnly(true)
                .secure(true)
                .maxAge(30 * 60) // 30분
//                .maxAge(10) // 10초
                .build();

        HttpServletResponse response = Utilities.getResponse();
        response.addHeader("Set-Cookie", jwtCookie.toString());
    }
}
