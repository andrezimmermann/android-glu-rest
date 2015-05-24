package com.github.andrezimmermann.gluappsample.server;


import android.util.Base64;

import com.github.andrezimmermann.gluappsample.server.converter.RequestDataConverter;
import com.github.andrezimmermann.gluappsample.server.data.RequestData;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
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
class RestConnection<T extends RequestData> {


    private RequestDataConverter converter;
    private String rootUrl = "https://api.appglu.com/v1/queries/";
    private HttpRequest httpRequest;

    public RestConnection(Endpoint endpoint, RequestDataConverter converter) {
        this.converter = converter;
        httpRequest = new HttpRequest(endpoint.getLocation()).
                basicAuth("WKD4N7YMA1uiM8V", "DtdTtzMLQlA0hk2C1Yi5pLyVIlAQ68").header("X-AppGlu-Environment", "staging");
    }

    public String sendData(T requestData) throws ServiceUnavaiableException, ServiceUnkownError {
        String data = converter.fromData(requestData);


        return httpRequest.execute(data);
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
            return getHeaders().isEmpty();
        }

        private void addAuthentication(HttpURLConnection connection) {
            String auth = new StringBuilder().append(user).append(":").append(password).toString();
            String basicAuth = "Basic " + new String(Base64.encodeToString(auth.getBytes(), Base64.DEFAULT));
            connection.setRequestProperty("Authorization", basicAuth);
        }
    }


}