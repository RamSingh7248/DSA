class Solution {
    public int distinctSubseqII(String s) {

        long[] dp = new long[26];

        long total = 0;
        long MOD = 1000000007;

        for (char ch : s.toCharArray()) {

            int index = ch - 'a';

            long newSub = (total + 1) % MOD;

            total = (total + newSub - dp[index] + MOD) % MOD;

            dp[index] = newSub;
        }

        return (int) total;
    }
}