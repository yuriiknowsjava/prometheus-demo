package edu.yuriikoval1997.prometheusdemo.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@RestControllerAdvice
public class YuriiExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e, WebRequest req) {
        log.info("Request to {} has failed because of {}.", req.getContextPath(), e.getLocalizedMessage());
        return ResponseEntity.status(400).body(e.getLocalizedMessage());
    }
}
