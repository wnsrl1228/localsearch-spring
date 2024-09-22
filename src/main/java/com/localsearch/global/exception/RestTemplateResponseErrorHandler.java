package com.localsearch.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

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
        log.info("RestTemplate API 요청 에러 발생");
        log.info("Status code : " + response.getStatusCode());
        // 에러 응답 본문을 읽어 로그에 출력
        String responseBody = new BufferedReader(new InputStreamReader(response.getBody()))
                .lines()
                .collect(Collectors.joining("\n"));
        log.info("Response body : " + responseBody);
        throw new RuntimeException("서버 문제 발생");
    }
}
