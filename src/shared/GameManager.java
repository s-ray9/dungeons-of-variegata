// Sinray November 9th, top-level game manager that orchestrates components

package shared;

import contexts.*;

public class GameManager { // Central game manager
    public static Terminal terminal = new Terminal();

    public static Menu menu = new Menu(); // Main menu
    public static Gameplay gameplay = new Gameplay();
    public static Narrative narrator = new Narrative();

    public static void main(String[] args) { // Entry point
        GameManager gameManager = new GameManager();
        gameManager.initiate();
    }

    public void initiate() {
        menu.set();
    }
}