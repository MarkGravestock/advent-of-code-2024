import kotlin.math.abs

class Locations(locations: List<String>) {
    private val firstList: List<Int> = locations.map { it.split("   ") }.map { it[0].toInt() }.sorted()
    private val secondList: List<Int> = locations.map { it.split("   ") }.map { it[1].toInt() }.sorted()

    fun firstDistance() : Int {
        return abs(firstList[0] - secondList[0])
    }

    fun totalDistances(): Int {
        return 0
    }
}
