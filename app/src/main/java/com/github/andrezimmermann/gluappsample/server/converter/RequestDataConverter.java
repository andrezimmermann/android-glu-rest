package com.github.andrezimmermann.gluappsample.server.converter;

import com.github.andrezimmermann.gluappsample.server.data.RequestData;


/**
 * Converter for POJO to String
 */
public interface RequestDataConverter {

    <T extends RequestData> String fromData(T data);

    <T extends RequestData> T toData(String data, Class<T> elementClass);

}
