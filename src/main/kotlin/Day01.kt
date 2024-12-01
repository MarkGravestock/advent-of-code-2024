import kotlin.math.abs

fun List<String>.readList(column: Int) : List<Int> = this.map { it.split("   ") }.map { it[column].toInt() }

class Distances(locations: List<String>) {
    private val firstList: List<Int> = locations.readList(0).sorted()
    private val secondList: List<Int> = locations.readList(1).sorted()

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
    private val firstList: List<Int> = locations.readList(0)
    private val secondList: List<Int> = locations.readList(1)

    fun nthSimilarity(n: Int) : Int {
        val firstListValue = firstList[n]
        return firstListValue * secondList.count { it == firstListValue }
    }

    fun totalSimilarity() : Int {
        return firstList.foldIndexed(0) {index, total, _ -> total + nthSimilarity(index)}
    }
}
