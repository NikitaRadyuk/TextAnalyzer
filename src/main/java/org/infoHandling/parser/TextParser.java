package org.infoHandling.parser;

import org.infoHandling.entity.TextComponent;
import org.infoHandling.entity.TextComposite;

public class TextParser implements Parser {
    private Parser next;

    @Override
    public void setNext(Parser next) { this.next = next; }

    @Override
    public TextComponent parse(String text) {
        TextComposite document = new TextComposite(TextComponent.ComponentType.TEXT);
        String[] paragraphs = text.split("\\n\\s*\\n|\\r\\n\\s*\\r\\n");

        for (String p : paragraphs) {
            if (!p.trim().isEmpty()) {
                TextComposite paragraph = new TextComposite(TextComponent.ComponentType.PARAGRAPH);
                if (next != null) {
                    TextComponent result = next.parse(p);
                    for (TextComponent child : result.getChildren()) {
                        paragraph.add(child);
                    }
                }
                document.add(paragraph);
            }
        }
        return document;
    }
}