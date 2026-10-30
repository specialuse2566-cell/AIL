import java.util.*;

public class TicTacToeAlphaBeta {

    static char[][] board = {
            {' ', ' ', ' '},
            {' ', ' ', ' '},
            {' ', ' ', ' '}
    };

    static Scanner sc = new Scanner(System.in);

    static void printBoard() {
        System.out.println("\n-------------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " | ");
            }
            System.out.println("\n-------------");
        }
    }

    static boolean isMovesLeft() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == ' ')
                    return true;
        return false;
    }

    static int evaluate() {

        for (int row = 0; row < 3; row++) {
            if (board[row][0] == board[row][1] &&
                board[row][1] == board[row][2]) {

                if (board[row][0] == 'O')
                    return 10;
                if (board[row][0] == 'X')
                    return -10;
            }
        }

        for (int col = 0; col < 3; col++) {
            if (board[0][col] == board[1][col] &&
                board[1][col] == board[2][col]) {

                if (board[0][col] == 'O')
                    return 10;
                if (board[0][col] == 'X')
                    return -10;
            }
        }

        if (board[0][0] == board[1][1] &&
            board[1][1] == board[2][2]) {

            if (board[0][0] == 'O')
                return 10;
            if (board[0][0] == 'X')
                return -10;
        }

        if (board[0][2] == board[1][1] &&
            board[1][1] == board[2][0]) {

            if (board[0][2] == 'O')
                return 10;
            if (board[0][2] == 'X')
                return -10;
        }

        return 0;
    }

    static int minimax(int depth, boolean isMax, int alpha, int beta) {

        int score = evaluate();

        if (score == 10)
            return score - depth;

        if (score == -10)
            return score + depth;

        if (!isMovesLeft())
            return 0;

        if (isMax) {

            int best = Integer.MIN_VALUE;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {

                    if (board[i][j] == ' ') {

                        board[i][j] = 'O';

                        best = Math.max(best,
                                minimax(depth + 1, false, alpha, beta));

                        board[i][j] = ' ';

                        alpha = Math.max(alpha, best);

                        if (beta <= alpha)
                            return best;
                    }
                }
            }

            return best;
        }

        else {

            int best = Integer.MAX_VALUE;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {

                    if (board[i][j] == ' ') {

                        board[i][j] = 'X';

                        best = Math.min(best,
                                minimax(depth + 1, true, alpha, beta));

                        board[i][j] = ' ';

                        beta = Math.min(beta, best);

                        if (beta <= alpha)
                            return best;
                    }
                }
            }

            return best;
        }
    }

    static void findBestMove() {

        int bestVal = Integer.MIN_VALUE;
        int bestRow = -1;
        int bestCol = -1;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                if (board[i][j] == ' ') {

                    board[i][j] = 'O';

                    int moveVal = minimax(0, false,
                            Integer.MIN_VALUE,
                            Integer.MAX_VALUE);

                    board[i][j] = ' ';

                    if (moveVal > bestVal) {
                        bestVal = moveVal;
                        bestRow = i;
                        bestCol = j;
                    }
                }
            }
        }

        board[bestRow][bestCol] = 'O';
    }

    public static void main(String[] args) {

        while (true) {

            printBoard();

            System.out.print("Enter row (0-2): ");
            int r = sc.nextInt();

            System.out.print("Enter column (0-2): ");
            int c = sc.nextInt();

            if (board[r][c] != ' ') {
                System.out.println("Invalid Move!");
                continue;
            }

            board[r][c] = 'X';

            if (evaluate() == -10) {
                printBoard();
                System.out.println("You Win!");
                break;
            }

            if (!isMovesLeft()) {
                printBoard();
                System.out.println("Draw!");
                break;
            }

            findBestMove();

            if (evaluate() == 10) {
                printBoard();
                System.out.println("Computer Wins!");
                break;
            }

            if (!isMovesLeft()) {
                printBoard();
                System.out.println("Draw!");
                break;
            }
        }
    }
}