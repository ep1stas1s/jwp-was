package webserver.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RequestBodyTest {

    private static final String PARAMS = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

    @Test
    void putAndGet() {
        RequestBody requestBody = new RequestBody();

        requestBody.put(PARAMS);
        assertEquals(requestBody.get("userId"), "javajigi");
        assertEquals(requestBody.get("password"), "password");
        assertEquals(requestBody.get("name"), "%EB%B0%95%EC%9E%AC%EC%84%B1");
        assertEquals(requestBody.get("email"), "javajigi%40slipp.net");
    }
}