package com.rawat.electrolok.store.exceptions;

import lombok.Builder;


@Builder
public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(){
        // Calling default constructors of RuntimeException and its parent classes.
        super("Resource not found");
    }
    // Parameterized for Custom Message
    public ResourceNotFoundException(String message){
        super(message);
    }
}

