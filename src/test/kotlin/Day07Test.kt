import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe

class Equations(input: List<String>, val includeConcatinate: Boolean = false) {
    private val equations = input.map{ it.split(":") }
    .map { Equation(it[0].toLong(), it[1].split(" ").filter { it.isNotBlank() }.map { it.toLong() }, includeConcatinate) }

    fun equations(): List<Equation> {
        return equations
    }

    fun totalCalibrationResult(): Long {
        return equations().sumOf { it.valueIfGenerated() }
    }
}

data class Equation(val testValue: Long, val numbers: List<Long>, val includeConcatinate: Boolean = false) {
    fun calculate(operators: String ): Long {
        val values = numbers.toMutableList()

        var total = numbers[0]
        values.drop(1).forEachIndexed { i, value -> if (operators[i] == '+') total += value else if (operators[i] == '*')  total *= value else total = (total.toString() + value.toString()).toLong()  }
        return total
    }
    fun evaluate(operators: String ): Boolean {

        return calculate(operators) == testValue
    }

    fun operatorPermutations() : List<String> {
        return generatePermutations(if (includeConcatinate) listOf('*', '+', '|') else listOf('*', '+'), numbers.size - 1)
    }

    fun generatePermutations(characters: List<Char>, length: Int): List<String> {
        if (length == 0) return listOf("")
        val permutations = mutableListOf<String>()

        for (character in characters) {
            val subPermutations = generatePermutations(characters, length - 1)
            for (subPermutation in subPermutations) {
                permutations.add(character + subPermutation)
            }
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
            Equation(testValue = 190, numbers = listOf(10, 19)).operatorPermutations() shouldContainExactlyInAnyOrder listOf("+", "*")
            Equation(testValue = 3267, numbers = listOf(81, 40, 27)).operatorPermutations() shouldContainExactlyInAnyOrder listOf("++", "+*", "*+", "**")
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

class Day07Part2Test : FunSpec({

    context("Part 1 Solution test data") {
        val testInput = readTestInputForDay(7)

        test("it can calculate the equation") {
            Equation(testValue = 7290, numbers = listOf(6, 8, 6, 15), includeConcatinate = true).calculate("*|*") shouldBe 7290
        }

        test("it can evaluate the equation") {
            Equation(testValue = 190, numbers = listOf(10, 19)).evaluate("+") shouldBe false
            Equation(testValue = 190, numbers = listOf(10, 19)).evaluate("*") shouldBe true
            Equation(testValue = 3267, numbers = listOf(81, 40, 27)).evaluate("**") shouldBe false
            Equation(testValue = 3267, numbers = listOf(81, 40, 27)).evaluate("+*") shouldBe true
            Equation(testValue = 3267, numbers = listOf(81, 40, 27)).evaluate("*+") shouldBe true
        }

        test("it can generate operator permutations") {
            Equation(testValue = 190, numbers = listOf(10, 19), includeConcatinate = true).operatorPermutations() shouldContainExactlyInAnyOrder listOf("+", "*", "|")
            Equation(testValue = 3267, numbers = listOf(81, 40, 27), includeConcatinate = true).operatorPermutations() shouldContainExactlyInAnyOrder listOf("++", "+*", "*+", "**", "+|", "*|", "|+", "|*", "||")
        }

        test("test value can be generated from values") {
            Equation(testValue = 3267, numbers = listOf(81, 40, 27), includeConcatinate = true).canBeGenerated() shouldBe true
            Equation(testValue = 161011, numbers = listOf(16, 10, 13), includeConcatinate = true).canBeGenerated() shouldBe false
            Equation(testValue = 156, numbers = listOf(15, 6), includeConcatinate = true).canBeGenerated() shouldBe true
            Equation(testValue = 7290, numbers = listOf(6, 8, 6, 15), includeConcatinate = true).canBeGenerated() shouldBe true
        }

        test("test total calibration result") {
            val sut = Equations(testInput, includeConcatinate = true)
            sut.totalCalibrationResult() shouldBe 11387
        }
    }

    context("Part 1 Solution real data") {
        val testInput = readInputForDay(7)

        test("total calibration result") {
            val sut = Equations(testInput, includeConcatinate = true)

            sut.totalCalibrationResult() shouldBe 362646859298554L
        }
    }
})

