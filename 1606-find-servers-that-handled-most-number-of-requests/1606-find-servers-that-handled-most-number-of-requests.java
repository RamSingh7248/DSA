
import java.util.*;

class Solution {
    public List<Integer> busiestServers(int k, int[] arrival, int[] load) {
        TreeSet<Integer> free = new TreeSet<>();
        PriorityQueue<int[]> busy = new PriorityQueue<>(
            (a, b) -> a[0] - b[0]
        );

        int[] count = new int[k];

        for (int i = 0; i < k; i++) {
            free.add(i);
        }

        for (int i = 0; i < arrival.length; i++) {

            while (!busy.isEmpty() && busy.peek()[0] <= arrival[i]) {
                free.add(busy.poll()[1]);
            }

            if (free.isEmpty()) {
                continue;
            }

            int server = i % k;
            Integer s = free.ceiling(server);

            if (s == null) {
                s = free.first();
            }

            free.remove(s);
            count[s]++;

            busy.add(new int[]{arrival[i] + load[i], s});
        }

        List<Integer> ans = new ArrayList<>();
        int max = 0;

        for (int c : count) {
            max = Math.max(max, c);
        }

        for (int i = 0; i < k; i++) {
            if (count[i] == max) {
                ans.add(i);
            }
        }

        return ans;
    }
}
