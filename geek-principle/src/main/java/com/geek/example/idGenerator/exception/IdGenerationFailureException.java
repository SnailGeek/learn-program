package com.geek.example.idGenerator.exception;

/**
 * @program: IdGenerationFailureException
 * @description:
 * @author: wangf-q
 * @date: 2022-12-28 14:58
 **/
public class IdGenerationFailureException extends RuntimeException {


    public IdGenerationFailureException(String message) {
        super(message);
    }
}
