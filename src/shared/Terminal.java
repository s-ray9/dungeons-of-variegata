//Sinray November 10th, custom terminal implementation for flexible printing and scanning

package shared;

import java.io.*;
import java.nio.charset.*;
import java.util.*;

import constants.*;
import util.*;

// Terminal handling class
public class Terminal {
    public static String registry = "";

    public enum AlignmentType { LEFT, RIGHT, CENTRE }
    public enum AlignmentBehaviour { LINE, BLOCK }

    public PrintStream printStream = new PrintStream(System.out, true, StandardCharsets.UTF_8);
    public Scanner scanner = new Scanner(System.in);

    public int width;
    public long interval;

    public Terminal(int width, long interval) {
        this.width = width;
        this.interval = interval;

        // Required for UTF-8 compatible characters to show (required for almost all ASCII characters)
        System.setOut(printStream);
    }

    public Terminal() { // Default constructor
        this(80, 10l); // Accept a default console width of 80 and print slow interval of 10 milliseconds
    }

    // Keep record of what's printed in the console
    private void register(String text) {
        registry += text;
        System.out.print(text);
    }

    private void registerLine(String text) {
        register(text + "\n");
    }

    public void clear() { // Clears the terminal screen
        registry = "";
        System.out.print("\033[H\033[2J"); // ANSI escape code to clear screen
        System.out.flush(); // Ensure the output is flushed
    }

    // Internal helper to handle all printing/alignment/wrapping
    private void output(String text, Integer width, AlignmentType alignmentType, AlignmentBehaviour alignmentBehaviour, boolean newline) {
        if (width == null) width = this.width;
        // Wrap text first
        text = TextWrapper.wrapText(text, width);

        // Determine what alignment type (switch statement)
        switch (alignmentType) {
            case LEFT -> {
                if (newline) registerLine(text); else register(text);
            }
            case RIGHT -> {
                String out = String.format("%" + this.width + "s", text);
                if (newline) registerLine(out); else register(out);
            }
            // If it's centred, we look at AlignmentBehaviour
            case CENTRE -> {
                String[] lines = text.split("\n");
                switch (alignmentBehaviour) {
                    // Centre text based on a per line basis
                    case LINE -> {
                        for (String line : lines) {
                            // Ignore ANSI so it doesn't break
                            String plainLine = line.replaceAll(ANSI.REGEX, "");
                            plainLine = plainLine.replace("\\", "\\\\");
                            int visibleLength = plainLine.length();
                            if (visibleLength >= this.width) {
                                if (newline) registerLine(text); else register(text);
                                continue;
                            }
                            // Add the padding
                            int leftPadding = (this.width - visibleLength) / 2;
                            String out = " ".repeat(leftPadding) + line;
                            if (newline) registerLine(out); else register(out);
                        }
                    }
                    // Treat the text as one big chunk and centre it
                    case BLOCK -> {
                        int maxVisibleLen = 0;
                        for (String line : lines) {
                            // Ignore ANSI so it doesn't break
                            String plainLine = line.replaceAll(ANSI.REGEX, "");
                            plainLine = plainLine.replace("\\", "\\\\");
                            maxVisibleLen = Math.max(maxVisibleLen, plainLine.length());
                        }
                        // Add the padding
                        int blockPadding = Math.max(0, (this.width - maxVisibleLen) / 2);
                        String basePadding = " ".repeat(blockPadding);
                        for (String line : lines) {
                            String out = basePadding + line;
                            if (newline) registerLine(out); else register(out);
                        }
                    }
                }
            }
        }
    }

    // Overload methods for different cases
    public void print(String text, Integer width, AlignmentType alignmentType, AlignmentBehaviour alignmentBehaviour) {
        output(text, width, alignmentType, alignmentBehaviour, false);
    }

    public void print(String text, Integer width, AlignmentType alignmentType) {
        print(text, width, alignmentType, AlignmentBehaviour.LINE);
    }

    public void print(String text, AlignmentType alignmentType, AlignmentBehaviour alignmentBehaviour) {
        print(text, null, alignmentType, alignmentBehaviour);
    }

    public void print(String text, AlignmentType alignmentType) {
        print(text, null, alignmentType, AlignmentBehaviour.LINE);
    }

    public void print(String text) {
        print(text, AlignmentType.LEFT);
    }

    // More overload methods
    public void printLine(String text, Integer width, AlignmentType alignmentType, AlignmentBehaviour alignmentBehaviour) {
        output(text, width, alignmentType, alignmentBehaviour, true);
    }

    public void printLine(String text, Integer width, AlignmentType alignmentType) {
        printLine(text, width, alignmentType, AlignmentBehaviour.LINE);
    }

    public void printLine(String text, AlignmentType alignmentType, AlignmentBehaviour alignmentBehaviour) {
        printLine(text, null, alignmentType, alignmentBehaviour);
    }

    public void printLine(String text, AlignmentType alignmentType) {
        printLine(text, null, alignmentType, AlignmentBehaviour.LINE);
    }

    public void printLine(String text) {
        printLine(text, AlignmentType.LEFT);
    }

    public void printLine() {
        printLine("");
    }

    // printSlow methods (ignores ANSI and escape characters to avoid broken printing)
    public void printSlow(String text, Integer width, Long interval) {
        if (width == null) width = this.width;
        if (interval == null) interval = this.interval;
        text = TextWrapper.wrapText(text, width);

        boolean isInEscape = false;
        StringBuilder escapeBuffer = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == '\u001B') {
                isInEscape = true;
                escapeBuffer.append(c);
                continue;
            }
            if (isInEscape) {
                escapeBuffer.append(c);
                if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')) {
                    register(escapeBuffer.toString());
                    escapeBuffer.setLength(0);
                    isInEscape = false;
                }
                continue;
            }

            register(Character.toString(c));

            try { Thread.sleep(interval); } catch (InterruptedException ignored) {}
        }

        System.out.flush();
    }

    // More overload methods
    public void printSlow(String text, Long interval) {
        printSlow(text, null, interval);
    }

    public void printSlow(String text, Integer width) {
        printSlow(text, width, null);
    }

    public void printSlow(String text) {
        printSlow(text, null, null);
    }

    public void printLineSlow(String text, Integer width, Long interval) {
        printSlow(text, width, interval);
        printLine();
    }

    public void printLineSlow(String text, Long interval) {
        printLineSlow(text, null, interval);
    }

    public void printLineSlow(String text, Integer width) {
        printLineSlow(text, width, null);
    }

    public void printLineSlow(String text) {
        printLineSlow(text, null, null);
    }

    public void printLineSlow() {
        printLineSlow("", null, null);
    }

    public void printLineSeparator() {
        printLine("=".repeat(width));
    }
    
    // Scans a single key input from the user
    public String scanKey() {
        String input = scanner.nextLine();
        String key = input.isBlank() ? "" : Character.toString(input.charAt(0));
        return key;
    }
}