package game.words

import java.io.File

interface DictionaryService {

    fun isWord(str: String): Boolean

}

class DictionaryServiceFactory private constructor() {
    fun getDictionaryService() = getDictionaryService("")
    fun getDictionaryService(type: String): DictionaryService {
        return when (type) {
            "noop" -> NoOpDictionaryService()
            else -> DefaultDictionaryService()
        }
    }

    companion object {
        private var instance: DictionaryServiceFactory? = null
        fun getInstance(): DictionaryServiceFactory {
            if (instance == null) {
                instance = DictionaryServiceFactory()
            }
            return instance!!
        }
    }
}

private class NoOpDictionaryService : DictionaryService {
    override fun isWord(str: String): Boolean = true
}

private class DefaultDictionaryService : DictionaryService {
    private var words: MutableSet<String> = mutableSetOf()

    init {
        File("src/main/resources/words.txt").forEachLine { words.add(it.strip().lowercase()) }
    }

    override fun isWord(str: String): Boolean = words.contains(str)

}