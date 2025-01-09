package com.iot.domain.exceptions;

public class PlantNotFound extends RuntimeException {
    public PlantNotFound(String message) {
        super(message);
    }
}
