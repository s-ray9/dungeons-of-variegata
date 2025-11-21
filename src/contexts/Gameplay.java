// Sinray November 10th, gameplay context that handles gameplay mechanics like dying and stores tasks

// Required to be recognized
package contexts;

// Import to access other directories
import constants.*;
import tasks.*;
import shared.*;

// Main gameplay context managing different game modes and game over state
public class Gameplay {
    // References to each task
    public Parry parry;
    public Riddle riddle;
    public Survive survive;
    public Fight fight;

    // Displays the game over screen
    public void gameOver() {
        GameManager.terminal.clear();
        GameManager.terminal.printLineSeparator();
        GameManager.terminal.printLine();
        // The \ character must be used to ignore escape characters 
        GameManager.terminal.printLine(
            ANSI.FOREGROUND_RED
            + """
                  .-"      "-.
                 /            \\
                |              |
                |,  .-.  .-.  ,|
                | )(__/  \\__)( |
                |/     /\\     \\|
      (@_       (_     ^^     _)
 _     ) \\_______\\__|IIIIII|__/_________________
(_)@8@8{}<________|-\\IIIIII/-|__________________>
       )_/        \\          /
      (@           `--------`"""
            + ANSI.RESET,
            Terminal.AlignmentType.CENTRE,
            Terminal.AlignmentBehaviour.BLOCK
        );
        GameManager.terminal.printLine();
        GameManager.terminal.printLine(
            """
 ▄▄█▀▀▀▄█                                                                 ▄█▄
▄█▀     ▀   ▄▄▄▄   ▄▄ ▄▄ ▄▄     ▄▄▄▄       ▄▄▄   ▄▄▄▄ ▄▄▄   ▄▄▄▄  ▄▄▄ ▄▄  ███
██    ▄▄▄▄ ▀▀ ▄██   ██ ██ ██  ▄█▄▄▄██    ▄█  ▀█▄  ▀█▄  █  ▄█▄▄▄██  ██▀ ▀▀ ▀█▀
▀█▄    ██  ▄█▀ ██   ██ ██ ██  ██         ██   ██   ▀█▄█   ██       ██      █
 ▀▀█▄▄▄▀█  ▀█▄▄▀█▀ ▄██ ██ ██▄  ▀█▄▄▄▀     ▀█▄▄█▀    ▀█     ▀█▄▄▄▀ ▄██▄     ▄
                                                                          ▀█▀
            """,
            Terminal.AlignmentType.CENTRE,
            Terminal.AlignmentBehaviour.BLOCK
        );
        GameManager.terminal.printLine();
        GameManager.terminal.printLineSeparator();
        GameManager.terminal.printLine();
        // Format string with given arguments, which are the ANSI codes here
        GameManager.terminal.printLine(
            String.format("[%s%s%s] | Restart",
                ANSI.ITALIC + ANSI.FOREGROUND_RED,
                Keybinds.RESTART,
                ANSI.RESET
            ),
            // Enum as part of the custom terminal implementation to align to centre
            Terminal.AlignmentType.CENTRE
        );
        GameManager.terminal.printLine();
        GameManager.terminal.printLineSeparator();
    }

    /* Checks saved point and restarts from there
    leftoverInput is input that is sunk from another context
    that way, you don't have to input multiple times */
    public void gameOverInput(String leftoverInput, String savePoint) {
        if (savePoint == null) savePoint = "";

        String key = leftoverInput;
        while (key == null) { // Waits for valid input
            try {
                key = GameManager.terminal.scanKey();
            } catch (Exception e) {}
        }
        String keyLower = key.toLowerCase();
        GameManager.terminal.clear();
        switch (keyLower) { // Switch for where the player did to restart from
            case "r" -> {
                /* Each case loads the old data that was saved at the beginning of each task.
                Then, When a new task is instantiated, it automatically saves that data.
                Finally, play the task again. */
                switch (savePoint) {
                    case "parry" -> {
                        Narrative.loadData(parry);
                        parry = new Parry();
                        parry.play();
                    }
                    case "riddle" -> {
                        Narrative.loadData(riddle);
                        riddle = new Riddle();
                        riddle.play();
                    }
                    case "survive" -> {
                        Narrative.loadData(survive);
                        survive = new Survive();
                        survive.play();
                    }
                    case "taskTwo2" -> {
                        Narrative.loadData(riddle);
                        riddle.TaskTwo2();
                    }
                    case "fight" -> {
                        Narrative.loadData(fight);
                        fight = new Fight();
                        fight.play();
                    }
                    /* If no specific task context is given (such as the death by morality ending),
                    reset data and restart from the menu so the game is not impossible */
                    default -> {
                        Narrative.resetData();
                        GameManager.menu.set();
                    }
                }
            }
            // Reprint and await input again
            default -> { gameOver(); gameOverInput(null, savePoint); }
        }
    }
}