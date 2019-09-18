package webserver.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

class CustomRequestTest {

    private CustomRequest customRequest;

    @BeforeEach
    void setUp() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1\r\n");
        stringBuilder.append("Host: localhost:8080\r\n");
        stringBuilder.append("Connection: keep-alive\r\n");
        stringBuilder.append("Accept: */*\r\n");
        stringBuilder.append("\r\n");

        InputStream inputStream = new ByteArrayInputStream(stringBuilder.toString().getBytes(StandardCharsets.UTF_8));
        customRequest = CustomRequest.of(inputStream);
    }

    @DisplayName("Version 을 확인한다.")
    @Test
    void getVersion() {
        assertThat(customRequest.getVersion()).isEqualTo("HTTP/1.1");
    }

    @DisplayName("Header 를 확인한다.")
    @Test
    void getHeader() {
        assertThat(customRequest.getHeader("Accept")).isEqualTo("*/*");
    }

    @DisplayName("Method 를 확인한다.")
    @Test
    void getMethod() {
        assertThat(customRequest.getMethod()).isEqualTo("GET");
    }

    @DisplayName("Param 을 확인한다.")
    @Test
    void getParam() {
        assertThat(customRequest.getParam("userId")).isEqualTo("javajigi");
    }

    @DisplayName("Body 를 확인한다.")
    @Test
    void getBody() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("POST /user/create HTTP/1.1\r\n");
        stringBuilder.append("Host: localhost:8080\r\n");
        stringBuilder.append("Connection: keep-alive\r\n");
        stringBuilder.append("Accept: */*\r\n");
        stringBuilder.append("\r\n");
        stringBuilder.append("userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net\r\n");

        InputStream inputStream = new ByteArrayInputStream(stringBuilder.toString().getBytes(StandardCharsets.UTF_8));
        CustomRequest customRequestWithContents = CustomRequest.of(inputStream);

        assertThat(customRequestWithContents.getBody("userId")).isEqualTo("javajigi");
    }

    @DisplayName("Path 를 확인한다.")
    @Test
    void getPath() {
        assertThat(customRequest.getPath()).isEqualTo("/user/create");
    }
}