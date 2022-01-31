package ru.yajaneya.Spring2Geekbrains.core.exceptions;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException (String message) {
        super(message);
    }
}
