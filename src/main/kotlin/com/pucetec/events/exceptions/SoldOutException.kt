package com.pucetec.events.exceptions

class SoldOutException (message: String = "Las entradas del evento se encuentras agotadas.") : RuntimeException(message)