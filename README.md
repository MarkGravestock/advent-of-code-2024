# advent-of-code-2024

Welcome to the Advent of Code[^aoc] Kotlin project created by [markgravestock][github] using the [Advent of Code Kotlin Template][template] delivered by JetBrains.

In this repository, markgravestock is about to provide solutions for the puzzles using [Kotlin][kotlin] language.

### Notes

#### Day 6 Part 2

- Key insight: To detect a loop, you can check if you are ever at the same coordinate in the same direction when you hit an obstruction.
- TODO: Possibly you could detect at any coordinate you have visited if you are in the same direction and combine this with counting visited locations.

### Hints

If you're stuck with Kotlin-specific questions or anything related to this template, check out the following resources:

- [Kotlin docs][docs]
- [Kotlin Slack][slack]
- Template [issue tracker][issues]


[^aoc]:
    [Advent of Code][aoc] – An annual event of Christmas-oriented programming challenges started December 2015.
    Every year since then, beginning on the first day of December, a programming puzzle is published every day for twenty-five days.
    You can solve the puzzle and provide an answer using the language of your choice.

[aoc]: https://adventofcode.com
[docs]: https://kotlinlang.org/docs/home.html
[github]: https://github.com/markgravestock
[issues]: https://github.com/kotlin-hands-on/advent-of-code-kotlin-template/issues
[kotlin]: https://kotlinlang.org
[slack]: https://surveys.jetbrains.com/s3/kotlin-slack-sign-up
[template]: https://github.com/kotlin-hands-on/advent-of-code-kotlin-template
