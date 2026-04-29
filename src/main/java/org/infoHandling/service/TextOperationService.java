package org.infoHandling.service;

import org.infoHandling.entity.*;
import java.util.*;

public class TextOperationService {

    public static List<TextComposite> findSentencesWithMostCommonWords(TextComponent text) {
        if (!(text instanceof TextComposite)) return List.of();

        TextComposite document = (TextComposite) text;
        List<TextComposite> sentences = document.getSentences();

        if (sentences.isEmpty()) return List.of();

        Map<String, Set<Integer>> wordToSentences = new HashMap<>();

        for (int i = 0; i < sentences.size(); i++) {
            Set<String> uniqueWords = new HashSet<>();
            for (TextComposite word : sentences.get(i).getWords()) {
                String wordText = word.getContent().toLowerCase();
                if (wordText.length() > 1 && wordText.matches("[a-z]+")) {
                    uniqueWords.add(wordText);
                }
            }
            for (String word : uniqueWords) {
                wordToSentences.computeIfAbsent(word, k -> new HashSet<>()).add(i);
            }
        }

        int maxSentences = wordToSentences.values().stream()
                .mapToInt(Set::size)
                .max()
                .orElse(0);

        if (maxSentences <= 1) return List.of();

        Set<Integer> selectedIndices = new HashSet<>();
        for (Map.Entry<String, Set<Integer>> entry : wordToSentences.entrySet()) {
            if (entry.getValue().size() == maxSentences) {
                selectedIndices.addAll(entry.getValue());
                System.out.println("Most common word: '" + entry.getKey() +
                        "' appears in " + maxSentences + " sentences");
            }
        }

        List<TextComposite> result = new ArrayList<>();
        for (int index : selectedIndices) {
            result.add(sentences.get(index));
        }

        return result;
    }

    public static List<TextComposite> sortSentencesByLetterFrequency(TextComponent text, char letter) {
        if (!(text instanceof TextComposite)) return List.of();

        TextComposite document = (TextComposite) text;
        List<TextComposite> sentences = new ArrayList<>(document.getSentences());

        sentences.sort(Comparator.comparingLong(s -> s.countLetter(letter)));

        return sentences;
    }

    public static void swapFirstAndLastLexemeInAllSentences(TextComponent text) {
        if (!(text instanceof TextComposite)) return;

        TextComposite document = (TextComposite) text;
        for (TextComposite sentence : document.getSentences()) {
            sentence.swapFirstAndLastLexeme();
        }
        System.out.println("Swapped first and last lexeme in all sentences");
    }
}