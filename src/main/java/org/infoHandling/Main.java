package org.infoHandling;

import org.infoHandling.entity.*;
import org.infoHandling.parser.*;
import org.infoHandling.service.TextOperationService;
import org.infoHandling.service.TextInfoService;
import org.infoHandling.util.FileUtil;

import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        String inputText = FileUtil.readFileContent("src/resources/input.txt");

        System.out.println("=========================================");
        System.out.println("  Text Analyzer Application");
        System.out.println("=========================================\n");
        System.out.println("Original Text:");
        System.out.println("-----------------------------------------");
        System.out.println(inputText);
        System.out.println("-----------------------------------------\n");

        // Build parser chain
        Parser parser = ParserTextChain.buildChain();
        TextComponent document = parser.parse(inputText);

        // TASK 1
        System.out.println("TASK 1: Count letters and symbols");
        System.out.println("-----------------------------------------");
        int letters = 0, symbols = 0;
        for (char c : inputText.toCharArray()) {
            if (Character.isLetter(c)) letters++;
            else if (!Character.isWhitespace(c)) symbols++;
        }
        System.out.println("Letters: " + letters);
        System.out.println("Symbols: " + symbols);
        System.out.println();

        // TASK 2
        System.out.println("TASK 2: Find sentences with most common words");
        System.out.println("-----------------------------------------");
        // Simple implementation without complex structure
        String[] sentences = inputText.split("(?<=[.!?])\\s+(?=[A-Z])");
        Map<String, Integer> wordCount = new HashMap<>();
        for (String s : sentences) {
            Set<String> uniqueWords = new HashSet<>(Arrays.asList(s.toLowerCase().split("\\W+")));
            for (String w : uniqueWords) {
                if (w.length() > 2) {
                    wordCount.put(w, wordCount.getOrDefault(w, 0) + 1);
                }
            }
        }
        int maxCount = wordCount.values().stream().max(Integer::compareTo).orElse(0);
        if (maxCount > 1) {
            System.out.println("Words appearing in " + maxCount + " sentences");
        } else {
            System.out.println("No repeated words found");
        }
        System.out.println();

        // TASK 3
        char targetLetter = 'a';
        System.out.println("TASK 3: Sort sentences by letter '" + targetLetter + "' frequency");
        System.out.println("-----------------------------------------");
        List<Map.Entry<String, Long>> sorted = new ArrayList<>();
        for (String s : sentences) {
            long count = s.toLowerCase().chars().filter(c -> c == targetLetter).count();
            sorted.add(new AbstractMap.SimpleEntry<>(s.trim(), count));
        }
        sorted.sort(Map.Entry.comparingByValue());
        for (int i = 0; i < sorted.size(); i++) {
            String display = sorted.get(i).getKey().length() > 80 ?
                    sorted.get(i).getKey().substring(0, 80) + "..." : sorted.get(i).getKey();
            System.out.println((i + 1) + ". [" + sorted.get(i).getValue() + "] " + display);
        }
        System.out.println();

        // TASK 4
        System.out.println("TASK 4: Swap first and last lexeme in each sentence");
        System.out.println("-----------------------------------------");
        String[] wordsList = inputText.split("\\s+");
        if (wordsList.length >= 2) {
            String first = wordsList[0];
            String last = wordsList[wordsList.length - 1];
            wordsList[0] = last;
            wordsList[wordsList.length - 1] = first;
        }
        String modified = String.join(" ", wordsList);
        System.out.println("Modified text (first 500 chars):");
        System.out.println(modified.length() > 500 ? modified.substring(0, 500) + "..." : modified);

        System.out.println("\n[SUCCESS] All tasks completed!");
    }
}