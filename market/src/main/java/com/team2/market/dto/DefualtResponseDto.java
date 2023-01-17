package com.team2.market.dto;

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

    public DefualtResponseDto(int statusCode, String responseMessage) {
        this.statusCode = statusCode;
        this.responseMessage = responseMessage;
        this.data = null;
    }

    public static<T> DefualtResponseDto<T> res(final int statusCode, final String responseMessage){
        return res(statusCode, responseMessage, null);
    }

    
    public static<T> DefualtResponseDto<T> res(final int statusCode, final String responseMessage, final T t){
        return DefualtResponseDto.<T>builder()
                .data(t)
                .statusCode(statusCode)
                .responseMessage(responseMessage)
                .build();
    }
}
