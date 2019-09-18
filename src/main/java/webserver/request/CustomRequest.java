package webserver.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class CustomRequest {

    private General general;
    private RequestHeaders requestHeaders;
    private RequestParams requestParams;
    private RequestBody requestBody;

    private CustomRequest(General general, RequestHeaders requestHeaders, RequestParams requestParams, RequestBody requestBody) {
        this.general = general;
        this.requestHeaders = requestHeaders;
        this.requestParams = requestParams;
        this.requestBody = requestBody;
    }

    public static CustomRequest of(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

        String firstLine = bufferedReader.readLine();
        General general = new General(firstLine);
        RequestParams requestParams = new RequestParams();

        if (general.hasParams()) {
            String search = general.getSearch();
            requestParams.put(search);
        }

        String headerLine;
        RequestHeaders requestHeaders = new RequestHeaders();
        while (!(headerLine = bufferedReader.readLine()).equals("")) {
            String[] splitHeader = headerLine.split(":");
            requestHeaders.put(splitHeader[0].trim(), splitHeader[1].trim());
        }

        RequestBody requestBody = new RequestBody();

        if (bufferedReader.ready()) {
            requestBody.put(bufferedReader.readLine());
        }

        return new CustomRequest(general, requestHeaders, requestParams, requestBody);
    }

    public String getMethod() {
        return general.getMethod();
    }

    public String getHeader(String key) {
        return requestHeaders.get(key);
    }

    public String getParam(String key) {
        return requestParams.get(key);
    }

    // TODO: 2019-09-18 key 없을 때 처리
    public String getBody(String key) {
        return requestBody.get(key);
    }

    public String getPath() {
        return general.getUri();
    }


    public String getVersion() {
        return general.getVersion();
    }

}
