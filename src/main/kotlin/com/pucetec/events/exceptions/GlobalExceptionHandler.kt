package com.pucetec.events.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

data class ExceptionResponse(
    val message: String,
    val source: String,
)

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(BlankFieldException::class)
    fun handleBlankFieldException(e: Exception): ResponseEntity<ExceptionResponse> {
        val response = ExceptionResponse(e.message ?: "Campo en blanco", "Validation")
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response)
    }

    @ExceptionHandler(InvalidCapacityException::class)
    fun handleInvalidCapacityException(e: Exception): ResponseEntity<ExceptionResponse> {
        val response = ExceptionResponse(e.message ?: "Capacidad invalida", "InvalidCapacity")
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response)
    }

    @ExceptionHandler(AttendeeNotFoundException::class)
    fun handleAttendeeNotFoundException(e: Exception): ResponseEntity<ExceptionResponse> {
        val response = ExceptionResponse(e.message ?: "Asistente no encontrado", "Validation")
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response)
    }

    @ExceptionHandler(EventNotFoundException::class)
    fun handleEventNotFoundException(e: Exception): ResponseEntity<ExceptionResponse> {
        val response = ExceptionResponse(e.message ?: "Evento no encontrado", "Validation")
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response)
    }

    @ExceptionHandler(ReservationNotFoundException::class)
    fun handleReservationNotFoundException(e: Exception): ResponseEntity<ExceptionResponse> {
        val response = ExceptionResponse(e.message ?: "Reservacion no encontrada", "Validation")
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response)
    }

    @ExceptionHandler(SoldOutException::class)
    fun handleSoldOutException(e: Exception): ResponseEntity<ExceptionResponse> {
        val response = ExceptionResponse(e.message ?: "Se agotaron las entradas", "Validation")
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response)
    }

    @ExceptionHandler(ReservationLimitExceededException::class)
    fun handleReservationLimitExceededException(e: Exception): ResponseEntity<ExceptionResponse> {
        val response = ExceptionResponse(e.message ?: "Se alcanzo el limite de reservas", "Validation")
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response)
    }

    @ExceptionHandler(ReservationAlreadyCancelledException::class)
    fun handleReservationAlreadyCancelledException(e: Exception): ResponseEntity<ExceptionResponse> {
        val response = ExceptionResponse(e.message ?: "Se cancelo la reservacion", "Validation")
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response)
    }
}