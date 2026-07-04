package com.pucetec.events.exceptions

class ReservationAlreadyCancelledException (message: String = "La reserva se ha cancelado.") : RuntimeException(message)