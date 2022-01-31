package ru.yajaneya.Spring2Geekbrains.api.exeptions;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException (String message) {
        super(message);
    }
}
