//Sinray November 11th, HeartParser code for formatting a string of hearts
package util;

// Import to access other files
import constants.*;

public class HeartParser {
    // Method that returns a string of hearts given a current HP and full HP
    public static String getHearts(int HP, int full) {
        String hearts;
        if (full > 0) {
            // Start with red
            hearts = ANSI.FOREGROUND_RED;
            // Iterate over the number of hearts
            for (int i = 0; i < full; i++) {
                // If still below current HP, make it red. If above, make it grey
                hearts += i < HP ? ANSI.FOREGROUND_RED : ANSI.FOREGROUND_GREY;
                // Append ANSI escape character if we have reached the end of current HP
                hearts += "❤ " + (i == HP ? ANSI.RESET : "");
            }
            // Escape
            hearts += ANSI.RESET;
        } else {
            // When there are no hearts to display
            hearts = "nothing";
        }
        return hearts;
    }

    // Overload if we just want a string of hearts and no full HP
    public static String getHearts(int HP) {
        return getHearts(HP, HP);
    }
}