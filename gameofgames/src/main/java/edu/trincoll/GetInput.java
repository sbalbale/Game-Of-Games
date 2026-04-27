/*
 * File: GetInput.java
 * Purpose: Utility class for robust user input handling, providing methods to
 * obtain validated integers, odd integers, and specific character inputs.
 * Author: Ben Lyons
 * Date: 4/20/2026
 */

package edu.trincoll;

import java.util.Arrays;
import java.util.Scanner;

@SuppressWarnings("checkstyle:DesignForExtension")
public class GetInput {
    private final Scanner reader = new Scanner(System.in);

    public int getInt() {
        System.out.println("Enter a positive integer:");
        String inputValue = reader.next();

        while (!inputValue.matches("\\d+")) {
            System.out.println("Invalid input: enter a positive integer");
            inputValue = reader.next();
        }

        return Integer.parseInt(inputValue);
    }

    public int getIntInRange(int min, int max) {
        System.out.println("Enter an integer between " + min + " & " + max + ":");
        String inputValue = reader.next();

        while (true) {
            if (inputValue.matches("\\d+")) {
                int parsedValue = Integer.parseInt(inputValue);
                if (parsedValue >= min && parsedValue <= max) {
                    return parsedValue;
                }
            }

            System.out.println("Invalid input: must be an integer between " + min + " & " + max + ":");
            inputValue = reader.next();
        }
    }

    public int getOddInt() {
        System.out.println("Enter an odd positive integer");
        String inputValue = reader.next();

        while (true) {
            if (inputValue.matches("\\d+")) {
                int parsedValue = Integer.parseInt(inputValue);
                if (parsedValue % 2 == 1) {
                    return parsedValue;
                }
            }

            System.out.println("Invalid input: must be an odd positive integer:");
            inputValue = reader.next();
        }
    }

    public char getChar(char[] validChars) {
        String chars = Arrays.toString(validChars);
        System.out.println("Enter one of these characters: " + chars);
        String inputValue = reader.next();

        while (true) {
            if (inputValue.length() == 1) {
                char candidate = inputValue.charAt(0);
                for (char validChar : validChars) {
                    if (candidate == validChar) {
                        return candidate;
                    }
                }
            }

            System.out.println("Error: enter one of these characters: " + chars);
            inputValue = reader.next();
        }
    }
}
