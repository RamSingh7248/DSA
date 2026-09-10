class Solution {
    public int averageOfSubtree(TreeNode root) {
        int[] ans = new int[1];
        solve(root, ans);
        return ans[0];
    }

    private int[] solve(TreeNode root, int[] ans) {
        if (root == null) {
            return new int[]{0, 0};
        }

        int[] left = solve(root.left, ans);
        int[] right = solve(root.right, ans);

        int sum = root.val + left[0] + right[0];
        int count = 1 + left[1] + right[1];

        if (root.val == sum / count) {
            ans[0]++;
        }

        return new int[]{sum, count};
    }
}