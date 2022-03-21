// INDIVIDUAL ASSIGNMENT #2: MAGIC SQUARES
// Author: Patrick Lindsay w/code provided by Susan Uland
// File: MagicSquareTest.java
// Date: 3/2/22

package game;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;


/**
 * This is the test file for testing the functionality of
 * the MagicSquare class functions.
 */
public class MagicSquareTest {

    private static final byte[] selections = {2, 7, 6, 9, 5, 1, 4, 3, 8};
    private static final short[] CHOICES = {0b0_0000_0010,
            0b0_0100_0010,
            0b0_0110_0010,
            0b1_0110_0010,
            0b1_0111_0010,
            0b1_0111_0011,
            0b1_0111_1011,
            0b1_0111_1111,
            0b1_1111_1111};

    // TEST getChoices() is the same as testChoose()

    @Test
    public void testGetSquare() {
        MagicSquare testMS = new MagicSquare(selections);

        assertEquals("Square does not match expected output", selections, testMS.getSquare());
    }


    @Test
    public void testHasAlreadyChosen() {
        MagicSquare testMS = new MagicSquare(selections);

        for (int i = 0; i < selections.length; i++) {
            assertFalse("Number has not been chosen", testMS.hasAlreadyChosen(selections[i]));
        }

        // for every selection choice in values array
        for (int i = 0; i < selections.length; i++) {

            // choose num
            testMS.choose((byte) selections[i]);

            // Test for TRUE hasAlreadyChosen
            for (int j = 0; j <= i; j++) {
                assertTrue("Number has been chosen", testMS.hasAlreadyChosen(selections[j]));
            }
            // Test for FALSE hasAlreadyChosen
            for (int j = selections.length -1; j > i; j--) {
                assertFalse("Number has not been chosen", testMS.hasAlreadyChosen(selections[j]));
            }
        }
    }
    
    @Test
    public void testPrintChoicesEmptySquare() {
        MagicSquare ms = new MagicSquare(selections);


        // redirect output from console window into a PrintStream
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        // invoke toString() which now prints to the PrintStream instead of the console window
        System.out.println(ms);

        String expectedConsoleOutput = "_ _ _ "+ System.lineSeparator()+"_ _ _ "+System.lineSeparator()+"_ _ _ "+System.lineSeparator();
        assertEquals("print choices incorrect output", expectedConsoleOutput, out.toString());

    }

    // Tests BOTH choose() and getChoices()
    @Test
    public void testChooseGetChoices() {

        MagicSquare sq = new MagicSquare(selections);

        // Test getChoices when none have been made
        assertEquals("choices have not been made yet",0, sq.getChoices());

        // for every selection choice in values array
        for (int i = 0; i < selections.length; i++) {

            // choose num
            sq.choose((byte) selections[i]);

            // verify that getChoices returns proper cumulative selections
            assertEquals("choice was noted incorrectly", CHOICES[i], sq.getChoices());

        }
    }



    @Test(expected = IllegalArgumentException.class)
    public void testInvalidChoice() {
        MagicSquare ms = new MagicSquare(selections);
        ms.choose((byte) -7);
        ms.choose((byte) 0);
        ms.choose((byte) 10);
    }

    @Test
    public void testPrintChoicesFullSquare() {
        MagicSquare ms = new MagicSquare(selections);

        // choose every selection in the values array
        for (byte selection : selections) {
            ms.choose((byte) selection);
        }

        // redirect output from console window into a PrintStream
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        // invoke toString() which now prints to the PrintStream instead of the console window
        System.out.println(ms);

        String expectedConsoleOutput = "2 7 6 "+
                System.lineSeparator()+"9 5 1 "+
                System.lineSeparator()+"4 3 8 "+
                System.lineSeparator();

        assertEquals("print choices incorrect output", expectedConsoleOutput, out.toString());

    }
}