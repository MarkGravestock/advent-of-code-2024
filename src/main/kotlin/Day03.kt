class Multiplicator(val testInput: List<String>) {

    val mulInstruction = "mul\\(\\d{1,3},\\d{1,3}\\)".toRegex()

    fun validInstructions(): List<String> {
        return testInput.flatMap { mulInstruction.findAll(it) }.map { it.value }.toList()
    }

    fun multiplicationArguments(): List<Pair<Int, Int>> {
        return validInstructions().map { it.removePrefix("mul(").removeSuffix(")").split(",") }
            .map { Pair(it[0].toInt(), it[1].toInt()) }
    }

    fun totalValue(): Int {
        return multiplicationArguments().sumOf { it.first * it.second }
    }
}


class AdvancedMultiplicator(val testInput: List<String>) {

    open class Instruction
    class Do : Instruction()
    class Dont : Instruction()

    data class Multiply(val arg1: Int, val arg2: Int) : Instruction() {
        fun value(): Int { return arg1 * arg2 }
    }

    val allInstructions = "(mul\\(\\d{1,3},\\d{1,3}\\))|(do\\(\\))|(don't\\(\\))".toRegex()

    fun extractInstructions(): List<String> {
        return testInput.flatMap { allInstructions.findAll(it) }.map { it.value }.toList()
    }

    fun parseInstructions(): List<Instruction> {
        return extractInstructions().map {
            when (it.substring(0..2)) {
                "do(" -> Do()
                "don" -> Dont()
                "mul" -> createMultiplyInstruction(it)
                else -> throw IllegalArgumentException("Invalid multiplication input: ${it}")
            }
        }
    }

    private fun createMultiplyInstruction(instruction: String): Multiply {
        val args = instruction.removePrefix("mul(").removeSuffix(")").split(",")
        return Multiply(args[0].toInt(), args[1].toInt())
    }

    fun totalValue(): Int {
        var total = 0
        var enabled = true

        for (instruction in parseInstructions()) {
            when (instruction) {
                is Do -> { enabled = true }
                is Dont -> { enabled = false }
                is Multiply -> { if (enabled) total += instruction.value() }
            }
        }

        return total
    }
}
