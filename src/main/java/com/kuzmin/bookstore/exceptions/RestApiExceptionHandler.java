package com.kuzmin.bookstore.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

@ControllerAdvice
public class RestApiExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(RestApiExceptionHandler.class);

    public RestApiExceptionHandler() {
    }


    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<Error>> handleException(ServerWebExchange request, Exception ex) {
        ex.printStackTrace();
        Error error = ErrorUtils
                .createError(HttpStatus.INTERNAL_SERVER_ERROR.value()).setUrl(request.getRequest().getPath().toString())
                .setReqMethod(Objects.requireNonNull(request.getRequest().getMethod()).toString());
        return Mono.just(new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR));
    }
}