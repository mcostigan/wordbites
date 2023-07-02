import java.io.File

fun main() {

    val file = File("./words.txt")
    val chars: MutableMap<Char, Int> = ('a'..'z').associateWith { 0 }.toMutableMap()
    val pairs: MutableMap<Pair<Char, Char>, Int> = mutableMapOf()

    var totalChars: Float = 0F
    var totalPairs: Float = 0F
    file.forEachLine { it ->
        val word = it.strip().lowercase().toCharArray()
        if (word.size >= 3) {
            var badChars = false
            for (char in word) {
                if (!chars.contains(char)) {
                    badChars = true
                    break
                }
            }
            if (!badChars) {
                word.forEach { c ->
                    chars[c] = chars[c]!! + 1
                    totalChars++
                }

                word.forEachIndexed { i: Int, c: Char ->
                    try {
                        val nextChar = word[i + 1]
                        pairs[Pair(c, nextChar)] = (pairs[Pair(c, nextChar)] ?: 0) + 1
                        totalPairs++
                    } catch (e: Exception) {

                    }

                }
            }

        }

    }

    val charFrequency = chars.map { e -> e.key to e.value.toFloat() / totalChars }
    val pairFrequency = pairs.map { e -> e.key to e.value.toFloat() / totalPairs }
    charFrequency.forEach { println(it) }
    pairFrequency.forEach { println(it) }
}