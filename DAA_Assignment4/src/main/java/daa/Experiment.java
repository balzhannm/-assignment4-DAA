package daa;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Experiment {
    private static final Random random = new Random(42);

    private static String randomString(int n) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < n; i++) s.append((char)('a' + random.nextInt(4)));
        return s.toString();
    }

    private static double medianTime(Runnable task) {
        double[] times = new double[3];
        for (int i = 0; i < 3; i++) {
            long start = System.nanoTime(); task.run();
            times[i] = (System.nanoTime() - start) / 1_000_000.0;
        }
        java.util.Arrays.sort(times);
        return times[1];
    }

    public static void main(String[] args) throws Exception {
        new File("results").mkdirs();
        try (PrintWriter out = new PrintWriter("results/results.csv")) {
            out.println("algorithm,input,n,time_ms,counter");
            int[] editSizes = {3, 4, 6, 12};
            for (int n : editSizes) {
                String a = randomString(n), b = randomString(n);
                addEdit(out, "Recursive", "random", n, a, b);
                addEdit(out, "Recursive", "different", n, "a".repeat(n), "b".repeat(n));
            }
            int[] dpSizes = {100, 200, 400, 800, 1200};
            for (int n : dpSizes) {
                String a = randomString(n), b = randomString(n);
                addEdit(out, "Table", "random", n, a, b);
                addEdit(out, "TwoRows", "random", n, a, b);
                addEdit(out, "Table", "different", n, "a".repeat(n), "b".repeat(n));
                addEdit(out, "TwoRows", "different", n, "a".repeat(n), "b".repeat(n));
            }
            int[] searchSizes = {1000, 2000, 4000, 8000, 12000};
            for (int n : searchSizes) {
                String bestText = "b".repeat(n);
                String bestPattern = "aaaaaaaa";
                String hardText = "a".repeat(n);
                String hardPattern = "aaaaaaab";
                addSearch(out, "Naive", "best_case", n, bestText, bestPattern);
                addSearch(out, "KMP", "best_case", n, bestText, bestPattern);
                addSearch(out, "Naive", "worst_case", n, hardText, hardPattern);
                addSearch(out, "KMP", "worst_case", n, hardText, hardPattern);
            }
        }
        System.out.println("Experiment completed. See results/results.csv");
    }


    private static void addEdit(PrintWriter out, String algorithm, String type, int n, String a, String b) {
        double time;
        long count;
        if (algorithm.equals("Recursive")) {
            time = medianTime(() -> EditDistance.recursive(a, b));
            EditDistance.recursive(a, b); count = EditDistance.recursiveCalls;
        } else if (algorithm.equals("Table")) {
            time = medianTime(() -> EditDistance.table(a, b));
            EditDistance.table(a, b); count = EditDistance.tableCells;
        } else {
            time = medianTime(() -> EditDistance.twoRows(a, b));
            EditDistance.twoRows(a, b); count = EditDistance.rowCells;
        }
        out.println(algorithm + "," + type + "," + n + "," + time + "," + count);
    }

    private static void addSearch(PrintWriter out, String algorithm, String type, int n, String text, String pattern) {
        double time;
        long count;
        if (algorithm.equals("Naive")) {
            time = medianTime(() -> StringSearch.naive(text, pattern));
            StringSearch.naive(text, pattern); count = StringSearch.naiveComparisons;
        } else {
            time = medianTime(() -> StringSearch.kmp(text, pattern));
            StringSearch.kmp(text, pattern); count = StringSearch.kmpComparisons;
        }
        out.println(algorithm + "," + type + "," + n + "," + time + "," + count);
    }
}
