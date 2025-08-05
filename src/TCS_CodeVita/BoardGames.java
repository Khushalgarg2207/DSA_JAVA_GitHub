package TCS_CodeVita;

import java.util.*;

public class BoardGames {
    static class State {
        int x, y, dir, steps;
        State(int x, int y, int dir, int steps) {
            this.x = x;
            this.y = y;
            this.dir = dir;
            this.steps = steps;
        }
    }

    public static int minMoves(int[][] grid, int[] source, int[] dest, int[] moveRule) {
        int M = grid.length, N = grid[0].length;

        int[][] moves = new int[4][2];
        moves[0] = moveRule;
        moves[1] = new int[] {moveRule[1], -moveRule[0]};
        moves[2] = new int[] {-moveRule[0], -moveRule[1]};
        moves[3] = new int[] {-moveRule[1], moveRule[0]};

        boolean[][][] visited = new boolean[M][N][4];
        Queue<State> queue = new LinkedList<>();

        for (int dir = 0; dir < 4; dir++) {
            visited[source[0]][source[1]][dir] = true;
        }
        queue.offer(new State(source[0], source[1], 0, 0));

        while (!queue.isEmpty()) {
            State curr = queue.poll();

            if (curr.x == dest[0] && curr.y == dest[1]) {
                return curr.steps;
            }

            for (int rotation = 0; rotation < 4; rotation++) {
                int newDir = (curr.dir + rotation) % 4;

                int nx = curr.x + moves[newDir][0];
                int ny = curr.y + moves[newDir][1];

                if (nx >= 0 && nx < M && ny >= 0 && ny < N && grid[nx][ny] == 0) {
                    if (!visited[nx][ny][newDir]) {
                        visited[nx][ny][newDir] = true;
                        queue.offer(new State(nx, ny, newDir, curr.steps + 1));
                    }
                }
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        int[][] grid = {
                {0,0,0,0,1,0},
                {0,0,1,0,0,1},
                {0,1,0,1,0,0},
                {1,1,1,0,0,0},
                {1,0,0,0,0,1},
                {1,0,0,1,1,0}
        };

        int[] source = {0,0};
        int[] dest = {4,4};
        int[] moveRule = {2,2};

        int result = minMoves(grid, source, dest, moveRule);
        System.out.println(result);
    }
}
