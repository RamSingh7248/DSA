
class Solution {
    private static final long MOD = 1_000_000_007L;

    public int sumCounts(int[] nums) {
        int n = nums.length;
        int[] last = new int[100001];
        java.util.Arrays.fill(last, -1);

        SegmentTree tree = new SegmentTree(n);
        long ans = 0;

        for (int r = 0; r < n; r++) {
            int l = last[nums[r]] + 1;

            tree.update(1, 0, n - 1, l, r);

            last[nums[r]] = r;
            ans = (ans + tree.squareSum[1]) % MOD;
        }

        return (int) ans;
    }

    static class SegmentTree {
        long[] sum, squareSum, lazy;
        int n;

        SegmentTree(int n) {
            this.n = n;
            sum = new long[4 * n];
            squareSum = new long[4 * n];
            lazy = new long[4 * n];
        }

        void apply(int node, int left, int right, long val) {
            long len = right - left + 1L;

            squareSum[node] =
                (squareSum[node] + 2 * val * sum[node]
                 + val * val % MOD * len) % MOD;

            sum[node] = (sum[node] + val * len) % MOD;
            lazy[node] = (lazy[node] + val) % MOD;
        }

        void push(int node, int left, int right) {
            if (lazy[node] == 0 || left == right) return;

            int mid = left + (right - left) / 2;

            apply(node * 2, left, mid, lazy[node]);
            apply(node * 2 + 1, mid + 1, right, lazy[node]);

            lazy[node] = 0;
        }

        void update(int node, int left, int right,
                    int ql, int qr) {
            if (ql > qr || right < ql || left > qr) return;

            if (ql <= left && right <= qr) {
                apply(node, left, right, 1);
                return;
            }

            push(node, left, right);

            int mid = left + (right - left) / 2;

            update(node * 2, left, mid, ql, qr);
            update(node * 2 + 1, mid + 1, right, ql, qr);

            sum[node] = (sum[node * 2] + sum[node * 2 + 1]) % MOD;

            squareSum[node] =
                (squareSum[node * 2] +
                 squareSum[node * 2 + 1]) % MOD;
        }
    }
}
