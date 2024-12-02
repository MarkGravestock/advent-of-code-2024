import kotlin.math.abs

class Reports(reports: List<String>) {

    private val reportLines = reports.map { it.split(" ").filter { it.isNotBlank() }.map { it.toInt() } }

    fun allLevels(): List<List<Int>> {
        return reportLines.map { it.levels() }
    }

    private fun List<Int>.levels(): List<Int> {
        return List(this.size) { index ->
            this[index] - this.getOrElse(index - 1) { 0 }
        }.drop(1)
    }

    fun allSafety(): List<Boolean> {
        return allLevels().map { isSafe(it) }
    }

    fun isSafe(levels: List<Int>): Boolean {
        val allIncreasing = levels.all { it > 0 }
        val allDecreasing =  levels.all { it < 0 }
        val maxLevel = levels.maxOfOrNull { abs(it) }
        return (allIncreasing || allDecreasing) && (maxLevel in 1..3)
    }

    fun totalSafety(): Int {
        return allSafety().count { it }
    }

    fun totalSafetyConsideringProblemDampener(): Int {
        return linesWithProblemDampener().map { it.any { isSafe(it.levels()) } }.count { it }
    }

    fun linesWithProblemDampener(): List<List<List<Int>>> {
        return reportLines.map { line ->
            val mappedLines = line.mapIndexed { index, _ ->
                val candidateDampener = line.toMutableList()
                candidateDampener.removeAt(index)
                candidateDampener.toList()
            }

            val all = mappedLines.toMutableList()
            all.add(line)
            all.toList()
        }
    }
}
