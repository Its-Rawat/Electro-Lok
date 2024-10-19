package com.rawat.electrolok.store.exceptions;

import com.rawat.electrolok.store.dtos.ApiResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
    The main purpose of @RestControllerAdvice in Spring Boot is to centralize exception handling for RESTful web services, providing a clean and consistent way to manage and respond to errors across the entire application. By using it, you can define how various exceptions should be handled globally, instead of handling exceptions separately in each controller.
*/
@RestControllerAdvice
public class GlobalExceptionHandler {

    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Handle ResourceNotFound Exception
    /*
       --> The @ExceptionHandler annotation in Spring is used to handle specific exceptions in methods within a class. It enables you to define custom error-handling logic for exceptions thrown by request-handling methods (@RequestMapping, @GetMapping, etc.) in your controllers.
       --> @RestControllerAdvice does become a Spring bean, managed by the Spring container.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseMessage> resourceNotFoundException(ResourceNotFoundException exception){
        logger.info("Exception Handler Invoked...");
        ApiResponseMessage responseMessage = ApiResponseMessage.builder().message(exception.getMessage()).httpStatus(HttpStatus.NOT_FOUND).success(true).build();
        return new ResponseEntity<>(responseMessage, HttpStatus.NOT_FOUND);
    }

    // MethodArgumentNotValidException Method
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        Map<String,Object> response  = new HashMap<>();
        allErrors.stream().forEach(objectError -> {
            String defaultMessage = objectError.getDefaultMessage();
            String field = ((FieldError) objectError).getField();
            response.put(field, defaultMessage);
        });

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
