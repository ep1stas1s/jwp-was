package webserver.request;

import java.util.HashMap;
import java.util.Map;

public class RequestBody {

    private Map<String, String> body;

    public RequestBody() {
        this.body = new HashMap<>();
    }

    public String get(String key) {
        return body.get(key);
    }

    public void put(String body) {
        String[] bodyParams = body.split("&");
        for (String param : bodyParams) {
            String[] queryParam = param.split("=");
            this.body.put(queryParam[0], queryParam[1]);
        }
    }
}
