import kotlin.math.abs

class Reports(reports: List<String>) {

    private val reportLines = reports.map { it.split(" ").filter { it.isNotBlank() }.map { it.toInt() } }

    fun levels(): List<List<Int>> {
        return reportLines.map { it.mapIndexed { index, _ ->
            it[index] - it.getOrElse(index - 1) { 0 }
        }.drop(1) }
    }

    fun levels(lineNumber: Int): List<Int> {
        return levels()[lineNumber]
    }

    fun safety(): List<Boolean> {
        return levels().map { isSafe(it) }
    }

    fun isSafe(levels: List<Int>): Boolean {
        val allIncreasing = levels.all { it > 0 }
        val allDecreasing =  levels.all { it < 0 }
        val maxLevel = levels.map { abs(it) }.max()
        return (allIncreasing || allDecreasing) && (maxLevel in 1..3)
    }

    fun isSafe(lineNumber: Int): Boolean {
        return isSafe(levels(lineNumber))
    }

    fun totalSafety(): Int {
        return safety().count { it }
    }
}
