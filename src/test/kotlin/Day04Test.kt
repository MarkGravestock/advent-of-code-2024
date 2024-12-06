import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day04Part1Test: FunSpec ({

    context("Part 1 Solution test data") {
        val testInput =  readTestInputForDay(4)

        val sut = WordSearch(testInput)

        test("it can read the bounds of the search") {
            sut.bounds().height.first shouldBe 0
            sut.bounds().height.last shouldBe 9
            sut.bounds().width.first shouldBe 0
            sut.bounds().width.last shouldBe 9
        }

        test("it can read the character at a location") {
            sut.readAt(9, 9) shouldBe "X"
        }

        test("it returns null when outside the bounds of the search") {
            sut.readAt(-1, 10) shouldBe ""
        }

        test("it can read candidates at a location") {
            sut.readCandidatesAt(0, 5) shouldBe listOf("XMAS", "XSAM", "XMMS", "XXSA", "XXSM", "X", "X", "X")
        }

        test("it can count matches at a location") {
            sut.matchesAt(0,5) shouldBe 1
            sut.matchesAt(9,5) shouldBe 3
        }

        test("it can count matches at all location") {
            sut.totalMatches() shouldBe 18
        }

    }

    test("it can count matches at all location in real file") {
        val input =  readInputForDay(4)

        val sut = WordSearch(input)

        sut.totalMatches() shouldBe 2336
    }

})

class WordSearch(val fileInput: List<String>) {
    fun bounds(): Bounds {
        return Bounds(IntRange(0, fileInput.size - 1), IntRange(0, fileInput.first().length - 1))
    }

    fun readAt(line: Int, column: Int): String {
        if (bounds().height.contains(line) && bounds().width.contains(column)) {
            return fileInput[line][column].toString()
        }

        return ""
    }

    fun totalMatches(): Int {
        var total = 0
        for (line in bounds().height) {
            for (column in bounds().width) {
                total += matchesAt(line, column)
            }
        }
        return total
    }


    fun matchesAt(line: Int, oolumn: Int): Int {
        return readCandidatesAt(line, oolumn).count { it.equals("XMAS") }
    }

    fun readCandidatesAt(line: Int, oolumn: Int): List<String> {
        val candidates = mutableListOf<String>()

        val changes = listOf(
            Changes(0,1),
            Changes(1, 1),
            Changes(1, 0),
            Changes(1, -1),
            Changes(0, -1),
            Changes(-1, -1),
            Changes(-1, 0),
            Changes(-1, 1),
        )

        changes.forEach{ candidates.add(readInDirection(line, oolumn, it)) }

        return candidates
    }

    private fun readInDirection(
        line: Int,
        oolumn: Int,
        changes: Changes
    ): String {
        var lineIndex = line
        var columnIndex = oolumn

        var results = ""

        repeat(4) {
            results = results + readAt(lineIndex, columnIndex)
            columnIndex += changes.column
            lineIndex += changes.line
        }
        return results
    }
}

class Changes(val line: Int, val column: Int)
class Bounds(val height: IntRange, val width: IntRange)