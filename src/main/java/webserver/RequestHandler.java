package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import webserver.request.CustomRequest;
import webserver.response.CustomResponse;
import webserver.response.MediaType;
import webserver.response.ResponseStatus;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream inputStream = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            CustomRequest customRequest = CustomRequest.of(inputStream);

            DataOutputStream dos = new DataOutputStream(out);

//            String filePath = getFilePath(customRequest.getPath());
//            byte[] body = FileIoUtils.loadFileFromClasspath(filePath);

            // request header - version
            // response code - 200, OK
            // Content-type
            // content-length - body,
//            CustomResponse customResponse = CustomResponse.of(customRequest.getVersion(), OK, body);
            CustomResponse.response(customRequest.getVersion(), ResponseStatus.FOUND, null, dos, MediaType.ALL_VALUE);
//            response200Header(dos, body.length);
//            responseBody(dos, body);
        } catch (IOException /*| URISyntaxException*/ e) {
            logger.error(e.getMessage());
        }
    }

    private String getFilePath(String path) {
        if (path.endsWith(".html")) {
            return "./templates" + path;
        }
        return "./static" + path;
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
