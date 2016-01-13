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
public class Scoreboard {
        private String homeTeam;
        private String awayTeam;
        public int curHomeScore; //change to private after subclass is built
        public int curAwayScore; //change to private after subclass is built
        private String scoringMethod;
        private int currentPeriodOfPlay;
        private String periodLength;
        private String periodName;
        
        Scoreboard(){
            homeTeam = null;
            awayTeam = null;
            curHomeScore = 0;
            curAwayScore = 0;
            currentPeriodOfPlay = 1;
        }
        //Scanner input = new Scanner(System.in);
        
        public void setTeams(String homeTeam, String awayTeam){
            this.homeTeam = homeTeam;
            this.awayTeam = awayTeam;
        }
        public String getHomeTeam(){
            return homeTeam;
        }
        public String getAwayTeam(){
            return awayTeam;
        }
        /*
        public void addScore(int selectedSport, int gameChoice){
            if(selectedSport == 1){
                if(gameChoice == 1){
                    curHomeScore+= 6;
                }
                else if(gameChoice == 2){
                    curHomeScore+= 3;
                }
                else if(gameChoice == 3){
                    curHomeScore+= 1;
                }
                else if(gameChoice == 4){
                    curHomeScore+= 2;
                }
                else if(gameChoice == 5){
                    curHomeScore+= 2;
                }
                else if(gameChoice == 6){
                    curAwayScore+= 6;
                }
                else if(gameChoice == 7){
                    curAwayScore+= 3;
                }
                else if(gameChoice == 8){
                    curAwayScore+= 1;
                }
                else if(gameChoice == 9){
                    curAwayScore+= 2;
                }
                else if(gameChoice == 10){
                    curAwayScore+= 2;
                }
            }
            else if(selectedSport == 2){
                if(gameChoice == 1){
                    curHomeScore+= 1;
                }
                else if(gameChoice == 2){
                    curHomeScore+= 2;
                }
                else if(gameChoice == 3){
                    curHomeScore+= 3;
                }
                else if(gameChoice == 4){
                    curAwayScore+= 1;
                }
                else if(gameChoice == 5){
                    curAwayScore+= 2;
                }
                else if(gameChoice == 6){
                    curAwayScore+= 3;
                }
            }
            else{
                if(gameChoice == 1){
                    curHomeScore+= 1;
                }
                else if(gameChoice == 2){
                    curAwayScore+= 1;
                }
            }
        }
        */
        public int getHomeScore(){
            return curHomeScore;
        }
        public int getAwayScore(){
            return curAwayScore;
        }
        /*
        public void setScoringMethod(int sport){
            if(sport == 1){
                this.scoringMethod = "Football";
            }
            else if(sport == 2){
                this.scoringMethod = "Basketball";
            }
            else if(sport == 3){
                this.scoringMethod = "Soccer";
            }
            else{
                this.scoringMethod = "Hockey";
            }
        }
        public String getScoringMethod(){
            return scoringMethod;
        }
        */
        public void startGame(){
            
        }
        
        public void endCurrentPeriodOfPlay(){
          currentPeriodOfPlay++;
        }
        public int getCurrentPeriodOfPlay(){
            return currentPeriodOfPlay;
        }
        /*
        public void setLengthOfPeriod(int sport){
            if(sport == 1){
                periodLength = "15:00";
            }
            else if(sport == 2){
                periodLength = "12:00";
            }
            else if(sport == 3){
                periodLength = "45:00";
            }  
            else{
                periodLength = "20:00";
            }  
        }
        public String getLengthOfPeriod(){
            return periodLength;
        }
        */
        /*
        public void setNameOfPeriod(int sport){
            if(sport == 1 || sport ==2){
                this.periodName = "Quarter";
            }
            else if(sport == 3){
                this.periodName = "Half";
            }
            else {
                this.periodName = "Period";
            }
        }
        public String getNameOfPeriod(){
            return periodName;
        }*/
}
