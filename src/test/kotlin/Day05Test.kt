import io.kotest.core.spec.style.FunSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.maps.shouldHaveKey
import io.kotest.matchers.maps.shouldHaveSize
import io.kotest.matchers.shouldBe

class Day05Part1Test : FunSpec({

    context("Part 1 Solution test data") {
        val testInput = readTestInputForDay(5)

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
            sut.correctlyOrdered().size shouldBe 3
        }

        test("it can return middle of the matches") {
            sut.middleOfMatches() shouldBe listOf(61, 53, 29)
        }

        test("it can return the total of the middle of the matches") {
            sut.totalOfMiddleOfMatches() shouldBe 143
        }
    }

    test("it can return total of middles in real file") {
        val input = readInputForDay(5)

        val sut = PageOrdering(input)

        sut.totalOfMiddleOfMatches() shouldBe 4814
    }

})

class Day05Part2Test : FunSpec({

    context("Part 2 Solution test data") {
        val testInput = readTestInputForDay(5)

        val sut = PageOrdering(testInput)

        test("it can return the incorrectly ordered") {
            sut.incorrectlyOrdered().size shouldBe 3
        }


        test("it can reorder one") {
            sut.correctlyReorderOne(listOf(97, 13, 75, 29, 47)) shouldBe listOf(97, 75, 47, 29, 13)
        }

        test("it can return the correctly ordered") {
            forAll(
                row(0, listOf(97, 75, 47, 61, 53)),
                row(1, listOf(61, 29, 13)),
                row(2, listOf(97, 75, 47, 29, 13)),
            ) { line, correct ->
                sut.correctlyReordered()[line] shouldBe correct
            }
        }
    }

    context("Part 2 Solution real data") {
        val input = readInputForDay(5)

        val sut = PageOrdering(input)


        test("it should have no incorrectly ordered results") {
            val reordered = sut.correctlyReordered()

            sut.incorrectlyOrdered(reordered) shouldHaveSize 0
        }

        test("it can return the correct amount from the real file") {
            sut.totalOfReorderedMiddleOfMatches() shouldBe 5448
        }
    }
})


class PageOrdering(val fileInput: List<String>) {
    fun rules(): Map<Int, List<Int>> {
        val rulesLines = fileInput.takeWhile { it.isNotEmpty() }
        val rules = mutableMapOf<Int, MutableList<Int>>()

        rulesLines.map { it.split("|") }.map { Pair(it[0].toInt(), it[1].toInt()) }
            .forEach { rules.getOrPut(it.first) { mutableListOf() }.add(it.second) }

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

    fun isRightOrder(update: List<Int>): Boolean {
        val listsOfPagesBefore =
            update.mapIndexed { index, value -> Pair(value, update.subList(0, index)) }

        val brokenRules = listsOfPagesBefore.map { value ->
            rules().getOrElse(value.first) { listOf() }.any { it in value.second }
        }

        return brokenRules.none { it }
    }

    private fun brokenRules(update: List<Int>): List<Pair<Int, Int>> {
        val listsOfPagesBefore =
            update.mapIndexed { index, value -> Pair(value, update.subList(0, index)) }

        val broken = listsOfPagesBefore.map { value ->
            rules().getOrElse(value.first) { listOf() }
                .map { Triple(value.first, it, it in value.second) }.filter { it.third }
        }.flatten().map { Pair(it.first, it.second) }

        return broken
    }

    fun correctlyOrdered(): List<List<Int>> {
        return updates().filter { isRightOrder(it) }
    }

    fun incorrectlyOrdered(): List<List<Int>> {
        return incorrectlyOrdered(updates())
    }

    fun incorrectlyOrdered(candidates: List<List<Int>>): List<List<Int>> {
        return candidates.filter { !isRightOrder(it) }
    }

    fun correctlyReordered(): List<List<Int>> {
        var toReorder = incorrectlyOrdered()

        while (incorrectlyOrdered(toReorder).isNotEmpty()) {
            toReorder = correctlyReorder(toReorder)
        }

        return toReorder
    }

    fun correctlyReorder(toReorder: List<List<Int>>): List<List<Int>> {
        return toReorder.map { correctlyReorderOne(it) }
    }

    fun correctlyReorderOne(toReorder: List<Int>): List<Int> {
        val incorrect = toReorder.toMutableList()
        val brokenRules = brokenRules(toReorder)

        brokenRules.forEach { rule ->
            incorrect.remove(rule.second)
            val index = incorrect.indexOf(rule.first)
            incorrect.add(index + 1, rule.second)
        }

        return incorrect.toList()
    }

    fun reorderedMiddleOfMatches(): List<Int> {
        return correctlyReordered().map { it[it.size / 2] }
    }

    fun totalOfReorderedMiddleOfMatches(): Int {
        return reorderedMiddleOfMatches().sum()
    }

    fun middleOfMatches(): List<Int> {
        return correctlyOrdered().map { it[it.size / 2] }
    }

    fun totalOfMiddleOfMatches(): Int {
        return middleOfMatches().sum()
    }
}

