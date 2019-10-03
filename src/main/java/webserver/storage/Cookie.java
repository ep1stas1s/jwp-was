package webserver.storage;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Cookie {
    private static final String COOKIE_SEPARATOR = "; ";
    private static final String EQUALS_SIGN = "=";

    private Map<String, String> cookies;

    public Cookie() {
        this.cookies = new HashMap<>();
    }

    public Cookie(String cookiesToString) {
        cookies = new HashMap<>();

        if (Objects.isNull(cookiesToString)) {
            return;
        }
        String[] pieceOfCookie = cookiesToString.split(COOKIE_SEPARATOR);

        for (String cookie : pieceOfCookie) {
            String[] brokenCookie = cookie.split(EQUALS_SIGN);
            cookies.put(brokenCookie[0], brokenCookie[1]);
        }
    }

    public void put(String key, String value) {
        cookies.put(key, value);
    }

    public String get(String key) {
        return cookies.get(key);
    }

    @Override
    public String toString() {
        return "Cookie{" +
                "cookies=" + cookies +
                '}';
    }
}
