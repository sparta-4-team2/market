package com.team2.market.dto;

import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class DefaultResponseDto<T> {
    private int statusCode;
    private String responseMessage;
    private T data;

    public DefaultResponseDto(HttpStatus status, String responseMessage) {
        this.statusCode = status.value();
        this.responseMessage = responseMessage;
        this.data = null;
    }

    public static<T> DefaultResponseDto<T> res(final HttpStatus statusCode, final String responseMessage){
        return res(statusCode, responseMessage);
    }

    public static<T> DefaultResponseDto<T> res(final HttpStatus statusCode, final String responseMessage, final T t){
        return DefaultResponseDto.<T>builder()
                .data(t)
                .statusCode(statusCode.value())
                .responseMessage(responseMessage)
                .build();
    }
}
