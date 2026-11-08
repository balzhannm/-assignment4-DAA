package daa;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

class Assignment4Test {
    @Test void editDistanceExamples() {
        assertEquals(3, EditDistance.table("kitten", "sitting"));
        assertEquals(2, EditDistance.table("flaw", "lawn"));
        assertEquals(5, EditDistance.table("intention", "execution"));
        assertEquals(3, EditDistance.table("", "abc"));
        assertEquals(0, EditDistance.table("abc", "abc"));
    }
    @Test void allEditVersionsAgreeOn100RandomInputs() {
        Random random = new Random(42);
        for (int k = 0; k < 100; k++) {
            String a = randomWord(random, random.nextInt(6));
            String b = randomWord(random, random.nextInt(6));
            int expected = EditDistance.table(a, b);
            assertEquals(expected, EditDistance.twoRows(a, b));
            assertEquals(expected, EditDistance.recursive(a, b));
        }
    }
    private String randomWord(Random random, int length) {
        StringBuilder word = new StringBuilder();
        for (int i = 0; i < length; i++) word.append((char)('A' + random.nextInt(3)));
        return word.toString();
    }
    @Test void lpsExamples() {
        assertArrayEquals(new int[]{0,1,2,0,1,2,3,3}, StringSearch.lps("AAACAAAA"));
        assertArrayEquals(new int[]{0,0,1,2,0,1,2,3,4}, StringSearch.lps("ABABCABAB"));
    }
    @Test void searchExamplesAndOverlap() {
        assertEquals(List.of(10), StringSearch.kmp("ABABDABACDABABCABAB", "ABABCABAB"));
        assertEquals(List.of(0,1,2,3), StringSearch.kmp("AAAAA", "AA"));
        assertEquals(List.of(0,2,4), StringSearch.naive("ABABABA", "ABA"));
    }
    @Test void searchEdgeCases() {
        assertThrows(IllegalArgumentException.class, () -> StringSearch.kmp("abc", ""));
        assertEquals(List.of(), StringSearch.naive("a", "long"));
    }
    @Test void naiveAndKmpAgreeOn100RandomTexts() {
        Random random = new Random(42);
        for (int i = 0; i < 100; i++) {
            String text = randomWord(random, 5 + random.nextInt(15));
            String pattern = randomWord(random, 1 + random.nextInt(5));
            assertEquals(StringSearch.naive(text, pattern), StringSearch.kmp(text, pattern));
        }
    }

}
