package webserver.request;

public class General {
    private String method;
    private String uri;
    private String version;

    public General(String requestFirstLine) {
        String[] splitFirstLine = requestFirstLine.split(" ");
        checkRequestFirstLine(splitFirstLine);
        this.method = splitFirstLine[0];
        this.uri = splitFirstLine[1];
        this.version = splitFirstLine[2];
    }

    private void checkRequestFirstLine(String[] splitFirstLine) {
        if (splitFirstLine.length != 3) {
            throw new IllegalArgumentException("잘못된 Http 요청입니다.");
        }
    }

    public String getUri() {
        // TODO: 2019-09-18 개선! search랑 ^^;
        return uri.split("\\?")[0];
    }

    public String getMethod() {
        return method;
    }

    public String getFullUri() {
        return uri;
    }

    public String getVersion() {
        return version;
    }

    public boolean hasParams() {
        return uri.contains("?");
    }

    public String getSearch() {
        checkParams();
        return uri.split("\\?")[1];
    }

    private void checkParams() {
        if (!hasParams()) {
            throw new IllegalArgumentException("Search 가 없는 Request 입니다.");
        }
    }
}
