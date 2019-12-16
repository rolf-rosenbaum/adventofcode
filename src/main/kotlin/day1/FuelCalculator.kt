package day1

fun main(args: Array<String>) {
    val fuelCalculator = FuelCalculator("modules.txt")

    println("Fuel needed: ${fuelCalculator.calculateFuelForAllModules()}")

}

class FuelCalculator(val fileName: String) {


    val modules = readInputData()

    private fun readInputData(): List<Int> =

            javaClass.classLoader.getResourceAsStream(fileName)
                    .reader()
                    .readLines()
                    .map { it.toInt() }

    fun calculateFuelForModule(mass: Int): Int {
        return mass / 3 - 2
    }

    fun calculateFuelForAllModules(): Int {
        return modules.sumBy { calculateFuelForModule(it) }
    }
}