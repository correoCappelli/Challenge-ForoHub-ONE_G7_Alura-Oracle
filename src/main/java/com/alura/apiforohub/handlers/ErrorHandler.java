package com.alura.apiforohub.handlers;

import com.alura.apiforohub.validaciones.ValidacionExcepcion;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarErrorNoEncontrado(EntityNotFoundException e){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarError400(MethodArgumentNotValidException e){
        var errores=e.getFieldErrors().stream().map(DatosErrorValidacion::new).toList();
        return ResponseEntity.badRequest().body(errores);
    }
    //para la excepcion de que se ingresa un valor que no es ADMIN o USER
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("error", "Invalid JSON input");
        errorDetails.put("message", "Unicos valores aceptados son : [USER, ADMIN]");
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidacionExcepcion.class)
    public ResponseEntity tratarErrorDeValidacion(ValidacionExcepcion e){
        var error=e.getMessage();
        //var errores=e.getFieldErrors().stream().map(DatosErrorValidacion::new).toList();
        return ResponseEntity.badRequest().body(error);
    }


    private record DatosErrorValidacion(String campo,String error){
        private DatosErrorValidacion(FieldError error) {
            this(
                    error.getField(),
                    error.getDefaultMessage()
                    );
        }
    }
}
