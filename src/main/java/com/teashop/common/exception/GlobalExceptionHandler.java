package com.teashop.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<com.teashop.dto.response.ApiResponse<Object>> handleBusinessException(BusinessException e) {
        return ResponseEntity.status(e.getCode())
                .body(com.teashop.dto.response.ApiResponse.error(e.getCode(), e.getMessage()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<com.teashop.dto.response.ApiResponse<Object>> handleResourceNotFoundException(ResourceNotFoundException e) {
        return ResponseEntity.status(404)
                .body(com.teashop.dto.response.ApiResponse.error(404, e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<com.teashop.dto.response.ApiResponse<Map<String, String>>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.status(400)
                .body(com.teashop.dto.response.ApiResponse.error(400, "参数验证失败: " + errors.toString()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<com.teashop.dto.response.ApiResponse<Object>> handleGenericException(Exception e) {
        return ResponseEntity.status(500)
                .body(com.teashop.dto.response.ApiResponse.error(500, "系统内部错误: " + e.getMessage()));
    }
}