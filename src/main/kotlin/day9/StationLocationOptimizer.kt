package day9

import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.sign

fun main(args: Array<String>) {
    val optimizer = StationLocationOptimizer("map.txt")

    println("Best location is ${optimizer.findBestAsteroid()}")
    val no200 = optimizer.vaporize().get(199)
    println("200th Asteroid destroyed is ${no200.x *100 + no200.y}")

}

class StationLocationOptimizer(private val fileName: String) {
    val asteroidMap = readInputData()

    private fun readInputData(): Set<Asteroid> {
        val asteroidMap = mutableSetOf<Asteroid>()

        javaClass.classLoader.getResourceAsStream(fileName)
            .reader()
            .readText()
            .split("\n")
            .mapIndexed { y, line ->
                line.mapIndexed { x, char ->
                    if (char == '#') {
                        asteroidMap.add(Asteroid(x, y))
                    }
                }
            }
        return asteroidMap
    }

    fun countVisibleAsteroidsFor(asteroid: Asteroid): Asteroid {
        if (!asteroidMap.contains(asteroid)) {
            return asteroid
        }

        var result = asteroidMap.size - 1

        asteroidMap.minusElement(asteroid).map { other ->

            if (!other.isVisbleFrom(asteroidMap, asteroid))
                result--
        }

        asteroid.visibleCounter = result
        return asteroid
    }

    fun findBestAsteroid(): Asteroid {
        return asteroidMap.map {
            countVisibleAsteroidsFor(it)
        }.maxBy {
            it.visibleCounter
        }!!
    }

    fun vaporize() : List<Asteroid> {

        val laserStation = findBestAsteroid()

        val vaporized = mutableListOf<Asteroid>()
        val map = asteroidMap.toMutableSet()
        var goner: Asteroid? = null
        while (map.size > 1) {
            goner = findNextToVaporize(map, laserStation, goner)
            map.remove(goner)
            vaporized.add(goner.copy(visibleCounter = 0))
        }
        return vaporized
    }

    fun findNextToVaporize(map: Set<Asteroid>, laserStation: Asteroid, last: Asteroid?): Asteroid {

        val lastAngle = if(last == null) PI else PI - (last - laserStation).angle()
        return map.minusElement(laserStation)
            .filter { it.isVisbleFrom(map, laserStation) }
            .minBy { lastAngle - (it - laserStation).angle() }!!
    }

    fun Asteroid.isVisbleFrom(map: Set<Asteroid>, other: Asteroid): Boolean {
        return !map.any { it.isInLineOfSightBetween(this, other) }
    }
}

data class Asteroid(val x: Int, val y: Int, var visibleCounter: Int = 0) {

    operator fun plus(v: Vector): Asteroid = Asteroid(x + v.x, y + v.y)

    operator fun minus(c: Asteroid): Vector = Vector(x - c.x, y - c.y)

    private fun distanceTo(other: Asteroid): Int {
        return (x - other.x) + (y - other.y)
    }

    private fun isInDifferentDirection(asteroid: Asteroid, other: Asteroid): Boolean {
        return (x - asteroid.x).sign != (x - other.x).sign ||
                (y - asteroid.y).sign != (y - other.y).sign
    }

    fun isInLineOfSightBetween(a: Asteroid, b: Asteroid): Boolean {
        if (x == 0 && y == 0) {
            return false
        }
        if (a == b || this == a || this == b) {
            return false
        }


        val cb = this - b
        val ac = this - a

        return cb.isDependant(ac) && isInDifferentDirection(a, b)
    }



}

data class Vector(val x: Int, val y: Int) {
    fun isDependant(other: Vector): Boolean {

        if (x == 0) {
            return other.x == 0
        }
        if (y == 0) {
            return other.y == 0
        }
        return x / gcd(x, y) == other.x / gcd(other.x, other.y) && y / gcd(x, y) == other.y / gcd(other.x, other.y)

    }

    fun angle():Float = atan2(x.toFloat(), y.toFloat())

}

fun gcd(a: Int, b: Int): Int {
    if (b == 0) {
        return a
    }
    return gcd(b, a % b)
}