import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.shouldNotBe

class Day01Part1Test: FunSpec ({
    test("it reads the input") {
        val testInput = readTestInputForDay(1)

        testInput shouldNotBe null
    }

    test("it calculates the first distance") {
        val testInput = readTestInputForDay(1)

        val locations = Locations(testInput)

        locations.firstDistance() shouldBeEqual 2
    }

    test("it calculates the total distances") {
        val testInput = readTestInputForDay(1)

        val locations = Locations(testInput)

        locations.totalDistances() shouldBeEqual 11
    }
})

