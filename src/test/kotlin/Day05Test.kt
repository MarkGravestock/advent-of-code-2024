import io.kotest.core.spec.style.FunSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.maps.shouldHaveKey
import io.kotest.matchers.maps.shouldHaveSize
import io.kotest.matchers.shouldBe

class Day05Part1Test: FunSpec ({

    context("Part 1 Solution test data") {
        val testInput =  readTestInputForDay(5)

        val sut = PageOrdering(testInput)

        test("it can read the rules") {
            sut.rules() shouldHaveKey 97
            sut.rules() shouldHaveSize 6
        }

        test("it can read the page updates") {
            sut.updates().size shouldBe 6
        }

        test("it can evaluate the rules on the updates") {
            forAll(
                row(0, true),
                row(1, true),
                row(2, true),
                row(3, false),
                row(4, false),
                row(5, false),
            ) { line, correct ->
                sut.isRightOrder(line) shouldBe correct
            }
        }

        test("it can return the matches") {
            sut.matches().size shouldBe 3
        }

        test("it can return middle of the matches") {
            sut.middleOfMatches() shouldBe listOf(61,53,29)
        }

        test("it can return the total of the middle of the matches") {
            sut.totalOfMiddleOfMatches() shouldBe 143
        }
    }

    test("it can count matches at all location in real file") {
        val input =  readInputForDay(5)

        val sut = PageOrdering(input)

        sut.totalOfMiddleOfMatches() shouldBe 4814
    }

})

class PageOrdering(val fileInput: List<String>) {
    fun rules(): Map<Int,List<Int>> {
        val rulesLines = fileInput.takeWhile { it.isNotEmpty() }
        val rules = mutableMapOf<Int,MutableList<Int>>()

        rulesLines.map { it.split("|") }.map { Pair(it[0].toInt(), it[1].toInt()) }.forEach { rules.getOrPut(it.first) { mutableListOf() }.add(it.second) }

        return rules
    }

    fun updates(): List<List<Int>> {
        val updateLines = fileInput.takeLastWhile { it.isNotEmpty() }

        return updateLines.map {
            it.split(",").map { it.toInt() }
        }
    }

    fun isRightOrder(updatedIndex: Int): Boolean {
        val update = updates()[updatedIndex]

        return isRightOrder(update)
    }

    private fun isRightOrder(update: List<Int>): Boolean {
        val listsOfPagesBefore =
            update.mapIndexed { index, value -> Pair(value, update.subList(0, index)) }

        val brokenRules = listsOfPagesBefore.map { value ->
            rules().getOrElse(value.first) { listOf() }.any { it in value.second }
        }

        return brokenRules.none { it }
    }

    fun matches(): List<List<Int>> {
        return updates().filter { isRightOrder(it) }
    }

    fun middleOfMatches() : List<Int> {
        return matches().map { it[it.size / 2] }
    }

    fun totalOfMiddleOfMatches() : Int {
        return middleOfMatches().sum()
    }
}

