
import java.util.*;

class Solution {
    public int[] findSubtreeSizes(int[] parent, String s) {

        int n = parent.length;

        List<Integer>[] adj = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int i = 1; i < n; i++) {
            adj[parent[i]].add(i);
        }

        int[] last = new int[26];
        Arrays.fill(last, -1);

        int[] newParent = new int[n];
        int[] prev = new int[n];

        int[] stack = new int[2 * n + 5];
        int[] order = new int[n];

        int top = 0;
        int count = 0;

        stack[top++] = 0;

        while (top > 0) {

            int x = stack[--top];

            // Exit node
            if (x < 0) {
                int node = -x - 1;
                int c = s.charAt(node) - 'a';
                last[c] = prev[node];
                continue;
            }

            // Enter node
            int node = x;
            int c = s.charAt(node) - 'a';

            prev[node] = last[c];

            if (last[c] == -1) {
                newParent[node] = parent[node];
            } else {
                newParent[node] = last[c];
            }

            last[c] = node;

            order[count++] = node;

            // Exit event
            stack[top++] = -(node + 1);

            // Visit children
            for (int i = adj[node].size() - 1; i >= 0; i--) {
                stack[top++] = adj[node].get(i);
            }
        }

        // Calculate subtree sizes
        int[] ans = new int[n];
        Arrays.fill(ans, 1);

        for (int i = n - 1; i > 0; i--) {

            int node = order[i];

            ans[newParent[node]] += ans[node];
        }

        return ans;
    }
}
