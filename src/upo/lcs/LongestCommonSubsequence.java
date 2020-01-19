package upo.lcs;

import java.util.Arrays;

public class LongestCommonSubsequence {
    public static char[] lcss(char[] a, char[] b) {
        int n = a.length;
        int m = b.length;
        char[][] lcs = new char[n + 1][m + 1];
        int[][] l = new int[n + 1][m + 1];
        for (int i = 1; i <= n; i++)
            for (int j = 1; j <= m; j++) {
                if (a[i - 1] == b[j - 1]) {
                    lcs[i][j] = a[i - 1];
                    l[i][j] = l[i][j - 1] + 1;
                } else if (l[i][j - 1] < l[i - 1][j])
                    l[i][j] = l[i - 1][j];
                else
                    l[i][j] = l[i][j - 1];
            }
        char[] ren = new char[l[n][m]];
        for (int k = l[n][m], i = n, j = m; k > 0; ) {
            if (l[i][j] <= l[i][j - 1] && l[i - 1][j] < l[i][j - 1]) {
                j--;
            } else if (l[i][j] <= l[i - 1][j]) {
                i--;
            } else {
                ren[k - 1] = lcs[i][j];
                k--;
                j--;
                i--;
            }
        }
        return ren;
    }

    public static void main(final String[] args) {
        String s1 = "AGGTAB";
        String s2 = "GXTXAYB";

        char[] X = s1.toCharArray();
        char[] Y = s2.toCharArray();
        System.out.println(X);
        System.out.println(Y);

        System.out.println(Arrays.toString(LongestCommonSubsequence.lcss(X, Y)));
    }
}