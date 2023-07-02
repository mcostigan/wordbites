package game.words

import org.junit.jupiter.api.Test

internal class DefaultDictionaryServiceTest {

    private var dictionaryService: DictionaryService =
        DictionaryServiceFactory.getInstance().getDictionaryService("default")

    @Test
    fun isWord() {
        assert(dictionaryService.isWord("test"))
        assert(!dictionaryService.isWord("notaword"))
    }
}