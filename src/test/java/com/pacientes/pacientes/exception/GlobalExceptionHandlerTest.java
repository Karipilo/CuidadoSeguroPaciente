package com.pacientes.pacientes.exception;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

public class GlobalExceptionHandlerTest {

    @Test
    void deberiaManejarRuntimeException() {

        GlobalExceptionHandler handler =
                new GlobalExceptionHandler();

        RuntimeException ex =
                new RuntimeException("No autorizado");

        ResponseEntity<Map<String, String>> response =
                handler.manejarError(ex);

        assertEquals(
                HttpStatus.UNAUTHORIZED,
                response.getStatusCode()
        );

        assertEquals(
                "No autorizado",
                response.getBody().get("mensaje")
        );
    }

    /*
     * Verifica que se manejen correctamente los errores
     * de validación de campos (@Valid) del modelo Paciente.
     */
    @Test
    void deberiaManejarErroresDeValidacion() {

        GlobalExceptionHandler handler =
                new GlobalExceptionHandler();

        FieldError errorNombre =
                new FieldError("paciente", "nombre", "El nombre es obligatorio");

        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getFieldErrors())
                .thenReturn(List.of(errorNombre));

        MethodArgumentNotValidException ex =
                mock(MethodArgumentNotValidException.class);
        when(ex.getBindingResult()).thenReturn(bindingResult);

        ResponseEntity<Map<String, String>> response =
                handler.manejarValidaciones(ex);

        assertEquals(
                HttpStatus.BAD_REQUEST,
                response.getStatusCode()
        );

        assertEquals(
                "El nombre es obligatorio",
                response.getBody().get("nombre")
        );
    }
}