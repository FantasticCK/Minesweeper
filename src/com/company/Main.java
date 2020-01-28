package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
    }
}

// BFS
class Solution {
    private static final int[][] d = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};

    public char[][] updateBoard(char[][] board, int[] click) {
        if (board.length == 0 || board[0].length == 0)
            return board;
        int n = board.length, m = board[0].length;
        if (board[click[0]][click[1]] == 'M') {
            board[click[0]][click[1]] = 'X';
            return board;
        }
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{click[0], click[1]});
        while (!q.isEmpty()) {
            int[] curr = q.poll();
            if (board[curr[0]][curr[1]] != 'E') {
                continue;
            }
            int count = 0;
            Queue<int[]> tempQ = new LinkedList<>();
            for (int i = 0; i < 8; i++) {
                int nr = curr[0] + d[i][0], nc = curr[1] + d[i][1];
                if (isValid(board, nr, nc)) {
                    if (board[nr][nc] == 'E') {
                        tempQ.offer(new int[]{nr, nc});
                    } else if (board[nr][nc] == 'M'){
                        count++;
                    }
                }
            }
            if (count == 0) {
                board[curr[0]][curr[1]] = 'B';
                q.addAll(tempQ);
            } else {
                board[curr[0]][curr[1]] = (char) ('0' + count);
            }
        }
        return board;
    }

    private boolean isValid(char[][] board, int r, int c) {
        int n = board.length, m = board[0].length;
        return r >= 0 && c >= 0 && r < n && c < m;
    }
}

// DFS
public class Solution {
    public char[][] updateBoard(char[][] board, int[] click) {
        int m = board.length, n = board[0].length;
        int row = click[0], col = click[1];

        if (board[row][col] == 'M') { // Mine
            board[row][col] = 'X';
        }
        else { // Empty
            // Get number of mines first.
            int count = 0;
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    if (i == 0 && j == 0) continue;
                    int r = row + i, c = col + j;
                    if (r < 0 || r >= m || c < 0 || c < 0 || c >= n) continue;
                    if (board[r][c] == 'M' || board[r][c] == 'X') count++;
                }
            }

            if (count > 0) { // If it is not a 'B', stop further DFS.
                board[row][col] = (char)(count + '0');
            }
            else { // Continue DFS to adjacent cells.
                board[row][col] = 'B';
                for (int i = -1; i < 2; i++) {
                    for (int j = -1; j < 2; j++) {
                        if (i == 0 && j == 0) continue;
                        int r = row + i, c = col + j;
                        if (r < 0 || r >= m || c < 0 || c < 0 || c >= n) continue;
                        if (board[r][c] == 'E') updateBoard(board, new int[] {r, c});
                    }
                }
            }
        }

        return board;
    }
}