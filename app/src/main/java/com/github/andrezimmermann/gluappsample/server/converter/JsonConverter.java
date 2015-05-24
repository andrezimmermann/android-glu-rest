package com.github.andrezimmermann.gluappsample.server.converter;


import com.github.andrezimmermann.gluappsample.server.data.ConvertableData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class JsonConverter implements DataConverter {

    Gson gson = getGson();

    private Gson getGson() {

        //Java 1.6 and below can use default pattern
        String property = System.getProperty("java.specification.version");
        double javaVersion = Double.parseDouble(property);


        if (javaVersion <= 1.6) {
            return new GsonBuilder()
                    .registerTypeAdapter(Date.class, new WorkaroundDateAdapter())
                    .create();
        } else {  //Above 1.6 needs to use a different pattern
            return new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssX").create();
        }
    }


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

    private class WorkaroundDateAdapter extends TypeAdapter<Date> {

        public final SimpleDateFormat iso8601DateFormater;

        public WorkaroundDateAdapter() {
            this.iso8601DateFormater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US) {


                public StringBuffer format(Date date, StringBuffer toAppendTo, java.text.FieldPosition pos) {
                    final StringBuffer buf = super.format(date, toAppendTo, pos);
                    buf.insert(buf.length() - 2, ':');
                    return buf;
                }

                public Date parse(String source) throws java.text.ParseException {
                    final int split = source.length() - 2;
                    return super.parse(source.substring(0, split - 1) + source.substring(split)); // replace ":" du TimeZone
                }
            };

            this.iso8601DateFormater.setTimeZone(TimeZone.getTimeZone("UTC"));
        }

        @Override
        public Date read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            return deserializeToDate(in.nextString());
        }

        private synchronized Date deserializeToDate(String json) {

            try {
                return iso8601DateFormater.parse(json);
            } catch (ParseException e) {
                throw new JsonSyntaxException(json, e);
            }
        }

        @Override
        public synchronized void write(JsonWriter out, Date value) throws IOException {
            if (value == null) {
                out.nullValue();
                return;
            }
            String dateFormatAsString = iso8601DateFormater.format(value);
            out.value(dateFormatAsString);
        }


    }
}
