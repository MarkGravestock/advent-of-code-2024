import AdvancedMultiplicator.*
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.equals.shouldBeEqual

class Day03Part1Test: FunSpec ({

    context("Part 1 Solution test data") {
        val testInput = listOf("xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))")

        val sut = Multiplicator(testInput)

        test("it can match multiplication instructions") {
            sut.validInstructions() shouldHaveSize 4
        }

        test("it can match multiplication instructions") {
            sut.multiplicationArguments() shouldBeEqual listOf(Pair(2,4), Pair(5,5), Pair(11,8), Pair(8,5))
        }

        test("it should multiply and total") {
            sut.totalValue() shouldBeEqual 161
        }
    }

    context("Part 1 Solution test data doubled") {
        val testInput = readTestInputForDay(3)

        val sut = Multiplicator(testInput)

        test("it can match multiplication instructions") {
            sut.validInstructions() shouldHaveSize 8
        }

        test("it should multiply and total") {
            sut.totalValue() shouldBeEqual 322
        }
    }

    test("it calculates the total") {
        val testInput = readInputForDay(3)

        val sut = Multiplicator(testInput)

        sut.totalValue() shouldBeEqual 174336360
    }
})


class Day03Part2Test: FunSpec ({

    context("Part 2 Solution test data") {
        val testInput =
            listOf("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))")

        val sut = AdvancedMultiplicator(testInput)

        test("it can match all instructions") {
            sut.extractInstructions() shouldHaveSize 6
        }

        xtest("it can match multiplication instructions") {
            sut.parseInstructions() shouldBeEqual listOf(
                Multiply(2,4), Dont(), Multiply(5,5), Multiply(11,8), Do(), Multiply(8,5))
        }

        test("it should multiply and total") {
            sut.totalValue() shouldBeEqual 48
        }
    }

    test("it calculates the total") {
        val testInput = readInputForDay(3)

        val sut = AdvancedMultiplicator(testInput)

        sut.totalValue() shouldBeEqual 88802350
    }
})
