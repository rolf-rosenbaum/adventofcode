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

class IntCodeComputer(private val fileName: String) {

    private val input = readInputData()

    private fun output() = input

    fun updateInput(corrections: List<Pair<Int, Int>>) {
        corrections.map { input[it.first] = it.second }
    }

    fun readInputData(): MutableList<Int> =

        javaClass.classLoader.getResourceAsStream(fileName)
            .reader()
            .readText()
            .split(",").map { it.toInt() }.toMutableList()

    fun execute(): List<Int> {
        var startPos = 0

        var opCode = input[startPos]
        while (opCode != 99) {
            when (opCode) {
                1 -> {
                    input[input[startPos + 3]] = input[input[startPos + 1]] + input[input[startPos + 2]]
                }
                2 -> {
                    input[input[startPos + 3]] = input[input[startPos + 1]] * input[input[startPos + 2]]
                }
                else -> {
                }
            }
            startPos += 4
            opCode = input[startPos]
        }
        return output()
    }
}
