package webserver.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GeneralTest {

    private static final String GET = "GET";
    private static final String HTTP_1_1 = "HTTP/1.1";
    private static final String URI = "/user/hello";
    private static final String PARAMS = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

    private static final General paramsGeneral = new General(String.format("%s %s?%s %s", GET, URI, PARAMS, HTTP_1_1));
    private static final General noParamsGeneral = new General(String.format("%s %s %s", GET, URI, HTTP_1_1));

    @DisplayName("성공적으로 General 객체를 생성한다.")
    @Test
    void constructor() {
        assertDoesNotThrow(() -> new General(String.format("%s %s %s", GET, URI, HTTP_1_1)));
    }

    @DisplayName("General 객체를 생성에 실패한다.")
    @Test
    void constructorFailed() {
        assertThrows(IllegalArgumentException.class, () -> new General("ADFAFE " + HTTP_1_1));
    }

    @DisplayName("General 객체의 Method 를 가져온다.")
    @Test
    void getMethod() {
        assertEquals(paramsGeneral.getMethod(), GET);
    }

    @DisplayName("General 객체의 FullUri 를 가져온다.")
    @Test
    void getFullUri() {
        assertEquals(paramsGeneral.getFullUri(), String.format("%s?%s", URI, PARAMS));
    }

    @DisplayName("General 객체의 Version 을 가져온다.")
    @Test
    void getVersion() {
        assertEquals(paramsGeneral.getVersion(), HTTP_1_1);
    }

    @DisplayName("General 객체의 parameter 있는지 확인한다.")
    @Test
    void hasParamsTrue() {
        assertTrue(paramsGeneral.hasParams());
    }

    @DisplayName("General 객체의 parameter 없는지 확인한다.")
    @Test
    void hasParamsFalse() {
        assertFalse(noParamsGeneral.hasParams());
    }

    @DisplayName("General 객체의 search 를 추출한다.")
    @Test
    void getSearchSuccess() {
        assertEquals(paramsGeneral.getSearch(), PARAMS);
    }

    @DisplayName("General 객체의 search 가 없을 때, search 를 추출에 실패한다.")
    @Test
    void getSearchFailed() {
        assertThrows(IllegalArgumentException.class, noParamsGeneral::getSearch);
    }
}