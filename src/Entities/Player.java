/**
 * @author Tobias
 */
package Entities;

public class Player implements Comparable {
    
    private String name;
    private int life, days;
    private double credits;
    private final int DAY_CYCLE = 20;
    
    public Player(String input1, double input2) {
        this.name = input1;
        this.life = 100;
        this.credits = input2;
        this.days = DAY_CYCLE;
    }

    public int getLife() {
        return life;
    }
    
    public void setName(String input) {
        name = input;
    }

    public void setLife(int life) {
        if((this.life + life) <= 100){
            this.life += life;
        } else{
            this.life = 100;
        }
        
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
    
    public int getDayCycle() {
        return DAY_CYCLE;
    }
    
    public void setDays(int input) {
        days = days + input;
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
