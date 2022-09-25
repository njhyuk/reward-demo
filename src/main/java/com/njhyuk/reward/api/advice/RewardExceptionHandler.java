package com.njhyuk.reward.api.advice;

import com.njhyuk.reward.common.exception.BusinessException;
import com.njhyuk.reward.common.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RewardExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BusinessException.class)
    protected ErrorResponse businessException(Throwable e) {
        return new ErrorResponse(e.getMessage());
    }
}

