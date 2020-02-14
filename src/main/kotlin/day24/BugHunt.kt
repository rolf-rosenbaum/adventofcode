package day24

import kotlin.math.pow


fun main() {


    val world = World("eris.txt")

    world.generations2(200)


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

        for (layer in livingCells.minLayer()..livingCells.maxLayer()) {
            for (x in 0..4) {
                for (y in 0..4) {
                    if (x == 2 && y == 2) {
                        continue
                    }

                    val cell = Cell(x, y, layer)

                    val cellInner = Cell(x, y, layer - 1)
                    val neighboursInner = cellInner.countNeighbours()
                    if (cellInner.isAlive()) {
                        if (neighboursInner == 1) {
                            nextGeneration.add(cellInner)
                        }
                    } else {
                        if (neighboursInner in 1..2) {
                            nextGeneration.add(cellInner)
                        }
                    }

                    val neighbours = cell.countNeighbours()
                    if (cell.isAlive()) {
                        if (neighbours == 1) {
                            nextGeneration.add(cell)
                        }
                    } else {
                        if (neighbours in 1..2) {
                            nextGeneration.add(cell)
                        }
                    }

                    val cellOuter = Cell(x, y, layer + 1)
                    val neighboursOuter = cellOuter.countNeighbours()
                    if (cellOuter.isAlive()) {
                        if (neighboursOuter == 1) {
                            nextGeneration.add(cellOuter)
                        }
                    } else {
                        if (neighboursOuter in 1..2) {
                            nextGeneration.add(cellOuter)
                        }
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

    private fun Cell.countNeighboursForOuter(): Int {
        return 0
    }

    private fun Cell.countNeighboursForInner(): Int {
        return 0
    }

    private fun Cell.countNeighbours(): Int {


        val specialCells = listOf(Cell(2, 1, layer), Cell(1, 2, layer), Cell(2, 3, layer), Cell(3, 2, layer))
        var count = 0
        count += countInner() + countOuter()

        count += livingCells.intersect(
            listOf(
                Cell(x, y + 1, layer),
                Cell(x, y - 1, layer),
                Cell(x - 1, y, layer),
                Cell(x + 1, y, layer)
            )
        ).minus(Cell(2, 2, layer))
            .size

        return count

    }

    private fun Cell.countOuter(): Int {
        var count = 0
        if (y == 0) {
            count += livingCells.intersect(listOf(Cell(2, 1, layer - 1))).size

        } else
            if (y == 4) {
                count += livingCells.intersect(listOf(Cell(2, 3, layer - 1))).size

            }
        if (x == 0) {
            count += livingCells.intersect(listOf(Cell(1, 2, layer - 1))).size

        } else
            if (x == 4) {
                count += livingCells.intersect(listOf(Cell(3, 2, layer - 1))).size
            }
        return count
    }


    private fun Cell.countInner(): Int {
        var neighbours = mutableSetOf<Cell>()

        if (x == 2 && y == 1) {
            neighbours.addAll(
                livingCells.intersect(
                    listOf(
                        Cell(0, 0, layer + 1),
                        Cell(1, 0, layer + 1),
                        Cell(2, 0, layer + 1),
                        Cell(3, 0, layer + 1),
                        Cell(4, 0, layer + 1)
                    )
                )
            )
        } else if (x == 1 && y == 2) {
            neighbours.addAll(
                livingCells.intersect(
                    listOf(
                        Cell(0, 0, layer + 1),
                        Cell(0, 1, layer + 1),
                        Cell(0, 2, layer + 1),
                        Cell(0, 3, layer + 1),
                        Cell(0, 4, layer + 1)
                    )
                )
            )
        } else if (x == 2 && y == 3) {
            neighbours.addAll(
                livingCells.intersect(
                    listOf(
                        Cell(0, 4, layer + 1),
                        Cell(1, 4, layer + 1),
                        Cell(2, 4, layer + 1),
                        Cell(3, 4, layer + 1),
                        Cell(4, 4, layer + 1)
                    )
                )
            )
        } else if (x == 3 && y == 2) {
            neighbours.addAll(
                livingCells.intersect(
                    listOf(
                        Cell(4, 0, layer + 1),
                        Cell(4, 1, layer + 1),
                        Cell(4, 2, layer + 1),
                        Cell(4, 3, layer + 1),
                        Cell(4, 4, layer + 1)
                    )
                )
            )
        }
        return livingCells.intersect(neighbours).size

    }

    fun generations2(gen: Int) {

        prettyPrint(livingCells)
        repeat(gen) {
            livingCells = nextGeneration().toMutableSet()
            println("generation: $it" )
//            prettyPrint(livingCells)
//            println()
        }

        println("number of bugs: ${livingCells.size}")
        println("layers: ${livingCells.minBy { it.layer }?.layer} to ${livingCells.maxBy { it.layer }?.layer}")
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
        for (layer in layout.minLayer()..layout.maxLayer()) {
            println("Layer: $layer")
            for (y in 0..4) {
                var line = ""
                for (x in 0..4) {
                    line += if (layout.contains(Cell(x, y, layer))) "#" else "."
                }
                println(line)
            }
            println()
        }
    }

    private fun bioDiversity(layout: Set<Cell>): Long {

        return layout.fold(0) { sum: Long, cell ->
            val tileNumber = (cell.y * 5) + cell.x
            sum + 2.0.pow(tileNumber.toDouble()).toLong()
        }
    }
}

private fun Set<Cell>.minLayer() = this.minBy { it.layer }?.layer ?: 0
private fun Set<Cell>.maxLayer() = this.maxBy { it.layer }?.layer ?: 0

data class Cell(val x: Int, val y: Int, val layer: Int = 0)
