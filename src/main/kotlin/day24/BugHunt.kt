package day24

import kotlin.math.pow

fun main() {


    val world = World("eris.txt")

    world.generations()
}


class World(private val fileName: String) {

    private var livingCells: MutableSet<Cell>

    private val generations: MutableList<Set<Cell>>

    init {
        livingCells = mutableSetOf()
        generations = mutableListOf()
        readInput()
    }

    private fun readInput() {
        javaClass.classLoader.getResourceAsStream(fileName)
                .reader()
                .readLines().mapIndexed { y, line ->
                    for (x in line.indices) {
                        if (line[x] == '#') {
                            livingCells.add(Cell(x, y))
                        }
                    }
                }
    }

    private fun nextGeneration(): Set<Cell> {
        val nextGeneration = mutableSetOf<Cell>()
        for (x in 0..4) {
            for (y in 0..4) {
                val cell = Cell(x, y)
                if (cell.isAlive()) {
                    if (cell.countNeighbours() == 1) {
                        nextGeneration.add(cell)
                    }
                } else {
                    if (cell.countNeighbours() in 1..2) {
                        nextGeneration.add(cell)
                    }
                }
            }
        }
        generations.add(livingCells)
        return nextGeneration.cutEdges()
    }

    private fun Cell.isAlive() = livingCells.contains(this)

    private fun Set<Cell>.cutEdges(): Set<Cell> = this.filter {
        it.x in 0..4 && it.y in 0..4
    }.toSet()

    private fun Cell.countNeighbours(): Int {
        return livingCells.intersect(listOf(
                Cell(x, y + 1),
                Cell(x, y - 1),
                Cell(x - 1, y),
                Cell(x + 1, y)
        )).size
    }

    fun generations() {
        var i = 0
        while (!generations.contains(livingCells)) {
            println("${i++}.")
            prettyPrint(livingCells)
            livingCells = nextGeneration().toMutableSet()
        }

        println("LAST GENERATION")
        prettyPrint(livingCells)
        val layOut = generations.first { it == livingCells }
        println("Generation ${generations.indexOf(layOut)}: ")
        prettyPrint(layOut)

        println("Bio diversity: ${bioDiversity(layOut)}")

    }

    private fun prettyPrint(layout: Set<Cell>) {
        for (y in 0..4) {
            var line = ""
            for (x in 0..4) {
                line += if (layout.contains(Cell(x, y))) "#" else "."
            }
            println(line)
        }
        println()
    }

    fun bioDiversity(layout: Set<Cell>): Long {
        val result = mutableListOf<Long>()

        for (x in 0..4) {
            for (y in 0..4) {
                if (Cell(x, y).isAlive()) {
                    val tileNumber = (y * 5) + x
                    result.add(2.0.pow(tileNumber.toDouble()).toLong())
                }
            }
        }
        return result.sum()
    }
}


data class Cell(val x: Int, val y: Int)

