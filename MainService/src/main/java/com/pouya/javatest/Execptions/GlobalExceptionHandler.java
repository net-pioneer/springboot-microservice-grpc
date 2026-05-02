package com.pouya.javatest.Execptions;

import com.pouya.library.Execptions.AuthException;
import com.pouya.library.PacketTemplate.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.pouya.library.Translations.MessageHelper._t;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<Object>> handleBadCredentials() {
        return ApiResponse.error(_t("auth.invalid_credentionals"), HttpStatus.UNAUTHORIZED);
    }
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ApiResponse<Object>> handleGeneral(Exception e) {
//        return ApiResponse.error("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}