package org.infoHandling.entity;

import java.util.List;

public class Symbol implements TextComponent {
    private final char value;
    private final boolean isLetter;

    public Symbol(char value) {
        this.value = value;
        this.isLetter = Character.isLetter(value);
    }

    public char getValue() { return value; }
    public boolean isLetter() { return isLetter; }

    @Override
    public String getContent() { return String.valueOf(value); }

    @Override
    public void add(TextComponent component) {}

    @Override
    public List<TextComponent> getChildren() { return List.of(); }

    @Override
    public ComponentType getType() { return ComponentType.SYMBOL; }
}