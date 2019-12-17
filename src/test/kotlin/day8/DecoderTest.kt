package day8

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class DecoderTest {

    @Test
    internal fun `test toLayers`() {
        val data = javaClass.classLoader.getResource("data.txt").readText()
        assertThat(data.length).isEqualTo(15000)

        val decoder = Decoder(25, 6)
        val message = decoder.findLayerWithFewestZeroes(data)
        println(message)
        println(message.count { it == '2' } * message.count { it == '1' })

        val result = decoder.decode(data)
        println("-".repeat(25))
        decoder.prettyPrintLayer(result)
        print(result)

    }

    @Test
    fun check() {
        val data = "022221201221021001"

        val decoder = Decoder(3, 2)

        assertThat(decoder.decode(data)).isEqualTo("001001")
    }


}