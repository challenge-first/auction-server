package com.example.auctionserver.global.exception;

public class RetryException extends RuntimeException{
    public RetryException(String message, Throwable cause) {
        super(message, cause);
    }
}
