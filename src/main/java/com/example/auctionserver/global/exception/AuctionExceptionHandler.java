package com.example.auctionserver.global.exception;

import com.example.auctionserver.global.response.ResponseMessageDto;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
public class AuctionExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseMessageDto> handleIllegalArgsException(IllegalArgumentException e) {
        ResponseMessageDto response = new ResponseMessageDto(e.getMessage(), BAD_REQUEST.value(), BAD_REQUEST.toString());
        return ResponseEntity.status(BAD_REQUEST).body(response);
    }


    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ResponseMessageDto> handleIllegalStateException(IllegalStateException e) {
        ResponseMessageDto response = new ResponseMessageDto(e.getMessage(), BAD_REQUEST.value(), BAD_REQUEST.toString());
        return ResponseEntity.status(BAD_REQUEST).body(response);
    }

    @ExceptionHandler(CallNotPermittedException.class)
    public ResponseEntity<ResponseMessageDto> handleCallNotPermittedException(CallNotPermittedException e) {
        ResponseMessageDto response = new ResponseMessageDto(e.getMessage(), INTERNAL_SERVER_ERROR.value(), INTERNAL_SERVER_ERROR.toString());
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity handleRuntimeException(RuntimeException e) {
        return ResponseEntity.status(BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception e) {
        return ResponseEntity.status(BAD_REQUEST).body(e.getMessage());
    }
}
