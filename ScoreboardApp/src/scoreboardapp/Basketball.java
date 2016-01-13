/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scoreboardapp;

/**
 *
 * @author armondluthens
 */
public class Basketball extends Scoreboard{
    public void addScore(int selectedSport, int gameChoice){  
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
    
    public void setScoringMethod(int sport){
        this.scoringMethod = "Basketball";       
    }
    public String getScoringMethod(){
        return scoringMethod;
    }
    public void setLengthOfPeriod(int sport){  
        periodLength = "12:00";
    }
    public String getLengthOfPeriod(){
        return periodLength;
    }
    public void setNameOfPeriod(int sport){
        this.periodName = "Quarter";
    }
    public String getNameOfPeriod(){
        return periodName;
    }
}
