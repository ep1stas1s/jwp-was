package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.request.Request;
import webserver.response.HttpResponse;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());


        try (InputStream in = connection.getInputStream();
             OutputStream out = connection.getOutputStream()) {

            Request request = Request.of(in);

            HttpResponse httpResponse = RequestMapper.controller(request);

            DataOutputStream dos = new DataOutputStream(out);
            renderer(dos, httpResponse.responseBuilder());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void renderer(DataOutputStream dos, List<String> responses) {
        try {
            for (String response : responses) {
                dos.write(response.getBytes());
            }
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
