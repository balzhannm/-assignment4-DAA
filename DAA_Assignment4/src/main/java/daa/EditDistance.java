package daa;

public class EditDistance {
    public static long recursiveCalls;
    public static long tableCells;
    public static long rowCells;

    // State: distance between first i and j characters.
    // Transition: minimum of delete, insert and replace/match.
    // Base case: distance from an empty string is the other string's length.
    public static int recursive(String a, String b) {
        recursiveCalls = 0;
        return recursiveHelper(a, b, a.length(), b.length());
    }

    private static int recursiveHelper(String a, String b, int i, int j) {
        recursiveCalls++;
        if (i == 0) return j;
        if (j == 0) return i;
        int cost = (a.charAt(i - 1) == b.charAt(j - 1)) ? 0 : 1;
        int delete = recursiveHelper(a, b, i - 1, j) + 1;
        int insert = recursiveHelper(a, b, i, j - 1) + 1;
        int replace = recursiveHelper(a, b, i - 1, j - 1) + cost;
        return Math.min(delete, Math.min(insert, replace));
    }

    // State: dp[i][j] is the distance between prefixes of length i and j.
    // Transition: minimum of delete, insert and replace/match.
    // Base case: first row and column contain prefix lengths.
    public static int table(String a, String b) {
        int n = a.length(), m = b.length();
        int[][] dp = new int[n + 1][m + 1];
        tableCells = 0;
        for (int i = 0; i <= n; i++) dp[i][0] = i;
        for (int j = 0; j <= m; j++) dp[0][j] = j;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                int cost = a.charAt(i - 1) == b.charAt(j - 1) ? 0 : 1;
                dp[i][j] = Math.min(dp[i - 1][j] + 1,
                        Math.min(dp[i][j - 1] + 1, dp[i - 1][j - 1] + cost));
                tableCells++;
            }
        }
        return dp[n][m];
    }

    // State: previous[j] and current[j] store two neighboring DP rows.
    // Transition: same minimum as the full table.
    // Base case: previous row starts with values 0..m.
    public static int twoRows(String a, String b) {
        int n = a.length(), m = b.length();
        int[] previous = new int[m + 1];
        int[] current = new int[m + 1];
        rowCells = 0;
        for (int j = 0; j <= m; j++) previous[j] = j;
        for (int i = 1; i <= n; i++) {
            current[0] = i;
            for (int j = 1; j <= m; j++) {
                int cost = a.charAt(i - 1) == b.charAt(j - 1) ? 0 : 1;
                current[j] = Math.min(previous[j] + 1,
                        Math.min(current[j - 1] + 1, previous[j - 1] + cost));
                rowCells++;
            }
            int[] temp = previous; previous = current; current = temp;
        }
        return previous[m];
    }
}
