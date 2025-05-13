package uz.pdp.apptictactoe.service;

import org.springframework.stereotype.Service;
import uz.pdp.apptictactoe.entity.GameState;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

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

    private boolean checkWinner(String[] board, String player) {
        int[][] winPatterns = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
                {0, 4, 8}, {2, 4, 6}
        };

        for (int[] pattern : winPatterns) {
            if (player.equals(board[pattern[0]]) &&
                    player.equals(board[pattern[1]]) &&
                    player.equals(board[pattern[2]])) {
                return true;
            }
        }
        return false;
    }

    public void makeSmartMove(GameState gameState) {
        if (gameState.isGameOver()) {
            return;
        }

        String[] board = gameState.getBoard();
        int countX=0;
        for (int i = 0; i < board.length; i++) {
            if (Objects.equals(board[i], "X")) countX++;
        }


        if(countX==1 && Objects.equals(board[4],"X")){

            List<Integer> blancPlace = List.of(0,2,6,8);

            int randomIndex = new Random().nextInt(blancPlace.size());
            int moveCell = blancPlace.get(randomIndex);
            makeMove(gameState, moveCell);
            System.out.println("Komputer random yuradi Burchak uchun: " + moveCell);
            return;

        }
        else if(Objects.isNull(gameState.getBoard()[4])){
            makeMove(gameState, 4);
            return;
        }






        // 1️⃣ Kompyuter O yutishini tekshirish
        Integer winMove = findWinningMove(board, "O");
        if (winMove != null) {
            makeMove(gameState, winMove);
            System.out.println("Yutish uchun yurish: " + winMove);
            return;
        }

        // 2️⃣ Raqib X yutishini bloklash
        Integer blockMove = findWinningMove(board, "X");
        if (blockMove != null) {
            makeMove(gameState, blockMove);
            System.out.println("Blok qilish: " + blockMove);
            return;
        }

        // 3️⃣ Bo'sh kataklardan random tanlash
        List<Integer> blancPlace = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            if (board[i] == null) {
                blancPlace.add(i);
            }
        }

        if (!blancPlace.isEmpty()) {
            int randomIndex = new Random().nextInt(blancPlace.size());
            int moveCell = blancPlace.get(randomIndex);
            makeMove(gameState, moveCell);
            System.out.println("Komputer random yuradi: " + moveCell);
        }
    }

    private Integer findWinningMove(String[] board, String player) {
        for (int i = 0; i < 9; i++) {
            if (board[i] == null) {
                board[i] = player;
                boolean isWin = checkWinner(board, player);
                board[i] = null;
                if (isWin) {
                    return i;
                }
            }
        }
        return null;
    }
}

