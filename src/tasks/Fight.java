//Sneh November 15th, Task four fight code (Formatting edited by Sinray November 16th)
package tasks;

// Imports
import java.util.*;

// Imports to access other files
import constants.*;
import contexts.*;
import shared.*;
import util.*;

public class Fight extends Task { 
    // Wyvern stats
    public static int WYVERN_MAX_HP = 100; 

    public static int WYVERN_SLASH_DAMAGE = 1;
    public static int WYVERN_BITE_DAMAGE = 2;    
    public static int ROWAN_ARCANE_HEAL = 1; // Amount healed by Rowan's Arcane Heal, variable for balancing if needed

    public int Damage = 0; // Called in PlayerAttack Method as the damage it does
    public int BowBlast = 10; // Uses left
    public int DaringDaggers = 5; // Uses left
    public Boolean Att = false; // Whether an attack has been made
    public int ArcaneHeal = 5; // Uses left
    public int ArcaneShield = 5; // Uses left
    public Boolean TrueShield = false; // Whether the shield is active
    public int WyvernHealth = WYVERN_MAX_HP; // Wyvern's current health, resets each fight
    
    public void displayHP() { // Displays current HP of player and Wyvern
        GameManager.terminal.printLine(
            "You",
            Terminal.AlignmentType.CENTRE
        );
        GameManager.terminal.printLine(
            HeartParser.getHearts(Narrative.HP, Narrative.MAX_HP),
            Terminal.AlignmentType.CENTRE
        );
        GameManager.terminal.printLine();
        GameManager.terminal.printLine(
            "Wyvern",
            Terminal.AlignmentType.CENTRE
        );
        GameManager.terminal.printLine(
            HeartParser.getHearts(WyvernHealth, WYVERN_MAX_HP),
            Terminal.AlignmentType.CENTRE
        );
        GameManager.terminal.printLine();
        Narrative.promptContinue();
    }
    public void OakAttack() { // Oak's attack method
        GameManager.terminal.clear();
        do {
            GameManager.terminal.printLine( //Ascii art of Oak
                """
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣀⠀⠀⠀⠀⠀⣀⣤⣤⣄⡀
⠀⠀⠀⠀⠀⣀⣤⣤⣤⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣦⣤⣄⡀
⠀⠀⠀⠀⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠿⡿⢿⣿⣿⣿⣿⣿⣿⣿⡀
⠀⠀⢀⣴⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣧⢠⣾⣿⣿⣿⣿⣿⣿⡟
⠀⠠⣿⣿⣿⣿⣿⣿⣿⡿⠋⠉⢻⣿⣿⣿⣿⡿⢿⣿⣿⣿⣿⣿⣿⣿⣷⣄
⠀⠀⠙⠛⣿⣿⣿⣿⣿⣶⣦⣄⠘⠛⠻⣿⠟⠁⠘⠿⢿⠿⣿⣿⣿⣿⣿⣿⠇
⠀⠀⠀⠀⠙⠿⠿⠿⠿⢿⣿⣿⣷⡄⠀⠀⠀⠀⠀⠀⣠⣾⣿⣿⡿⠟⠋⠁
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠙⠿⣿⣿⠀⢀⣿⠀⠀⣴⠿⠿⣿⣿⠿
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⠁⢸⡇⠀⣸⠃
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢰⡟⢠⡿⠀⠀⡏
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣰⠏⢠⡿⠁⢀⡀⢻⡀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⣠⡾⠁⣠⡿⠁⠀⠸⣧⠈⢷⣄
⠀⠀⠀⠀⠀⠀⢀⣠⡾⠋⢀⣴⠟⠀⠀⠀⠀⠹⣧⣀⣹⣧⡀
⠀⠀⠀⠀⠀⠐⠋⠉⠉⣹⣿⣧⣤⣤⣄⣠⣤⣤⣽⣿⡍⠉⠙⠂
⠀⠀⠀⠀⠀⠀⠀⠀⠊⠉⠉⠁⠀⠀⠙⠋⠀⠀⠀⠉⠙⠂""",
                Integer.MAX_VALUE,
                Terminal.AlignmentType.CENTRE,
                Terminal.AlignmentBehaviour.BLOCK
            );
            GameManager.terminal.printLine();
            GameManager.terminal.printLine(
                String.format(
                    // Infinite uses for sword slash
                    // Uses left for Bow Blast printed
                    // Uses left for Daring Daggers printed
                    "[%s%s%s] | Sword Slash (%s∞%s uses left)\n"
                    + "[%s%s%s] | Bow Blast (%s" + BowBlast + "%s uses left)\n"
                    + "[%s%s%s] | Daring Daggers (%s" + DaringDaggers + "%s uses left)",
                    ANSI.ITALIC + ANSI.FOREGROUND_BLUE, Keybinds.OPTION_1, ANSI.RESET, ANSI.ITALIC, ANSI.RESET,
                    ANSI.ITALIC + ANSI.FOREGROUND_GREEN, Keybinds.OPTION_2, ANSI.RESET, ANSI.ITALIC, ANSI.RESET,
                    ANSI.ITALIC + ANSI.FOREGROUND_RED, Keybinds.OPTION_3, ANSI.RESET, ANSI.ITALIC, ANSI.RESET
                ),
                Terminal.AlignmentType.CENTRE,
                Terminal.AlignmentBehaviour.BLOCK
            );
            GameManager.terminal.printLine();
            GameManager.terminal.printLine(
                "Oak's attack?",
                Terminal.AlignmentType.CENTRE
            );
            int OakAtt = 0; // Oak's attack choice
            try { // Try-catch to prevent errors
                String input = GameManager.terminal.scanner.nextLine();
                OakAtt = Integer.parseInt(input);
            } catch (Exception e) {}
            GameManager.terminal.clear();
            if (OakAtt == 1) { // Sword Slash
                Damage = (int) (Math.random()*3) + 5;
                int dealt = Math.min(WyvernHealth, Damage); 
                WyvernHealth -= dealt; // Reduces Wyvern health by damage dealt
                GameManager.terminal.printLine(
                    "Oak slashes his sword.",
                    Terminal.AlignmentType.CENTRE
                );
                GameManager.terminal.printLine();
                GameManager.terminal.printLine(
                    "🔻 Dealt " + HeartParser.getHearts(dealt), // Prints how many hearts of damage were dealt
                    Terminal.AlignmentType.CENTRE
                );
                GameManager.terminal.printLine(); 
                Att = true; // Attack made
                displayHP(); // Displays current HP
            } else if (OakAtt == 2 && BowBlast > 0) { // Bow Blast
                Damage = (int) (Math.random()*5) + 7; // Damage calculation
                int dealt = Math.min(WyvernHealth, Damage); 
                WyvernHealth -= dealt; // Reduces Wyvern health by damage dealt
                GameManager.terminal.printLine(
                    "Oak shoots his bow.",
                    Terminal.AlignmentType.CENTRE
                );
                GameManager.terminal.printLine();
                GameManager.terminal.printLine(
                    "🔻 Dealt " + HeartParser.getHearts(dealt), // Prints how many hearts of damage were dealt
                    Terminal.AlignmentType.CENTRE
                );
                GameManager.terminal.printLine();
                BowBlast--;
                Att = true;
                displayHP();
                //This same structure is used for all attacks, the next comments will be on the defense and healing 
            } else if (OakAtt == 3 && DaringDaggers > 0) {
                Damage = (int) (Math.random()*7) + 9;
                int dealt = Math.min(WyvernHealth, Damage);
                WyvernHealth -= dealt;
                GameManager.terminal.printLine(
                    "Oak throws a barrage of daggers.",
                    Terminal.AlignmentType.CENTRE
                );
                GameManager.terminal.printLine();
                GameManager.terminal.printLine(
                    "🔻 Dealt " + HeartParser.getHearts(dealt),
                    Terminal.AlignmentType.CENTRE
                );
                GameManager.terminal.printLine();
                DaringDaggers--;
                Att = true;
                displayHP();
            }
        } while (WyvernHealth > 0 && Att == false); // Loops until an attack is made and Wyvern health is above 0
        Att = false; // Resets attack made boolean
    }

