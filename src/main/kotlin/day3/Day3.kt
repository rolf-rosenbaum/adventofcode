package day3

import day3.Move.*
import java.lang.IllegalArgumentException
import kotlin.math.abs

fun main(args: Array<String>) {

    val wireChecker = WireChecker("wires.txt")

    println(wireChecker.partOne())
    println(wireChecker.partTwo())

}

class WireChecker(private val fileName: String) {

    private val input = readInputData()
    private val port = Point(0, 0)

    private fun readInputData(): List<List<String>> =

            javaClass.classLoader.getResourceAsStream(fileName)
                    .reader()
                    .readLines()
                    .map {
                        it.split(",").toList()
                    }


    fun partOne(): String {

        val paths: List<Path> = generatePaths(listOf(port))
        val crossings = paths[0].intersect(paths[1])
        val closest = crossings.minBy { it.distance(port) }
        return "Closest crossing is: $closest at a distance of ${closest?.distance(port)}"

    }

    private fun generatePaths(start: List<Point>): List<Path> {
        return input.map {
            it.map(::parseMove)
                    .fold(start) { path, move -> path + move.generatePath(path.last()) }
                    .drop(1) // drop port
        }.toList()
    }

    fun partTwo(): String {
        val paths: List<Path> = generatePaths(listOf(port))
        val firstWire = paths[0]
        val secondWire = paths[1]
        val crossings = firstWire.intersect(secondWire)
        val x = crossings.minBy { firstWire.stepsTo(it) + secondWire.stepsTo(it) }!!

        return "Crossing reachable with fewest combined steps: $x, number of steps needed: ${firstWire.stepsTo(x) + secondWire.stepsTo(x)}"

    }

}

fun parseMove(move: String): Move {
    val direction = move[0]
    val steps = move.substring(1).toInt()

    return when (direction) {
        'U' -> Up(steps)
        'D' -> Down(steps)
        'L' -> Left(steps)
        'R' -> Right(steps)
        else -> throw IllegalArgumentException("illegal direction code: $direction")
    }
}


sealed class Move(val vector: Vector, val steps: Int) {

    class Up(steps: Int) : Move(Vector(0, 1), steps)
    class Down(steps: Int) : Move(Vector(0, -1), steps)
    class Left(steps: Int) : Move(Vector(-1, 0), steps)
    class Right(steps: Int) : Move(Vector(1, 0), steps)

    fun generatePath(start: Point): Path =
            generateSequence(start + vector) { point -> point + vector }.take(steps).toList()

}

data class Point(val x: Int, val y: Int) {
    operator fun plus(v: Vector) = copy(x = x + v.x, y = y + v.y)

    fun distance(other: Point): Int = abs(x - other.x) + abs(y - other.y)

}

typealias Vector = Point
typealias Path = List<Point>

fun Path.stepsTo(point: Point) =
        takeWhile { it != point }.count() + 1


