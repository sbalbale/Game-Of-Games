/*
 * File: FindTheRedThread.java
 * Purpose: Minigame where user competes with computer
 * to pull a red thread from among 20 spools
 * Author: Ben Lyons
 * Date: 4/22/2026
 */

package edu.trincoll;

import java.util.concurrent.ThreadLocalRandom;

public final class FindTheRedThread {
    private static final int TOTAL_SPOOLS = 20;
    private static final int MIN_PULL_VALUE = 1;
    private static final int MAX_PULL_VALUE = 10;

    private int remainingSpools;
    private int maxPullValue;
    private int redThreadPosition;
    private boolean isUserTurn;

    private final GetInput input = new GetInput();

    public boolean playGame() {
        maxPullValue = getMaxPullValue();
        initializeSpools();
        isUserTurn = true;

        while (true) {
            if (isUserTurn) {
                if (PlayGames.isTestMode()) {
                    System.out.println("[TEST MODE] Red Thread is at position "
                            + redThreadPosition + " of " + TOTAL_SPOOLS);
                }

                int pull = getPlayerPullAmount();
                if (checkRedThreadPulled(pull)) {
                    declareGameWinner(true);
                    return true;
                }

                executePull(pull);
                System.out.println("Spools remaining: " + remainingSpools);
                System.out.println("Now it's the computer's turn:");
                switchTurn();
            } else {
                int pull = getComputerPullAmount();
                if (checkRedThreadPulled(pull)) {
                    declareGameWinner(false);
                    return false;
                }

                executePull(pull);
                System.out.println("Spools remaining: " + remainingSpools);
                System.out.println("Now it's the player's turn:");
                switchTurn();
            }
        }
    }

    private int getMaxPullValue() {
        System.out.println("What should maximum pull value be?");
        return input.getIntInRange(MIN_PULL_VALUE, MAX_PULL_VALUE);
    }

    private void initializeSpools() {
        remainingSpools = TOTAL_SPOOLS;
        redThreadPosition = ThreadLocalRandom.current().nextInt(TOTAL_SPOOLS) + 1;
    }

    private void switchTurn() {
        isUserTurn = !isUserTurn;
    }

    private int getPlayerPullAmount() {
        System.out.println("How much would you like to pull?");
        return input.getIntInRange(MIN_PULL_VALUE, maxPullValue);
    }

    private int getComputerPullAmount() {
        int computerPull = ThreadLocalRandom.current().nextInt(maxPullValue) + 1;
        System.out.println("Computer will pull " + computerPull + " thread(s) this turn");
        return computerPull;
    }

    private void executePull(int amount) {
        if (remainingSpools - amount < 0) {
            remainingSpools = 0;
        } else {
            remainingSpools -= amount;
        }
    }

    private boolean checkRedThreadPulled(int amount) {
        return remainingSpools - amount < redThreadPosition;
    }

    private void declareGameWinner(boolean userWon) {
        System.out.println();
        if (userWon) {
            System.out.println("You win Red Thread!");
        } else {
            System.out.println("Computer wins Red Thread!");
        }
    }
}
