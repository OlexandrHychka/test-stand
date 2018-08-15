package com.gmail.maksimus40a.test.stand;

import com.gmail.maksimus40a.test.stand.features.base.exeptions.NoSuchSearchCriteriaException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Objects;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ResponseBody> handleNoSuchEntityError(NoSuchElementException e, HttpServletRequest req) {
        return new ResponseEntity<>(prepareResponseBody(e, req), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseBody> handleNegativeIdError(IllegalArgumentException e, HttpServletRequest req) {
        return new ResponseEntity<>(prepareResponseBody(e, req), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchSearchCriteriaException.class)
    public ResponseEntity<ResponseBody> handleNoSuchSearchCriteriaError(NoSuchSearchCriteriaException e, HttpServletRequest req) {
        return new ResponseEntity<>(prepareResponseBody(e, req), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ResponseBody> handleBadCredentials(BadCredentialsException e, HttpServletRequest req) {
        return new ResponseEntity<>(prepareResponseBody(e, req), HttpStatus.FORBIDDEN);
    }

    private ResponseBody prepareResponseBody(Exception e, HttpServletRequest req) {
        String qs = req.getQueryString();
        String queryString = (Objects.nonNull(qs)) ? "?" + qs : "";
        return ResponseBody.builder()
                .timestamp(LocalDateTime.now())
                .message(e.getMessage())
                .path(req.getServletPath() + queryString)
                .build();
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    private static class ResponseBody {
        private LocalDateTime timestamp;
        private String message;
        private String path;
    }
}