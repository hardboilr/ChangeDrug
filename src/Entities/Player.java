/**
 * @author Tobias
 */
package Entities;

public class Player{
    
    private String name;
    private int life, days;
    private double credits;
    
    public Player(String input1) {
        this.name = input1;
        this.life = 100;
        this.credits = 5000;
        this.days = 20;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public double getCredits() {
        return credits;
    }

    public void setCredits(double credits) {
        this.credits = credits;
    }
    
    public int getDays() {
        return days;
    }
    
    public void setDays(int input) {
        days = days + input;
    }
    
    
    @Override
    public String toString() {
        return  name + "," + credits;
    }
    
}
