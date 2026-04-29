package org.infoHandling.parser.impl;

import org.infoHandling.entity.TextComponent;
import org.infoHandling.entity.TextComposite;
import org.infoHandling.parser.Parser;

public class ParagraphParser implements Parser {
    private Parser next;

    @Override
    public void setNext(Parser next) { this.next = next; }

    @Override
    public TextComponent parse(String text) {
        TextComposite paragraph = new TextComposite(TextComponent.ComponentType.PARAGRAPH);
        String[] sentences = text.split("(?<=[.!?])\\s+(?=[A-ZА-Я\\d\"\'\\(])");

        for (String s : sentences) {
            if (!s.trim().isEmpty()) {
                if (next != null) {
                    TextComponent result = next.parse(s);
                    for (TextComponent child : result.getChildren()) {
                        paragraph.add(child);
                    }
                }
            }
        }
        return paragraph;
    }
}