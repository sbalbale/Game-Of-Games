/* 
* File: FindTheRedThread.java 
* Purpose: Minigame where user competes with computer 
* to pull a red thread from among 20 spools 
* Author: Ben Lyons 
* Date: 4/22/2026 
*/ 
 
package edu.trincoll; 
 
public final class FindTheRedThread { 
    private static final int TOTAL_SPOOLS = 20; 
 
    private int remainingSpools; 
    private int maxPullValue; 
    private int redThreadPosition; 
    private boolean isUserTurn; 
 
    private final GetInput input = new GetInput(); 
 
    public boolean playGame() { 
        maxPullValue = getMaxPullValue(); 
        initializeSpools(); 
        isUserTurn = true; 
        int pull; 
        while(true) { 
            if(isUserTurn){ 
                //Code for the user's turn pulling spools 
                pull = getPlayerPullAmount(); 
                if(checkRedThreadPulled(pull)){ 
                    declareGameWinner(isUserTurn); 
                    return isUserTurn; //return true and break the loop, ending the game 
                } 
                executePull(pull); 
                System.out.println("Spools remaining: "+remainingSpools); 
                System.out.println("Now it's the computer's turn:"); 
                switchTurn(); 
            } 
            else{ 
                //Code for the computer's turn pulling spools 
                pull = getComputerPullAmount(); 
                if(checkRedThreadPulled(pull)){ 
                    declareGameWinner(isUserTurn); 
                    return isUserTurn; //return false and break the loop, ending the game 
                } 
                executePull(pull); 
                System.out.println("Spools remaining: "+remainingSpools); 
                System.out.println("Now it's the player's turn:"); 
                switchTurn(); 
            } 
        } 
    }  

    private int getMaxPullValue() { 
        System.out.println("What should maximum pull value be?"); 
        int maxVal  = input.getIntInRange(1, 10); 
        return maxVal; 
    } 

    private void initializeSpools() { 
        this.remainingSpools = TOTAL_SPOOLS; //reset spool amount to 20 
        this.redThreadPosition = (int)(Math.random()*20)+1; 
    } 

    private void switchTurn() { 
        this.isUserTurn = !this.isUserTurn; 
    }  

    private int getPlayerPullAmount() { 
        System.out.println("How much would you like to pull?"); 
        int playerPull = input.getIntInRange(1, maxPullValue); 
        return playerPull; 
    } 
 
    private int getComputerPullAmount() { 
        int computerPull = (int)(Math.random()*maxPullValue)+1; //a random number between 1 to maxValue 
        System.out.println("Computer will pull "+computerPull+" thread(s) this turn"); 
        return computerPull; 
    } 

 

    private void executePull(int amount) { 
        if(remainingSpools - amount <0){ 
            this.remainingSpools = 0; 
        } 
        else{ 
            this.remainingSpools -= amount; 
        }  
    } 
 
    private boolean checkRedThreadPulled(int amount) { 
        return remainingSpools - amount < redThreadPosition; 
    } 
 
    private void declareGameWinner(boolean userWon) { 
        System.out.println(); 
        if(userWon){ 
            System.out.println("You win Red Thread!"); 
            } 
        else{ 
            System.out.println("Computer wins Red Thread!"); 
            } 
    } 
}