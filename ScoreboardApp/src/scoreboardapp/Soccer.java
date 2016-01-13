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
public class Soccer extends Scoreboard {
    public void addScore(int selectedSport, int gameChoice){  
        if(gameChoice == 1){
        curHomeScore+= 1;
        }
        else if(gameChoice == 2){
        curAwayScore+= 1;
        }
    }
    
    public void setScoringMethod(int sport){
        this.scoringMethod = "Soccer";       
    }
    public String getScoringMethod(){
        return scoringMethod;
    }
    public void setLengthOfPeriod(int sport){  
        periodLength = "45:00";
    }
    public String getLengthOfPeriod(){
        return periodLength;
    }
    public void setNameOfPeriod(int sport){
        this.periodName = "Half";
    }
    public String getNameOfPeriod(){
        return periodName;
    }
}
