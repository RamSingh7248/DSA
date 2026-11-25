
import java.util.*;

class Solution {

    public int smallestUniqueSubarray(int[] nums) {

        int n = nums.length;

        long MOD1 = 1000000007;
        long MOD2 = 1000000009;

        long BASE1 = 100019;
        long BASE2 = 100043;

        int left = 1;
        int right = n;

        while (left < right) {

            int mid = left + (right - left) / 2;

            if (check(nums, mid, BASE1, BASE2, MOD1, MOD2)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

    private boolean check(int[] nums, int len,
                          long b1, long b2,
                          long m1, long m2) {

        int n = nums.length;

        long p1 = 1;
        long p2 = 1;

        for (int i = 0; i < len; i++) {
            p1 = (p1 * b1) % m1;
            p2 = (p2 * b2) % m2;
        }

        long h1 = 0;
        long h2 = 0;

        Map<String, Integer> map = new HashMap<>();

        for (int i = 0; i < n; i++) {

            h1 = (h1 * b1 + nums[i]) % m1;
            h2 = (h2 * b2 + nums[i]) % m2;

            if (i >= len) {

                h1 = (h1 - (p1 * nums[i - len]) % m1 + m1) % m1;
                h2 = (h2 - (p2 * nums[i - len]) % m2 + m2) % m2;
            }

            if (i >= len - 1) {

                String key = h1 + "," + h2;

                map.put(key, map.getOrDefault(key, 0) + 1);
            }
        }

        for (int count : map.values()) {
            if (count == 1) {
                return true;
            }
        }

        return false;
    }
}
