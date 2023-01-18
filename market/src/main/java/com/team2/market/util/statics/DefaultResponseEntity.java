package com.team2.market.util.statics;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.team2.market.dto.DefaultResponseDto;

@Component
public class DefaultResponseEntity {
    
    /**
     * 반환 값으로 Header의 key 값을 필요로 하지 않을 경우 사용하는 메소드입니다.
     * @return 제너릭 타입의 변수에 맞는 반환값으로 사용합니다.
     * @param data : 제너릭 타입의 RespondeDto를 변수로 사용합니다. Null 값을 포함할 수 있습니다.
     * @param msg  : String 타입의 응답 메시지를 변수로 사용합니다.
     * @param status : HttpStatus의 Enum 타입을 변수로 사용합니다.
     * @exception 
     */
    public <T> ResponseEntity<DefaultResponseDto<T>> setResponseEntity(T data, String msg, HttpStatus status) {
        DefaultResponseDto<T> defaultResponse = new DefaultResponseDto<>(status.value(), msg, data);
        ResponseEntity<DefaultResponseDto<T>> ret = new ResponseEntity<>(defaultResponse, status);
        return ret;
    }
    
    /**
     * 반환 값으로 Header의 key에 AUTHORIZATION의 Bearer Key 값이 필요한 경우 사용하는 메소드입니다.
     * @return 제너릭 타입의 변수에 맞는 반환값으로 사용합니다.
     * @param data : 제너릭 타입의 RespondeDto를 변수로 사용합니다. Null 값을 포함할 수 있습니다.
     * @param msg  : String 타입의 응답 메시지를 변수로 사용합니다.
     * @param token : STRING 타입의 JWT 토큰을 매개변수로 사용합니다.
     * @param status : HttpStatus의 Enum 타입을 변수로 사용합니다.
     * @exception 
     */
    public <T> ResponseEntity<DefaultResponseDto<T>> setResponseEntity(T data, String msg, String token, HttpStatus status) {
        DefaultResponseDto<T> defaultResponse = new DefaultResponseDto<>(status.value(), msg, data);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, token);
        ResponseEntity<DefaultResponseDto<T>> ret = new ResponseEntity<>(defaultResponse, headers, status);
        return ret;
    }

}
