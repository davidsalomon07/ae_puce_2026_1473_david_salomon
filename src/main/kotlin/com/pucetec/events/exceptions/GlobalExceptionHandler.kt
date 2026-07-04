package com.pucetec.events.exceptions

import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

/***
 *El RestControllerAdvice es el que vigila al sistema
 *Intercepta los errores de los controladores y genera respuestas usando 'Exception Response'
 */

@RestControllerAdvice
class GlobalExceptionHandler {
    private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)


    // --- ERRORES 400: Bad Request ---

    @ExceptionHandler(BlankFieldException::class)
    fun handleBlankFieldException(ex: BlankFieldException): ResponseEntity <ExceptionResponse> {
        logger.warn("Excepcion de validacion: BlankFieldException - Mensaje: ${ex.message}")
        return ResponseEntity
    }
}