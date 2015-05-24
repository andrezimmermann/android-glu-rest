package com.github.andrezimmermann.gluappsample.server.api.command;


import com.github.andrezimmermann.gluappsample.server.api.Endpoint;
import com.github.andrezimmermann.gluappsample.server.api.error.ServiceUnavaiableException;
import com.github.andrezimmermann.gluappsample.server.api.error.ServiceUnkownError;
import com.github.andrezimmermann.gluappsample.server.converter.DataConverter;
import com.github.andrezimmermann.gluappsample.server.data.RequestData;
import com.github.andrezimmermann.gluappsample.server.data.ResponseData;

import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Wraps the HttpConnection to the WebService
 *
 * @param <T> the kind of data it process
 */
public class RestConnection<T extends RequestData<R>, R extends ResponseData> {


    private DataConverter converter;
    private String rootUrl = "https://api.appglu.com/v1/queries/";
    private HttpRequest httpRequest;
    private Class<R> resultClass;

    RestConnection(Endpoint endpoint, DataConverter converter) {
        this.converter = converter;
        httpRequest = new HttpRequest(rootUrl + endpoint.getLocation()).
                basicAuth("WKD4N7YMA1uiM8V", "DtdTtzMLQlA0hk2C1Yi5pLyVIlAQ68").header("X-AppGlu-Environment", "staging");

        resultClass = findTypeParameters(getClass(), RestConnection.class)[1];
    }

    public <S, B extends S> Class[] findTypeParameters(Class<B> base, Class<S> superClass) {
        Class[] actuals = new Class[0];
        for (Class clazz = base; !clazz.equals(superClass); clazz = clazz.getSuperclass()) {
            if (!(clazz.getGenericSuperclass() instanceof ParameterizedType))
                continue;

            Type[] types = ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments();
            Class[] nextActuals = new Class[types.length];
            for (int i = 0; i < types.length; i++)
                if (types[i] instanceof Class)
                    nextActuals[i] = (Class) types[i];
                else
                    nextActuals[i] = map(clazz.getTypeParameters(), types[i], actuals);
            actuals = nextActuals;
        }
        return actuals;
    }

    private Class map(Object[] variables, Object variable, Class[] actuals) {
        for (int i = 0; i < variables.length && i < actuals.length; i++)
            if (variables[i].equals(variable))
                return actuals[i];
        return null;
    }

    public R sendData(T requestData) throws ServiceUnavaiableException, ServiceUnkownError {
        String data = converter.fromData(requestData);


        String result = httpRequest.execute(data);


        return converter.toData(result, resultClass);
    }

    /**
     * Responsible to actually do the HTTP request, tries to be "robust", delegating any checked exception to the caller
     */
    class HttpRequest {

        private String url;
        private String user;
        private String password;
        private Map<String, String> headers;
        private boolean hasAuthentication;

        public HttpRequest(String url) {
            this.url = url;
        }

        public HttpRequest basicAuth(String user, String password) {
            hasAuthentication = true;
            this.user = user;
            this.password = password;
            return this;
        }

        public HttpRequest header(String key, String value) {
            getHeaders().put(key, value);
            return this;
        }

        private Map<String, String> getHeaders() {
            if (headers == null) {
                headers = new HashMap<>();
            }

            return headers;
        }

        public String execute(String data) throws ServiceUnavaiableException, ServiceUnkownError {
            HttpURLConnection connection = null;
            try {
                URL url = new URL(this.url);
                connection = (HttpURLConnection) url.openConnection();

                if (hasAuthentication()) {
                    addAuthentication(connection);
                }

                if (hasHeaders()) {
                    addHeaders(connection);
                }

                addExtraConfiguration(data, connection);

                outputDataToStream(data, connection);


                return getInputDataFromStream(connection);

            } catch (ProtocolException e) {
                throw new ServiceUnkownError();
            } catch (IOException e) {
                throw new ServiceUnavaiableException("Failed to connect to service", e);
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }

            }


        }

        private String getInputDataFromStream(HttpURLConnection connection) throws ServiceUnavaiableException {

            int code = getCodeFromConnection(connection);

            if (code == HttpURLConnection.HTTP_OK) {
                return getMessageFromInputStream(connection);
            } else {

                String errorResponse = getMessageFromErrorStream(connection);

                throw new ServiceUnavaiableException(errorResponse);
            }


        }

        private String getMessageFromInputStream(HttpURLConnection connection) throws ServiceUnavaiableException {
            try {
                return getMessageFromStream(connection.getInputStream());
            } catch (IOException e) {
                throw new ServiceUnavaiableException(e);
            }
        }

        private String getMessageFromErrorStream(HttpURLConnection connection) throws ServiceUnavaiableException {
            return getMessageFromStream(connection.getErrorStream());
        }

        private String getMessageFromStream(InputStream stream) throws ServiceUnavaiableException {
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream(8192);
                byte[] buffer = new byte[8192];
                int count = 0;

                while ((count = stream.read(buffer)) != -1) {
                    baos.write(buffer, 0, count);
                }

                return baos.toString();
            } catch (IOException e) {
                throw new ServiceUnavaiableException(e);
            }
        }

        private int getCodeFromConnection(HttpURLConnection connection) throws ServiceUnavaiableException {
            int code = 0;
            try {
                code = connection.getResponseCode();
            } catch (IOException e) {
                throw new ServiceUnavaiableException(e);
            }
            return code;
        }


        private void addExtraConfiguration(String data, HttpURLConnection connection) throws ProtocolException {
            connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            connection.setRequestProperty("Content-Length", "" + Integer.toString(data.getBytes().length));
            connection.setRequestProperty("Content-Language", "pt-BR");
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);
        }

        private void outputDataToStream(String data, HttpURLConnection connection) throws IOException {
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(data);
            writer.flush();
        }

        private void addHeaders(HttpURLConnection connection) {
            Set<Map.Entry<String, String>> entries = getHeaders().entrySet();

            for (Map.Entry<String, String> entry : entries) {
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }

        private boolean hasAuthentication() {
            return hasAuthentication;
        }

        private boolean hasHeaders() {
            return !getHeaders().isEmpty();
        }

        private void addAuthentication(HttpURLConnection connection) {
            String auth = new StringBuilder().append(user).append(":").append(password).toString();

            String basicAuth = "Basic " + new String(Base64.encodeBase64(auth.getBytes()));
            connection.setRequestProperty("Authorization", basicAuth);
        }
    }


}