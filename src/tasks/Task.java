//Sinray November 11th, abstract class for tasks that implements data saving

// Required to be recognized
package tasks;

// Imports to access other files
import contexts.*;

// This is an abstract class that tasks inherit
public abstract class Task {
    // Define saved stats
    public int Morality;
    public int HP;
    public int Intelligence;
    public int Strength;

    // Auto save on instantiation
    public Task() {
        saveData();
    }

    // Simple save method
    public final void saveData() {
        Morality = Narrative.Morality;
        HP = Narrative.HP;
        Intelligence = Narrative.Intelligence;
        Strength = Narrative.Strength;
    }
}