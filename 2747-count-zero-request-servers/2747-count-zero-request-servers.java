
import java.util.*;

class Solution {
    public int[] countServers(int n, int[][] logs, int x, int[] queries) {

        int m = queries.length;
        int[] ans = new int[m];

        Arrays.sort(logs, (a, b) -> a[1] - b[1]);

        Integer[] idx = new Integer[m];

        for (int i = 0; i < m; i++) {
            idx[i] = i;
        }

        Arrays.sort(idx, (a, b) -> queries[a] - queries[b]);

        HashMap<Integer, Integer> map = new HashMap<>();

        int left = 0;
        int right = 0;
        int active = 0;

        for (int i = 0; i < m; i++) {
            int q = queries[idx[i]];

            while (right < logs.length && logs[right][1] <= q) {
                int id = logs[right][0];

                map.put(id, map.getOrDefault(id, 0) + 1);

                if (map.get(id) == 1) {
                    active++;
                }

                right++;
            }

            while (left < right && logs[left][1] < q - x) {
                int id = logs[left][0];

                map.put(id, map.get(id) - 1);

                if (map.get(id) == 0) {
                    map.remove(id);
                    active--;
                }

                left++;
            }

            ans[idx[i]] = n - active;
        }

        return ans;
    }
}
