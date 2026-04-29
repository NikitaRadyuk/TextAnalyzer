package org.infoHandling.parser;

import org.infoHandling.parser.impl.LexemeParser;
import org.infoHandling.parser.impl.ParagraphParser;
import org.infoHandling.parser.impl.SentenceParser;

public class ParserTextChain {

    public static Parser buildChain() {
        Parser textParser = new TextParser();
        Parser paragraphParser = new ParagraphParser();
        Parser sentenceParser = new SentenceParser();
        Parser lexemeParser = new LexemeParser();

        textParser.setNext(paragraphParser);
        paragraphParser.setNext(sentenceParser);
        sentenceParser.setNext(lexemeParser);

        return textParser;
    }
}