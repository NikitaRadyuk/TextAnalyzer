package org.infoHandling.parser.impl;

import org.infoHandling.entity.Symbol;
import org.infoHandling.entity.TextComponent;
import org.infoHandling.entity.TextComposite;
import org.infoHandling.parser.Parser;

public class LexemeParser implements Parser {
    private Parser next;

    @Override
    public void setNext(Parser next) { this.next = next; }

    @Override
    public TextComponent parse(String text) {
        TextComposite lexeme = new TextComposite(TextComponent.ComponentType.LEXEME);
        for (char c : text.toCharArray()) {
            lexeme.add(new Symbol(c));
        }
        return lexeme;
    }
}