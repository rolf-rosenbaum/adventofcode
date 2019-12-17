package day2

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class IntCodeComputerTest {

    @Test
    fun foo() {

        val computer = IntCodeComputer("day2Input.txt")
        assertThat(computer.execute()).isEqualTo(listOf(2, 4, 4, 5, 99, 9801))
    }
}

