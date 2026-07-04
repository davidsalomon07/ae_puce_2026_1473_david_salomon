package com.pucetec.events.exceptions

class EventNotFoundException (message: String = "El evento no fue encontrado.") : RuntimeException(message)