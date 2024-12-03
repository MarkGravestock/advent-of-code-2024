class Multiplicator(val testInput: List<String>) {

    val mulInstruction = "mul\\(\\d{1,3},\\d{1,3}\\)".toRegex()

    fun validInstructions(): List<String> {
        return testInput.flatMap { mulInstruction.findAll(it) }.map { it.value }.toList()
    }

    fun multiplicationArguments(): List<Pair<Int, Int>> {
        return validInstructions().map { it.removePrefix("mul(").removeSuffix(")").split(",") }.map { Pair(it[0].toInt(), it[1].toInt()) }
    }

    fun totalValue(): Int {
        return multiplicationArguments().sumOf { it.first * it.second }
    }
}
