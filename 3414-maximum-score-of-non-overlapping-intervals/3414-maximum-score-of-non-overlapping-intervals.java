import java.util.*;

class Solution {
    public int[] maximumWeight(List<List<Integer>> intervals) {
        int n = intervals.size();

        int[][] a = new int[n][4];

        for (int i = 0; i < n; i++) {
            a[i][0] = intervals.get(i).get(0);
            a[i][1] = intervals.get(i).get(1);
            a[i][2] = intervals.get(i).get(2);
            a[i][3] = i;
        }

        Arrays.sort(a, (x, y) -> {
            if (x[0] != y[0]) return Integer.compare(x[0], y[0]);
            return Integer.compare(x[3], y[3]);
        });

        int[] starts = new int[n];
        for (int i = 0; i < n; i++) {
            starts[i] = a[i][0];
        }

        int[][] next = new int[n][5];

        for (int i = 0; i < n; i++) {
            int lo = i + 1, hi = n;

            while (lo < hi) {
                int mid = lo + (hi - lo) / 2;

                if (starts[mid] > a[i][1])
                    hi = mid;
                else
                    lo = mid + 1;
            }

            next[i][0] = lo;
        }

        long[][] dp = new long[n + 1][5];
        List<Integer>[][] best = new ArrayList[n + 1][5];

        for (int i = 0; i <= n; i++) {
            for (int k = 0; k <= 4; k++) {
                best[i][k] = new ArrayList<>();
            }
        }

        for (int i = n - 1; i >= 0; i--) {
            for (int k = 0; k <= 4; k++) {
                dp[i][k] = dp[i + 1][k];
                best[i][k] = new ArrayList<>(best[i + 1][k]);

                if (k < 4) {
                    int j = next[i][0];

                    long takeScore = a[i][2] + dp[j][k + 1];

                    List<Integer> takeList =
                            new ArrayList<>(best[j][k + 1]);

                    takeList.add(a[i][3]);
                    Collections.sort(takeList);

                    if (takeScore > dp[i][k] ||
                        (takeScore == dp[i][k] &&
                         lexicographicallySmaller(takeList, best[i][k]))) {

                        dp[i][k] = takeScore;
                        best[i][k] = takeList;
                    }
                }
            }
        }

        return best[0][0].stream()
                .mapToInt(Integer::intValue)
                .toArray();
    }

    private boolean lexicographicallySmaller(
            List<Integer> a, List<Integer> b) {

        int n = Math.min(a.size(), b.size());

        for (int i = 0; i < n; i++) {
            if (!a.get(i).equals(b.get(i))) {
                return a.get(i) < b.get(i);
            }
        }

        return a.size() < b.size();
    }
}