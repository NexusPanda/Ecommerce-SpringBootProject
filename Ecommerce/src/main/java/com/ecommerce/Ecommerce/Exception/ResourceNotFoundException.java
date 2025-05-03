package com.ecommerce.Ecommerce.Exception;

public class ResourceNotFoundException extends RuntimeException {
    String fieldName;
    String resourceName;
    String field;
    Long fieldId;

    public ResourceNotFoundException() {
    }

    public ResourceNotFoundException(String fieldName, String resourceName, String field, Long fieldId) {
        this.fieldName = fieldName;
        this.resourceName = resourceName;
        this.field = field;
        this.fieldId = fieldId;
    }

    public ResourceNotFoundException(String fieldName, String resourceName, String field) {
        super(String.format("%s not found with %s: %s",fieldName, resourceName, field));
        this.fieldName = fieldName;
        this.resourceName = resourceName;
        this.field = field;
    }

    public ResourceNotFoundException(String resourceName, String field, Long fieldId) {
        super(String.format("%s not found with %s: %d",resourceName, field, fieldId));
        this.resourceName = resourceName;
        this.field = field;
        this.fieldId = fieldId;
    }
}
