package com.tripod.homeloansystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -1427778975201009476L;
    
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
