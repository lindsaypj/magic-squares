// INDIVIDUAL ASSIGNMENT #2: MAGIC SQUARES
// Author: Patrick Lindsay
// File: MagicSquare.java
// Date: 3/2/22

package game;

/**
 * Object to track the game board and the choices made players
 */
public class MagicSquare {

    // Fields              // 0000_0000_0000_0000 | tracks which numbers
    private short choices; //         9 8765 4321 | have been chosen
    private final byte[] square; // stores the positions of the 9 numbers on the square

    // Constructor
    public MagicSquare(byte[] square) {
        this.square = square;
        this.choices = 0;
    }

    /**
     * Given a number 1-9, checks if number has been chosen. If
     * number has already been selected returns false, otherwise
     * sets the corresponding position in the choices to on and
     * returns true.
     *
     * @throws IllegalArgumentException if number is not 1-9
     * @param selection chosen number (1-9)
     * @return true if selection is made, false if choice is already selected
     */
    public boolean choose(byte selection) {
        // Validate selection
        if (selection < 1 || selection > 9) {
            throw new IllegalArgumentException(selection + " is not between 1 and 9");
        }

        // Return false if number is already selected
        if (hasAlreadyChosen(selection)) {
            return false;
        }

        // Make selection
        makeSelection(selection);
        return true;
    }

    /**
     * Determines if a given number has been chosen or not.
     *
     * @param selection chosen number (1-9)
     * @return true if number has been selected, false otherwise
     */
    public boolean hasAlreadyChosen(byte selection) {
        // Validate selection range
        if (selection < 1 || selection > 9) {
            return false;
        }

        // Check if selection is selected within choices
        return (generateMask(selection) & choices) != 0;
    }

    /**
     * Method to get the choices. Used to test for win condition.
     *
     * @return the choices field containing selected numbers.
     */
    public short getChoices() {
        return choices;
    }

    /**
     * Method to get the stored layout of the numbers.
     * @return the square field containing the positions of the 9 numbers
     */
    public byte[] getSquare() {
        return square;
    }

    /**
     * Method to get a string representation of the board's 3x3 layout
     * and the numbers in their corresponding positions.
     *
     * @return Visual representation of game board and selections
     */
    @Override
    public String toString() {
        // Variable to store output
        StringBuilder output = new StringBuilder();

        for (int row = 0; row < 3; row++) {
            for (int col = 1; col <= 3; col++) {
                // store value in square position
                int index = (3*row + col)-1;
                byte value = square[index];

                // Check if value should be displayed
                if (hasAlreadyChosen((value))) {
                    output.append(value).append(" ");
                }
                else {
                    output.append("_ ");
                }
            }
            // go to next line at the end of row (excluding last row)
            if (row != 2) {
                output.append(System.lineSeparator());
            }

        }
        return output.toString();
    }

    // Method to update the corresponding bit index of choices
    // field with given selection
    private void makeSelection(byte selection) {
        // make bit-mask to apply to choices field (short)
        short mask = generateMask(selection);

        // apply mask to choices (add selection)
        choices = (short)(choices | mask);
    }

    private short generateMask(byte selection) {
        return (short)(0b0000_0000_0000_0001 << selection-1);
    }
}
