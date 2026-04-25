/*
 * File: GetInputTest.java
 * Purpose: JUnit 5 unit tests for the GetInput utility class, verifying correct
 * behavior of input validation methods including integer parsing, range checking,
 * odd number validation, and character validation.
 * Author: Sean Balbale
 * Date: 4/21/2026
 */

package edu.trincoll;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the GetInput utility class.
 * Each test focuses on a single method, following the unit-test guidelines
 * in the implementation specification (test before integration, stub where
 * needed, verify and mark passed/failed).
 *
 * Tests validate prompt display and correct return values for valid inputs,
 * as well as re-prompting behavior for invalid inputs.
 */
class GetInputTest {

        // Preserve original stdin and stdout so they can be restored after every test
        private final InputStream originalIn = System.in;
        private final PrintStream originalOut = System.out;

        // Captures text that GetInput prints so assertions can inspect it
        private ByteArrayOutputStream capturedOutput;

        // -----------------------------------------------------------------------
        // JUnit lifecycle: redirect / restore System.in and System.out
        // -----------------------------------------------------------------------

        /**
         * Redirects {@code System.out} to a capture buffer before each test.
         */
        @BeforeEach
        void redirectSystemOut() {
                capturedOutput = new ByteArrayOutputStream();
                System.setOut(new PrintStream(capturedOutput));
        }

        /**
         * Restores the original {@code System.out} after each test.
         */
        @AfterEach
        void restoreSystemOut() {
                System.setOut(originalOut);
                System.setIn(originalIn);
        }

        /**
         * Convenience helper — returns everything printed to System.out
         * during the current test as a single String.
         */
        private String output() {
                return capturedOutput.toString();
        }

        /**
         * Helper method to redirect System.in to provide mock user input.
         * 
         * @param input The simulated user input as a String.
         */
        private void provideInput(String input) {
                ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
                System.setIn(in);
        }

        // -----------------------------------------------------------------------
        // getInt() — sunny-day tests
        // -----------------------------------------------------------------------

        /**
         * Verifies getInt() accepts a valid positive integer and returns it.
         */
        @Test
        @DisplayName("getInt() accepts valid positive integer and returns correct value")
        void testGetIntValidPositive() {
                provideInput("42\n");
                GetInput input = new GetInput();
                int result = input.getInt();

                assertEquals(42, result,
                                "getInt() should return the exact integer entered by the user");
                assertTrue(output().contains("Enter a positive integer"),
                                "getInt() should display a prompt for positive integer input");
        }

        /**
         * Verifies getInt() displays the prompt message.
         */
        @Test
        @DisplayName("getInt() displays prompt message to user")
        void testGetIntDisplaysPrompt() {
                provideInput("5\n");
                GetInput input = new GetInput();
                input.getInt();

                assertTrue(output().contains("Enter a positive integer"),
                                "getInt() must display a prompt asking for a positive integer");
        }

        /**
         * Verifies getInt() accepts zero as a valid positive integer.
         */
        @Test
        @DisplayName("getInt() accepts zero as a valid input")
        void testGetIntAcceptsZero() {
                provideInput("0\n");
                GetInput input = new GetInput();
                int result = input.getInt();

                assertEquals(0, result,
                                "getInt() should accept and return zero");
        }

        // -----------------------------------------------------------------------
        // getInt() — rainy-day tests
        // -----------------------------------------------------------------------

        /**
         * Verifies getInt() rejects non-numeric input and re-prompts.
         */
        @Test
        @DisplayName("getInt() rejects non-numeric input and re-prompts")
        void testGetIntRejectsNonNumeric() {
                provideInput("abc\n42\n");
                GetInput input = new GetInput();
                int result = input.getInt();

                assertEquals(42, result,
                                "After rejecting invalid input, getInt() should accept the next valid input");
                assertTrue(output().contains("Invalid input"),
                                "getInt() should display error message for non-numeric input");
        }

        /**
         * Verifies getInt() rejects negative numbers (expects positive only).
         */
        @Test
        @DisplayName("getInt() rejects negative integers")
        void testGetIntRejectsNegative() {
                provideInput("-5\n10\n");
                GetInput input = new GetInput();
                int result = input.getInt();

                assertEquals(10, result,
                                "After rejecting negative input, getInt() should accept next valid input");
                assertTrue(output().contains("Invalid input"),
                                "getInt() should reject negative values");
        }

        // -----------------------------------------------------------------------
        // getIntInRange() — sunny-day tests
        // -----------------------------------------------------------------------

        /**
         * Verifies getIntInRange() accepts a valid integer within the specified range.
         */
        @Test
        @DisplayName("getIntInRange() accepts valid integer within range")
        void testGetIntInRangeValidMiddle() {
                provideInput("5\n");
                GetInput input = new GetInput();
                int result = input.getIntInRange(1, 10);

                assertEquals(5, result,
                                "getIntInRange() should return the value when it falls within min and max");
                assertTrue(output().contains("1") && output().contains("10"),
                                "getIntInRange() prompt should display the min and max bounds");
        }

