package day8

class Decoder (val width: Int, val height: Int) {



    internal fun String.toLayers(width: Int, height: Int): List<String> {
        var index = 0
        var layers = mutableListOf<String>()
        while (index < length) {
            layers.add(substring(index, index + width * height))
            index += width * height
        }
        layers.forEach {
             prettyPrintLayer(it)
        }
        return layers
    }

    fun findLayerWithFewestZeroes(data: String): String {
        return data.toLayers(width, height).minBy { it.count { c -> c == '0' } }!!
    }

    fun decode(data: String): String {
        val result = "2".repeat(width * height).toCharArray()
        val layers = data.toLayers(width, height)


        for (i in 0 until (width * height)) {
            for (layer in layers) {
                if (result[i] == '2') {
                    result[i] = layer[i]
                }
            }
        }
        return String(result)
    }

    fun prettyPrintLayer(layer: String) {
        for (i in 0 until height) {
            println(layer.substring(i * width, (i+1) * width).replace('1', '#').replace('0', ' '))
        }
        println()
    }

}