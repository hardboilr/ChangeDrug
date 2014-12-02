/**
 * @author Tobias & Sebastian
 */
package Entities;

public class Event {
    private final String name, description;
    private int probability;
    private final double lifeModifier, creditsModifier, drugModifier;

    public Event(String nameInput, String descrpInput, int probInput, double lifeModInput, double credModInput, double drugModInput) {
        this.name = nameInput;
        this.description = descrpInput;
        this.probability = probInput;
        this.lifeModifier = lifeModInput;
        this.creditsModifier = credModInput;
        this.drugModifier = drugModInput;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getProbability() {
        return probability;
    }
    
    public void setProbability(int input) {
        probability = probability + input;
    }
    

    public double getLifeModifier() {
        return lifeModifier;
    }

    public double getCreditsModifier() {
        return creditsModifier;
    }

    public double getDrugModifier() {
        return drugModifier;
    }

   
    
    
    
}
