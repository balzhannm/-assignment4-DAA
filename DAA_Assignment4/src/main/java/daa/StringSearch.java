package daa;

import java.util.ArrayList;
import java.util.List;

public class StringSearch {
    public static long naiveComparisons;
    public static long kmpComparisons;

    public static List<Integer> naive(String text, String pattern) {
        if (pattern.isEmpty()) throw new IllegalArgumentException("Pattern cannot be empty");
        List<Integer> result = new ArrayList<>();
        naiveComparisons = 0;
        if (pattern.length() > text.length()) return result;
        for (int i = 0; i <= text.length() - pattern.length(); i++) {
            int j = 0;
            while (j < pattern.length()) {
                naiveComparisons++;
                if (text.charAt(i + j) != pattern.charAt(j)) break;
                j++;
            }
            if (j == pattern.length()) result.add(i);
        }
        return result;
    }

    public static int[] lps(String pattern) {
        if (pattern.isEmpty()) throw new IllegalArgumentException("Pattern cannot be empty");
        int[] lps = new int[pattern.length()];
        int length = 0, i = 1;
        while (i < pattern.length()) {
            if (pattern.charAt(i) == pattern.charAt(length)) {
                length++;
                lps[i] = length;
                i++;
            } else if (length != 0) {
                length = lps[length - 1];
            } else {
                lps[i] = 0;
                i++;
            }
        }
        return lps;
    }

    public static List<Integer> kmp(String text, String pattern) {
        if (pattern.isEmpty()) throw new IllegalArgumentException("Pattern cannot be empty");
        List<Integer> result = new ArrayList<>();
        kmpComparisons = 0;
        if (pattern.length() > text.length()) return result;
        int[] prefix = lps(pattern);
        int i = 0, j = 0;
        while (i < text.length()) {
            kmpComparisons++;
            if (text.charAt(i) == pattern.charAt(j)) {
                i++; j++;
                if (j == pattern.length()) {
                    result.add(i - j);
                    j = prefix[j - 1];
                }
            } else if (j != 0) {
                j = prefix[j - 1];
            } else {
                i++;
            }
        }
        return result;
    }
}
