package com.pouya.library.DTO;

public class ValidationErrorDTO {
    private final String field;
    private final String message;

    public ValidationErrorDTO(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}
