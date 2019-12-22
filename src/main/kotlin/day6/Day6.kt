package day6

fun main(args: Array<String>) {
    val orbitCalculator = OrbitCalculator("orbits.txt")
    println(orbitCalculator.partOne())
}

class OrbitCalculator(private val fileName: String) {

    private var centerPlanets: MutableList<String> = mutableListOf()
    private var orbiters: MutableList<Pair<String, String>> = mutableListOf()
    private var center = ""

    init {
        readInputData()
        center = center()
    }

    fun readInputData() {

        javaClass.classLoader.getResourceAsStream(fileName)
                .reader()
                .readLines()
                .map {
                    it.split(")")
                }.map {
                    centerPlanets.add(it[0])
                    orbiters.add(it[1] to it[0])
                }
    }

    fun center(): String {
        return centerPlanets.first { !orbiters.any { orbiter -> orbiter.first == it } }
    }

    fun distanceToCenter(orbiter: Pair<String, String>): Int {

        if (orbiter.second == center) {
            return 1
        }
        return 1 + distanceToCenter(orbiters.first { it.first == orbiter.second })
    }

    fun totalOrbits(): Int = orbiters.sumBy { distanceToCenter(it) }

    fun partOne() : String {
        return "total orbits: ${totalOrbits()}"
    }
}
