package com.zerobase.cms.order.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    NOT_FOUNT_PRODUCT(HttpStatus.BAD_REQUEST, "상품을 찾을 수 업습니다."),
    SAME_ITEM_NAME(HttpStatus.BAD_REQUEST, "아이템명 중복입니다.");

    private final HttpStatus httpStatus;
    private final String detail;
}
