import io.kotest.core.spec.style.FunSpec
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
            sut.findNextObstruction()
            sut.findNextObstruction()
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

    private fun inBounds(coordinate: Coordinate): Boolean {
        return coordinate.y >= 0 && coordinate.y < input.size && coordinate.x < input.first().length && coordinate.x >= 0
    }

    fun totalMoves(): Int {
        return visited.size
    }

    fun at(coordinate: Coordinate) : Char {
        val char = if (inBounds(coordinate)) input[coordinate.y][coordinate.x] else ' '
        return if(char == '^') '.' else char
    }

    fun start(): State {
        return State(start, Directions.North)
    }

    fun current(): State {
        return state
    }

    fun findExit() {
        while (state.direction != Directions.Exit) {
            findNextObstruction()
            println(state)
        }
    }

    fun findNextObstruction() {

        while(at(state.nextMove()) == '.') {
            state.move()
            visited.add(state.coordinate)
        }

        if (at(state.nextMove()) == ' ') state.exit() else state.turn()
    }
}