        /**
         * Verifies getIntInRange() accepts the minimum boundary value (inclusive).
         */
        @Test
        @DisplayName("getIntInRange() accepts minimum boundary value")
        void testGetIntInRangeValidMin() {
                provideInput("1\n");
                GetInput input = new GetInput();
                int result = input.getIntInRange(1, 10);

                assertEquals(1, result,
                                "getIntInRange() should accept the minimum boundary value");
        }

        /**
         * Verifies getIntInRange() accepts the maximum boundary value (inclusive).
         */
        @Test
        @DisplayName("getIntInRange() accepts maximum boundary value")
        void testGetIntInRangeValidMax() {
                provideInput("10\n");
                GetInput input = new GetInput();
                int result = input.getIntInRange(1, 10);

                assertEquals(10, result,
                                "getIntInRange() should accept the maximum boundary value");
        }

        // -----------------------------------------------------------------------
        // getIntInRange() — rainy-day tests
        // -----------------------------------------------------------------------

        /**
         * Verifies getIntInRange() rejects values below the minimum and re-prompts.
         */
        @Test
        @DisplayName("getIntInRange() rejects value below minimum")
        void testGetIntInRangeBelowMin() {
                provideInput("0\n5\n");
                GetInput input = new GetInput();
                int result = input.getIntInRange(1, 10);

                assertEquals(5, result,
                                "After rejecting below-min input, should accept next valid input");
                assertTrue(output().contains("Invalid input"),
                                "getIntInRange() should display error for values below min");
        }

        /**
         * Verifies getIntInRange() rejects values above the maximum and re-prompts.
         */
        @Test
        @DisplayName("getIntInRange() rejects value above maximum")
        void testGetIntInRangeAboveMax() {
                provideInput("11\n7\n");
                GetInput input = new GetInput();
                int result = input.getIntInRange(1, 10);

                assertEquals(7, result,
                                "After rejecting above-max input, should accept next valid input");
                assertTrue(output().contains("Invalid input"),
                                "getIntInRange() should display error for values above max");
        }

        /**
         * Verifies getIntInRange() rejects non-numeric input and re-prompts.
         */
        @Test
        @DisplayName("getIntInRange() rejects non-numeric input and re-prompts")
        void testGetIntInRangeRejectsNonNumeric() {
                provideInput("xyz\n5\n");
                GetInput input = new GetInput();
                int result = input.getIntInRange(1, 10);

                assertEquals(5, result,
                                "After rejecting non-numeric input, should accept next valid input");
                assertTrue(output().contains("Invalid input"),
                                "getIntInRange() should display error for non-numeric input");
        }

        // -----------------------------------------------------------------------
        // getOddInt() — sunny-day tests
        // -----------------------------------------------------------------------

        /**
         * Verifies getOddInt() accepts a valid odd positive integer.
         */
        @Test
        @DisplayName("getOddInt() accepts odd positive integer and returns it")
        void testGetOddIntValidOdd() {
                provideInput("7\n");
                GetInput input = new GetInput();
                int result = input.getOddInt();

                assertEquals(7, result,
                                "getOddInt() should return the odd integer entered");
                assertTrue(output().contains("odd"),
                                "getOddInt() prompt should mention 'odd' requirement");
        }

        /**
         * Verifies getOddInt() accepts 1 as the smallest odd number.
         */
        @Test
        @DisplayName("getOddInt() accepts 1 as the smallest odd number")
        void testGetOddIntAcceptsOne() {
                provideInput("1\n");
                GetInput input = new GetInput();
                int result = input.getOddInt();

                assertEquals(1, result,
                                "getOddInt() should accept 1 as a valid odd number");
        }

        /**
         * Verifies getOddInt() accepts large odd numbers.
         */
        @Test
        @DisplayName("getOddInt() accepts large odd numbers")
        void testGetOddIntAcceptsLarge() {
                provideInput("999\n");
                GetInput input = new GetInput();
                int result = input.getOddInt();

                assertEquals(999, result,
                                "getOddInt() should accept large odd numbers without issue");
        }

        // -----------------------------------------------------------------------
        // getOddInt() — rainy-day tests
        // -----------------------------------------------------------------------

        /**
         * Verifies getOddInt() rejects even numbers and re-prompts.
         */
        @Test
        @DisplayName("getOddInt() rejects even numbers and re-prompts")
        void testGetOddIntRejectsEven() {
                provideInput("4\n5\n");
                GetInput input = new GetInput();
                int result = input.getOddInt();

                assertEquals(5, result,
                                "After rejecting even number, getOddInt() should accept next odd input");
                assertTrue(output().contains("Invalid input"),
                                "getOddInt() should display error for even numbers");
        }

        /**
         * Verifies getOddInt() rejects zero (which is even).
         */
        @Test
        @DisplayName("getOddInt() rejects zero as not odd")
        void testGetOddIntRejectsZero() {
                provideInput("0\n3\n");
                GetInput input = new GetInput();
                int result = input.getOddInt();

                assertEquals(3, result,
                                "After rejecting zero, getOddInt() should accept next odd input");
        }

