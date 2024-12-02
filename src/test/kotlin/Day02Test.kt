import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual

class Day02Part1Test: FunSpec ({

    test("it calculates the levels of the first test line") {
        val testInput = readTestInputForDay(2)

        val reports = Reports(testInput)

        reports.levels(0) shouldBeEqual listOf(-1, -2, -2, -1)
    }

    test("it calculates the safety of the first test line") {
        val testInput = readTestInputForDay(2)

        val reports = Reports(testInput)

        reports.isSafe(0) shouldBeEqual true
    }

    test("it calculates the safety of the test lines") {
        val testInput = readTestInputForDay(2)

        val reports = Reports(testInput)

        reports.safety() shouldBeEqual listOf(true, false, false, false, false, true)
    }

    test("it calculates the total safety of the test lines") {
        val testInput = readTestInputForDay(2)

        val reports = Reports(testInput)

        reports.totalSafety() shouldBeEqual 2
    }

    test("it calculates the total safety of the lines") {
        val testInput = readInputForDay(2)

        val reports = Reports(testInput)

        reports.totalSafety() shouldBeEqual 341
    }
})


