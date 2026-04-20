package edu.trincoll;

import java.util.Scanner;

public final class GetInput {
    private final Scanner scanner = new Scanner(System.in);

    public int getInt(String prompt) {
        // TODO: Implement resilient integer parsing and retry on invalid input.
        System.out.print(prompt);
        return 0;
    }

    public int getIntInRange(String prompt, int min, int max, String errorMsg) {
        // TODO: Implement range validation loop using min/max and errorMsg.
        System.out.print(prompt);
        return min;
    }

    public int getOddInt(String prompt, String errorMsg) {
        // TODO: Implement odd-number validation with retry messaging.
        System.out.print(prompt);
        return 1;
    }

    public char getChar(String prompt, char[] validChars, String errorMsg) {
        // TODO: Implement char normalization and allowed-values validation.
        System.out.print(prompt);
        return validChars.length > 0 ? Character.toUpperCase(validChars[0]) : 'A';
    }
}
