package com.ecommerce.project.Exceptions;

import com.ecommerce.project.payLoad.APIresponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice

public class MyGlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity<Map<String, String>> myMethodArgumentNotValidException(MethodArgumentNotValidException e){
    Map<String, String> response = new HashMap<>();

    e.getBindingResult().getAllErrors().forEach(err -> {
        String FieldName = ((FieldError)err).getField();
        String Message = err.getDefaultMessage();

         response.put(FieldName, Message);
    });
return new ResponseEntity<Map<String, String>>(response, HttpStatus.BAD_REQUEST);
}

@ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIresponse> myResourceNotFoundException(ResourceNotFoundException e){
 String Message = e.getMessage();
 APIresponse apiResponse = new APIresponse(Message, false);



 return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);



}
@ExceptionHandler(APIException.class)
public ResponseEntity<APIresponse> myAPIException(APIException e){
 String message = e.getMessage();
 APIresponse apiResponse = new APIresponse(message, false);

        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
}
}
