import java.util.*;

class Solution {

    public long minOperations(int[] nums, int x, int k) {

        int n = nums.length;
        long[] cost = getWindowCosts(nums, x);

        long INF = Long.MAX_VALUE / 4;

        long[][] dp = new long[k + 1][n + 1];

        for (int i = 0; i <= k; i++) {
            Arrays.fill(dp[i], INF);
        }

        dp[0][0] = 0;

        for (int i = 0; i < n; i++) {

            for (int used = 0; used <= k; used++) {

                if (dp[used][i] == INF) {
                    continue;
                }

                dp[used][i + 1] =
                    Math.min(dp[used][i + 1], dp[used][i]);

                if (used < k && i + x <= n) {

                    dp[used + 1][i + x] =
                        Math.min(
                            dp[used + 1][i + x],
                            dp[used][i] + cost[i]
                        );
                }
            }
        }

        return dp[k][n];
    }

    private long[] getWindowCosts(int[] nums, int x) {

        int n = nums.length;

        long[] costs = new long[n - x + 1];

        TreeMap<Integer, Integer> left = new TreeMap<>();
        TreeMap<Integer, Integer> right = new TreeMap<>();

        int leftSize = 0, rightSize = 0;
        long leftSum = 0, rightSum = 0;

        for (int i = 0; i < n; i++) {

            int val = nums[i];

            if (left.isEmpty() || val <= left.lastKey()) {

                left.merge(val, 1, Integer::sum);
                leftSize++;
                leftSum += val;

            } else {

                right.merge(val, 1, Integer::sum);
                rightSize++;
                rightSum += val;
            }

            while (leftSize > rightSize + 1) {

                int median = left.lastKey();

                remove(left, median);

                leftSize--;
                leftSum -= median;

                right.merge(median, 1, Integer::sum);

                rightSize++;
                rightSum += median;
            }

            while (leftSize < rightSize) {

                int val2 = right.firstKey();

                remove(right, val2);

                rightSize--;
                rightSum -= val2;

                left.merge(val2, 1, Integer::sum);

                leftSize++;
                leftSum += val2;
            }

            if (i >= x) {

                int out = nums[i - x];

                if (left.containsKey(out)) {

                    remove(left, out);

                    leftSize--;
                    leftSum -= out;

                } else {

                    remove(right, out);

                    rightSize--;
                    rightSum -= out;
                }

                while (leftSize > rightSize + 1) {

                    int median = left.lastKey();

                    remove(left, median);

                    leftSize--;
                    leftSum -= median;

                    right.merge(median, 1, Integer::sum);

                    rightSize++;
                    rightSum += median;
                }

                while (leftSize < rightSize) {

                    int val2 = right.firstKey();

                    remove(right, val2);

                    rightSize--;
                    rightSum -= val2;

                    left.merge(val2, 1, Integer::sum);

                    leftSize++;
                    leftSum += val2;
                }
            }

            if (i >= x - 1) {

                int median = left.lastKey();

                long leftCost =
                    (long) median * leftSize - leftSum;

                long rightCost =
                    rightSum - (long) median * rightSize;

                costs[i - x + 1] = leftCost + rightCost;
            }
        }

        return costs;
    }

    private void remove(TreeMap<Integer, Integer> map, int val) {

        int count = map.get(val);

        if (count == 1) {
            map.remove(val);
        } else {
            map.put(val, count - 1);
        }
    }
}