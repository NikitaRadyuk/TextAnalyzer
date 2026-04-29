package org.infoHandling.service;

import org.infoHandling.entity.*;
import org.infoHandling.parser.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TextInfoServiceTest {
    private TextComponent document;

    @BeforeEach
    void setUp() {
        String text = "Hello world. Hello again. This is a test.";
        Parser parser = ParserTextChain.buildChain();
        document = parser.parse(text);
    }

    @Test
    void testFindSentencesWithMostCommonWords() {
        var result = TextOperationService.findSentencesWithMostCommonWords(document);
        assertNotNull(result);
    }

    @Test
    void testSortSentencesByLetterFrequency() {
        var result = TextOperationService.sortSentencesByLetterFrequency(document, 'e');
        assertNotNull(result);
    }

    @Test
    void testSwapFirstAndLastLexeme() {
        TextOperationService.swapFirstAndLastLexemeInAllSentences(document);
        assertNotNull(document.getContent());
    }
}