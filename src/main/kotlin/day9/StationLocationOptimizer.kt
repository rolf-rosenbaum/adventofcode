package day9

import kotlin.math.sign

fun main(args: Array<String>) {
    val optimizer = StationLocationOptimizer("map.txt")

    println("Best location is ${optimizer.findBestAsteroid()}")
}

class StationLocationOptimizer(private val fileName: String) {
    val asteroidMap = readInputData()
        get() = field

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

            if (asteroidMap.any { it.isInLineOfSightBetween(asteroid, other) })
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

    fun vaporize(laserStation: Asteroid) {
        val maxX = asteroidMap.maxBy(Asteroid::x)?.x
        val maxY = asteroidMap.maxBy(Asteroid::y)?.y

        var currentX = laserStation.x
        var currentY = laserStation.y
        val map = asteroidMap.toMutableSet()
        while (map.size > 1) {

            for (yPos in currentY downTo 0) {
                val asteroid = Asteroid(currentX, yPos)
                if (map.contains(asteroid)) {
                    if (! map.any { it.isInLineOfSightBetween(asteroid, laserStation) }) {
                        map.remove(asteroid)
                    }
                }
            }
        }
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

}

fun gcd(a: Int, b: Int): Int {
    if (b == 0) {
        return a
    }
    return gcd(b, a % b)
}