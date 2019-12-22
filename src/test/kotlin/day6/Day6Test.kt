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
    internal fun distance() {
        val calculator = OrbitCalculator("orbits.txt")
        assertThat(calculator.totalOrbits()).isEqualTo(42)
    }
}