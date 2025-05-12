package uz.pdp.apptictactoe.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
@Setter
public class GameState {

    private String[] board;
    private String currentPlayer;
    private String winner;
    private boolean isDraw;
    private boolean isGameOver;

    public GameState() {
        this.board = new String[9];
        Arrays.fill(this.board, null);
        this.currentPlayer = "X";
        this.winner = null;
        this.isDraw = false;
        this.isGameOver = false;
    }


    public void reset() {
        Arrays.fill(this.board, null);
        this.currentPlayer = "X";
        this.winner = null;
        this.isDraw = false;
        this.isGameOver = false;
    }
}
