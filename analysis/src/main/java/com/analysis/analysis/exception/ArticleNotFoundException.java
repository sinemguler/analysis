package com.analysis.analysis.exception;

public class ArticleNotFoundException extends RuntimeException{

    public ArticleNotFoundException(String message) {
        super(message);
    }
}
