package com.zhihuianxin.xyaxf.http;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by Vincent on 2016/12/9.
 */

public class CustomGsonConverterFactory<T extends Object> extends Converter.Factory{

    public CustomGsonConverterFactory() {}

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new CustomGsonResponseBodyConverter<String,T>();
    }
}
