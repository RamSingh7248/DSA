class Solution {
    public long[] resultArray(int[] nums, int k) {
        long[] ans = new long[k];
        long[] dp = new long[k];

        for (int num : nums) {
            int val = num % k;
            long[] newDp = new long[k];

            newDp[val]++;

            for (int r = 0; r < k; r++) {
                int nr = (r * val) % k;
                newDp[nr] += dp[r];
            }

            for (int r = 0; r < k; r++) {
                ans[r] += newDp[r];
            }

            dp = newDp;
        }

        return ans;
    }
}