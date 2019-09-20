package webserver.controller;

import webserver.request.Request;
import webserver.response.HttpResponse;

import java.util.function.Function;

public interface Responsive extends Function<Request, HttpResponse> {
    @Override
    HttpResponse apply(Request request);
}
