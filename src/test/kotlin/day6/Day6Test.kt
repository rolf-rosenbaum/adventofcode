package day6

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day6Test {

    @Test
    internal fun `find center`() {
        val calculator = OrbitCalculator("orbits.txt")

        assertThat(calculator.center()).isEqualTo("COM")
    }

    @Test
    internal fun totalOrbits() {
        val calculator = OrbitCalculator("orbits.txt")
        assertThat(calculator.totalOrbits()).isEqualTo(42)
    }

    @Test
    internal fun pathToCenter() {
        val calculator = OrbitCalculator("orbits.txt")
        assertThat(calculator.pathToCenterFrom("C" to "B")).isEqualTo(listOf("B"))
        assertThat(calculator.pathToCenterFrom("I" to "D")).isEqualTo(listOf("D", "C", "B"))
    }

    @Test
    internal fun closestCommonNode() {
        val calculator = OrbitCalculator("orbits.txt")
        assertThat(calculator.closestCommonNode("J", "I")).isEqualTo("D")
    }

    @Test
    internal fun `orbital transfers from YOU to SAN`() {
        val calculator = OrbitCalculator("orbits_full.txt")
        assertThat(calculator.closestCommonNode("YOU", "SAN")).isEqualTo("D")
        assertThat(calculator.orbitalTransfers("YOU", "SAN")).isEqualTo(4)
    }

}