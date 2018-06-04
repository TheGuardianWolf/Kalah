package kalah.display;

import kalah.game.IGame;
import kalah.game.TurnResult;

public class ConsoleGame {
    IGamePrinter printer;
    IGame game;

    public ConsoleGame(IGamePrinter printer, IGame game) {
        this.printer = printer;
        this.game = game;
    }

    public void runGame() {
        game.newGame();

        while (true) {
            printer.printGame();

            String userInput;

            if (!game.isGameOver()) {
                userInput = printer.printPlayerPrompt();
            } else {
                printer.printGameOver();
                printer.printGame();
                printer.printScores();
                printer.printWinner();
                break;
            }

            if (userInput.equals("q")) {
                printer.printGameOver();
                printer.printGame();
                break;
            }

            TurnResult result = game.takeTurn(game.parseInput(userInput));

            if (result == TurnResult.NEXT_TURN) {
                game.nextPlayer();
            } else if (result == TurnResult.INVALID_MOVE) {
                printer.printInvalidMove();
            }
        }
    }
}
