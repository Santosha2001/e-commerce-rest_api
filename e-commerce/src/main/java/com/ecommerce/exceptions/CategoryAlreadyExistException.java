package com.ecommerce.exceptions;

public class CategoryAlreadyExistException extends RuntimeException {
    public CategoryAlreadyExistException(String message) {
        super(message);
    }
}
