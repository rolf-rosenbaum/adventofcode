package day6

fun main(args: Array<String>) {
    val orbitCalculator = OrbitCalculator("orbits.txt")
    println(orbitCalculator.partOne())
    println(orbitCalculator.partTwo())
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

    private fun distanceToCenter(orbiter: Pair<String, String>): Int {

        if (orbiter.second == center) {
            return 1
        }
        return 1 + distanceToCenter(orbiters.first { it.first == orbiter.second })
    }

    private fun orbitsBetween(planet: String, orbiter: Pair<String, String>): Int {

        if (orbiter.second == planet) {
            return 0
        }
        return 1 + orbitsBetween(planet, orbiters.first { it.first == orbiter.second })
    }

    fun totalOrbits(): Int = orbiters.sumBy { distanceToCenter(it) }

    fun pathToCenterFrom(planet: Pair<String, String>, pathSoFar: MutableList<String> = mutableListOf()): List<String> {
        if (planet.second == center) {
            return pathSoFar
        }
        pathSoFar.add(planet.second)

        return pathToCenterFrom(orbiters.first { it.first == planet.second }, pathSoFar)
    }

    fun closestCommonNode(planetA: String, planetB: String): String {
        val pairA = orbiters.first { it.first == planetA }
        val pairB = orbiters.first { it.first == planetB }

        val commonNodes = pathToCenterFrom(pairA).intersect(pathToCenterFrom(pairB))

        return commonNodes.maxBy { name ->
            distanceToCenter(orbiters.first {
                it.second == name
            })
        }!!
    }

    fun partOne(): String {
        return "total orbits: ${totalOrbits()}"
    }

    fun partTwo(): String {
        return "orbital transfers: ${orbitalTransfers("YOU", "SAN")}"
    }

    fun orbitalTransfers(planetA: String, planetB: String): Int {
        val pairA = orbiters.first { it.first == planetA }
        val pairB = orbiters.first { it.first == planetB }

        val closestCommonNode = closestCommonNode(planetA, planetB)

        return orbitsBetween(closestCommonNode, pairA) + orbitsBetween(closestCommonNode, pairB)
    }
}
