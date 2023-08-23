package com.example.auctionserver.global.exception;

import com.example.auctionserver.global.response.ResponseMessageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

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

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity handleRuntimeException(RuntimeException e) {
        return ResponseEntity.status(BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception e) {
        return ResponseEntity.status(BAD_REQUEST).body(e.getMessage());
    }
}
