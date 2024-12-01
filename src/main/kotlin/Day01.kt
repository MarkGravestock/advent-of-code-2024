import kotlin.math.abs

class Distances(locations: List<String>) {
    private val firstList: List<Int> = locations.map { it.split("   ") }.map { it[0].toInt() }.sorted()
    private val secondList: List<Int> = locations.map { it.split("   ") }.map { it[1].toInt() }.sorted()

    fun firstDistance() : Int {
        return nthDistance(0)
    }

    fun nthDistance(n: Int) : Int {
        return abs(firstList[n] - secondList[n])
    }

    fun totalDistances(): Int {
        return firstList.foldIndexed(0) {index, total, _ -> total + nthDistance(index)}
    }

}

class Similarities(locations: List<String>) {
    private val firstList: List<Int> = locations.map { it.split("   ") }.map { it[0].toInt() }
    private val secondList: List<Int> = locations.map { it.split("   ") }.map { it[1].toInt() }

    fun nthSimilarity(n: Int) : Int {
        val firstListValue = firstList[n]
        return firstListValue * secondList.count { it == firstListValue }
    }

    fun totalSimilarity() : Int {
        return firstList.foldIndexed(0) {index, total, _ -> total + nthSimilarity(index)}
    }
}
