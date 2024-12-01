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

        val locations = Distances(testInput)

        locations.firstDistance() shouldBeEqual 2
    }

    test("it calculates the test total distances") {
        val testInput = readTestInputForDay(1)

        val locations = Distances(testInput)

        locations.totalDistances() shouldBeEqual 11
    }

    test("it calculates the total distances") {
        val testInput = readInputForDay(1)

        val locations = Distances(testInput)

        locations.totalDistances() shouldBeEqual 1258579
    }
})

class Day01Part2Test: FunSpec ({
    test("it calculates the first similarity") {
        val testInput = readTestInputForDay(1)

        val similarities = Similarities(testInput)

        similarities.nthSimilarity(0) shouldBeEqual 9
    }

    test("it calculates the test total similarity") {
        val testInput = readTestInputForDay(1)

        val similarities = Similarities(testInput)

        similarities.totalSimilarity() shouldBeEqual 31
    }

    test("it calculates the total similarity") {
        val testInput = readInputForDay(1)

        val similarities = Similarities(testInput)

        similarities.totalSimilarity() shouldBeEqual 23981443
    }
})


