package com.control.visitas.exceptions;

import com.control.visitas.dtos.ErrorResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler{

    @ExceptionHandler(ResourseNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourseNotFoundException(
            ResourseNotFoundException ex,
            HttpServletRequest request
    ){
        log.warn("Recurso no encontrado:{}", ex.getMessage());

        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .errorCode("RESOURCE_NOT_FOUND")
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseDTO);
    }

    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<ErrorResponseDTO> handlerInvalidParameterException(
            InvalidParameterException ex,
            HttpServletRequest request
    ){
        String messaje = String.format(
                "El parámetro '%s' no es correcto",
                ex.getParameter()
        );

        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .errorCode("INVALID_PARAMETER_TYPE")
                .message(messaje)
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDTO);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ){
        Map<String, String> validationErrors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
            validationErrors.put(error.getField(), error.getDefaultMessage())
        );

        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .errorCode("VALIDATION_ERROR")
                .message("Errores de validación en los datos enviados")
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .validationErrors(validationErrors)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDTO);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleDataIntegrityViolation(
            DataIntegrityViolationException ex,
            HttpServletRequest request) {

        String rootMsg = ex.getMostSpecificCause().getMessage();

        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .errorCode("INTEGRITY_VIOLATION")
                .message(rootMsg)
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponseDTO);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handlerEntityNotExist (
            EntityNotFoundException ex,
            HttpServletRequest request
    ){
        String rootMsg = ex.getMessage();

        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .errorCode("ENTITY_NOT_EXIST")
                .message(rootMsg)
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDTO);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDTO> handlerMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException ex,
            HttpServletRequest request
    ){
        String rootMsg = ex.getMostSpecificCause().getMessage();

        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .errorCode("FAILED_TO_CONVERT_PARAMETER")
                .message(rootMsg + ", require number")
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDTO);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handlerNoResourceFoundException(
            NoResourceFoundException ex,
            HttpServletRequest request
    ){
        String rootMsg = ex.getMessage();

        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .errorCode("URL_NOT_FOUND")
                .message(rootMsg)
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseDTO);
    }

    // ─────────────────────────────────────────────────────────────────
    // Acceso denegado (credenciales incorrectas)
    // ─────────────────────────────────────────────────────────────────
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponseDTO> handleBadCredentials(
            BadCredentialsException ex,
            HttpServletRequest request
    ){
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .errorCode(HttpStatus.UNAUTHORIZED.toString())
                .message("Credenciales incorrectas")
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponseDTO);
    }

    // ─────────────────────────────────────────────────────────────────
    // Acceso denegado (usuario autenticado pero sin rol suficiente)
    // ─────────────────────────────────────────────────────────────────
    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ErrorResponseDTO> handleAccessDenied(
            AuthorizationDeniedException ex, HttpServletRequest request
    ) {
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .errorCode(HttpStatus.FORBIDDEN.toString())
                .message("No tienes permisos para acceder a este recurso")
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponseDTO);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> emailAlreadyExistsException(
            EmailAlreadyExistsException ex, HttpServletRequest request
    ) {
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .errorCode(HttpStatus.CONFLICT.toString())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponseDTO);
    }




//    // ─────────────────────────────────────────────────────────────────
//    // 3. JWT expirado — lanzado por JJWT al parsear el token
//    // ─────────────────────────────────────────────────────────────────
//    @ExceptionHandler(ExpiredJwtException.class)
//    public ResponseEntity<ErrorResponse> handleExpiredJwt(
//            ExpiredJwtException ex, HttpServletRequest request
//    ) {
//        return buildResponse(
//                HttpStatus.UNAUTHORIZED,
//                "El access token expiró. Usa el refresh token.",
//                request
//        );
//    }
//

//
//    // ─────────────────────────────────────────────────────────────────
//    // 5. JWT con formato roto (no tiene 3 partes, no es Base64, etc.)
//    // ─────────────────────────────────────────────────────────────────
//    @ExceptionHandler(MalformedJwtException.class)
//    public ResponseEntity<ErrorResponse> handleMalformedJwt(
//            MalformedJwtException ex, HttpServletRequest request
//    ) {
//        return buildResponse(
//                HttpStatus.UNAUTHORIZED,
//                "Token con formato inválido.",
//                request
//        );
//    }

}
