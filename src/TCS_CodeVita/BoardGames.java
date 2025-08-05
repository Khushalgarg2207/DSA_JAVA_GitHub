package TCS_CodeVita;

import java.util.*;

public class BoardGames {
    static class Cell {
        int x, y, steps;
        Cell(int x, int y, int steps) {
            this.x = x;
            this.y = y;
            this.steps = steps;
        }
    }

    public static int minMoves(int[][] grid, int[] source, int[] dest, int[] moveRule) {
        int M = grid.length,N = grid[0].length;
        int dx = moveRule[0];
        int dy = moveRule[1];

        int[][] directions = {
                {dx, dy},
                {dy, -dx},
                {-dy, dx},
                {-dx, -dy}
        };

        boolean[][] visited = new boolean[M][N];
        Queue<Cell> q = new LinkedList<>();

        q.offer(new Cell(source[0], source[1], 0));
        visited[source[0]][source[1]] = true;

        while (!q.isEmpty()) {
            Cell curr = q.poll();

            if (curr.x == dest[0] && curr.y == dest[1]) {
                return curr.steps;
            }

            for (int[] dir : directions) {
                int nx = curr.x + dir[0];
                int ny = curr.y + dir[1];

                if(nx >= 0 && nx < M && ny >= 0 && ny < N && grid[nx][ny] == 0) {
                    if(!visited[nx][ny]) {
                        visited[nx][ny] = true;
                        q.offer(new Cell(nx, ny, curr.steps + 1));
                    }
                }
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        int[][] grid = {
                {0,1,0,0,0,0},
                {0,0,0,0,0,1},
                {0,1,0,0,0,0},
                {1,1,0,0,0,1},
                {0,0,0,0,0,0},
                {1,1,0,0,1,0}
        };
        int[] source = {1,0};
        int[] dest = {5,3};
        int[] moveRule = {1,2};

        int result = minMoves(grid, source, dest, moveRule);
        System.out.println(result);
    }
}
