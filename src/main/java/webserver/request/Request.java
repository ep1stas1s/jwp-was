package webserver.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class Request {

    private static final Logger logger = LoggerFactory.getLogger(Request.class);

    private RequestFirstLine requestFirstLine;
    private RequestHeaders requestHeaders;
    private RequestParams requestParams;
    private RequestBody requestBody;

    public Request(List<String> lines) {
        if (lines.size() == 0) {
            throw new IllegalArgumentException("line이 0임;;");
        }
        this.requestFirstLine = new RequestFirstLine(lines.get(0));
        this.requestParams = getRequestParams(requestFirstLine);
        getRequestHeaders(lines);
    }

    private static RequestParams getRequestParams(RequestFirstLine requestFirstLine) {
        RequestParams requestParams = new RequestParams();

        if (requestFirstLine.hasParams()) {
            String search = requestFirstLine.getSearch();
            requestParams.put(search);
        }
        return requestParams;
    }

    private void getRequestHeaders(List<String> lines) {
        this.requestHeaders = new RequestHeaders();
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] splitHeader = line.split(":", 2);
            requestHeaders.put(splitHeader[0].trim(), splitHeader[1].trim());

            if (line.equals("")) {
                this.requestBody = new RequestBody();
                if (requestHeaders.containsKey("Content-Length")) {
                    requestBody.put(lines.get(i + 1));
                }
            }
        }
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
