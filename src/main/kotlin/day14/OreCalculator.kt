package day14

class OreCalculator(val fileName: String) {


    fun readInputData() {
        val lines = javaClass.classLoader.getResourceAsStream(fileName)
                .reader()
                .readLines()
        val reactions: List<Reaction> = mutableListOf()
        lines.forEach { line ->
            val foo = line.split("=>")
            val result: Result = foo.last()
            val inputs = foo.first().split(",")

        }
    }


    companion object {
        val ORE_NAME = "ORE"
    }
}

typealias Result = String
typealias Input = String
typealias Reaction = Map<Result, List<Input>>