    public void RowanAttack() { //rowan's attack method, for concepts same as oak's, there wont be comments unless something new
        GameManager.terminal.clear();
        do {
            GameManager.terminal.printLine(
                """
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣀⣀
⠀⠀⠀⠀⠀⠀⠀⠀⠀ ⠀⠀⠀⣠⡶⠛⠉⠉⢹⡆
⠀⠀⠀⣀⠀⠀⠀⠀⠀⠀⠀⢰⡏⢀⡴⠀⠀⣾⠁
⠰⣟⠋⠉⠙⠛⢶⣄⠀⠀⠀⢸⣷⠟⠁⣀⡼⠋
⠀⢻⡄⠀⠰⢦⣄⣹⡆⠀⠀⣼⠿⠛⠛⠉
⠀⠀⠻⢦⣄⣀⣈⣻⣿⡀⢰⡏⠀⠀⠀⠀⠀⣀
⠀⠀⠀⠀⠈⠉⠉⠉⠈⠻⣼⡇⠀⢀⡴⠛⠋⠉⢙⡿
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢹⡇⢀⣾⣥⠾⠃⢀⣼⠃
⠀⠀⠀⠀⣤⣤⣤⣤⣀⠀⢸⣇⣼⠿⠷⠶⠶⠛⠁
⠀⠀⠀⠀⢻⡄⠀⣤⣹⣧⢸⡿⠁
⠀⠀⠀⠀⠀⠛⢶⣤⣽⣿⣿⡇
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢹⡇
⠀⠀⠀⠀⠀⠀⠀⠀⢀⣠⡼⢧⣄⡀
⠀⠀⠀⠀⣀⣀⣤⣾⣋⣁⣀⣀⣈⣙⣷⣤⣀⣀
⠀⠀⠀⠀⠉⠉⠉⠉⠉⠉⠉⠉⠉⠉⠉⠉⠉⠉""",
                Integer.MAX_VALUE,
                Terminal.AlignmentType.CENTRE,
                Terminal.AlignmentBehaviour.BLOCK
            );
            GameManager.terminal.printLine();
            GameManager.terminal.printLine(
                String.format(
                    "[%s%s%s] | Arcane Blast (%s∞%s uses left)\n"
                    + "[%s%s%s] | Arcane Shield (%s" + ArcaneShield + "%s uses left)\n"
                    + "[%s%s%s] | Arcane Heal (%s" + ArcaneHeal + "%s uses left)",
                    ANSI.ITALIC + ANSI.FOREGROUND_RED, Keybinds.OPTION_1, ANSI.RESET, ANSI.ITALIC, ANSI.RESET,
                    ANSI.ITALIC + ANSI.FOREGROUND_BLUE, Keybinds.OPTION_2, ANSI.RESET, ANSI.ITALIC, ANSI.RESET,
                    ANSI.ITALIC + ANSI.FOREGROUND_GREEN, Keybinds.OPTION_2, ANSI.RESET, ANSI.ITALIC, ANSI.RESET
                ),
                Terminal.AlignmentType.CENTRE,
                Terminal.AlignmentBehaviour.BLOCK
            );
            GameManager.terminal.printLine();
            GameManager.terminal.printLine(
                "Rowan's move?",
                Terminal.AlignmentType.CENTRE
            );
            int RowanAttChoice = 0;
            try {
                String input = GameManager.terminal.scanner.nextLine();
                RowanAttChoice = Integer.parseInt(input);
            } catch (Exception e) {}
            GameManager.terminal.clear();
            if (RowanAttChoice == 1) {
                Damage = (int) (Math.random()*3) + 5;
                int dealt = Math.min(WyvernHealth, Damage);
                WyvernHealth -= dealt;
                GameManager.terminal.printLine(
                    "Rowan sends an arcane blast at the dragon.",
                    Terminal.AlignmentType.CENTRE
                );
                GameManager.terminal.printLine();
                GameManager.terminal.printLine(
                    "🔻 Dealt " + HeartParser.getHearts(dealt),
                    Terminal.AlignmentType.CENTRE
                );
                GameManager.terminal.printLine();
                Att = true;
                displayHP();
            } else if (RowanAttChoice == 2 && ArcaneShield > 0) { // New shield mechanic
                GameManager.terminal.printLine(
                    "Rowan summons a barrier around the party.",
                    Terminal.AlignmentType.CENTRE
                );
                GameManager.terminal.printLine();
                GameManager.terminal.printLine(
                    "🛡️  Shielded turn",
                    Terminal.AlignmentType.CENTRE 
                );
                GameManager.terminal.printLine();
                TrueShield = true; // Activates shield
                ArcaneShield--;
                Att = true; 
                displayHP();
            } else if (RowanAttChoice == 3 && ArcaneHeal > 0) {
                int healed = Math.min(Narrative.MAX_HP - Narrative.HP, ROWAN_ARCANE_HEAL); // Heals up to max HP
                Narrative.HP += healed; // Increases player HP
                GameManager.terminal.printLine(
                    "Rowan summons a piercing light.",
                        Terminal.AlignmentType.CENTRE
                );
                GameManager.terminal.printLine();
                GameManager.terminal.printLine(
                    "🔺 Healed " + HeartParser.getHearts(healed),
                    Terminal.AlignmentType.CENTRE
                );
                GameManager.terminal.printLine();
                ArcaneHeal--;
                Att = true;
                displayHP();
            }
        } while (WyvernHealth > 0 && Att == false);
        Att = false;
    }

