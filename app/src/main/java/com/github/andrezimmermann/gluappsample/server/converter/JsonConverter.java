package com.github.andrezimmermann.gluappsample.server.converter;


import com.github.andrezimmermann.gluappsample.server.data.ConvertableData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class JsonConverter implements DataConverter {

    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssX").create();

    @Override
    public <T extends ConvertableData> String fromData(T data) {

        Type type = new TypeToken<T>() {
        }.getType();

        return gson.toJson(data, type);
    }

    @Override
    public <T extends ConvertableData> T toData(String data, Class<T> elementClass) {
        return gson.fromJson(data, elementClass);
    }
}
