package org.infoHandling.parser;

import org.infoHandling.entity.TextComponent;
import org.infoHandling.entity.TextComposite;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractTextParser implements Parser {
    protected static final Logger logger = LogManager.getLogger();
    protected Parser nextParser;

    @Override
    public void setNext(Parser next) {
        this.nextParser = next;
    }

    @Override
    public TextComponent parse(String text) {
        if (text == null || text.trim().isEmpty()) {
            return null;
        }

        logger.debug("{} processing: {}", this.getClass().getSimpleName(),
                text.length() > 50 ? text.substring(0, 50) + "..." : text);

        TextComponent result = parseComponent(text);

        if (nextParser != null && result instanceof TextComposite) {
            result = processChildren((TextComposite) result, text);
        }

        return result;
    }

    private TextComponent processChildren(TextComposite composite, String originalText) {
        TextComposite newComposite = new TextComposite(composite.getType());

        TextComponent processed = nextParser.parse(originalText);

        if (processed != null) {
            if (processed instanceof TextComposite) {
                for (TextComponent child : ((TextComposite) processed).getChildren()) {
                    newComposite.add(child);
                }
            } else {
                newComposite.add(processed);
            }
        }

        return newComposite;
    }

    protected abstract TextComponent parseComponent(String text);
}