    public void WyvernAtt() {
        int wyvernAtt = (int) (Math.random()*2) + 1; // Randomly chooses between slash and bite
        if (TrueShield==true) { // If shield is active, blocks attack
            GameManager.terminal.printLine(
                "The Wyvern launches an attack; however, it is blocked by the Arcane Shield set up, "
                + "breaking the shield in the process.",
                Terminal.AlignmentType.CENTRE
            );
            GameManager.terminal.printLine();
            TrueShield=false; 
        } else if (wyvernAtt ==1) {
            int dealt = Math.min(Narrative.HP, WYVERN_SLASH_DAMAGE);
            GameManager.terminal.printLine(
                "The Wyvern lunges forward, slashing your party.",
                Terminal.AlignmentType.CENTRE
            );
            GameManager.terminal.printLine();
            GameManager.terminal.printLine(
                "🔻 Dealt " + HeartParser.getHearts(dealt),
                Terminal.AlignmentType.CENTRE
            );
            GameManager.terminal.printLine();
            Narrative.HP -= dealt;
        } else {
            int dealt = Math.min(Narrative.HP, WYVERN_BITE_DAMAGE);
            GameManager.terminal.printLine(
                "The Wyvern lunges forward, biting your party.",
                Terminal.AlignmentType.CENTRE
            );
            GameManager.terminal.printLine();
            GameManager.terminal.printLine(
                "🔻 Dealt " + HeartParser.getHearts(dealt),
                Terminal.AlignmentType.CENTRE
            );
            GameManager.terminal.printLine();
            Narrative.HP -= dealt;
        }
    }

    public void play() {
        displayHP();
        while (Narrative.HP > 0 && WyvernHealth > 0) {
            OakAttack();
            if (WyvernHealth <= 0) break;
            RowanAttack();
            if (WyvernHealth <= 0) break;
            WyvernAtt();
            displayHP();
        }

        if (Narrative.HP <= 0) {
            GameManager.terminal.printLineSlow(
                "The Wyvern strikes straight through you and Rowan, splitting your bodies in half. "
                + "You look at Rowan, you don't think about yourself in these last few moments; it's "
                + "not like you had anything to live for. Instead, you look at Rowan. You look at him "
                + "desperately trying to hold himself together, you can do nothing but watch as he "
                + "helplessly calls out to his younger brother, \"Spruce, please stay safe.\" With "
                + "nothing left to do, your life fades to black.",
                40l
            );
            GameManager.terminal.printLineSlow();
            Narrative.promptContinue();

            GameManager.gameplay.gameOver();
            GameManager.gameplay.gameOverInput(null, "fight");
        }
    }
}