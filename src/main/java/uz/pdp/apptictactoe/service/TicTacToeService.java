package uz.pdp.apptictactoe.service;

import org.springframework.stereotype.Service;
import uz.pdp.apptictactoe.entity.GameState;

@Service
public class TicTacToeService {

    public GameState startNewGame() {
        return new GameState(); // Yangi o'yin holatini yaratadi
    }

    /**
     * O'yinchining yurishini qayta ishlaydi.
     * gameState Joriy o'yin holati
     * cellIndex O'yinchi bosgan katakcha indeksi
     * Yurish muvaffaqiyatli bo'lsa true, aks holda false.
     */
    public boolean makeMove(GameState gameState, int cellIndex) {
        if (gameState.isGameOver() || !isValidMove(gameState.getBoard(), cellIndex)) {
            return false;
        }

        gameState.getBoard()[cellIndex] = gameState.getCurrentPlayer();

        String winner = checkWinner(gameState.getBoard());
        if (winner != null) {
            gameState.setWinner(winner);
            gameState.setGameOver(true);
        } else if (isBoardFull(gameState.getBoard())) {
            gameState.setDraw(true);
            gameState.setGameOver(true);
        } else {

            gameState.setCurrentPlayer(
                    "X".equals(gameState.getCurrentPlayer()) ? "O" : "X"
            );
        }
        return true;
    }

    private boolean isValidMove(String[] board, int index) {
        return index >= 0 && index < 9 && board[index] == null;
    }

    private boolean isBoardFull(String[] board) {
        for (String cell : board) {
            if (cell == null) {
                return false;
            }
        }
        return true;
    }

    private String checkWinner(String[] board) {
        int[][] winConditions = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
                {0, 4, 8}, {2, 4, 6}
        };

        for (int[] condition : winConditions) {
            String cell1 = board[condition[0]];
            String cell2 = board[condition[1]];
            String cell3 = board[condition[2]];

            if (cell1 != null && cell1.equals(cell2) && cell1.equals(cell3)) {
                return cell1;
            }
        }
        return null;
    }
}

