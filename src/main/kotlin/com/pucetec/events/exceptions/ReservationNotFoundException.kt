package com.pucetec.events.exceptions

class ReservationNotFoundException (message: String = "La reservacion no fue encontrada.") : RuntimeException(message)