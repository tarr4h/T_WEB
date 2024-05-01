package com.demo.t_web.comn.model;

import com.demo.t_web.comn.util.Utilities;
import org.springframework.jdbc.support.JdbcUtils;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

/**
 * <pre>
 * com.web.trv.comn.model.Tmap
 *  - Tmap.java
 * </pre>
 *
 * @author : tarr4h
 * @ClassName : Tmap
 * @description :
 * @date : 2023-04-20
 */

public class Tmap extends LinkedHashMap<String, Object> {

    public Tmap(){
        super();
    }

    public Tmap(Map<String, Object> param){
        super();
        this.putAll(param);
    }

    public Tmap(Object obj){
        this(Utilities.beanToMap(obj));
    }

    @Override
    public Object put(String key, Object value) {
        if(key.contains("_")){
            return super.put(JdbcUtils.convertUnderscoreNameToPropertyName(key), value);
        } else {
            return super.put(key.toLowerCase(), value);
        }
    }

    public String getString(String key) {
        try{
            Object value = super.get(key);
            if(value instanceof String){
                return (String) value;
            } else {
                throw new Exception("MAP VALUE IS NOT INSTANCEOF STRING");
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
