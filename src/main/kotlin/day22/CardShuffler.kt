package day22

fun main() {


    println(partOne())
    println(partTwo())

}

private fun partOne(): String {
    val cardShuffler = CardShuffler(10007)

    val commandList = cardShuffler.javaClass.classLoader.getResourceAsStream("shuffle.txt")
            .reader()
            .readLines()
            .map { toShuffleCommand(it) }


    commandList.map { cardShuffler.executeCommand(it) }


    return "Position of Card 2019 is ${cardShuffler.deck.indexOf(2019)}"
}

private fun partTwo(): String {
    val cardShuffler = CardShuffler(119315717514047)

    val commandList = cardShuffler.javaClass.classLoader.getResourceAsStream("shuffle.txt")
            .reader()
            .readLines()
            .map { toShuffleCommand(it) }


    commandList.map { cardShuffler.executeCommand(it) }


    return "Position of Card 2019 is ${cardShuffler.deck.indexOf(2019)}"
}


fun toShuffleCommand(line: String): ShuffleCommand {
    if (line.startsWith("deal with increment"))
        return ShuffleCommand(Command.INC, line.filter { it.isDigit() }.toInt())

    return if (line.startsWith("cut")) {

        val number = line.filter { it.isDigit() }.toInt()
        if (line.contains("-"))
            ShuffleCommand(Command.CUT, -number)
        else
            ShuffleCommand(Command.CUT, number)
    } else
        ShuffleCommand(Command.REVERSE, 0)
}


class CardShuffler(numberOfCards: Long) {

    fun executeCommand(shuffleCommand: ShuffleCommand) {
        deck = when (shuffleCommand.command) {
            Command.REVERSE -> dealIntoNewStack()
            Command.CUT -> cut(shuffleCommand.number)
            Command.INC -> dealWithIncrement(shuffleCommand.number)
        }
    }

    var deck: Deck = (0 until numberOfCards).toList()

    fun dealIntoNewStack(): Deck = deck.reversed()

    fun cut(number: Int): Deck {
        return if (number > 0) {
            deck.cut(number)
        } else {
            deck.reversed().cut(-number).reversed()
        }
    }

    fun dealWithIncrement(inc: Int): Deck {
        val newDeck = Array<Long>(deck.size) { 0 }
        var currentPosition = 0

        for (element in deck) {
            newDeck[currentPosition] = element
            currentPosition = (currentPosition + inc) % deck.size
        }
        return newDeck.asList()
    }
}

enum class Command {
    CUT, REVERSE, INC
}

data class ShuffleCommand(val command: Command, val number: Int)

typealias Deck = List<Long>

fun Deck.cut(number: Int): Deck = subList(number, size).plus(subList(0, number))
