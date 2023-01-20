package com.team2.market.util.statics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


@Component
public class DefaultResponseEntity {
    
    /**
     * 반환 값으로 Header의 key 값을 필요로 하지 않을 경우 사용하는 메소드입니다.
     * @return 제너릭 타입의 변수에 맞는 반환값으로 사용합니다.
     * @param data : 제너릭 타입의 RespondeDto를 상속받는 Dto를 변수로 사용합니다. Null 값을 포함할 수 있습니다.
     * @param msg  : String 타입의 응답 메시지를 변수로 사용합니다.
     * @param status : HttpStatus의 Enum 타입을 변수로 사용합니다.
     * @exception 
     */
    public static <T> ResponseEntity<Map<String, Object>> setResponseEntity
                                (T data, String msg, HttpStatus status) {
        Map<String, Object> result = new HashMap<>();
        result.put("statusCode", status);
        result.put("responseMessage", msg);
        result.put("data", data);
        
        return ResponseEntity.ok().body(result);
    }
    
    /**
     * 반환 값으로 Header의 key 값을 필요로 하지 않을 경우 사용하는 메소드입니다.
     * @return 제너릭 타입의 변수에 맞는 반환값으로 사용합니다.
     * @param data : 제너릭 타입의 리스트 형태의 RespondeDto를 상속받는 Dto를 변수로 사용합니다. Null 값을 포함할 수 있습니다.
     * @param msg  : String 타입의 응답 메시지를 변수로 사용합니다.
     * @param status : HttpStatus의 Enum 타입을 변수로 사용합니다.
     * @exception 
     */
    public static <T> ResponseEntity<Map<String, Object>> setResponseEntity
                                (List<T> data, String msg, HttpStatus status) {
        Map<String, Object> result = new HashMap<>();
        result.put("statusCode", status);
        result.put("responseMessage", msg);
        result.put("data", data);

        return ResponseEntity.ok().body(result);
    }
    
    /**
     * 반환 값으로 Cookie가 필요한 경우 사용하는 메소드입니다.
     * @return 제너릭 타입의 변수에 맞는 반환값으로 사용합니다.
     * @param data : 제너릭 타입의 RespondeDto를 변수로 사용합니다. Null 값을 포함할 수 있습니다.
     * @param msg  : String 타입의 응답 메시지를 변수로 사용합니다.
     * @param cookie : 보내줄 Cookie 매개변수로 사용합니다.
     * @param status : HttpStatus의 Enum 타입을 변수로 사용합니다.
     * @exception 
     */
    public static <T> ResponseEntity<Map<String, Object>> setResponseEntity
                                (T data, String msg, String token, HttpStatus status) {
        Map<String, Object> result = new HashMap<>();
        result.put("statusCode", status);
        result.put("responseMessage", msg);
        result.put("data", data);
        
		// Cookie idCookie = new Cookie(requestDto.getUsername(), String.valueOf(token));
		// idCookie.setMaxAge(30 * 60 * 1000); //30분 유효
		// response.addCookie(idCookie);

        // ResponseCookie responseCookie = ResponseCookie.from("Authorization", token)
        //                                               .httpOnly(true)
        //                                               .secure(true)
        //                                               .path("/")
        //                                               .maxAge(30*60*1000)
        //                                               .domain("localhost:8080/")
        //                                               .build();

        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, token).body(result);
    }
}
