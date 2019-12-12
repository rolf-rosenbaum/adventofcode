package day4

class SecureCodeCalculator {
    fun countCodeCandidates(start: String, end: String): Int {

        return (start.toInt()..end.toInt()).count {
            isCandidate(it.toSixDigitString())
        }
    }

    private fun isCandidate(number: String): Boolean {
        var previousDigit = 0
        val sames = mutableMapOf<Int, Int>()

        number.forEach {
            if (it.toInt() < previousDigit) {
                return false
            }
            if (it.toInt() == previousDigit) {
                sames[it.toInt()] = (sames[it.toInt()]?: 1) + 1
            }
            previousDigit = it.toInt()
        }
        return sames.values.contains(2)
    }
}

private fun Int.toSixDigitString(): String = this.toString().padStart(6, '0')

