import io.kotest.core.spec.style.FunSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class Day06Part1Test : FunSpec({

    context("Part 1 Solution test data") {
        val testInput = readTestInputForDay(6)

        test("it can read the map and find the start") {
            val sut = GuardMap(testInput)
            sut.current() shouldBe State(Coordinate(4, 6), Directions.North)
        }

        test("it can move to the next obstruction") {
            val sut = GuardMap(testInput)
            sut.findNextObstruction()
            sut.current() shouldBe State(Coordinate(4, 1), Directions.East)
        }

        test("it can move to the second obstruction") {
            val sut = GuardMap(testInput)
            sut.findNextObstruction() shouldBe true
            sut.findNextObstruction() shouldBe true
            sut.current() shouldBe State(Coordinate(8, 1), Directions.South)
        }

        test("it can find the exit") {
            val sut = GuardMap(testInput)
            sut.findExit()

            sut.current() shouldBe State(Coordinate(7, 9), Directions.Exit)
            sut.totalMoves() shouldBe 41
        }
    }

    context("Part 1 Solution real data") {
        val testInput = readInputForDay(6)

        test("it can find the exit") {
            val sut = GuardMap(testInput)
            sut.findExit()

            sut.totalMoves() shouldBe 5067
        }
    }
})

class Day06Part2Test : FunSpec({

    context("Part 2 Solution test data") {
        val testInput = readTestInputForDay(6)

        test("it can read the map and find the exit") {
            val sut = GuardMap(testInput)
            sut.addObstruction(Coordinate(0, 0))

            sut.findExit() shouldBe true
            sut.totalMoves() shouldBe 41
        }

        test("it exits with obstruction") {
            forAll(
                row(Coordinate(3, 6), false),
                row(Coordinate(6, 7), false),
                row(Coordinate(7, 7), false),
                row(Coordinate(1, 8), false),
                row(Coordinate(3, 8), false),
                row(Coordinate(7, 9), false),
                row(Coordinate(0, 0), true),

                ) { obstruction, result ->
                val sut = GuardMap(testInput)
                sut.addObstruction(obstruction)
                sut.findExit() shouldBe result
            }
        }

        test("it can calculate how many obstructions cause loops") {
            val sut = GuardMap(testInput)
            sut.countAllLoops() shouldBe 6
        }

    }
    context("Part 2 Solution real data") {
        val testInput = readInputForDay(6)

        test("it can calculate how many obstructions cause loops") {
            val sut = GuardMap(testInput)
            sut.findExit()

            sut.countAllLoops() shouldBe 1793
        }
    }

})

data class State(var coordinate: Coordinate, var direction: Directions) {
    fun move() {
        coordinate = nextMove()
    }

    fun nextMove() : Coordinate {
        return coordinate.move(direction.offset)
    }

    fun turn() {
        direction = direction.nextTurn!!
    }

    fun exit() {
        direction = Directions.Exit
    }
}

enum class Directions(val offset: Coordinate, var nextTurn: Directions?) {
    Exit(Coordinate(0,0),  null),
    North(Coordinate(0, -1),  null),
    East(Coordinate(1, 0),  null),
    South(Coordinate(0, 1),  null),
    West(Coordinate(-1, 0),  null);

    companion object {
        init {
            Exit.nextTurn = Exit
            North.nextTurn = East
            East.nextTurn = South
            South.nextTurn = West
            West.nextTurn = North
        }
    }
}

data class Coordinate(val x: Int, val y: Int) {
    fun move(offset: Coordinate) : Coordinate {
        return Coordinate(x + offset.x, y + offset.y)
    }
}

class GuardMap(val input: List<String>) {

    private val start = Coordinate(x = input.single { it.contains("^") }.indexOfFirst { it == '^' }, y = input.indexOfFirst { it.contains("^") })

    private var state: State = start()

    private val visited = mutableSetOf(start)

    private val stateAtObstruction = mutableListOf<State>()

    private var obstruction: Coordinate? = null

    fun bounds(): Bounds {
        return Bounds(IntRange(0, input.size - 1), IntRange(0, input.first().length - 1))
    }

    private fun inBounds(coordinate: Coordinate): Boolean {
        return coordinate.y >= 0 && coordinate.y < input.size && coordinate.x < input.first().length && coordinate.x >= 0
    }

    fun totalMoves(): Int {
        return visited.size
    }

    fun at(coordinate: Coordinate) : Char {
        if (coordinate == obstruction) return '#'
        val char = if (inBounds(coordinate)) input[coordinate.y][coordinate.x] else ' '
        return if(char == '^') '.' else char
    }

    fun start(): State {
        return State(start, Directions.North)
    }

    fun current(): State {
        return state
    }

    fun findExit() : Boolean {
        while (state.direction != Directions.Exit) {
            if (!findNextObstruction()) {
                return false
            }
        }

        return true
    }

    fun findNextObstruction() : Boolean {

        while(at(state.nextMove()) == '.') {
            state.move()
            visited.add(state.coordinate)
        }

        if (stateAtObstruction.contains(state)) {
            println("Obstruction ${state}")
            return false
        }

        stateAtObstruction.add(State(state.coordinate, state.direction ))

        if (at(state.nextMove()) == ' ') state.exit() else state.turn()

        return true
    }

    fun addObstruction(coordinate: Coordinate) {
        obstruction = coordinate
    }

    fun reset() {
        obstruction = null
        stateAtObstruction.clear()
        visited.clear()
        state = start()
    }

    fun countAllLoops(): Int {
        var total = 0
        for (y in bounds().height) {
            for (x in bounds().width) {
                if (at(Coordinate(x, y)) == '.') {
                    reset()
                    addObstruction(Coordinate(x, y))
                    total += if (!findExit()) 1 else 0
                }
            }
        }
        return total
    }
}


