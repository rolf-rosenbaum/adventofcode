package day2

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