package com.github.andrezimmermann.gluappsample.server.converter;

import com.github.andrezimmermann.gluappsample.server.data.ConvertableData;


/**
 * Converter for POJO to String
 */
public interface DataConverter {

    <T extends ConvertableData> String fromData(T data);

    <T extends ConvertableData> T toData(String data, Class<T> elementClass);

}
