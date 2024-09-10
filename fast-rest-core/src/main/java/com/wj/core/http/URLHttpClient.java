package com.wj.core.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class URLHttpClient implements HttpClient {

    private RequestConfig requestConfig;

    public URLHttpClient(RequestConfig requestConfig) {
        this.requestConfig = requestConfig;
    }

    @Override
    public <T> ResponseEntity<T> req(RequestEntity<?> request, Class<T> respType) {
        HttpURLConnection connection = null;
        try {
            connection = createHttpConnection(request);
            int responseCode = connection.getResponseCode();

            InputStream errorStream = connection.getErrorStream();
            InputStream responseStream = (errorStream != null ? errorStream : connection.getInputStream());
            String data = inputStreamToString(responseStream);
            System.out.println(data);
            return new ResponseEntity<>(null, new HttpHeaders(connection.getHeaderFields()), responseCode);
        } catch (IOException e) {
            throw new HttpException(e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private HttpURLConnection createHttpConnection(RequestEntity<?> request) throws IOException {
        URL url = request.getUri().toURL();
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setConnectTimeout(requestConfig.getConnectTimeout());
        connection.setReadTimeout(requestConfig.getReadTimeout());
        connection.setUseCaches(requestConfig.isUseCaches());
        connection.setInstanceFollowRedirects(requestConfig.isInstanceFollowRedirects());

        connection.setRequestMethod(request.getMethod() == null ? HttpMethod.GET.getName() : request.getMethod().getName());

        HttpHeaders headers = request.getHeaders();
        if (headers != null) {
            for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
                String key = entry.getKey();
                List<String> values = entry.getValue();
                String value = String.join("; ", values);
                connection.setRequestProperty(key, value);
            }
        }
        return connection;
    }

    public static String inputStreamToString(InputStream inputStream) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append(System.lineSeparator());
            }
        }
        return sb.toString();
    }
}
