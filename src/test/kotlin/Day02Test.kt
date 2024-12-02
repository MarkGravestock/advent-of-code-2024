import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.equals.shouldBeEqual

class Day02Part1Test: FunSpec ({

    test("it calculates the levels of the first test line") {
        val testInput = readTestInputForDay(2)

        val reports = Reports(testInput)

        reports.allLevels()[0] shouldBeEqual listOf(-1, -2, -2, -1)
    }

    test("it calculates the safety of the first test line") {
        val testInput = readTestInputForDay(2)

        val reports = Reports(testInput)

        reports.allSafety()[0] shouldBeEqual true
    }

    test("it calculates the safety of the test lines") {
        val testInput = readTestInputForDay(2)

        val reports = Reports(testInput)

        reports.allSafety() shouldBeEqual listOf(true, false, false, false, false, true)
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

class Day02Part2Test: FunSpec ({

    test("it calculates the lines of the first test line") {
        val testInput = readTestInputForDay(2)

        val reports = Reports(testInput)

        reports.linesWithProblemDampener()[0] shouldHaveSize 6
    }

    test("it calculates the total safety of all test lines considering problem dampening") {
        val testInput = readTestInputForDay(2)

        val reports = Reports(testInput)

        reports.totalSafetyConsideringProblemDampener() shouldBeEqual 4
    }

    test("it calculates the total safety of all lines considering problem dampening") {
        val testInput = readInputForDay(2)

        val reports = Reports(testInput)

        reports.totalSafetyConsideringProblemDampener() shouldBeEqual 404
    }
})

