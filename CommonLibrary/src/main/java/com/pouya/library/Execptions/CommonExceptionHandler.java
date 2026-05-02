package com.pouya.library.Execptions;

import com.pouya.library.DTO.ValidationErrorDTO;
import com.pouya.library.PacketTemplate.ApiResponse;
import io.grpc.StatusRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static com.pouya.library.Translations.MessageHelper._t;

@RestControllerAdvice
public class CommonExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidation(MethodArgumentNotValidException ex) {
        List<ValidationErrorDTO> errors = ex.getBindingResult().getFieldErrors().stream().map(error -> new ValidationErrorDTO(error.getField(), _t(error.getDefaultMessage()))).toList();
        return ApiResponse.error(errors,"err", HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ApiResponse<Object>> handleAuth(AuthException ex) {
        return ApiResponse.error(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(StatusRuntimeException.class)
    public ResponseEntity<ApiResponse<Object>> handleWrongToken(StatusRuntimeException ex) {
        return ApiResponse.error(ex.getStatus().getDescription(), HttpStatus.UNAUTHORIZED);
    }
}