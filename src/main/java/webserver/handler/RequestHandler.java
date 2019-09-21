package webserver.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.controller.RequestMapper;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
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

        try (BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
             DataOutputStream dos = new DataOutputStream(connection.getOutputStream())) {

            List<String> lines = RequestParser.parse(bufferedReader);

            HttpRequest httpRequest = new HttpRequest(lines);
            HttpResponse httpResponse = RequestMapper.controller(httpRequest);

            Renderer.renderer(dos, httpResponse.responseBuilder());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}