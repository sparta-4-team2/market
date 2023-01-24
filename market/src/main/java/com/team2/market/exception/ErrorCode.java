package com.team2.market.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;



@Getter
@AllArgsConstructor
public enum ErrorCode {
    //400 잘못된 요청 (BAD_REQUEST)
    
    //409 중복된 데이터 존재, resource 의 현 상태와 충돌 (CONFLICT)
}
