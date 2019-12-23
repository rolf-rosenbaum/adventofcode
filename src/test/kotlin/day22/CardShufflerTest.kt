package day22

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CardShufflerTest {

    @Test
    internal fun `deal into new deck`() {
        val cardShuffler = CardShuffler(10)
        assertThat(cardShuffler.dealIntoNewStack()).isEqualTo((9 downTo 0).toList())
    }

    @Test
    internal fun `cut from top`() {
        val cardShuffler = CardShuffler(10)
        assertThat(cardShuffler.cut(4)).isEqualTo(listOf(4, 5, 6, 7, 8, 9, 0, 1, 2, 3))
        assertThat(cardShuffler.cut(-4)).isEqualTo(listOf(6, 7, 8, 9, 0, 1, 2, 3, 4, 5))
    }

    @Test
    internal fun `deal with increment`() {
        val cardShuffler = CardShuffler(10)
        assertThat(cardShuffler.dealWithIncrement(3)).isEqualTo(listOf(0, 7, 4, 1, 8, 5, 2, 9, 6, 3))
    }


}