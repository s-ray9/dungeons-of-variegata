//Sinray November 11th, task three survive code
package tasks;

// Imports to access other files
import constants.*;
import contexts.*;
import shared.*;

// Inheritance
public class Survive extends Task {
    // Constants
    public static final int GRID_SIZE = 15;
    public static final int PLAYER_START_X = GRID_SIZE / 2, PLAYER_START_Y = GRID_SIZE - 2;
    public static final int BOSS_START_X = GRID_SIZE / 2, BOSS_START_Y = 1;

    // Speed is how many cells per every tick ms
    public static final int PLAYER_STEP = 1;

    public static final double BOSS_SPEED_NORMAL = 0.05;
    public static final double BOSS_SPEED_CHARGE = 0.8;
    public static final int CHARGE_COOLDOWN = 4_000;
    public static final int CHARGE_DURATION = 400;

    public static final int TICK_MS = 50;
    public static final int SURVIVE_TIME_MS = 60*1_000;
    
    // Positional values
    public volatile int playerX = PLAYER_START_X;
    public volatile int playerY = PLAYER_START_Y;

    public volatile double bossX = BOSS_START_X;
    public volatile double bossY = BOSS_START_Y;

    public volatile boolean isRunning = true;
    public volatile boolean hasWon = false;
    // Readily accessible last key that was inputted
    private volatile Character lastKey;

    // Leftover input
    private volatile String leftoverInput;

    // Print the title
    public void print() {
        GameManager.terminal.clear();
        GameManager.terminal.printLine(
            ANSI.FOREGROUND_RED +
            """
 ▄▀▀ █ █ █▀▄ █ █ █ █ █ ██▀
 ▄██ ▀▄█ █▀▄ ▀▄▀ █ ▀▄▀ █▄▄"""
            + ANSI.RESET,
            Terminal.AlignmentType.CENTRE,
            Terminal.AlignmentBehaviour.BLOCK
        );
    }

    // Start the task
    public void play() {
        isRunning = true;

        /*
        Input thread (movement + game over input)
        Threads allow asynchronous execution; scanner doesn't block everything
        */
        Thread inputThread = new Thread(() -> {
            // Continuously loop for input
            while (true) {
                String line = GameManager.terminal.scanKey();
                if (isRunning) {
                    // Game is still live, handle movement
                    if (!line.isBlank()) lastKey = line.charAt(0);
                } else {
                    leftoverInput = line;
                    break; // exit thread
                }
            }
        });
        inputThread.start();

        // Boss thread
        Thread bossThread = new Thread(() -> {
            // Keep track of time between charges
            long lastCharge = System.currentTimeMillis();
            boolean isCharging = false;

            try {
                while (isRunning) {
                    // Check if it's time to charge if delta time exceeds charge cooldown
                    long now = System.currentTimeMillis();
                    if (!isCharging && now - lastCharge >= CHARGE_COOLDOWN) {
                        isCharging = true;
                        lastCharge = now;
                    }
                    
                    // Move the golem/boss
                    moveBossDirect(isCharging);

                    // Stop charging
                    if (isCharging && now - lastCharge > CHARGE_DURATION) isCharging = false;
                    // Wait until next render step
                    Thread.sleep(TICK_MS);
                }
            // Safeguard
            } catch (InterruptedException ignored) {}
        });
        // Daemon threads don't prevent exiting if the main game loop stops
        bossThread.setDaemon(true);
        bossThread.start();

        // Main game loop
        long gameStart = System.currentTimeMillis();
        while (isRunning) {
            // Track time elapsed/survived
            long frameStart = System.currentTimeMillis();
            long elapsed = frameStart - gameStart;
            if (elapsed >= SURVIVE_TIME_MS) {
                hasWon = true;
                isRunning = false;
                break;
            }

            // Process movement
            Character move = lastKey;
            // Flush input
            lastKey = null;
            // Fast way of keying the move direction
            if (move != null && "wasd".indexOf(move) != -1) movePlayer(move);

            // Collision check
            if (playerX == (int) Math.round(bossX) && playerY == (int) Math.round(bossY)) {
                isRunning = false; // triggers game over input in the scanner thread
                continue;
            }

            // Printing the screen
            print();
            GameManager.terminal.printLine();
            drawGrid();

            // Tick rate
            long deltaTime = System.currentTimeMillis() - frameStart;
            long sleep = TICK_MS - deltaTime;
            if (sleep > 0) {
                try { Thread.sleep(sleep); } catch (InterruptedException ignored) {}
            }
        }

        // Wait for leftover input
        while (leftoverInput != null) {}

        if (!hasWon) {
            GameManager.terminal.clear();
            GameManager.terminal.printLineSlow(
                "The golem catches up to your party, clobbering its fist straight through your body. "
                + "Torn to shreds in seconds, you don't get the privilege of last words. Your life "
                + "fades to black…",
                40l
            );
            GameManager.terminal.printLineSlow();
            Narrative.printContinue();
            
            // Kill the player and send them to the save at the beginning of Survive
            GameManager.gameplay.gameOver();
            GameManager.gameplay.gameOverInput(leftoverInput, "survive");
        }
    }

    // Player movement
    public void movePlayer(char c) {
        // Increment position
        switch (c) {
            case 'w' -> playerY -= PLAYER_STEP;
            case 's' -> playerY += PLAYER_STEP;
            case 'a' -> playerX -= PLAYER_STEP;
            case 'd' -> playerX += PLAYER_STEP;
        }
        // Clamp to bounds
        playerX = Math.max(0, Math.min(GRID_SIZE - 1, playerX));
        playerY = Math.max(0, Math.min(GRID_SIZE - 1, playerY));
    }

    // Boss movement (direct line)
    public void moveBossDirect(boolean charging) {
        double speed = charging ? BOSS_SPEED_CHARGE : BOSS_SPEED_NORMAL;
        // Determine how much to move
        double dx = playerX - bossX;
        double dy = playerY - bossY;
        // Calculate magnitude to player
        double dist = Math.sqrt(dx * dx + dy * dy);
        if (dist == 0) return;

        // Move toward player's position
        bossX += (dx / dist) * speed;
        bossY += (dy / dist) * speed;

        // Clamp position
        bossX = Math.max(0, Math.min(GRID_SIZE - 1, bossX));
        bossY = Math.max(0, Math.min(GRID_SIZE - 1, bossY));
    }

    // Draw grid
    public void drawGrid() {
        // To manipulate strings
        StringBuilder grid = new StringBuilder();
        // Iterate through as a grid
        for (int y = 0; y < GRID_SIZE; y++) {
            for (int x = 0; x < GRID_SIZE; x++) {
                // Determine what to draw and where
                if (x == playerX && y == playerY) grid.append("🌳");
                else if (x == (int)Math.round(bossX) && y == (int)Math.round(bossY)) grid.append("🗿");
                else grid.append("· ");
            }
            // Next row
            grid.append("\n");
        }
        // Convert to string and print out the grid
        GameManager.terminal.printLine(grid.toString(), Terminal.AlignmentType.CENTRE);
    }
}
