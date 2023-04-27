package com.minair.common.exception;

import com.minair.common.response.ResponseStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;


@Getter
@RequiredArgsConstructor
public enum CustomExceptionStatus implements ResponseStatus {

    // common exception
    INVALID_PARAMETER(BAD_REQUEST, 2000, "잘못된 요청이 존재합니다."),
    INVALID_URL(NOT_FOUND, 3000, "잘못된 URL 요청입니다."),
    SERVER_ERROR(INTERNAL_SERVER_ERROR, 5000, "서버 내부 오류입니다."),

    // weather exception
    INVALID_WEATHER_CODE(INTERNAL_SERVER_ERROR, 2100, "잘못된 Weather code 값입니다."),

    // city exception
    NOT_EXIST_CITY(BAD_REQUEST, 2200, "존재하지 않는 City 입니다."),
    ;

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;
}
