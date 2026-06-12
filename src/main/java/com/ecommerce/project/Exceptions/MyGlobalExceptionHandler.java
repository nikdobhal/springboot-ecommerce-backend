package com.ecommerce.project.Exceptions;

import com.ecommerce.project.payLoad.APIresponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class MyGlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> myMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> response = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(err -> {
            String fieldName = ((FieldError) err).getField();
            String message = err.getDefaultMessage();
            response.put(fieldName, message);
        });
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIresponse> myResourceNotFoundException(ResourceNotFoundException e) {
        String message = e.getMessage();
        APIresponse apiResponse = new APIresponse(message, false);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    //  Catch HttpMessageNotReadableException → throw our custom InvalidRequestException
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<APIresponse> myHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        throw new InvalidRequestException("Invalid request body: " + e.getMostSpecificCause().getMessage());
    }

    //  Handle our custom InvalidRequestException
    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<APIresponse> myInvalidRequestException(InvalidRequestException e) {
        String message = e.getMessage();
        APIresponse apiResponse = new APIresponse(message, false);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<APIresponse> myAPIException(APIException e) {
        String message = e.getMessage();
        APIresponse apiResponse = new APIresponse(message, false);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }
}
