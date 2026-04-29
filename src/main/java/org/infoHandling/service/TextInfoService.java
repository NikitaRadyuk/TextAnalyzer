package org.infoHandling.service;

import org.infoHandling.entity.*;

public class TextInfoService {

    public static int countLetters(TextComponent component) {
        if (component == null) return 0;

        int count = 0;

        if (component instanceof Symbol) {
            if (((Symbol) component).isLetter()) {
                count = 1;
            }
        } else if (component instanceof TextComposite) {
            for (TextComponent child : component.getChildren()) {
                count += countLetters(child);
            }
        }

        return count;
    }

    public static int countSymbols(TextComponent component) {
        if (component == null) return 0;

        int count = 0;

        if (component instanceof Symbol) {
            if (!((Symbol) component).isLetter()) {
                count = 1;
            }
        } else if (component instanceof TextComposite) {
            for (TextComponent child : component.getChildren()) {
                count += countSymbols(child);
            }
        }

        return count;
    }
}