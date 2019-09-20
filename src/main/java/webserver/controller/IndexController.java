package webserver.controller;

import utils.FileUtils;
import webserver.response.HttpResponse;
import webserver.response.ResponseBody;
import webserver.response.ResponseHeaders;
import webserver.response.ResponseStatus;

public class IndexController {
    public static Responsive goIndex() {
        return request -> {
            ResponseHeaders responseHeaders = new ResponseHeaders();
            String path = request.getPath();

            HttpResponse httpResponse = new HttpResponse(
                    ResponseStatus.OK, responseHeaders, new ResponseBody(path)
            );

            httpResponse.buildGetHeader(FileUtils.getExtension(path));
            return httpResponse;
        };
    }
}
