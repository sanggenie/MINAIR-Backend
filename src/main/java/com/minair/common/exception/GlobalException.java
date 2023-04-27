package com.minair.common.exception;

import com.minair.common.response.ResponseStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GlobalException extends RuntimeException{

    private final ResponseStatus status;
}
