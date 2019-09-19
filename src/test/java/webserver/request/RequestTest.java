package webserver.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

class RequestTest {

    private Request request;

    @BeforeEach
    void setUp() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1\r\n");
        stringBuilder.append("Host: localhost:8080\r\n");
        stringBuilder.append("Connection: keep-alive\r\n");
        stringBuilder.append("Accept: */*\r\n");
        stringBuilder.append("\r\n");

        InputStream inputStream = new ByteArrayInputStream(stringBuilder.toString().getBytes(StandardCharsets.UTF_8));
        request = Request.of(inputStream);
    }

    @DisplayName("Version 을 확인한다.")
    @Test
    void getVersion() {
        assertThat(request.getVersion()).isEqualTo("HTTP/1.1");
    }

    @DisplayName("Header 를 확인한다.")
    @Test
    void getHeader() {
        assertThat(request.getHeader("Accept")).isEqualTo("*/*");
    }

    @DisplayName("Method 를 확인한다.")
    @Test
    void getMethod() {
        assertThat(request.getMethod()).isEqualTo("GET");
    }

    @DisplayName("Param 을 확인한다.")
    @Test
    void getParam() {
        assertThat(request.getParam("userId")).isEqualTo("javajigi");
    }

    @DisplayName("Body 를 확인한다.")
    @Test
    void getBody() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("POST /user/create HTTP/1.1\r\n");
        stringBuilder.append("Host: localhost:8080\r\n");
        stringBuilder.append("Connection: keep-alive\r\n");
        stringBuilder.append("Content-Length: 93\r\n");
        stringBuilder.append("Accept: */*\r\n");
        stringBuilder.append("\r\n");
        stringBuilder.append("userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net\r\n");

        InputStream inputStream = new ByteArrayInputStream(stringBuilder.toString().getBytes(StandardCharsets.UTF_8));
        Request requestWithContents = Request.of(inputStream);

        assertThat(requestWithContents.getBody("userId")).isEqualTo("javajigi");
        assertThat(requestWithContents.getBody("password")).isEqualTo("password");
        assertThat(requestWithContents.getBody("name")).isEqualTo("%EB%B0%95%EC%9E%AC%EC%84%B1");
        assertThat(requestWithContents.getBody("email")).isEqualTo("javajigi%40slipp.net");
    }

    @DisplayName("Path 를 확인한다.")
    @Test
    void getPath() {
        assertThat(request.getPath()).isEqualTo("/user/create");
    }
}