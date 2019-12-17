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

    fun calculateFuelFor(mass: Int): Int {
        return mass / 3 - 2
    }

    fun calculateFuelForModule(mass: Int) : Int{
        val fuelForModule =  calculateFuelFor(mass)
        return fuelForModule + calculateFuelForFuel(fuelForModule)
    }

    fun calculateFuelForFuel(fuel: Int): Int {
        val fuelForFuel = calculateFuelFor(fuel)
        return if (fuelForFuel < 0) {
            0
        } else {
            fuelForFuel + calculateFuelForFuel(fuelForFuel)
        }
    }

    fun calculateFuelForAllModules(): Int {
        return modules.sumBy { calculateFuelForModule(it) }
    }
}