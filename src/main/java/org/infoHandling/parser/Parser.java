package org.infoHandling.parser;

import org.infoHandling.entity.TextComponent;

public interface Parser {
    TextComponent parse(String text);
    void setNext(Parser next);
}