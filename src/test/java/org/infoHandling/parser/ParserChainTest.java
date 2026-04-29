package org.infoHandling.parser;

import org.infoHandling.entity.TextComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ParserChainTest {

    private Parser parser;

    @BeforeEach
    void setUp() {
        parser = ParserTextChain.buildChain();
    }

    @Test
    void testParseWithParagraphs() {
        String text = "First paragraph.\n\nSecond paragraph.";
        TextComponent result = parser.parse(text);

        assertNotNull(result);
        assertEquals(2, result.getChildren().size());
    }

    @Test
    void testParseSentenceLexemes() {
        String text = "Hello world. This is a test.";
        TextComponent result = parser.parse(text);

        assertNotNull(result);

        // Get first paragraph, first sentence
        TextComponent paragraph = result.getChildren().get(0);
        TextComponent sentence = paragraph.getChildren().get(0);

        // Sentence should have 2 lexemes: "Hello" and "world."
        assertEquals(2, sentence.getChildren().size(),
                "First sentence should have 2 lexemes, but got " + sentence.getChildren().size());

        // Check first lexeme content
        TextComponent firstLexeme = sentence.getChildren().get(0);
        assertEquals("Hello", firstLexeme.getContent());

        // Check second lexeme content
        TextComponent secondLexeme = sentence.getChildren().get(1);
        assertEquals("world.", secondLexeme.getContent());
    }

    @Test
    void testLexemeWithPunctuation() {
        String text = "Hello!";
        TextComponent result = parser.parse(text);

        TextComponent paragraph = result.getChildren().get(0);
        TextComponent sentence = paragraph.getChildren().get(0);
        TextComponent lexeme = sentence.getChildren().get(0);

        // Lexeme "Hello!" should have 5 symbols: 'H','e','l','l','o','!'
        assertEquals(6, lexeme.getChildren().size());
    }

    @Test
    void testMultipleSentences() {
        String text = "First sentence. Second sentence! Third?";
        TextComponent result = parser.parse(text);

        TextComponent paragraph = result.getChildren().get(0);

        // Should have 3 sentences
        assertEquals(3, paragraph.getChildren().size());
    }

    @Test
    void testPreserveOriginalText() {
        String original = "Hello world. This is a test.";
        TextComponent result = parser.parse(original);

        // The reconstructed text should match the original
        assertEquals(original, result.getContent());
    }
}