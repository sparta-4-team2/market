package com.team2.market.util.statics;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.team2.market.dto.DefaultResponseDto;

@Component
public class DefaultResponseEntity {
    
    public <T> ResponseEntity<DefaultResponseDto<T>> setResponseEntity(T data, String msg, HttpStatus status) {
        DefaultResponseDto<T> defaultResponse = new DefaultResponseDto<>(status.value(), msg, data);
        ResponseEntity<DefaultResponseDto<T>> ret = new ResponseEntity<>(defaultResponse, status);
        return ret;
    }
    
    public <T> ResponseEntity<DefaultResponseDto<T>> setResponseEntity(T data, String msg, String token, HttpStatus status) {
        DefaultResponseDto<T> defaultResponse = new DefaultResponseDto<>(status.value(), msg, data);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, token);
        ResponseEntity<DefaultResponseDto<T>> ret = new ResponseEntity<>(defaultResponse, headers, status);
        return ret;
    }

}
