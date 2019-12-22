package day2

fun main(args: Array<String>) {

    val computer = IntCodeComputer("day2Input.txt")
    computer.updateInput(listOf(1 to 12, 2 to 2))
    println(computer.execute())

    for (noun in 0..99) {
        for (verb in 0..99) {
            val comp = IntCodeComputer("day2Input.txt")
            comp.updateInput(listOf(1 to noun, 2 to verb))
            if (comp.execute()[0] == 19690720) {
                println("RESULT: ${noun * 100 + verb}")
                return
            }
        }
    }
}