        /**
         * Verifies getOddInt() rejects non-numeric input and re-prompts.
         */
        @Test
        @DisplayName("getOddInt() rejects non-numeric input and re-prompts")
        void testGetOddIntRejectsNonNumeric() {
                provideInput("five\n9\n");
                GetInput input = new GetInput();
                int result = input.getOddInt();

                assertEquals(9, result,
                                "After rejecting non-numeric input, should accept next valid odd input");
                assertTrue(output().contains("Invalid input"),
                                "getOddInt() should display error for non-numeric input");
        }

        /**
         * Verifies getOddInt() rejects negative odd numbers.
         */
        @Test
        @DisplayName("getOddInt() rejects negative odd numbers")
        void testGetOddIntRejectsNegativeOdd() {
                provideInput("-5\n7\n");
                GetInput input = new GetInput();
                int result = input.getOddInt();

                assertEquals(7, result,
                                "After rejecting negative input, should accept next valid positive odd input");
        }

        // -----------------------------------------------------------------------
        // getChar() — sunny-day tests
        // -----------------------------------------------------------------------

        /**
         * Verifies getChar() accepts a valid character from the allowed set.
         */
        @Test
        @DisplayName("getChar() accepts valid character from allowed set")
        void testGetCharValidSingleCharacter() {
                provideInput("H\n");
                GetInput input = new GetInput();
                char[] validChars = { 'H', 'T' };
                char result = input.getChar(validChars);

                assertEquals('H', result,
                                "getChar() should return the character entered by user");
                assertTrue(output().contains("Enter one of these characters"),
                                "getChar() should display prompt listing valid characters");
        }

        /**
         * Verifies getChar() accepts another valid character from the allowed set.
         */
        @Test
        @DisplayName("getChar() accepts different valid character from allowed set")
        void testGetCharValidDifferentCharacter() {
                provideInput("R\n");
                GetInput input = new GetInput();
                char[] validChars = { 'L', 'R' };
                char result = input.getChar(validChars);

                assertEquals('R', result,
                                "getChar() should return the correct character");
        }

        /**
         * Verifies getChar() accepts lowercase input and converts to uppercase.
         */
        @Test
        @DisplayName("getChar() converts lowercase to uppercase")
        void testGetCharConvertsLowercaseToUppercase() {
                provideInput("h\n");
                GetInput input = new GetInput();
                char[] validChars = { 'H', 'T' };
                char result = input.getChar(validChars);

                assertEquals('H', result,
                                "getChar() should convert lowercase input to uppercase");
        }

        // -----------------------------------------------------------------------
        // getChar() — rainy-day tests
        // -----------------------------------------------------------------------

        /**
         * Verifies getChar() rejects characters not in the allowed set.
         */
        @Test
        @DisplayName("getChar() rejects character not in allowed set")
        void testGetCharRejectsInvalidCharacter() {
                provideInput("X\nH\n");
                GetInput input = new GetInput();
                char[] validChars = { 'H', 'T' };
                char result = input.getChar(validChars);

                assertEquals('H', result,
                                "After rejecting invalid char, should accept next valid input");
                assertTrue(output().contains("Error"),
                                "getChar() should display error message for invalid character");
        }

        /**
         * Verifies getChar() rejects multi-character input.
         */
        @Test
        @DisplayName("getChar() rejects multi-character input")
        void testGetCharRejectsMultipleCharacters() {
                provideInput("HT\nL\n");
                GetInput input = new GetInput();
                char[] validChars = { 'L', 'R' };
                char result = input.getChar(validChars);

                assertEquals('L', result,
                                "After rejecting multi-char input, should accept single valid character");
                assertTrue(output().contains("Error"),
                                "getChar() should reject input with multiple characters");
        }

        /**
         * Verifies getChar() rejects empty input and re-prompts.
         */
        @Test
        @DisplayName("getChar() rejects empty input and re-prompts")
        void testGetCharRejectsEmpty() {
                provideInput("\nE\n");
                GetInput input = new GetInput();
                char[] validChars = { 'E', 'O' };
                char result = input.getChar(validChars);

                assertEquals('E', result,
                                "After rejecting empty input, should accept next valid character");
        }

        /**
         * Verifies getChar() rejects numeric input.
         */
        @Test
        @DisplayName("getChar() rejects numeric input")
        void testGetCharRejectsNumeric() {
                provideInput("1\nL\n");
                GetInput input = new GetInput();
                char[] validChars = { 'L', 'R' };
                char result = input.getChar(validChars);

                assertEquals('L', result,
                                "After rejecting numeric input, should accept valid character");
                assertTrue(output().contains("Error"),
                                "getChar() should reject numeric input");
        }

        /**
         * Verifies getChar() works correctly with a larger set of valid characters.
         */
        @Test
        @DisplayName("getChar() validates against multiple character options")
        void testGetCharWithLargeCharacterSet() {
                provideInput("Z\n");
                GetInput input = new GetInput();
                char[] validChars = { 'H', 'T', 'L', 'R', 'E', 'O', 'Z' };
                char result = input.getChar(validChars);

                assertEquals('Z', result,
                                "getChar() should correctly identify character in a larger set");
        }
}