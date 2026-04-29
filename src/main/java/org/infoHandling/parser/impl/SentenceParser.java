package org.infoHandling.parser.impl;

import org.infoHandling.entity.TextComponent;
import org.infoHandling.entity.TextComposite;
import org.infoHandling.parser.Parser;

public class SentenceParser implements Parser {
    private Parser next;

    @Override
    public void setNext(Parser next) { this.next = next; }

    @Override
    public TextComponent parse(String text) {
        TextComposite sentence = new TextComposite(TextComponent.ComponentType.SENTENCE);
        String[] words = text.trim().split("\\s+");

        for (String w : words) {
            if (!w.isEmpty()) {
                if (next != null) {
                    TextComponent result = next.parse(w);
                    for (TextComponent child : result.getChildren()) {
                        sentence.add(child);
                    }
                }
            }
        }
        return sentence;
    }
}