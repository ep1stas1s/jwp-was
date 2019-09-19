package webserver.request;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RequestParams {

    private Map<String, String> params;

    public RequestParams() {
        this.params = new HashMap<>();
    }

    public String get(String key) {
        return Optional.ofNullable(params.get(key))
                .orElseThrow(() ->
                        new IllegalArgumentException(String.format("key 값(%s)에 해당되는 Parameter 가 존재하지 않습니다.", key)));
    }

    public void put(String search) {
        String[] queryParams = search.split("&");
        for (String param : queryParams) {
            String[] queryParam = param.split("=");
            params.put(queryParam[0], queryParam[1]);
        }
    }

    public boolean isEmpty() {
        return params.isEmpty();
    }
}
