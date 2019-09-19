package webserver.request;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RequestBody {

    private Map<String, String> body;

    public RequestBody() {
        this.body = new HashMap<>();
    }

    public String get(String key) {
        return Optional.ofNullable(body.get(key))
                .orElseThrow(() ->
                        new IllegalArgumentException(String.format("key 값(%s)에 해당되는 Body 데이터가 존재하지 않습니다.", key)));
    }

    public void put(String body) {
        String[] bodyParams = body.split("&");
        for (String param : bodyParams) {
            String[] queryParam = param.split("=");
            this.body.put(queryParam[0], queryParam[1]);
        }
    }
}
