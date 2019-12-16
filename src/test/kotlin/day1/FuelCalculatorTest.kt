package day1

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class FuelCalculatorTest {

    @Test
    internal fun `should calculate fuel for one module correctly`() {
        val fuelCalculator = FuelCalculator("test.txt")
        assertThat(fuelCalculator.calculateFuelForModule(100756)).isEqualTo(33583)
    }
}

