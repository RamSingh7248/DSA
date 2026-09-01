import java.util.*;

class Solution {
    public int minMoves(String[] classroom, int energy) {
        int m = classroom.length;
        int n = classroom[0].length();

        int startRow = 0;
        int startCol = 0;

        int[][] litterIndex = new int[m][n];

        for (int[] row : litterIndex) {
            Arrays.fill(row, -1);
        }

        int litterCount = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                char cell = classroom[i].charAt(j);

                if (cell == 'S') {
                    startRow = i;
                    startCol = j;
                }

                if (cell == 'L') {
                    litterIndex[i][j] = litterCount++;
                }
            }
        }

        int targetMask = (1 << litterCount) - 1;

        if (litterCount == 0) {
            return 0;
        }

        boolean[][][][] visited =
                new boolean[m][n][energy + 1][1 << litterCount];

        Queue<int[]> queue = new LinkedList<>();

        queue.offer(new int[]{startRow, startCol, energy, 0, 0});

        visited[startRow][startCol][energy][0] = true;

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        while (!queue.isEmpty()) {

            int[] current = queue.poll();

            int row = current[0];
            int col = current[1];
            int currentEnergy = current[2];
            int mask = current[3];
            int moves = current[4];

            if (mask == targetMask) {
                return moves;
            }

            if (currentEnergy == 0) {
                continue;
            }

            for (int i = 0; i < 4; i++) {

                int newRow = row + dr[i];
                int newCol = col + dc[i];

                if (newRow < 0 || newRow >= m ||
                    newCol < 0 || newCol >= n) {
                    continue;
                }

                char cell = classroom[newRow].charAt(newCol);

                if (cell == 'X') {
                    continue;
                }

                int newEnergy = currentEnergy - 1;
                int newMask = mask;

                if (cell == 'L') {
                    int index = litterIndex[newRow][newCol];
                    newMask = newMask | (1 << index);
                }

                if (cell == 'R') {
                    newEnergy = energy;
                }

                if (!visited[newRow][newCol][newEnergy][newMask]) {

                    visited[newRow][newCol][newEnergy][newMask] = true;

                    queue.offer(new int[]{
                            newRow,
                            newCol,
                            newEnergy,
                            newMask,
                            moves + 1
                    });
                }
            }
        }

        return -1;
    }
}