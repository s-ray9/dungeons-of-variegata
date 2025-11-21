//Sinray November 9th, the menu of the game, navigating the game and compiling together the tests

// Required to be recognized
package contexts;

// Imports to access other files
import constants.*; // ANSI, Terminal
import shared.*; // GameManager
import tasks.*; // Parry, Riddle, Survive, Fight

// Menu context
public class Menu {
    // Send to menu
    public void set() {
        print();
        listen(null);
    }
    
    // Print menu/title screen
    public void print() {
        GameManager.terminal.clear();
        GameManager.terminal.printLineSeparator();
        GameManager.terminal.printLine();
        /* Must use custom terminal centre block alignment implementation or else
        ASCII looks jumbled if each line is centred independently */
        GameManager.terminal.printLine(
            ANSI.FOREGROUND_RED
            + """
▀██▀▀█▄                                                             
 ██   ██  ▄▄▄ ▄▄▄  ▄▄ ▄▄▄     ▄▄▄ ▄   ▄▄▄▄    ▄▄▄   ▄▄ ▄▄▄    ▄▄▄▄
 ██    ██  ██  ██   ██  ██   ██ ██  ▄█▄▄▄██ ▄█  ▀█▄  ██  ██  ██▄ ▀
 ██    ██  ██  ██   ██  ██    █▀▀   ██      ██   ██  ██  ██  ▄ ▀█▄▄
▄██▄▄▄█▀   ▀█▄▄▀█▄ ▄██▄ ██▄  ▀████▄  ▀█▄▄▄▀  ▀█▄▄█▀ ▄██▄ ██▄ █▀▄▄█▀
                            ▄█▄▄▄▄▀"""
            + ANSI.RESET,
            // Define a large console width to wrap text in case it's not wide enough
            Integer.MAX_VALUE,
            Terminal.AlignmentType.CENTRE,
            Terminal.AlignmentBehaviour.BLOCK
        );
        GameManager.terminal.printLine();
        GameManager.terminal.printLine(
            """
▄▀▄ █▀
▀▄▀ █▀""",
            Terminal.AlignmentType.CENTRE,
            Terminal.AlignmentBehaviour.BLOCK
        );
        GameManager.terminal.printLine();
        GameManager.terminal.printLine(
            ANSI.FOREGROUND_GREEN
            + """
 ██▒   █▓ ▄▄▄      ██▀███    ██▓ ▓█████ ▄████  ▄▄▄     ▄▄▄█████▓ ▄▄▄
▓██░   █▒▒████▄   ▓██ ▒ ██▒▒▓██▒ ▓█   ▀ ██▒ ▀█▒████▄   ▓  ██▒ ▓▒▒████▄
 ▓██  █▒░▒██  ▀█▄ ▓██ ░▄█ ▒▒▒██▒ ▒███  ▒██░▄▄▄▒██  ▀█▄ ▒ ▓██░ ▒░▒██  ▀█▄
  ▒██ █░░░██▄▄▄▄██▒██▀▀█▄  ░░██░ ▒▓█  ▄░▓█  ██░██▄▄▄▄██░ ▓██▓ ░ ░██▄▄▄▄██
   ▒▀█░  ▒▓█   ▓██░██▓ ▒██▒░░██░▒░▒████▒▓███▀▒▒▓█   ▓██  ▒██▒ ░ ▒▓█   ▓██
   ░ ▐░  ░▒▒   ▓▒█░ ▒▓ ░▒▓░ ░▓  ░░░ ▒░ ░▒   ▒ ░▒▒   ▓▒█  ▒ ░░   ░▒▒   ▓▒█
   ░ ░░  ░ ░   ▒▒   ░▒ ░ ▒ ░ ▒ ░░ ░ ░   ░   ░ ░ ░   ▒▒     ░    ░ ░   ▒▒
     ░░    ░   ▒    ░░   ░ ░ ▒ ░    ░   ░   ░   ░   ▒    ░        ░   ▒
      ░        ░     ░       ░  ░   ░       ░       ░                 ░"""
            + ANSI.RESET,
            Integer.MAX_VALUE,
            Terminal.AlignmentType.CENTRE,
            Terminal.AlignmentBehaviour.BLOCK
        );
        GameManager.terminal.printLine();
        GameManager.terminal.printLineSeparator();
        GameManager.terminal.printLine();
        GameManager.terminal.printLine(
            // Accept arguments to simplify the process of formatting
            String.format(
                "By %sSinray Yang%s & %sSneh Patel%s",
                ANSI.BOLD, ANSI.RESET, ANSI.BOLD, ANSI.RESET
            ),
            Terminal.AlignmentType.CENTRE
        );
        GameManager.terminal.printLine();
        GameManager.terminal.printLineSeparator();
        GameManager.terminal.printLine();
        // Options are printed together in a block so they are aligned
        GameManager.terminal.printLine(
            String.format(
                "[%s%s%s] | Play\n[%s%s%s] | Quit",
                ANSI.ITALIC + ANSI.FOREGROUND_GREEN, Keybinds.PLAY, ANSI.RESET,
                ANSI.ITALIC + ANSI.FOREGROUND_RED, Keybinds.QUIT, ANSI.RESET
            ),
            Terminal.AlignmentType.CENTRE,
            Terminal.AlignmentBehaviour.BLOCK
        );
        GameManager.terminal.printLine();
        GameManager.terminal.printLineSeparator();
    }

