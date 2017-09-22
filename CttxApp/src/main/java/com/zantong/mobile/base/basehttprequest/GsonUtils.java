package com.zantong.mobile.base.basehttprequest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 
 * @author zyb
 *
 *  
 *    *  *   *  *     
 *  *      *      *   
 *  *             *   
 *   *           *    
 *      *     *       
 *         *          
 * 
 *
 * create at 17/2/9 下午3:35
 */


public class GsonUtils {

    public static ParameterizedType type(final Class<?> raw, final Type... args) {
        return new ParameterizedType() {
            public Type getRawType() {
                return raw;
            }

            public Type[] getActualTypeArguments() {
                return args;
            }

            public Type getOwnerType() {
                return null;
            }
        };
    }

    public static Gson getGson() {
        GsonBuilder builder = new GsonBuilder();
        return builder.create();
    }

    public static <T>List<T> getModelListValue(String string, Class<T> class_) {
        ResponseList response;
        Type objectTypes= GsonUtils.type(ResponseList.class, class_);
        response=GsonUtils.getGson().fromJson(string, objectTypes);
        return response.getData();
    }

    public static <T>List<T> getModelListValue2(String string, Class<T> class_) {
        ResponseList2 response;
        Type objectTypes= GsonUtils.type(ResponseList2.class, class_);
        response=GsonUtils.getGson().fromJson(string, objectTypes);
        return response.getData();
    }
}
