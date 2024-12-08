import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlin.math.pow

class Equations(input: List<String>) {
    private val equations = input.map{ it.split(":") }
    .map { Equation(it[0].toLong(), it[1].split(" ").filter { it.isNotBlank() }.map { it.toLong() }) }

    fun equations(): List<Equation> {
        return equations
    }

    fun totalCalibrationResult(): Long {
        return equations().sumOf { it.valueIfGenerated() }
    }
}

data class Equation(val testValue: Long, val numbers: List<Long>) {
    fun evaluate(operators: String ): Boolean {
        var total = numbers[0]
        numbers.drop(1).forEachIndexed { i, value -> if (operators[i] == '+') total += value else total *= value }
        return total == testValue
    }

    fun operatorPermutations() : List<String> {
        val permutations = mutableListOf<String>()

        val numberOfPermutations = 2.0.pow(numbers.size - 1).toInt() - 1
        for (permutation in 0.. numberOfPermutations) {
            permutations.add(Integer.toBinaryString(permutation).padStart( numbers.size - 1, '0').replace("0", "+").replace("1", "*"))
        }

        return permutations
    }

    fun canBeGenerated(): Boolean {
        return operatorPermutations().any { evaluate(it) }
    }

    fun valueIfGenerated(): Long {
        return if (canBeGenerated()) testValue else 0L
    }

}

class Day07Part1Test : FunSpec({

    context("Part 1 Solution test data") {
        val testInput = readTestInputForDay(7)

        test("it can read the input") {
            val sut = Equations(testInput)
            sut.equations()[0] shouldBe Equation(testValue = 190, numbers = listOf(10, 19))
        }

        test("it can evaluate the equation") {
            Equation(testValue = 190, numbers = listOf(10, 19)).evaluate("+") shouldBe false
            Equation(testValue = 190, numbers = listOf(10, 19)).evaluate("*") shouldBe true
            Equation(testValue = 3267, numbers = listOf(81, 40, 27)).evaluate("**") shouldBe false
            Equation(testValue = 3267, numbers = listOf(81, 40, 27)).evaluate("+*") shouldBe true
            Equation(testValue = 3267, numbers = listOf(81, 40, 27)).evaluate("*+") shouldBe true
        }

        test("it can generate operator permutations") {
            Equation(testValue = 190, numbers = listOf(10, 19)).operatorPermutations() shouldBe listOf("+", "*")
            Equation(testValue = 3267, numbers = listOf(81, 40, 27)).operatorPermutations() shouldBe listOf("++", "+*", "*+", "**")
        }

        test("test value can be generated from values") {
            Equation(testValue = 190, numbers = listOf(10, 19)).canBeGenerated() shouldBe true
            Equation(testValue = 3267, numbers = listOf(81, 40, 27)).canBeGenerated() shouldBe true
            Equation(testValue = 161011, numbers = listOf(16, 10, 13)).canBeGenerated() shouldBe false
        }

        test("test total calibration result") {
            val sut = Equations(testInput)
            sut.totalCalibrationResult() shouldBe 3749
        }
    }

    context("Part 1 Solution real data") {
        val testInput = readInputForDay(7)

        test("total calibration result") {
            val sut = Equations(testInput)

            sut.totalCalibrationResult() shouldBe 2299996598890
        }
    }
})


