package webserver.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Request {

    private static final Logger logger = LoggerFactory.getLogger(Request.class);

    private RequestFirstLine requestFirstLine;
    private RequestHeaders requestHeaders;
    private RequestParams requestParams;
    private RequestBody requestBody;

    private Request(RequestFirstLine requestFirstLine, RequestHeaders requestHeaders, RequestParams requestParams, RequestBody requestBody) {
        this.requestFirstLine = requestFirstLine;
        this.requestHeaders = requestHeaders;
        this.requestParams = requestParams;
        this.requestBody = requestBody;
    }

    public static Request of(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

        RequestFirstLine requestFirstLine = new RequestFirstLine(bufferedReader.readLine());
        RequestParams requestParams = getRequestParams(requestFirstLine);
        RequestHeaders requestHeaders = getRequestHeaders(bufferedReader);
        RequestBody requestBody = getRequestBody(bufferedReader, requestHeaders);

        return new Request(requestFirstLine, requestHeaders, requestParams, requestBody);
    }

    private static RequestBody getRequestBody(BufferedReader bufferedReader, RequestHeaders requestHeaders) throws IOException {
        RequestBody requestBody = new RequestBody();

        if (requestHeaders.containsKey("Content-Length")) {
            String requestBodyString = IOUtils.readData(bufferedReader, Integer.parseInt(requestHeaders.get("Content-Length")));
            requestBody.put(requestBodyString);
        }
        return requestBody;
    }

    private static RequestHeaders getRequestHeaders(BufferedReader bufferedReader) throws IOException {
        RequestHeaders requestHeaders = new RequestHeaders();
        String headerLine;
        while (!(headerLine = bufferedReader.readLine()).equals("")) {
            String[] splitHeader = headerLine.split(":");
            requestHeaders.put(splitHeader[0].trim(), splitHeader[1].trim());
        }
        return requestHeaders;
    }

    private static RequestParams getRequestParams(RequestFirstLine requestFirstLine) {
        RequestParams requestParams = new RequestParams();

        if (requestFirstLine.hasParams()) {
            String search = requestFirstLine.getSearch();
            requestParams.put(search);
        }
        return requestParams;
    }

    public String getMethod() {
        return requestFirstLine.getMethod();
    }

    public String getHeader(String key) {
        return requestHeaders.get(key);
    }

    public String getParam(String key) {
        if (requestParams.isEmpty()) {
            throw new IllegalArgumentException("Parameter 가 없습니다.");
        }
        return requestParams.get(key);
    }

    public String getBody(String key) {
        return requestBody.get(key);
    }

    public String getPath() {
        return requestFirstLine.getUri();
    }


    public String getVersion() {
        return requestFirstLine.getVersion();
    }

    public boolean isGet() {
        return requestFirstLine.getMethod().equals("GET");
    }

    public boolean isPost() {
        return requestFirstLine.getMethod().equals("POST");
    }
}
