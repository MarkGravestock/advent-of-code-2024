import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe

class Day08Part1Test : FunSpec({

    context("Part 1 Solution test data") {
        val testInput = readTestInputForDay(8)

        test("it can read the input") {
            val sut = AntennaLocations(testInput)
            sut.antennas() shouldHaveSize 2
            sut.antennas().first { it.frequency == 'A' }.locations shouldBe mutableListOf(Coordinate(6, 5),Coordinate(8, 8), Coordinate(9, 9))
        }

        test("it can calculate the differences") {
            Antenna('a', mutableListOf(Coordinate(4, 3), Coordinate(5, 5))).differences().map { it.second } shouldBe listOf(Difference(2.0,2.23606797749979))
        }

        test("it can calculate the anti nodes") {
            Antenna('a', mutableListOf(Coordinate(4, 3), Coordinate(5, 5))).antinodes() shouldBe listOf(Coordinate(6, 7), Coordinate(3, 1))
            Antenna('a', mutableListOf(Coordinate(4, 3), Coordinate(5, 5), Coordinate(8, 4))).antinodes() shouldHaveSize 6
        }

        xtest("it can calculate more anti nodes") {
            Antenna('A', mutableListOf(Coordinate(6, 5),Coordinate(8, 8), Coordinate(9, 9))).antinodes() shouldContainExactlyInAnyOrder listOf(Coordinate(10, 10), Coordinate(10, 11), Coordinate(7, 7), Coordinate(4, 2), Coordinate(12, 13), Coordinate(3, 1))
            Antenna('0', mutableListOf(Coordinate(4, 4),Coordinate(5, 2), Coordinate(8, 3), Coordinate(9, 1))).antinodes() shouldHaveSize 12
        }

        xtest("it can calculate all the anti nodes") {
            val sut = AntennaLocations(testInput)
            sut.visibleAntinodes() shouldHaveSize 14
        }

        context("Part 1 Solution real data") {
            val input = readInputForDay(8)

            xtest("total calibration result") {
                val sut = AntennaLocations(input)

                sut.visibleAntinodes() shouldHaveSize 100
            }
        }
    }
})

class AntennaLocations(val input: List<String>) {

    fun inBounds(coordinate: Coordinate): Boolean {
        return coordinate.y >= 0 && coordinate.y < input.size && coordinate.x < input.first().length && coordinate.x >= 0
    }


    fun antennas() : List<Antenna> {
        val antennas = mutableMapOf<Char, Antenna>()

        input.forEachIndexed{ y, value -> value.forEachIndexed{ x, ant -> if (ant.isLetterOrDigit()) antennas.getOrPut(ant) { Antenna(ant)}.addLocation(Coordinate(x, y))  } }

        return antennas.values.toList()
    }

    fun visibleAntinodes() : List<Coordinate> {
        return antennas().flatMap { it.antinodes() }.filter { inBounds(it) }
    }

}

class Antenna(val frequency: Char, val locations: MutableList<Coordinate> = mutableListOf()) {
    fun addLocation(coordinate: Coordinate) {
        locations.add(coordinate)
    }

    fun paris() : List<Pair<Coordinate, Coordinate>> {
        if (locations.size == 0) {
            return emptyList()
        }

        return generateCombinations(locations)
    }

    fun <T> generateCombinations(list: List<T>): List<Pair<T, T>> {
        val combinations = mutableListOf<Pair<T, T>>()
        for (i in list.indices) {
            for (j in i + 1 until list.size) {
                combinations.add(Pair(list[i], list[j]))
            }
        }
        return combinations
    }

    fun differences() : List<Pair<Coordinate, Difference>> {
        return paris().map { Pair(it.first, it.first.difference(it.second)) }
    }

    fun antinodes() : List<Coordinate> {
        return differences().flatMap { listOf(it.first.move(Difference(it.second.gradient, it.second.distance * 2)), it.first.move(Difference(it.second.gradient, it.second.distance), -1.0)) }
    }
}


