package com.demo.t_web.comn.model;

import com.demo.t_web.comn.util.Utilities;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.support.JdbcUtils;

import java.util.LinkedHashMap;
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
@Slf4j
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
            if(value instanceof byte[]){
                return super.put(JdbcUtils.convertUnderscoreNameToPropertyName(key), new String((byte[]) value));
            } else {
                return super.put(JdbcUtils.convertUnderscoreNameToPropertyName(key), value);
            }
        } else if (value instanceof String){
            return super.put(key.toLowerCase(), String.valueOf(value));
        } else {
            return super.put(key, value);
        }
    }

    public String getString(String key) {
        Object value = super.get(key);
        if(value instanceof String){
            return (String) value;
        } else {
            return null;
        }
    }

    public int getInt(String key){
        Object v = super.get(key);
        if(v instanceof Integer){
            return (int) v;
        } else {
            return Utilities.parseInt(v);
        }
    }

    public double getDouble(String key){
        Object v = super.get(key);
        if(v instanceof Double){
            return (double) v;
        } else {
            return Utilities.parseDouble(v);
        }
    }

    public boolean getBoolean(String key){
        Object v = super.get(key);
        if(v instanceof Boolean){
            return (boolean) v;
        } else {
            return Utilities.parseBoolean(v);
        }
    }
}
