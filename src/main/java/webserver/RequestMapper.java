package webserver;

import utils.FileUtils;
import webserver.controller.IndexController;
import webserver.controller.Responsive;
import webserver.controller.UserController;
import webserver.request.Request;
import webserver.response.HttpResponse;
import webserver.response.ResponseBody;
import webserver.response.ResponseHeaders;
import webserver.response.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

public class RequestMapper {
    private static final Map<String, Responsive> requestMapping = new HashMap<>();

    static {
        requestMapping.put("/index.html", IndexController.goIndex());
        requestMapping.put("/user/form.html", UserController.goForm());
        requestMapping.put("/user/create", UserController.createUser());
    }

    private static HttpResponse getStaticFileResponse(Request request) {
        ResponseHeaders responseHeaders = new ResponseHeaders();
        String path = request.getPath();

        HttpResponse httpResponse = new HttpResponse(
                ResponseStatus.OK, responseHeaders, new ResponseBody(path)
        );

        httpResponse.buildGetHeader(FileUtils.getExtension(path));
        return httpResponse;
    }

    public static HttpResponse controller(Request request) {
        String path = request.getPath();
        if (requestMapping.containsKey(path)) {
            return requestMapping.get(path).apply(request);
        }
        return getStaticFileResponse(request);
    }
}
