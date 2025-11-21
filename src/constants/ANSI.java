//Sinray November 10th, stores ANSI-related constants used across the codebase for formatting

// Required to be recognized
package constants;

// ANSI escape codes for text formatting in the console
public final class ANSI {
    // For identifying ANSI characters, such as when considering text alignment
    public static final String REGEX = "\\u001B\\[[;\\d]*m";
    // Escape character
    public static final String RESET = "\u001B[0m";

    public static final String BOLD = "\033[1m";
    public static final String ITALIC = "\033[3m";

    // Foreground means text colour
    public static final String FOREGROUND_RED = "\u001B[31m";
    public static final String FOREGROUND_GREEN = "\u001B[32m";
    public static final String FOREGROUND_BLUE = "\u001B[34m";

    public static final String FOREGROUND_GREY = "\u001B[90m";
}