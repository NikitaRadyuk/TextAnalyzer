package org.infoHandling.entity;

import java.util.List;

public interface TextComponent {
    String getContent();
    void add(TextComponent component);
    List<TextComponent> getChildren();
    ComponentType getType();

    enum ComponentType {
        TEXT, PARAGRAPH, SENTENCE, LEXEME, SYMBOL
    }
}