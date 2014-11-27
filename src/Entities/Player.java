/**
 * @author Tobias
 */
package Entities;

public class Player {
    
    private String name;
    private int life;
    private double credits;
    
    public Player(String input1) {
        this.name = input1;
        this.life = 100;
        this.credits = 5000;
    }
}
