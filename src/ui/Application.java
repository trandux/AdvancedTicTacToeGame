package ui;

import model.TicTacToe;

import java.util.Scanner;

/**
 * Main entry class to run application
 *
 * @author Tobias Thirolf
 */
public final class Application {

    private static final String ERROR_UTILITY_CLASS_INSTANTIATION = "Utility class cannot be instantiated.";
    private static final String ARGUMENT_NO_AI = "pvp";
    private static final String ARGUMENT_AI = "ki";

    private Application() {
        throw new IllegalStateException(ERROR_UTILITY_CLASS_INSTANTIATION);
    }

    /**
     * Main method used as entry point
     * @param args {@code ki} for using an AI as opponent. {@code pvp} for playing against another human being.
     */
    public static void main(String[] args) {
        if (args.length == 1) {
            TicTacToe ticTacToe = new TicTacToe();
            Scanner scanner = new Scanner(System.in);
            if (ARGUMENT_AI.equals(args[0])) {
                ticTacToe.useAI();
                InputHandler inputHandler = new InputHandler(ticTacToe, true, scanner);
                inputHandler.interact();
            } else if (ARGUMENT_NO_AI.equals(args[0])) {
                InputHandler inputHandler = new InputHandler(ticTacToe, false, scanner);
                inputHandler.interact();
            }
            scanner.close();
        }
    }
}

