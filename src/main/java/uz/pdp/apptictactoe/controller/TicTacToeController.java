package uz.pdp.apptictactoe.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uz.pdp.apptictactoe.entity.GameState;
import uz.pdp.apptictactoe.service.TicTacToeService;


@Controller
@RequiredArgsConstructor
public class TicTacToeController {


    private static final String GAME_STATE_SESSION_ATTR = "gameState";
    private static final String ERROR_MESSAGE_ATTR = "errorMessage";

    private final TicTacToeService gameService;

    @GetMapping("/game")
    public String showGame(HttpSession session, Model model) {
        GameState gameState = (GameState) session.getAttribute(GAME_STATE_SESSION_ATTR);

        if (gameState == null) {
            gameState = gameService.startNewGame();
            session.setAttribute(GAME_STATE_SESSION_ATTR, gameState);
            System.out.println("New game started and saved to session.");
        }

        model.addAttribute("gameState", gameState);

        // Agar redirectdan kelgan xatolik bo'lsa, uni ham modelga qo'shamiz
        if (model.containsAttribute(ERROR_MESSAGE_ATTR)) {
            System.out.println("Error message being passed to view: " + model.getAttribute(ERROR_MESSAGE_ATTR));
        }

        return "game";
    }

    @GetMapping("/move")
    public String makeMove(@RequestParam("cell") int cellIndex,
                           HttpSession session,
                           RedirectAttributes redirectAttributes) {

        GameState gameState = (GameState) session.getAttribute(GAME_STATE_SESSION_ATTR);

        if (gameState == null) {
            return "redirect:/game";
        }

        boolean moveSuccessful = gameService.makeMove(gameState, cellIndex);

        if (!moveSuccessful && !gameState.isGameOver()) {
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_ATTR, "Noto'g'ri yurish! (Katak band yoki mavjud emas, yoki o'yin tugagan)");
            System.out.println("Invalid move attempted for cell: " + cellIndex);
        } else {
            System.out.println("Move successful for cell: " + cellIndex + ", Player: " + gameState.getCurrentPlayer() + ", Game Over: " + gameState.isGameOver());
        }

        return "redirect:/game";
    }

    @GetMapping("/reset")
    public String resetGame(HttpSession session) {
        GameState newGameState = gameService.startNewGame();
        session.setAttribute(GAME_STATE_SESSION_ATTR, newGameState);
        session.removeAttribute(ERROR_MESSAGE_ATTR);
        System.out.println("Game reset and new state saved to session.");
        return "redirect:/game";
    }


}
