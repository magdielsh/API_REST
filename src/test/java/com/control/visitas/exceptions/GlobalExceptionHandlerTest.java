package com.control.visitas.exceptions;

import com.control.visitas.dtos.ErrorResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import org.springframework.http.HttpMethod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;
    private HttpServletRequest httpServletRequest;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
        httpServletRequest = mock(HttpServletRequest.class);
        when(httpServletRequest.getRequestURI()).thenReturn("/api/test");
    }

    @Test
    void handleResourseNotFoundException_ShouldReturn404() {
        // Given
        ResourseNotFoundException ex = new ResourseNotFoundException("Resource not found");

        // When
        ResponseEntity<ErrorResponseDTO> result = globalExceptionHandler.handleResourseNotFoundException(ex, httpServletRequest);

        // Then
        assertNotNull(result);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("RESOURCE_NOT_FOUND", result.getBody().getErrorCode());
        assertEquals("Resource not found", result.getBody().getMessage());
    }

    @Test
    void handlerInvalidParameterException_ShouldReturn400() {
        // Given
        InvalidParameterException ex = new InvalidParameterException("INVALID_CODE");

        // When
        ResponseEntity<ErrorResponseDTO> result = globalExceptionHandler.handlerInvalidParameterException(ex, httpServletRequest);

        // Then
        assertNotNull(result);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("INVALID_PARAMETER_TYPE", result.getBody().getErrorCode());
    }

    @Test
    void handleMethodArgumentNotValidException_ShouldReturn400() {
        // Given
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        org.springframework.validation.BindingResult bindingResult = mock(org.springframework.validation.BindingResult.class);
        
        Map<String, String> errors = new HashMap<>();
        errors.put("field1", "error message");
        
        when(ex.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(
            List.of(new org.springframework.validation.FieldError("object", "field1", "error message"))
        );

        // When
        ResponseEntity<ErrorResponseDTO> result = globalExceptionHandler.handleMethodArgumentNotValidException(ex, httpServletRequest);

        // Then
        assertNotNull(result);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("VALIDATION_ERROR", result.getBody().getErrorCode());
    }

    @Test
    void handleDataIntegrityViolation_ShouldReturn409() {
        // Given
        DataIntegrityViolationException ex = mock(DataIntegrityViolationException.class);
        java.sql.SQLException cause = mock(java.sql.SQLException.class);
        
        when(ex.getMostSpecificCause()).thenReturn(cause);
        when(cause.getMessage()).thenReturn("Duplicate key value violates unique constraint");

        // When
        ResponseEntity<ErrorResponseDTO> result = globalExceptionHandler.handleDataIntegrityViolation(ex, httpServletRequest);

        // Then
        assertNotNull(result);
        assertEquals(HttpStatus.CONFLICT, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("INTEGRITY_VIOLATION", result.getBody().getErrorCode());
    }

    @Test
    void handlerEntityNotExist_ShouldReturn400() {
        // Given
        EntityNotFoundException ex = new EntityNotFoundException("Entity not found");

        // When
        ResponseEntity<ErrorResponseDTO> result = globalExceptionHandler.handlerEntityNotExist(ex, httpServletRequest);

        // Then
        assertNotNull(result);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("ENTITY_NOT_EXIST", result.getBody().getErrorCode());
    }

    @Test
    void handlerMethodArgumentTypeMismatchException_ShouldReturn400() {
        // Given
        MethodArgumentTypeMismatchException ex = mock(MethodArgumentTypeMismatchException.class);
        java.sql.SQLException cause = mock(java.sql.SQLException.class);
        
        when(ex.getMostSpecificCause()).thenReturn(cause);
        when(cause.getMessage()).thenReturn("Invalid value");

        // When
        ResponseEntity<ErrorResponseDTO> result = globalExceptionHandler.handlerMethodArgumentTypeMismatchException(ex, httpServletRequest);

        // Then
        assertNotNull(result);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("FAILED_TO_CONVERT_PARAMETER", result.getBody().getErrorCode());
    }

    @Test
    void handlerNoResourceFoundException_ShouldReturn404() {
        // Given
        NoResourceFoundException ex = new NoResourceFoundException(HttpMethod.GET, "/test");

        // When
        ResponseEntity<ErrorResponseDTO> result = globalExceptionHandler.handlerNoResourceFoundException(ex, httpServletRequest);

        // Then
        assertNotNull(result);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("URL_NOT_FOUND", result.getBody().getErrorCode());
    }
}