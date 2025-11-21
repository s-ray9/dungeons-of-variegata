//Sinray November 11th, TextWrapper code for wrapping text
package util;

// Imports
import java.util.*;
import java.util.regex.*;

public class TextWrapper {
    // Wraps text, returning a string
    public static String wrapText(String text, int width) {
        if (width <= 0) return text;

        // StringBuilder to manipulate strings more effectively
        StringBuilder result = new StringBuilder();
        /*
        Split in an array of lines by \n (line breaks)
        -1 indicates split until the last character
        */
        String[] lines = text.split("\n", -1);

        // Identify ANSI pattern
        Pattern ansiPattern = java.util.regex.Pattern.compile("\u001B\\[[0-9;]*m");

        // Iterate through lines
        for (int l = 0; l < lines.length; l++) {
            String line = lines[l];
            StringBuilder lineBuilder = new StringBuilder();
            String activeANSI = "";

            // Array of words, preserving all spaces
            String[] words = line.split("(?<= )|(?= )");
            int visible = 0;
            
            // Iterate through words
            for (String word : words) {
                // Remove all ANSI characters
                String plainWord = word.replaceAll("\u001B\\[[0-9;]*m", "");
                int wordLen = plainWord.length();

                if (visible + wordLen > width && !plainWord.isBlank()) {
                    // wrap to next line
                    lineBuilder.append("\n").append(activeANSI);
                    visible = 0;
                }

                // preserve ANSI
                java.util.regex.Matcher matcher = ansiPattern.matcher(word);
                String prefixANSI = "";
                // Matching patterns
                while (matcher.find()) prefixANSI += matcher.group();
                if (!prefixANSI.isEmpty()) activeANSI = prefixANSI;

                // Add word to the StringBuilder lineBuilder
                lineBuilder.append(word);
                visible += wordLen;
            }

            result.append(lineBuilder);
            if (l < lines.length - 1) result.append("\n");
        }

        return result.toString();
    }

    /*
    Overload in case we don't want to specify a width every time
    Fallback to the largest integer value.
    */
    public static String wrapText(String text) {
        return wrapText(text, Integer.MAX_VALUE);
    }
}