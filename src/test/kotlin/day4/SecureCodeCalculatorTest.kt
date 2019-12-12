package day4

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class SecureCodeCalculatorTest {

    @Test
    fun `count of plain numbers should be correct`() {
        val secureCodeCalculator = SecureCodeCalculator()
        Assertions.assertThat(secureCodeCalculator.countCodeCandidates("145852", "616942")).isEqualTo(333)
    }
}