//Sinray November 10th, task three survive code

package tasks;

import java.util.*;
import java.util.concurrent.*;

import constants.*;
import contexts.*;
import shared.*;
import util.*;

public class Parry extends Task { 
    private static final Random random = new Random();
    // Special feature that's used for scheduling periodic tasks
    private ScheduledExecutorService combatScheduler = Executors.newSingleThreadScheduledExecutor();
    
    // Hashmaps
    public static final String[] ATTACK_KEYS = {"w", "a", "s", "d"};
    public static final String[] ATTACK_DIRECTIONS = {"↑", "←", "↓", "→"};
    
    public static final int DAMAGE = 2;
    public static final int BATTLE_DELAY = 4_000;

    public boolean hasAttacked = false;

    // Variables for opponent stats
    public String currentOpponent = "Goblin 1";
    public int opponentMaxHP = 12;
    public int opponentHP = opponentMaxHP;
    public int opponentDamage = 2;
    public int parryWindow = 1_500;
    public int attackTime = 2_000;

    public Integer lastOpponentAttack = null;
    public Integer currentAttack = null;
    public boolean isMyAttack = false;
    public boolean isGameOver = false;
    public boolean hasWon = false;
    public boolean hasBeatMonster = false;

    // Messages to fisplay
    public String first = "", second = "";

    // Print the health
    public void printHP() {
        GameManager.terminal.printLine("You", Terminal.AlignmentType.CENTRE);
        GameManager.terminal.printLine(HeartParser.getHearts(Narrative.HP, Narrative.MAX_HP), Terminal.AlignmentType.CENTRE);
    }
    // Print the opponent's health
    public void printOpponentHP() {
        GameManager.terminal.printLine(currentOpponent, Terminal.AlignmentType.CENTRE);
        GameManager.terminal.printLine(HeartParser.getHearts(opponentHP, opponentMaxHP), Terminal.AlignmentType.CENTRE);
    }

    // Player takes damage (also returns damage taken)
    public int takeDamage() {
        currentAttack = null;

        // Clamp the damage taken
        int taken = Math.min(Narrative.HP, opponentDamage);
        Narrative.HP -= taken;

        // Indicate what messages to display
        first = "";
        if (Narrative.HP <= 0) {
            second = "💀 Dead";
            isGameOver = true;
        } else {
            second = "🔻 Took " + HeartParser.getHearts(taken);
        }

        redraw();
        
        if (Narrative.HP <= 0) {
            GameManager.terminal.printLine();
            Narrative.printContinue();
        }

        return taken;
    }
    // Opponent takes damage (also returns damage taken)
    public int opponentTakeDamage() {
        // Also clamp damage taken
        int taken = Math.min(opponentHP, DAMAGE);
        opponentHP -= taken;
        printOpponentHP();
        redraw();

        // Move on to next monster or beat task
        if (opponentHP == 0) {
            hasBeatMonster = true;
            // Switch case for what opponent we are facing
            switch (currentOpponent) {
                case "Goblin 1" -> {
                    currentOpponent = "Goblin 2";
                    opponentHP = opponentMaxHP = 12;
                    opponentDamage = 2;
                    parryWindow = 1_500;
                    attackTime = 1_750;
                    reschedule();
                }
                case "Goblin 2" -> {
                    currentOpponent = "Troll";
                    opponentHP = opponentMaxHP = 24;
                    opponentDamage = 3;
                    parryWindow = 1_000;
                    attackTime = 1_250;
                    reschedule();
                }
                // We beat the game if the opponent is anything else like Troll
                default -> {
                    isGameOver = true;
                    hasWon = true;
                    GameManager.terminal.printLine();
                    // Input will be sinked so only use print for visual purposes
                    Narrative.printContinue();
                }
            }
        }

        return taken;
    }

    // Reschedules the combat loop for when the monster attacks and such
    public void reschedule() {
        // Create a new scheduler for each new monster
        combatScheduler.shutdownNow();
        combatScheduler = Executors.newSingleThreadScheduledExecutor();
        currentAttack = lastOpponentAttack = null;
        first = "The battle begins...";
        second = "⚔️  Brace";
        redraw();

        // Schedule periodic attacks
        combatScheduler.scheduleAtFixedRate(() -> {
            if (isGameOver) return;

            // Randomize an attack
            currentAttack = random.nextInt(ATTACK_KEYS.length);
            lastOpponentAttack = currentAttack;
            first = "";
            second = "❗ Incoming attack!";
            isMyAttack = false;
            redraw();

            // Window of opportunity for parrying. If not parried after the parry window, take damage
            combatScheduler.schedule(() -> {
                if (!isGameOver && !isMyAttack && currentAttack != null) {
                    takeDamage();
                }
            }, parryWindow, TimeUnit.MILLISECONDS);
        }, BATTLE_DELAY, attackTime, TimeUnit.MILLISECONDS); // Battle delay gives some time to catch up with what's happening
    }

    // ASCII art for the task
    public void print() {
        GameManager.terminal.clear();
        GameManager.terminal.printLine(
            ANSI.FOREGROUND_GREEN
            + """
█▀ █   ▄▀▄ ▄▀▄ █▀▄   ▄█
█▀ █▄▄ ▀▄▀ ▀▄▀ █▀▄    █"""
            + ANSI.RESET,
            Terminal.AlignmentType.CENTRE,
            Terminal.AlignmentBehaviour.BLOCK
        );
    }
    
