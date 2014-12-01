/**
 * @author Tobias
 */
package Entities;

public class Player implements Comparable {
    
    private String name;
    private int life, days;
    private double credits;
    
    public Player(String input1, double input2) {
        this.name = input1;
        this.life = 100;
        this.credits = input2;
        this.days = 2;
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
        days = days - input;
    }

    public String getName() {
        return name;
    }
    
    
    
    @Override
    public String toString() {
        return  "" + name + "," + credits;
    }

    @Override
    public int compareTo(Object o) {
        Player p = (Player) o;
        return (int)(p.getCredits() - credits);
    }
    
}
