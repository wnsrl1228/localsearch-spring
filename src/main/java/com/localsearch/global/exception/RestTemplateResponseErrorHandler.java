package com.localsearch.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

@Slf4j
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode() == HttpStatus.BAD_REQUEST
                || response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        log.info("restTemplate api 요청 에러 발생");
        log.info("state code : " + response.getStatusCode());
        log.info("state msg : " + response.getStatusText());
        log.info("state msg : " + response);
        throw new RuntimeException("서버 문제 발생");
    }
}
