package co.istad.Banking.exception;

import co.istad.Banking.base.BaseError;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ApiException {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseError<?> handleValidationException(MethodArgumentNotValidException e){

        List<Map<String,String>> errors=new ArrayList<>();

        for (FieldError error : e.getFieldErrors()){
            Map<String,String> errorDetails = new HashMap<>();
            errorDetails.put("name",error.getField());
            errorDetails.put("message",error.getDefaultMessage());
            errors.add(errorDetails);
        }
        return BaseError.builder()
                .status(false)
                .code(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .message("Validation is Error Please check detail")
                .error(errors)
                .build();

    }

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
@ExceptionHandler(ResponseStatusException.class)
    public BaseError<?> handleServiceException(ResponseStatusException e){
        return BaseError.builder()
                .status(false)
                .code(e.getStatusCode().value())
                .timestamp(LocalDateTime.now())
                .message("Something Went Wrong")
                .error(e.getReason())
                .build();
    }



    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public BaseError<?> handleImageSize(MaxUploadSizeExceededException e){
        return BaseError.builder()
                .status(false)
                .code(HttpStatus.PAYLOAD_TOO_LARGE.value())
                .timestamp(LocalDateTime.now())
                .message("Maximum upload size exceeded : 1000KB")
                .error(e.getMessage())
                .build();
    }
}