    /* Separate method that listens for menu input in case there is leftover input
    like in Gameplay.gameOver(). */
    public void listen(String leftoverInput) {
        String key = leftoverInput != null ? leftoverInput : GameManager.terminal.scanKey();
        // Lowercase to be case insensitive
        String keyLower = key.toLowerCase();
        GameManager.terminal.clear();
        switch (keyLower) {
            case "p" -> play();
            case "q" -> quit();
            default -> set();
        }
    }

    // Play the game by call the narrative sections, dialogues, and tasks in order
    public void play() {
        // Introduction
        GameManager.narrator.introduce();
        Narrative.introduceStats();
        Narrative.introDialogue();
        
        // Task one (Floor 1)
        Narrative.TaskOneIntro();
        Narrative.TaskOneInstructions();
        GameManager.gameplay.parry = new Parry();
        GameManager.gameplay.parry.play();

        // Task two (Wall of Mystery)
        Narrative.TaskTwoIntro();
        Narrative.TaskTwoInstructions();
        GameManager.gameplay.riddle = new Riddle();
        GameManager.gameplay.riddle.play();
        
        // Task three (Survive)
        Narrative.TaskThreeIntro();
        Narrative.TaskThreeInstructions();
        GameManager.gameplay.survive = new Survive();
        GameManager.gameplay.survive.play();
        
        // Task four (Fight)
        Narrative.TaskFourIntro();
        Narrative.TaskFourInstructions();
        GameManager.gameplay.fight = new Fight();
        GameManager.gameplay.fight.play();

        // Conclusion
        Narrative.conclude();
        // Reset all data if the player wants to replay the game
        Narrative.resetData();

        // Return to the menu
        set();
    }

    //End screen
    public void quit() {
        GameManager.terminal.clear();
        GameManager.terminal.printLineSeparator();
        GameManager.terminal.printLine();
        GameManager.terminal.printLine("""
█▀▀██▀▀█ ▀██                       ▀██                                    
   ██     ██ ▄▄    ▄▄▄▄   ▄▄ ▄▄▄    ██  ▄▄      ▄▄▄▄ ▄▄▄   ▄▄▄   ▄▄▄ ▄▄▄
   ██     ██▀ ██  ▀▀ ▄██   ██  ██   ██ ▄▀        ▀█▄  █  ▄█  ▀█▄  ██  ██
   ██     ██  ██  ▄█▀ ██   ██  ██   ██▀█▄         ▀█▄█   ██   ██  ██  ██
  ▄██▄   ▄██▄ ██▄ ▀█▄▄▀█▀ ▄██▄ ██▄ ▄██▄ ██▄        ▀█     ▀█▄▄█▀  ▀█▄▄▀█▄
                                                ▄▄ █
                                                 ▀▀""",
            Terminal.AlignmentType.CENTRE,
            Terminal.AlignmentBehaviour.BLOCK
        );
        GameManager.terminal.printLine();
        GameManager.terminal.printLine("""
  ▄▀█▄                             ▀██                     ██                   ▄█▄
▄██▄     ▄▄▄   ▄▄▄ ▄▄     ▄▄▄ ▄▄▄   ██   ▄▄▄▄    ▄▄▄▄ ▄▄▄ ▄▄▄  ▄▄ ▄▄▄     ▄▄▄ ▄ ███
 ██    ▄█  ▀█▄  ██▀ ▀▀     ██▀  ██  ██  ▀▀ ▄██    ▀█▄  █   ██   ██  ██   ██ ██  ▀█▀
 ██    ██   ██  ██         ██    █  ██  ▄█▀ ██     ▀█▄█    ██   ██  ██    █▀▀    █
▄██▄    ▀█▄▄█▀ ▄██▄        ██▄▄▄▀  ▄██▄ ▀█▄▄▀█▀     ▀█    ▄██▄ ▄██▄ ██▄  ▀████▄  ▄
                           ██                    ▄▄ █                   ▄█▄▄▄▄▀ ▀█▀
                          ▀▀▀▀                    ▀▀""",
            Integer.MAX_VALUE,
            Terminal.AlignmentType.CENTRE,
            Terminal.AlignmentBehaviour.BLOCK
        );
        GameManager.terminal.printLine();
        GameManager.terminal.printLineSeparator();
    }
}