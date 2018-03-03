package net.uranus.astra.rest.requests

import java.time.LocalDate

data class OnePieceOfFood(val name: String, val quantity: Double, val expirationDate: LocalDate)