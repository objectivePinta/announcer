package net.uranus.astra.rest

import net.uranus.astra.rest.requests.OnePieceOfFood
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController("foodController")
class FoodsController {

    @GetMapping(path = arrayOf("/foods"), produces = arrayOf("application/json;charset=UTF-8"))
    fun getFood(): List<OnePieceOfFood> {
        val foods = ArrayList<OnePieceOfFood>()
        (1..10).mapTo(foods) { OnePieceOfFood("sarmale", 34.3, LocalDate.of(2018, it, it)) }
        return foods
    }
}