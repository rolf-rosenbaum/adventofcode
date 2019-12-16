package day9

import kotlin.math.atan2
import kotlin.math.sign

fun main(args: Array<String>) {
    val optimizer = StationLocationOptimizer("map.txt")

    println("Best location is ${optimizer.findBestAsteroid()}")
    val no200 = optimizer.vaporizeAsteroids()[199]
    println("200th Asteroid destroyed is ${no200.x * 100 + no200.y}")

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

    fun vaporizeAsteroids(): List<Asteroid> {

        val laserStation = findBestAsteroid()
        val remainingAsteroids = asteroidMap
                .minusElement(laserStation)
                .toMutableSet()

        val vaporized = mutableListOf<Asteroid>()
        while (remainingAsteroids.size > 0) {
            findNextAsteroidsToVaporize(remainingAsteroids, laserStation).map {
                remainingAsteroids.remove(it)
                vaporized.add(it.copy(visibleCounter = 0))
            }
        }
        return vaporized
    }

    fun findNextAsteroidsToVaporize(remainingAsteroids: Set<Asteroid>, laserStation: Asteroid): List<Asteroid> {

        return remainingAsteroids
                .filter { it.isVisbleFrom(remainingAsteroids, laserStation) }
                .sortedByDescending { (it - laserStation).angle() }
    }

    fun Asteroid.isVisbleFrom(remainingAsteroids: Set<Asteroid>, other: Asteroid): Boolean {
        return !remainingAsteroids.any { it.isInLineOfSightBetween(this, other) }
    }
}

data class Asteroid(val x: Int, val y: Int, var visibleCounter: Int = 0) {

    operator fun plus(v: Vector): Asteroid = Asteroid(x + v.x, y + v.y)

    operator fun minus(c: Asteroid): Vector = Vector(x - c.x, y - c.y)

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

        return (this - b).isDependant(this - a) && isInDifferentDirection(a, b)
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

    fun angle(): Float = atan2(x.toFloat(), y.toFloat())
}

fun gcd(a: Int, b: Int): Int {
    if (b == 0) {
        return a
    }
    return gcd(b, a % b)
}