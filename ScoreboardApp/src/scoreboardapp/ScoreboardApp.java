/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scoreboardapp;

import java.util.Scanner;

/**
 *
 * @author armondluthens
 */
public class ScoreboardApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int selectedSport;
        Scanner input = new Scanner(System.in);
        int gameChoice;
        do {
            System.out.println("Select type of game:");
            System.out.println("1. Football");
            System.out.println("2. Basketball");
            System.out.println("3. Soccer");
            System.out.println("4. Hockey");
            System.out.println("5. Exit Program.");
            System.out.print("Choice: ");
            selectedSport = input.nextInt();

            while (selectedSport < 1 || selectedSport > 5) {
                System.out.println("INVALID ENTRY. CHOOSE A VALID OPTION");
                selectedSport = input.nextInt();
            }
            if (selectedSport != 5) {
                //Scoreboard sportsObject = new Scoreboard();
                if(selectedSport == 1){
                    Football sportsObject = new Football();
                }
                else if(selectedSport == 2){
                    Basketball sportsObject = new Basketball();
                }
                else if(selectedSport == 3){
                    Soccer sportsObject = new Soccer();
                }
                else{
                    Hockey sportsObject = new Hockey();
                }
                sportsObject.setScoringMethod(selectedSport);
                sportsObject.setNameOfPeriod(selectedSport);

                System.out.println("\nEnter Home Team: ");
                String homeTeam = input.next();
                System.out.println("\nEnter Away Team: ");
                String awayTeam = input.next();
                sportsObject.setTeams(homeTeam, awayTeam);

                System.out.println("Scoring Method (Selected Sport): " + sportsObject.getScoringMethod());

                while ((selectedSport == 1 && sportsObject.getCurrentPeriodOfPlay() <= 4)
                        || (selectedSport == 2 && sportsObject.getCurrentPeriodOfPlay() <= 4)
                        || (selectedSport == 3 && sportsObject.getCurrentPeriodOfPlay() <= 2)
                        || (selectedSport == 4 && sportsObject.getCurrentPeriodOfPlay() <= 3)) {
                    System.out.println(sportsObject.getHomeTeam() + " - " + sportsObject.getHomeScore() + ", " + sportsObject.getAwayTeam() + " - " + sportsObject.getAwayScore());
                    System.out.println(sportsObject.getNameOfPeriod() + ": " + sportsObject.getCurrentPeriodOfPlay() + "\n");
                    printGameMenu(homeTeam, awayTeam, selectedSport);
                    System.out.print("Enter Choice: ");
                    gameChoice = input.nextInt();
                    System.out.println();
                    if (selectedSport == 1) {
                        if (gameChoice != 11) {
                            sportsObject.addScore(selectedSport, gameChoice);
                        } else if (gameChoice == 11) {
                            //increment period   
                            sportsObject.endCurrentPeriodOfPlay();
                        }
                    
                    } else if (selectedSport == 2) {
                        if (gameChoice != 7) {
                            sportsObject.addScore(selectedSport, gameChoice);
                        } else if (gameChoice == 7) {
                            //increment period
                            sportsObject.endCurrentPeriodOfPlay();
                        }
                    
                    } else {
                        if (gameChoice != 3) {
                            sportsObject.addScore(selectedSport, gameChoice);
                        } else if (gameChoice == 3) {
                            //increment period
                            sportsObject.endCurrentPeriodOfPlay();
                        }
                    }
                    System.out.println(sportsObject.getHomeTeam() + " - " + sportsObject.getHomeScore() + ", " + sportsObject.getAwayTeam() + " - " + sportsObject.getAwayScore());
                    if((selectedSport == 1 && sportsObject.getCurrentPeriodOfPlay() > 4)
                        || (selectedSport == 2 && sportsObject.getCurrentPeriodOfPlay() > 4)
                        || (selectedSport == 3 && sportsObject.getCurrentPeriodOfPlay() > 2)
                        || (selectedSport == 4 && sportsObject.getCurrentPeriodOfPlay() > 3)){
                            if(sportsObject.getHomeScore() > sportsObject.getAwayScore()){
                                System.out.println("Winner: " + sportsObject.getHomeTeam()) ;
                            }
                            else if(sportsObject.getHomeScore() < sportsObject.getAwayScore()){
                                System.out.println("Winner: " + sportsObject.getAwayTeam());
                            }
                            else{
                                System.out.println("Game ended in tie.");
                            }
                            System.out.println("FINAL SCORE\n");
                    }
                    else{
                        System.out.println(sportsObject.getNameOfPeriod() + ": " + sportsObject.getCurrentPeriodOfPlay() + "\n");
                    }
                    
                }
            }

        } while (selectedSport != 5);
        System.out.println("Goodbye.");
    } //end main method

    public static void printGameMenu(String homeTeam, String awayTeam, int selectedSport) {
        if (selectedSport == 1) {
            System.out.println("Menu:");
            System.out.println("1. " + homeTeam + " touchdown");
            System.out.println("2. " + homeTeam + " field goal");
            System.out.println("3. " + homeTeam + " extra-point");
            System.out.println("4. " + homeTeam + " two-point conversion");
            System.out.println("5. " + homeTeam + " safety");
            System.out.println("6. " + awayTeam + " touchdown");
            System.out.println("7. " + awayTeam + " field goal");
            System.out.println("8. " + awayTeam + " extra-point");
            System.out.println("9. " + awayTeam + " two-point conversion");
            System.out.println("10. " + awayTeam + " safety");
            System.out.println("11. End Quarter");
        } else if (selectedSport == 2) {
            System.out.println("Menu:");
            System.out.println("1. " + homeTeam + " free-throw");
            System.out.println("2. " + homeTeam + " 2-point basket");
            System.out.println("3. " + homeTeam + " 3-pointer");
            System.out.println("4. " + awayTeam + " free-throw");
            System.out.println("5. " + awayTeam + " 2-point basket");
            System.out.println("6. " + awayTeam + " 3-pointer");
            System.out.println("7. End Quarter");
        } else if(selectedSport == 3) {
            System.out.println("Menu:");
            System.out.println("1. " + homeTeam + " goal");
            System.out.println("2. " + awayTeam + " goal");
            System.out.println("3. End Half");
        }
        else{
            System.out.println("Menu:");
            System.out.println("1. " + homeTeam + " goal");
            System.out.println("2. " + awayTeam + " goal");
            System.out.println("3. End Period");
        }
    }
} // end public class ScoreboardApp
