package org.infoHandling.entity;

import java.util.*;

public class TextComposite implements TextComponent {
    private final ComponentType type;
    private final List<TextComponent> children;
    private String cachedContent;

    public TextComposite(ComponentType type) {
        this.type = type;
        this.children = new ArrayList<>();
        this.cachedContent = null;
    }

    @Override
    public String getContent() {
        if (cachedContent != null) {
            return cachedContent;
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < children.size(); i++) {
            TextComponent child = children.get(i);
            String childContent = child.getContent();

            if (childContent == null || childContent.isEmpty()) {
                continue;
            }

            sb.append(childContent);

            if (type == ComponentType.SENTENCE && i < children.size() - 1) {
                sb.append(" ");
            } else if (type == ComponentType.PARAGRAPH && i < children.size() - 1) {
                sb.append(" ");
            } else if (type == ComponentType.TEXT && i < children.size() - 1) {
                sb.append("\n\n");
            }
        }

        cachedContent = sb.toString();
        return cachedContent;
    }

    @Override
    public void add(TextComponent component) {
        children.add(component);
        cachedContent = null;
    }

    @Override
    public List<TextComponent> getChildren() {
        return new ArrayList<>(children);
    }

    @Override
    public ComponentType getType() {
        return type;
    }

    public List<TextComposite> getSentences() {
        List<TextComposite> result = new ArrayList<>();

        if (type == ComponentType.SENTENCE) {
            result.add(this);
        } else {
            for (TextComponent child : children) {
                if (child instanceof TextComposite) {
                    result.addAll(((TextComposite) child).getSentences());
                }
            }
        }

        return result;
    }

    public List<TextComposite> getLexemes() {
        List<TextComposite> result = new ArrayList<>();

        if (type == ComponentType.LEXEME) {
            result.add(this);
        } else {
            for (TextComponent child : children) {
                if (child instanceof TextComposite) {
                    result.addAll(((TextComposite) child).getLexemes());
                }
            }
        }

        return result;
    }

    public List<TextComposite> getWords() {
        List<TextComposite> result = new ArrayList<>();

        if (type == ComponentType.LEXEME) {
            boolean isWord = true;
            for (TextComponent child : children) {
                if (child instanceof Symbol && !((Symbol) child).isLetter()) {
                    isWord = false;
                    break;
                }
            }
            if (isWord) {
                result.add(this);
            }
        } else {
            for (TextComponent child : children) {
                if (child instanceof TextComposite) {
                    result.addAll(((TextComposite) child).getWords());
                }
            }
        }

        return result;
    }

    public long countLetter(char letter) {
        long count = 0;
        for (TextComponent child : children) {
            if (child instanceof Symbol) {
                if (Character.toLowerCase(((Symbol) child).getValue()) == Character.toLowerCase(letter)) {
                    count++;
                }
            } else if (child instanceof TextComposite) {
                count += ((TextComposite) child).countLetter(letter);
            }
        }
        return count;
    }

    public void swapFirstAndLastLexeme() {
        if (type != ComponentType.SENTENCE) return;

        List<TextComponent> lexemes = new ArrayList<>();
        for (TextComponent child : children) {
            if (child instanceof TextComposite && ((TextComposite) child).getType() == ComponentType.LEXEME) {
                lexemes.add(child);
            }
        }

        if (lexemes.size() >= 2) {
            int firstIdx = children.indexOf(lexemes.get(0));
            int lastIdx = children.indexOf(lexemes.get(lexemes.size() - 1));
            Collections.swap(children, firstIdx, lastIdx);
            cachedContent = null;
        }
    }
}