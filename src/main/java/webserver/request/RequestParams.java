package webserver.request;

import java.util.HashMap;
import java.util.Map;

public class RequestParams {

    private Map<String, String> params;

    public RequestParams() {
        this.params = new HashMap<>();
    }

    public void put(String key, String value) {
        params.put(key, value);
    }

    public String get(String key) {
        return params.get(key);
    }

    public void put(String search) {
        String[] queryParams = search.split("&");
        for (String param : queryParams) {
            String[] queryParam = param.split("=");
            params.put(queryParam[0], queryParam[1]);
        }
    }
}
