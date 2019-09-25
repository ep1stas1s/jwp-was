package webserver.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.utils.FileIoUtils;
import webserver.utils.FilePathUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Objects;

public class ResponseBody {
    private static final Logger log = LoggerFactory.getLogger(ResponseBody.class);

    private String path;
    private byte[] body;

    public ResponseBody(String path) {
        this.path = path;
        try {
            this.body = FileIoUtils.loadFileFromClasspath(FilePathUtils.getResourcePath(path));
        } catch (IOException | URISyntaxException e) {
            log.error("{}, {}", path, e.getMessage());
        }
    }

    public int getBodyLength() {
        return body.length;
    }

    public byte[] getBody() {
        return body;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return "ResponseBody{" +
                "path='" + path + '\'' +
                ", body=" + Arrays.toString(body) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseBody that = (ResponseBody) o;
        return Objects.equals(path, that.path) &&
                Arrays.equals(body, that.body);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(path);
        result = 31 * result + Arrays.hashCode(body);
        return result;
    }
}
