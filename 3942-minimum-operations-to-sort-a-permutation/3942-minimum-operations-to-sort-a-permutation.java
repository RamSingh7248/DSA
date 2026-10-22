class Solution {
    public int minOperations(int[] nums) {
        int n = nums.length;
        int k = nums[0];

        // Check ascending cyclic order
        for (int i = 0; i < n; i++) {
            if (nums[i] != (k + i) % n) {
                break;
            }
            if (i == n - 1) {
                return k == 0 ? 0 : Math.min(n - k, k + 2);
            }
        }

        // Check descending cyclic order
        int start = nums[0];

        for (int i = 0; i < n; i++) {
            if (nums[i] != (start - i + n) % n) {
                return -1;
            }
        }

        int k2 = (n - 1 - start + n) % n;
        return 1 + Math.min(k2, n - k2);
    }
}
