package com.team2.market.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class DefualtResponseDto<T> {
    private int statusCode;
    private String responseMessage;
    private T data;

    public DefualtResponseDto(HttpStatus status, String responseMessage) {
        this.statusCode = status.value();
        this.responseMessage = responseMessage;
        this.data = null;
    }

    public static<T> DefualtResponseDto<T> res(final HttpStatus statusCode, final String responseMessage){
        return res(statusCode, null);
    }

    public static<T> DefualtResponseDto<T> res(final HttpStatus statusCode, final String responseMessage, final T t){
        return DefualtResponseDto.<T>builder()
                .data(t)
                .statusCode(statusCode.value())
                .responseMessage(responseMessage)
                .build();
    }
}
