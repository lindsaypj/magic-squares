// INDIVIDUAL ASSIGNMENT #2: MAGIC SQUARES
// Author: Patrick Lindsay
// File: MagicSquareGame.java
// Date: 3/2/22

package client;

import game.MagicSquare;

import java.util.Scanner;

/**
 * This is the client side program that will create and manage the
 * Magic Squares game based on user input.
 */
public class MagicSquaresGame {

    // Constants
    private static final byte[] SELECTIONS = {2, 7, 6, 9, 5, 1, 4, 3, 8};
    private static final MagicSquare BOARD;
    private static final short[] WIN_CONDITIONS;

    // Initialise Constants
    static {
        // Initialize BOARD
        BOARD = new MagicSquare(SELECTIONS);

        // Fill board for presentation
        for (int i = 1; i <= 9; i++) {
            BOARD.choose((byte) i);
        }

        // Initialize Win conditions
        WIN_CONDITIONS = new short[]{
            0b0_0110_0010,
            0b1_0001_0001,
            0b0_1000_1100,
            0b1_0000_1010,
            0b0_0101_0100,
            0b0_1010_0001,
            0b0_1001_0010,
            0b0_0011_1000
        };
    }

    // GAME SCRIPT
    public static void main(String[] args) {

        // Create game objects
        Scanner userInput = new Scanner(System.in);

        MagicSquare msP1 = new MagicSquare(SELECTIONS);
        MagicSquare msP2 = new MagicSquare(SELECTIONS);

        String nameP1;
        String nameP2;

        int turnTracker = 1; // Odd: player1's turn; Even: player2's turn


        // Display opening text
        printOpeningText();

        // Ask for player names
        nameP1 = requestName(userInput, "Player #1");
        nameP2 = requestName(userInput, "Player #2");

        System.out.println(); // Space

        // Start Game
        // Players take turns entering their guesses until win condition is met
        while (!testWinConditions(msP1, msP2, nameP1, nameP2)) {

            // Store the current player (switches after each valid guess)
            String currentPlayer = getCurrentPlayer(turnTracker, nameP1, nameP2);

            // Ask player for input
            try {
                // Get valid number (may not be 1-9)
                byte input = requestNextNumber(userInput, currentPlayer);

                // validate that guess has not already been guessed
                if (msP1.hasAlreadyChosen(input) || msP2.hasAlreadyChosen(input)) {
                    System.out.println("A player has already chosen " + input + "\n");
                    continue;
                }

                // attempt to add number to player's choices
                if (turnTracker % 2 != 0) { // player = 1
                    msP1.choose(input);
                    System.out.println(msP1 + "\n");
                }
                else { // Player = 2
                    msP2.choose(input);
                    System.out.println(msP2 + "\n");

                }

                // update turn tracker if no exceptions were thrown
                turnTracker++;
            }
            // If a player enters a number that is not between 1 and 9
            catch(IllegalArgumentException e) {
                System.out.println(e.getMessage());
                System.out.println();
            }
        }
    }

    // Method to display the opening text
    private static void printOpeningText() {
        System.out.println("Welcome to the game of Magic Squares");
        System.out.println("***********************************");
        System.out.println("Rules:");
        System.out.println("2 players play the game.");
        System.out.println("Each player takes turns picking a number from 1-9.");
        System.out.println("No number can be chosen twice");
        System.out.println("The first player to have 3 numbers that sum to 15 wins!");
        System.out.println(BOARD.toString());
        System.out.println("***********************************");
        System.out.println();
    }

    // Method to get the player name
    private static String requestName(Scanner input, String identifier) {
        System.out.println("Enter a name for " + identifier);
        return input.nextLine();
    }

    // Method to get a request from a player
    private static byte requestNextNumber(Scanner input, String player) {
        boolean invalidGuess = true;
        byte validGuess = -1;
        String guess = "";

        while(invalidGuess) {
            try {
                System.out.print(player + ", please enter a number: ");
                guess = input.next();

                // Attempt to convert guess to
                validGuess = Byte.parseByte(guess);

                // If no exceptions are thrown, exit loop and return valid guess
                // NOTE: Guess may still be out of bounds!
                invalidGuess = false;
            }
            catch (NumberFormatException e) {
                System.out.println(guess + " is not a number\n");
            }
        }
        return validGuess;
    }

    // method to get the current player guessing based on turn tracker
    private static String getCurrentPlayer(int tracker, String name1, String name2) {
        if (tracker % 2 == 0) {
            return name2;
        }
        return name1;
    }

    // Method to test for win conditions
    // If any conditions are met return true
    private static boolean testWinConditions(MagicSquare player1, MagicSquare player2, String name1, String name2) {
        // Test for Player win
        if (checkPlayerForWin(player1, name1) || checkPlayerForWin(player2, name2)) {
            return true;
        }

        // Test for Draw
        if ((player1.getChoices() | player2.getChoices()) == 0b1_1111_1111) {
            System.out.println("The game is a draw!");
            return true;
        }

        // no win condition met
        return false;
    }

    // Method to check a player's board for a win condition
    private static boolean checkPlayerForWin(MagicSquare player, String name) {
        short playerBoard = player.getChoices();
        for (short condition : WIN_CONDITIONS) {
            if ((short)(playerBoard & condition) == condition) {
                System.out.println(name + " wins!");
                return true;
            }
        }
        return false;
    }
}
