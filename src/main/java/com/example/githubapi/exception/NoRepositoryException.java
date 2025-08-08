package com.example.githubapi.exception;

public class NoRepositoryException extends RuntimeException {
    public NoRepositoryException(String message) {
        super(message);
    }
}