    // Redraws the screen
    public void redraw() {
        print();
        GameManager.terminal.printLine();
        GameManager.terminal.printLine(currentOpponent, Terminal.AlignmentType.CENTRE);
        GameManager.terminal.printLine();
        GameManager.terminal.printLine(ANSI.FOREGROUND_RED
            + """
   ()
   )(
o======o
   ||
   ||
   ||
   ||
   \\/"""
            + ANSI.RESET,
            Terminal.AlignmentType.CENTRE,
            Terminal.AlignmentBehaviour.BLOCK
        );
        GameManager.terminal.printLine();
        GameManager.terminal.printLine(first, Terminal.AlignmentType.CENTRE);
        GameManager.terminal.printLine();
        printOpponentHP();
        
        // Colour the corresponding direction symbol
        // Make a copy of the directions
        String[] dirs = ATTACK_DIRECTIONS.clone();
        if (currentAttack != null) {
            // Determine what colour to use (red for monster, blue for player)
            String colour = isMyAttack ? ANSI.FOREGROUND_BLUE : ANSI.FOREGROUND_RED;
            // Colourize
            dirs[currentAttack] = colour + ATTACK_DIRECTIONS[currentAttack] + ANSI.RESET;
        }
        // Use String.format to efficiently format it
        String attackWheel = String.format("""
  %s
%s   %s
  %s
        """, dirs[0], dirs[1], dirs[3], dirs[2]);

        GameManager.terminal.printLine();
        GameManager.terminal.printLine(attackWheel, Terminal.AlignmentType.CENTRE, Terminal.AlignmentBehaviour.BLOCK);
        GameManager.terminal.printLine();

        printHP();
        GameManager.terminal.printLine();
        GameManager.terminal.printLine(second, Terminal.AlignmentType.CENTRE);
        GameManager.terminal.printLine();
        GameManager.terminal.printLine(
            ANSI.FOREGROUND_BLUE
            + """                                          
   .
  / \\
  |.|
  |.|
  |:|
  |:|
`--8--'
   8
   O"""
            + ANSI.RESET,
            Terminal.AlignmentType.CENTRE,
            Terminal.AlignmentBehaviour.BLOCK
        );
    }

    // Start the task
    public void play() {
        // Immediately schedule the first battle
        reschedule();
        redraw();
        
        // Input loop (the scheduler runs asynchronously so the scanner blocks the main thread and not the combat scheduler)
        String attackKey = null;
        while (!isGameOver) {
            attackKey = GameManager.terminal.scanKey();
            if (isGameOver) break;
            redraw();

            // Ensure the direction inputted is a valid one
            boolean isValid = false;
            for (String otherAttackKey : ATTACK_KEYS) {
                if (otherAttackKey.equals(attackKey)) isValid = true;
            }
            if (isValid) {
                // Determine what index it is
                int attack = -1;
                for (int i = 0; i < ATTACK_KEYS.length; i++) {
                    if (ATTACK_KEYS[i].equals(attackKey)) attack = i;
                }
                String attackDirection = ATTACK_DIRECTIONS[attack];
                
                // Determine how it was parried or not
                if (currentAttack != null && attack == currentAttack) {
                    currentAttack = null;
                    second = String.format("🛡️  Parried %s" + attackDirection + "%s", ANSI.FOREGROUND_RED, ANSI.RESET);
                    hasAttacked = false;
                } else {
                    if (currentAttack == null) {
                        if (!hasAttacked) {
                            if (lastOpponentAttack == null) {
                                // Brace for attack means wait for the monster to attack first before trying to parry or attack
                                second = "❌  Must brace for an attack first";
                            } else if (lastOpponentAttack != attack) {
                                hasAttacked = true;
                                currentAttack = attack;
                                isMyAttack = true;
                                int dealt = opponentTakeDamage();
                                if (hasBeatMonster) {
                                    hasBeatMonster = false;
                                    first = "";
                                    second = "⚔️  Brace";
                                } else {
                                    first = "🔻 Dealt " + HeartParser.getHearts(dealt) + "";
                                    second = "";
                                }
                            } else {
                                second = "❌  Cannot attack in parried direction!";
                            }
                        }
                    } else {
                        // Take damage if the player tried attacking in the wrong direction
                        takeDamage();
                    }
                }
                if (!isGameOver) redraw();
            }
        }
        
        // The sinked input is the attack key, so pass it in to avoid entering inputs multiple times to proceed
        Narrative.listenContinue(attackKey);

        // Death outcome
        if (!hasWon) {
            GameManager.terminal.printLineSlow(
                "You feel the sharp pain of a club stabbing through your skin. You look to your left and right, "
                + "screaming for help, but it seems like Rowan and Willow are in the same predicament as you. "
                + "“What a cruel life” you whisper to yourself as your life fades to black.",
                40l
            );
            GameManager.terminal.printLineSlow();
            Narrative.promptContinue();
            
            GameManager.gameplay.gameOver();
            GameManager.gameplay.gameOverInput(null, "parry");
        }
    }
}