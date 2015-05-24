package com.github.andrezimmermann.gluappsample.server.converter;


import com.github.andrezimmermann.gluappsample.server.data.RequestData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class JsonConverter implements RequestDataConverter {

    Gson gson = new Gson();

    @Override
    public <T extends RequestData> String fromData(T data) {

        Type type = new TypeToken<T>() {
        }.getType();

        return gson.toJson(data, type);
    }

    @Override
    public <T extends RequestData> T toData(String data, Class<T> elementClass) {
        return gson.fromJson(data, elementClass);
    }
}
