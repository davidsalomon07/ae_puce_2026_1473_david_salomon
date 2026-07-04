package com.pucetec.events.exceptions

/***
 * Se lanza si se intenta cancelar una reserva que ya fue cancelada.
 * Retorna HTTP 409
 */
class ReservationLimitExceededException (message: String = "No se pueden tener mas de 4 reservas.") : RuntimeException(message)