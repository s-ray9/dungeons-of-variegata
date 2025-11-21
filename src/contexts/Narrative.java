//Sneh November 10th, narrative context that handles all storytelling and narration, including stats (compiled together and refactored by Sinray)

// Required to be recognized
package contexts;

// Imports to access other files
import constants.*;
import shared.*;
import tasks.*;
import util.*;

public class Narrative {
    public static final int MAX_HP = 6; // Maximum health the character can have so it resets if needed 

    public static int Morality = 0; // Higher the number worse it is.
    public static int HP = MAX_HP; // Health the character
    public static int Intelligence = 0; // Higher the number worse it is.
    public static int Strength = 0; // Higher the number worse it is.
    //Start of Introduction, November 5th Sneh
    public void print() {
        GameManager.terminal.clear();
        GameManager.terminal.printLineSeparator();
        GameManager.terminal.printLine();
        GameManager.terminal.printLine("""
█ █▄ █ ▀█▀ █▀▄ ▄▀▄ █▀▄ █ █ ▄▀▀ ▀█▀ █ ▄▀▄ █▄ █
█ █ ▀█  █  █▀▄ ▀▄▀ █▄▀ ▀▄█ ▀▄▄  █  █ ▀▄▀ █ ▀█""", // The ascii art
            Terminal.AlignmentType.CENTRE, // Centre aligned the ascii art
            Terminal.AlignmentBehaviour.BLOCK
        );
        GameManager.terminal.printLine();
        GameManager.terminal.printLineSeparator();
    }
    
    /*
    Load data saved from a task
    The previous task is passed in to load data saved from the last save
    */
    public static void loadData(Task task) { 
        Morality = task.Morality; // Only resets morality if a morality-related death
        HP = task.HP;
        Intelligence = task.Intelligence; // Only resets intelligence if an intelligence-related death
        Strength = task.Strength;
    }
    
    // Called at the end to reset the game
    public static void resetData() {
        Morality = 0;
        HP = MAX_HP;
        Intelligence = 0;
        Strength = 0;
    }

    // The introduction sequence
    public void introduce() {
        print();
        GameManager.terminal.printLine();
        GameManager.terminal.printLine( // Prompt to skip intro
            "Do you want to want to know the lore?",
            Terminal.AlignmentType.CENTRE
        );
        GameManager.terminal.printLine();
        GameManager.terminal.printLine(
            "(Recommended)",
            Terminal.AlignmentType.CENTRE
        );
        GameManager.terminal.printLine();
        /*Print the introduction options as one and centre it using the block behaviour
        This aligns the text as opposed to printing separately
        */
        GameManager.terminal.printLine(
            String.format(
                "[%s%s%s] | Yes\n[%s%s%s] | No", //  Options
                ANSI.ITALIC + ANSI.FOREGROUND_GREEN, Keybinds.YES, ANSI.RESET,
                ANSI.ITALIC + ANSI.FOREGROUND_RED, Keybinds.NO, ANSI.RESET
            ),
            Terminal.AlignmentType.CENTRE,
            Terminal.AlignmentBehaviour.BLOCK
        );
        GameManager.terminal.printLine();
        GameManager.terminal.printLineSeparator();
        
        String key = GameManager.terminal.scanKey();
        String keyLower = key.toLowerCase();
        switch (keyLower) { // Switch case for their input
            case "y" -> GameManager.terminal.clear();
            case "n" -> { GameManager.terminal.clear(); return; } // Return skips
            default -> { introduce(); return; } // Return prevents reintroduction after finishing
        }
        
        // Introduction text
        GameManager.terminal.printLineSlow(
            "You grow up in Bauhinia, a village too stubborn to die yet too weak to truly live. "
            + "Mornings smell of metal and sawdust; nights taste of thin stew."
        );
        GameManager.terminal.printLineSlow();
        GameManager.terminal.printLineSlow(
            "The village runs on the work of the people and the rules the council sets: children "
            + "under sixteen eat from the common stores, water's free, but schooling doesn't "
            + "exist; your parents teach you whatever they can and everything else you learn "
            + "at work."
        );
        GameManager.terminal.printLineSlow(); 
        //Line break for readability(Same use throughout)
        promptContinue(); 
        //Prompt to continue the game, makes it more engaging for the player
        
        GameManager.terminal.printLineSlow(
            "They make furniture and weapons because that's what sells. Lately it barely does. "
            + "Merchants come and go, and the council limits how much timber the residents can "
            + "take from Variegata because the forest depths are riddled with dungeons and "
            + "monsters; take too much from the outskirts and the next year there's nothing left "
            + "that's safe to cut."
        );
        GameManager.terminal.printLineSlow();
        GameManager.terminal.printLineSlow(
            "The kingdom's bounties for clearing those dungeons have always been there, but "
            + "adventurers don't come this far into the Acer march. The land around Bauhinia is "
            + "too barren; fear keeps them away."
        );
        GameManager.terminal.printLineSlow();
        promptContinue();
        
        GameManager.terminal.printLineSlow(
            "You met Rowan and Willow at one of those shared dinners when you were four."
        );
        GameManager.terminal.printLineSlow();
        GameManager.terminal.printLineSlow(
            "Rowan's family is doing well enough; stable, careful, and plan for the future. He "
            + "grew into the kind of person who reads the margins of maps and learns spells that "
            + "nobody here remembers how to use. He studies because he wants to protect the "
            + "people he loves, and because he's genuinely clever in ways the village notices."
        );
        GameManager.terminal.printLineSlow();
        promptContinue();

        GameManager.terminal.printLineSlow(
            "Willow's story is different. Her parents were carpenters, and when demand for "
            + "furniture died she watched them starve. She was eleven when they went and that loss "
            + "flipped something in her. Determination turned into obsession, bravery into "
            + "recklessness. Your families were close; your parents were her godparents and took "
            + "her in. She never acclimated to your family, though that never stopped you from "
            + "seeing her as such. She trained till her hands bled, and talked about gold the way "
            + "other people talk about air."
        );
        GameManager.terminal.printLineSlow();
        promptContinue();

        GameManager.terminal.printLineSlow(
            "As for you, you're Oak. Your parents are blacksmiths and raised you at the forge. "
            + "You were taught to swing a sword and to give what you can. People call it bravery; "
            + "sometimes it's more like impatience. You're generous by habit, reckless by choice, "
            + " and want to save Bauhinia because those who live here are never free."
        );
        promptContinue();

        GameManager.terminal.printLineSlow(
            "Now you guys are sixteen, old enough by law to call yourselves adventurers, and "
            + "that legal line is everything. Since no one else from Bauhinia will go, you will. So "
            + "you look toward Variegata not because it suddenly became safer, but because it's the "
            + "only honest shot any of you guys have at changing things for those left behind."
        );
        GameManager.terminal.printLineSlow();
        GameManager.terminal.printLineSlow(
            "That's where it begins. "
            + "The trees at the forest's edge already look like a thing that remembers better days, "
            + "with no animals left behind. Just monsters. You stand at the border and take a breath. "
        );
        GameManager.terminal.printLineSlow();
        promptContinue();

        GameManager.terminal.clear(); //Clears terminal for better readability
    }

    // Print out the prompt for visual purposes
    public static void printContinue() {
        GameManager.terminal.printLine(
            String.format(
                "[%s%s%s] | Continue",
                ANSI.ITALIC + ANSI.FOREGROUND_BLUE, //Turns "Continue" italic and blue
                Keybinds.CONTINUE,
                ANSI.RESET
            ),
            Terminal.AlignmentType.CENTRE // Aligns text to centre
        );
    }
    
    // Listens for the continue key, accepting leftover input like in Gameplay.gameOverInput()
    public static void listenContinue(String leftoverInput) {
        String key = leftoverInput != null ? leftoverInput : "";
        while (!key.equalsIgnoreCase(Keybinds.CONTINUE)) {
            /*
            CRITICAL: uses the record of what's been printed to reprint if the player enters bad input
            This one feature has saved me from sitting through thousands of lines of code and hundreds
            of print statements
            */
            String temp = Terminal.registry;
            GameManager.terminal.clear();
            GameManager.terminal.print(temp);
            key = GameManager.terminal.scanKey();
        }
        GameManager.terminal.clear();
    }

    // Overloaded method for no leftover input
    public static void listenContinue() {
        listenContinue(null);
    }

    // Prompts the player to continue
    public static void promptContinue() {
        printContinue();
        listenContinue();
    }

    public static void introduceStats() { // Introduces the player's hearts
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
            "You",
            Terminal.AlignmentType.CENTRE
        );
        GameManager.terminal.printLine(
            HeartParser.getHearts(HP, MAX_HP),
            Terminal.AlignmentType.CENTRE
        );
        GameManager.terminal.printLine();
        Narrative.promptContinue();
    }
    // November 6th, Sneh, Dialogue after introduction before task one
    public static void introDialogue() { 
        GameManager.terminal.clear();
        GameManager.terminal.printLineSlow(
            "Your party searches through the Variegata. The deeper you go, the more frequently you see monsters."
        );
        GameManager.terminal.printLineSlow(); //Line break for readability(same use throughout)
        GameManager.terminal.printLineSlow(
            "Rowan: Let's avoid any fighting for now, no point in wasting our energy."
        );
        GameManager.terminal.printLineSlow();
        GameManager.terminal.printLineSlow(
            "Willow: You're overreacting, it's only slimes, not like they'd cause us any trouble."
        );
        GameManager.terminal.printLineSlow();
        GameManager.terminal.printLineSlow(
            "Rowan: There is no reward. I get you want to fight, but at least be sensible with it."
        );
        GameManager.terminal.printLineSlow();
        GameManager.terminal.printLineSlow(
            "After a bit of back and forth, Willow agrees not to bring any attention to you guys, and the party continues moving."
        );
        GameManager.terminal.printLineSlow();
        GameManager.terminal.printLineSlow(
            "Going deeper in the forest, you start spotting Goblins and Trolls."
        );
        GameManager.terminal.printLineSlow();
        Narrative.promptContinue();

        int PickSide; //Declare it for now
        do { //Do while loop to ensure valid input
            GameManager.terminal.printLineSlow(
                "Rowan: I think we should head back, I mean, the basic monsters here could cause us some trouble. \n"
            );
            GameManager.terminal.printLineSlow(
                "Willow: We have to move forward and find a dungeon! Do you want our town to be poor forever!? \n"
            );
            GameManager.terminal.printLine(
                "Will you side with Rowan or Willow?",
                Terminal.AlignmentType.CENTRE
            );
            GameManager.terminal.printLineSlow();
            GameManager.terminal.printLine(
                String.format(
                    "[%s%s%s] | Rowan\n"
                    + "[%s%s%s] | Willow",
                    ANSI.ITALIC + ANSI.FOREGROUND_RED, Keybinds.OPTION_1,ANSI.RESET,
                    ANSI.ITALIC + ANSI.FOREGROUND_GREEN, Keybinds.OPTION_2, ANSI.RESET
                ),
                Terminal.AlignmentType.CENTRE,
                Terminal.AlignmentBehaviour.BLOCK
            );
            try {
                String input = GameManager.terminal.scanner.nextLine();
                PickSide = Integer.parseInt(input);
            } catch (Exception e) {
                PickSide = 0;
            }
            GameManager.terminal.clear();
            switch (PickSide) { //Switch case for the player's choice
                case 1 -> {
                    GameManager.terminal.printLineSlow(
                        "Oak: I agree with Rowan, we should head back, it's only going to get harder from here. \n"
                    );
                    GameManager.terminal.printLineSlow(
                        "Willow: Well, I'm going on, whether you guys join me or not! \n"
                    );
                    GameManager.terminal.printLineSlow(
                        "You and Rowan reluctantly decide to follow Willow, as you guys care about her "
                        + "safety, and maybe later you can convince her to turn back."
                    );
                    GameManager.terminal.printLineSlow();
                    //Morally Correct Option, nothing added
                    promptContinue();
                }
                case 2 -> {
                    GameManager.terminal.printLineSlow(
                        "I'm with Willow on this one; no point in coming back empty-handed. \n"
                    );
                    GameManager.terminal.printLineSlow(
                        "Rowan reluctantly decides to follow you and Willow as it's safer to be in a group."
                    );
                    Morality++; //Affects task 2
                    GameManager.terminal.printLineSlow();
                    promptContinue();
                }
            }
        } while (PickSide != 1 && PickSide != 2); //Repeats if an invalid input is given

        GameManager.terminal.printLineSlow(
            "As you guys travel deeper into the distance, you hear a screech from a Wyvern."
        );
        GameManager.terminal.printLineSlow();
        GameManager.terminal.printLineSlow("Rowan: It's not safe, we seriously need to turn back!");
        GameManager.terminal.printLineSlow();
        GameManager.terminal.printLineSlow(
            "Willow: Whether you come or not, I'm going to clear a dungeon, so make up your mind, either "
            + "leave or come with us and stop whining!"
        );
        GameManager.terminal.printLineSlow();
        GameManager.terminal.printLineSlow("Your party sees a dungeon in the distance and approaches.");
        GameManager.terminal.printLineSlow();
        promptContinue();

        do { //Do while loop to ensure valid input
            GameManager.terminal.printLineSlow(
                "Willow: Finally, it took long enough, and if I remember correctly, six-floor dungeons have a "
                + "1,000 gold reward!\n"
            );
            GameManager.terminal.printLineSlow(
                "Rowan: It's a seven-floor dungeon though...\n"
            );
            GameManager.terminal.printLineSlow("Willow: Even better! That's a 1,500 gold reward!\n");
            GameManager.terminal.printLineSlow(
                "Rowan: Yes, but that's only if we beat the dungeon?! How would we do that "
                + "for a seven-floor dungeon! It's rated experienced, heck, I don't think we could beat a six-floor dungeon!\n"
            );
            GameManager.terminal.printLineSlow("Willow: Always the scared Rowan, Oak, are you coming?\n");
            GameManager.terminal.printLineSlow("Will you refuse to enter the dungeon or follow Willow in?\n");
            GameManager.terminal.printLineSlow("Enter 1 to refuse and 2 to follow Willow:");
            try {
                String input = GameManager.terminal.scanner.nextLine();
                PickSide = Integer.parseInt(input);
            } catch (Exception e) {
                PickSide = 0;
            }
            
            GameManager.terminal.clear();
            switch (PickSide) { //Switch case for the player's choice
                case 1 -> {
                    GameManager.terminal.printLineSlow(
                        "Oak: I agree with Rowan, I don't think we can complete the dungeon. If we die, then we are still bringing "
                        + "nothing to the village; we need to play it safe. \n"
                    );
                    GameManager.terminal.printLineSlow("Willow: I am going to go, follow me or don't, but I'm going! \n");
                    GameManager.terminal.printLineSlow(
                        "You and Rowan decide to follow Willow as you guys silently agree you have a better chance at clearing a dungeon "
                        + "if you stick together."
                    );
                    GameManager.terminal.printLineSlow();
                    //Morally Correct Option, nothing added
                    promptContinue();
                }
                case 2 -> {
                    GameManager.terminal.printLineSlow(
                        "Oak: I'm coming, we are already pretty far in, imagine what Bauhinia can do with 1200 gold! \n"
                    );
                    GameManager.terminal.printLineSlow("Rowan: I'm not sure about this, but I guess I will come if you're going.");
                    Morality+=8; //Affects ending and task 2
                    GameManager.terminal.printLineSlow();
                    promptContinue();
                }
            }
        } while (PickSide != 1 && PickSide != 2);
    }
    //Printed before task one as introduction, Sneh November 6th
    public static void TaskOneIntro() {
        GameManager.terminal.clear();
        GameManager.terminal.printLineSlow("Your party slowly enters the dungeon, with your eyes darting, you see... \n");
        //Enter ASCII here
        GameManager.terminal.printLineSlow("Six Goblins, three Trolls. \n");
        GameManager.terminal.printLineSlow("Willow: That's two Goblins and one Troll per person, easy work, let's charge! \n");
        promptContinue();
    }
    //printed before task one as instructions
    public static void TaskOneInstructions() {
        GameManager.terminal.clear();
        GameManager.terminal.printLineSlow(String.format("%sFLOOR ONE%s", ANSI.BOLD, ANSI.RESET)); //Centers and bolds title
        GameManager.terminal.printLineSlow(
            "In this minigame, you’ll fight three monsters in order: two goblins followed by one troll. Goblins deal "
            + HeartParser.getHearts(2) + ", and the troll deals " + HeartParser.getHearts(3) + "."
        );
        GameManager.terminal.printLineSlow();
        GameManager.terminal.printLineSlow(String.format("%sHow to Play%s", ANSI.BOLD, ANSI.RESET)); //Centers and bolds subtitle
        GameManager.terminal.printLineSlow(String.format("There are two phases each turn: %sAttack%s and %sParry%s."
        , ANSI.BOLD, ANSI.RESET, ANSI.BOLD, ANSI.RESET)
        );
        GameManager.terminal.printLineSlow();
        GameManager.terminal.printLineSlow(String.format("%sParry Phase%s", ANSI.BOLD, ANSI.RESET));
        GameManager.terminal.printLineSlow(String.format(
            "A red arrow appears on screen. Press the matching %sW%s, %sA%s, %sS%s, or %sD%s key and press enter as quickly "
            + "as possible.",
            ANSI.BOLD, ANSI.RESET, ANSI.BOLD, ANSI.RESET, ANSI.BOLD, ANSI.RESET, ANSI.BOLD, ANSI.RESET //Bolded keys
        ));
        GameManager.terminal.printLineSlow();
        GameManager.terminal.print("    ");
        GameManager.terminal.printLineSlow("> If you respond within the time limit, you avoid all damage.");
        GameManager.terminal.print("    ");
        GameManager.terminal.printLineSlow(
            "> If you fail, you take the monster's set damage (" + HeartParser.getHearts(2)
            + " from goblins, " + HeartParser.getHearts(3) + " from the troll)."
        );
        GameManager.terminal.printLineSlow();
        GameManager.terminal.printLineSlow(String.format("%sAttack Phase%s", ANSI.BOLD, ANSI.RESET)); //Centers and bolds subtitle
        GameManager.terminal.printLineSlow(String.format(
            "This phase begins immediately after a parry. Press any %sW%s, %sA%s, %sS%s, or %sD%s key and enter it as quickly as "
            + "possible. The only difference in this phase is that you can't enter an attack in the same direction you parried "
            + "from.", 
            ANSI.BOLD, ANSI.RESET, ANSI.BOLD, ANSI.RESET, ANSI.BOLD, ANSI.RESET, ANSI.BOLD, ANSI.RESET //Bolded keys
        ));
        GameManager.terminal.printLineSlow();
        GameManager.terminal.print("    ");
        GameManager.terminal.printLineSlow("> If you respond within the time limit, you deal one heart for damage to the monster.");
        GameManager.terminal.print("    ");
        GameManager.terminal.printLineSlow(
            "> If you fail to reply in time or choose the same direction that you just parried in, your turn is skipped without "
            + "dealing any damage."
        );
        GameManager.terminal.printLineSlow();
        promptContinue();
    }
    //Printed before task two as exposition November 7th Sneh
    public static void TaskTwoIntro(){
        GameManager.terminal.clear();
        GameManager.terminal.printLineSlow("Your party looks at each other, exhausted, after defeating all the monsters. \n");
        GameManager.terminal.printLineSlow("Rowan: I- I think we should leave now. \n");
        GameManager.terminal.printLineSlow("Willow: Maybe you're right. This is too much to handle. \n");
        GameManager.terminal.printLineSlow("Rowan: Okay, it's decided, we need to run now. Let's split up and look for any possible exit. \n");
        GameManager.terminal.printLineSlow("Your party quickly splits up, scrambling for any possible exit.");
        GameManager.terminal.printLineSlow();
        promptContinue();
        
        GameManager.terminal.printLineSlow("Rowan: There's nothing on this wall! \n");
        GameManager.terminal.printLineSlow("Willow: Nothing here either… \n");
        GameManager.terminal.printLineSlow(
            "On the wall you're checking, you spot a brick slightly pulled out compared to the rest of the wall. "
            + "Realizing it may be a way out, you call the rest of the group over."
        );
        GameManager.terminal.printLineSlow();
        promptContinue();
        
        GameManager.terminal.printLineSlow(
            "Oak: Rowan, Willow, come here quick! I think I found the way out of this place! You see this brick " 
            + "over here? If we push it in, I think it might reveal a passage. \n"
        );
        GameManager.terminal.printLineSlow(
            "Rowan: Wait, I think you're right, I've read about this before. In older dungeons, people would carve out hidden passages "
            + "as a sort of emergency exit, usually with a riddle or puzzle to solve. If my memory isn't mistaken, this was meant to be a "
            + "magical seal, so monsters can't get out, but humans can. But we have to be careful. I mean, it could be a trap; let's not rush "
            + "into any decision we may regret. \n"
        );
        GameManager.terminal.printLineSlow("Willow: Oh my days, I'll do it myself and stop overthinking everything. \n");
        GameManager.terminal.printLineSlow("Willow pushes the brick in. \n");
        promptContinue();
    }
    //Printed before task two as instructions, Sneh November 7th
    public static void TaskTwoInstructions() {
        GameManager.terminal.clear();
        GameManager.terminal.printLineSlow(String.format("%sWALL OF MYSTERY%s", ANSI.BOLD, ANSI.RESET)); //Centers and bolds title
        GameManager.terminal.printLineSlow(
            "In this minigame, you'll solve a set of riddles. Hints are hidden in the dialogue, the introduction, and your actions "
            + "throughout the game so far."
        );
        GameManager.terminal.printLineSlow();
        GameManager.terminal.printLineSlow(String.format("%sHow to Play%s", ANSI.BOLD, ANSI.RESET)); //Centers and bolds subtitle
        GameManager.terminal.printLineSlow(String.format("%sPuzzle One%s", ANSI.BOLD, ANSI.RESET)); //Centers and bolds subtitle
        GameManager.terminal.printLineSlow("Type a word as your answer to the riddle.");
        GameManager.terminal.printLineSlow();
        GameManager.terminal.printLineSlow(String.format("%sPuzzles Two & Three%s", ANSI.BOLD, ANSI.RESET)); //Centers and bolds subtitle
        GameManager.terminal.printLineSlow("Enter the integer that corresponds to the answer you want to choose.");
        GameManager.terminal.printLineSlow();
        promptContinue();
    }
    //Printed before task three as exposition, Sneh November 11th
    public static void TaskThreeIntro() {
        GameManager.terminal.clear();
        GameManager.terminal.printLineSlow(
            "A golem lunges out of the next room, with your party barely dodging. It slams against the wall, seemingly stunned for a second."
        );
        GameManager.terminal.printLineSlow();
        GameManager.terminal.printLineSlow("You, in a panic, yell.");
        GameManager.terminal.printLineSlow();
        GameManager.terminal.printLineSlow("Oak: THIS WAS THE DOOR TO THE SECOND FLOOR. WHAT THE HELL, ROWAN!");
        GameManager.terminal.printLineSlow();
        GameManager.terminal.printLineSlow("Rowan: I DIDN'T KNOW EITHER. I'M SORRY.");
        GameManager.terminal.printLineSlow();
        Narrative.promptContinue();

        GameManager.terminal.printLineSlow(
            "Willow: Rowan, just like you said, we all make mistakes, but you are the best chance of thinking our way out of here. "
            + "What do we do?! Even I know we can't fight this beast; golems are as strong as fully-grown wyverns… "
        );
        GameManager.terminal.printLineSlow();
        GameManager.terminal.printLineSlow(
            "Rowan: Okay, okay, umm, judging by the gem on its back, it's an older edition built centuries ago. Their main weakness is "
            + "stamina, right? All we need to do is survive for a few minutes, then when it's down, we need to rush out of here. Even when "
            + "exhausted, the durability is too high for any of us to deal any real damage. But even then, where would we escape?"
        );
        GameManager.terminal.printLineSlow();
        GameManager.terminal.printLineSlow("You pipe up, suddenly thinking of a solution.");
        GameManager.terminal.printLineSlow();
        Narrative.promptContinue();

        GameManager.terminal.printLineSlow(
            "Oak: What about the entrance, even if it's not open, the wall should be the weakest there, maybe we can blast through it."
        );
        GameManager.terminal.printLineSlow();
        GameManager.terminal.printLineSlow("Your party hears rumbling from where the Golem is as if it's getting up.");
        GameManager.terminal.printLineSlow();
        GameManager.terminal.printLineSlow("Willow: Then it's decided, we just need to-");
        GameManager.terminal.printLineSlow();
        promptContinue();

        GameManager.terminal.printLine(
            ANSI.FOREGROUND_RED +
            """
 ▄▀▀ █ █ █▀▄ █ █ █ █ █ ██▀
 ▄██ ▀▄█ █▀▄ ▀▄▀ █ ▀▄▀ █▄▄"""
            + ANSI.RESET,
            Terminal.AlignmentType.CENTRE,
            Terminal.AlignmentBehaviour.BLOCK
        );
        GameManager.terminal.printLine();
        Narrative.promptContinue();
    }
    //Printed before task three as instructions
    public static void TaskThreeInstructions() {
        GameManager.terminal.clear();
        GameManager.terminal.printLineSlow(String.format("%sSURVIVE%s", ANSI.BOLD, ANSI.RESET));
        GameManager.terminal.printLineSlow(
            "In this minigame, you must survive a chase against a golem on a grid. Your party is marked as 🌳, "
            + "and the golem is marked as 🗿. If the golem reaches your position, you die instantly."
        );
        GameManager.terminal.printLineSlow();
        GameManager.terminal.printLineSlow(String.format("%sHow to Play%s", ANSI.BOLD, ANSI.RESET));
        GameManager.terminal.printLineSlow(String.format(
            "Enter a %sW%s, %sA%s, %sS%s, or %sD%s key (press Enter after each input) to move in that direction. Input quickly; the golem "
            + "moves fast. You must survive long enough to wear it out."
            , ANSI.BOLD, ANSI.RESET, ANSI.BOLD, ANSI.RESET, ANSI.BOLD, ANSI.RESET, ANSI.BOLD, ANSI.RESET)
        );
        GameManager.terminal.printLineSlow();
        promptContinue();
    }
    //Printed before task four as exposition, Sneh and Sinray November 13th
    public static void TaskFourIntro() {
        GameManager.terminal.clear();
        GameManager.terminal.printLineSlow("Rowan: OKAY, NOW EVERYONE SPLIT UP AND FIND THE ENTRANCE.");
        GameManager.terminal.printLineSlow();
        GameManager.terminal.printLineSlow(
            "The three of you spread through the wreckage. After a few minutes you find a gap behind the debris "
            + "from the first fight, and see a thinner wall where the entrance was. "
        );
        GameManager.terminal.printLineSlow();
        GameManager.terminal.printLineSlow("Oak: GUYS, OVER HERE, QUICK.");
        GameManager.terminal.printLineSlow();
        GameManager.terminal.printLineSlow(
            "From farther down, Rowan sends an arcane blast. Stone falls away and a hole opens in the wall. "
            + "You shove through it, Rowan close behind. Willow is across the room, sprinting to catch up."
        );
        GameManager.terminal.printLineSlow();
        GameManager.terminal.printLineSlow(
            "The golem rises in her path, a hulking shape between her and the exit. Willow pushes forward, and yells."
        );
        GameManager.terminal.printLineSlow();
        GameManager.terminal.printLineSlow("Willow: I'M COMING, JUST KEEP RUNNING.");
        GameManager.terminal.printLineSlow();
        GameManager.terminal.printLineSlow(
            "The golem's arm swings. It connects with a clean shot. For a heartbeat there is nothing but the dull thud from her body "
            + "hitting the wall. A dark stain spreads where she went down, but the golem does not pause to savor it."
        );
        GameManager.terminal.printLineSlow();
        Narrative.promptContinue();
        
        GameManager.terminal.printLineSlow("Rowan: WILLOW! NO, PLEASE!");
        GameManager.terminal.printLineSlow();
        GameManager.terminal.printLineSlow("Rowan bolts toward her. He does not see that she will not move again. He cries out as he runs.");
        GameManager.terminal.printLineSlow();
        GameManager.terminal.printLineSlow("Rowan: JUST STALL! I'M COMING!");
        GameManager.terminal.printLineSlow();
        GameManager.terminal.printLineSlow(
            "You grab him and pull him back with everything in you. You cannot stop thinking of the way the golem turned, the speed in its arm."
        );
        GameManager.terminal.printLineSlow();
        Narrative.promptContinue();

        GameManager.terminal.printLineSlow("Oak: Rowan, stop. We can't, we can't get her anymore...");
        GameManager.terminal.printLineSlow();
        GameManager.terminal.printLineSlow(
            "Rowan tries to wrench free. He wants to go back. He wants to save her, almost like he's unaware she's already gone. "
            + "You pull him until your arms burn, until the air in your chest is thin, and then you run because the golem might wake "
            + "again at any moment."
        );
        GameManager.terminal.printLineSlow();
        GameManager.terminal.printLineSlow(
            "You do not see Willow's body up close. You don't get the satisfaction of a goodbye. Behind you, the room smears into shadow. "
            + "All you have left is the stain on the wall that you will carry in your head."
        );
        GameManager.terminal.printLineSlow();
        Narrative.promptContinue();

        GameManager.terminal.printLineSlow(
            "You run until the trees cover the dungeons opening. Only when the danger feels distant do you "
            + "let yourself slow. The two of you stop among a willow tree, panting, the noise of the dungeon muffled like a nightmare shrinking "
            + "away. That is when everything breaks."
        );
        GameManager.terminal.printLineSlow();
        //If statement that decides which ending the player gets based on morality/previous choices
        if (Morality > 1) {
            GameManager.terminal.printLineSlow(
                "Rowan collapses to his knees. His hands claw at the earth as if the ground might give back what the dungeon took. "
                + "He looks at you like you are the axis of the fault."
            );
            GameManager.terminal.printLineSlow();
            promptContinue();

            GameManager.terminal.printLineSlow(
                "Rowan: You said yes. You agreed to come. If you had said no, she would still be here. You made us go in. You killed her."
            );
            GameManager.terminal.printLineSlow();
            GameManager.terminal.printLineSlow("His voice is raw, almost as if he has no control what comes out of his mouth.");
            GameManager.terminal.printLineSlow();
            GameManager.terminal.printLineSlow("Oak: I… I said yes. I thought we could do it, I really did.");
            GameManager.terminal.printLineSlow();
            GameManager.terminal.printLineSlow("You speak too quickly, words stumbling out with the sobs you cannot swallow.");
            GameManager.terminal.printLineSlow();
            Narrative.promptContinue();

            GameManager.terminal.printLineSlow(
                "Oak: I am sorry. I thought being brave meant pushing forward no matter what. I was wrong this time."
            );
            GameManager.terminal.printLineSlow();
            GameManager.terminal.printLineSlow("Rowan: Wrong? She is dead, Oak. She is gone. You promised, you promised she would be okay.");
            GameManager.terminal.printLineSlow();
            GameManager.terminal.printLineSlow("Rowan's breathing is as unsteady as ever.");
            GameManager.terminal.printLineSlow();
            Narrative.promptContinue();

            GameManager.terminal.printLineSlow(
                "Oak: I will not let this end mean nothing. For Bauhinia. For the village. For Willow. I will make this count."
            );
            GameManager.terminal.printLineSlow();
            GameManager.terminal.printLineSlow("Rowan looks up at you with eyes wet and furious.");
            GameManager.terminal.printLineSlow();
            GameManager.terminal.printLineSlow("Rowan: Make it count, then. Fix it. Go back. Do something.");
            GameManager.terminal.printLineSlow();
            Narrative.promptContinue();

            GameManager.terminal.printLineSlow(
                "Not another word is spoken, the silence is deafening. Rowan leaves, presumably back to his brother. "
                + "Out of pure desperation, you continue deeper into the forest, looking for anything to come back with. "
                + "However, you hear a screech. In front of you lies an adolescent Wyvern. It lunges at you, your senses dulled by grief you "
                + "fail to dodge. As you bleed out on the floor you think… Maybe, maybe if you trained harder you would be home, with riches, "
                + "with food, with your friends… Instead you're here, on your death bed with more than your blood leaking out, with Willows "
                +"too. It fades to black.",
                40l
            );
            GameManager.terminal.printLineSlow();
            promptContinue();
            GameManager.gameplay.gameOver();
            GameManager.gameplay.gameOverInput(null, null);
        } else { //The "better" sequence of en
            GameManager.terminal.printLineSlow(
                "Rowan collapses to his knees. His hands claw at the earth as if the ground might give back what the dungeon took. "
                + "You lay right beside him. No words are spoken for a while."
            );
            GameManager.terminal.printLineSlow();
            Narrative.promptContinue();

            GameManager.terminal.printLineSlow("Gathering yourselves, you guys stand up slowly walking back to the village.");
            GameManager.terminal.printLineSlow();
            GameManager.terminal.printLineSlow("However, a Wyvern lies in front of you. It tenses up as if it is going to fight.");
            GameManager.terminal.printLineSlow();
            Narrative.promptContinue();
            //Ascii art of wyvern
            GameManager.terminal.printLine(
                ANSI.FOREGROUND_RED
                + """
                 /           /
                /' .,,,,  ./
               /';'     ,/
              / /   ,,//,`'`
             ( ,, '_,  ,,,' ``
             |    /@  ,,, ;" `
            /    .   ,''/' `,``
           /   .     ./, `,, ` ;
        ,./  .   ,-,',` ,,/''\\,'
       |   /; ./,,'`,,'' |   |
       |     /   ','    /    |
        \\___/'   '     |     |
          `,,'  |      /     `\\
               /      |        ~\\
              '       (
             :
            ; .         \\--
          :   \\         ;"""
                + ANSI.RESET,
                Integer.MAX_VALUE,
                Terminal.AlignmentType.CENTRE,
                Terminal.AlignmentBehaviour.BLOCK
            );
            GameManager.terminal.printLineSlow();
            Narrative.promptContinue();

            GameManager.terminal.printLineSlow("Rowan, muttering something about not being a coward for once, speaks up.");
            GameManager.terminal.printLineSlow();
            GameManager.terminal.printLineSlow("Rowan: We can't outrun this.");
            GameManager.terminal.printLineSlow();
            GameManager.terminal.printLineSlow("Oak: What do we do...");
            GameManager.terminal.printLineSlow();
            GameManager.terminal.printLineSlow(
                "Rowan: The only thing we can do is fight, it's only in its adolescent stages. Maybe, its not likely but maybe we can win this."
            );
            GameManager.terminal.printLineSlow();
            GameManager.terminal.printLineSlow("Oak: Then lets-");
            GameManager.terminal.printLineSlow();
            promptContinue();
            GameManager.terminal.printLine(
                ANSI.FOREGROUND_BLUE
                + """
█▀ █ ▄▀  █▄█ ▀█▀
█▀ █ ▀▄█ █ █  █"""
                + ANSI.RESET,
                Integer.MAX_VALUE,
                Terminal.AlignmentType.CENTRE,
                Terminal.AlignmentBehaviour.BLOCK
            );
            GameManager.terminal.printLineSlow();
            promptContinue();
        }
    }
    // Printed before task four as instructions
    public static void TaskFourInstructions() {
        GameManager.terminal.clear();
        GameManager.terminal.printLineSlow(String.format("%sFIGHT%s", ANSI.BOLD, ANSI.RESET));
        GameManager.terminal.printLineSlow(
            "In this minigame, you'll fight a Wyvern alongside Rowan. Each side takes turns attacking or supporting their side."
        );
        GameManager.terminal.printLineSlow();
        GameManager.terminal.printLineSlow(String.format("%sHow to Play%s", ANSI.BOLD, ANSI.RESET));
        GameManager.terminal.printLineSlow(String.format(
            "Some attacks have limited uses. For Oak, select one of the three options for attacks. Daring Daggers does more damage "
            + "than Bow Blast, which does more damage than Sword Slash. For Rowan, select one of the three actions. Arcane Blast does damage, "
            + "Arcane Shield shields the dragon's attack once, and Arcane Heal heals your HP by " + HeartParser.getHearts(1) + "."
            , ANSI.BOLD, ANSI.RESET, ANSI.BOLD, ANSI.RESET, ANSI.BOLD, ANSI.RESET, ANSI.BOLD, ANSI.RESET)
        );
        GameManager.terminal.printLineSlow();
        promptContinue();
    }
    // Printed after task four as exposition Sinray November 12th
    public static void conclude() { //Change to game ending happy :D
        GameManager.terminal.clear();
        GameManager.terminal.printLineSlow("Oak: I- I can't believe we managed to do that…");
        GameManager.terminal.printLineSlow();
        GameManager.terminal.printLineSlow(
            "Rowan mutters something to himself, you infer he’s thinking about if we should have fought the golem. "
            + "Maybe Willow would still be alive. However, he doesn't say anything about it, he clenches his jaw and speaks."
        );
        GameManager.terminal.printLineSlow();
        GameManager.terminal.printLineSlow(
            "Rowan: We need to skin this Wyvern, get whatever scales we can. These are extremely delicate and valuable; we "
            + "can't mess this part up. Afterwards we need to bolt back home."
        );
        GameManager.terminal.printLineSlow();
        GameManager.terminal.printLineSlow(
            "Oak: We need to be extra cautious here or it would’ve all been for nothing. This’ll be our first..."
        );
        GameManager.terminal.printLineSlow();
        promptContinue();

        GameManager.terminal.printLineSlow(
            "You carefully skin the Wyvern with a sword, being careful as to not expose much flesh to avoid attracting "
            + "its mother. Any mistakes you make Rowan quickly fixes with a modified arcane shield."
        );
        GameManager.terminal.printLineSlow();
        GameManager.terminal.printLineSlow("You are quite impressed by his ingenuity.");
        GameManager.terminal.printLineSlow();
        GameManager.terminal.printLineSlow("Oak: Wow, you really are a genius.");
        GameManager.terminal.printLineSlow();
        GameManager.terminal.printLineSlow(
            "Rowan: Stay focused, this fix is only temporary after we finish we need to bolt as it won’t hold for "
            + "long."
        );
        GameManager.terminal.printLineSlow();
        promptContinue();

        GameManager.terminal.printLineSlow(
            "After completely skinning the Wyvern, you and Rowan bolt back home, with the scales in hand and Willow gone, "
            + "the villagers have a mix of grief and joy on their faces. Quickly, you sell the scales off to the Acer court "
            + "as a bounty, claiming 800 gold. Much to everyone's dismay, you and Rowan decided to buy a horde of wild "
            + "trolls and some armour and weapons just in case. You release them into the Variegata forest, to the confusion "
            + " of the townsfolk. In a short few months, their confusion/anger turns into a look of thanks as the forest "
            + "seemingly cleared itself out on its own."
        );
        GameManager.terminal.printLineSlow();
        GameManager.terminal.printLineSlow("However, still unsatisfied, you ask.");
        GameManager.terminal.printLineSlow();
        GameManager.terminal.printLineSlow("Oak: Was it worth it? ");
        GameManager.terminal.printLineSlow();
        Narrative.promptContinue();

        GameManager.terminal.printLineSlow(
            "Rowan: I think this is what Willow wanted, isn't it? Everyone's safe now, we cleared the forest, and the "
            + "animals are coming back. We have food, we have resources to sell, and Bauhinia is saved."
        );
        GameManager.terminal.printLineSlow();
        GameManager.terminal.printLineSlow("Disappointed by his answer, you ask again.");
        GameManager.terminal.printLineSlow();
        GameManager.terminal.printLineSlow("Oak: Was it worth it for you?");
        promptContinue();

        GameManager.terminal.printLineSlow("Rowan takes a breath, looking down.");
        GameManager.terminal.printLineSlow();
        GameManager.terminal.printLineSlow("Rowan: I don't think I can answer that...");
        GameManager.terminal.printLineSlow();
        GameManager.terminal.printLineSlow("You nod in solemn agreement.");
        GameManager.terminal.printLineSlow();
        promptContinue();

        GameManager.terminal.printLineSlow(
            "Despite everything, you look to tomorrow with a smile on your face, the dull past of Bauhinia is now "
            + "finally the past..."
        );
        GameManager.terminal.printLineSlow();
        promptContinue();
        //Sinray November 14th - Ending ASCII art and final message
        GameManager.terminal.printLine(
            ANSI.FOREGROUND_GREY
            + """
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣀⣀
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣴⠟⣿⣿⣿⣿⣷⣦
⠀⠀⠀⠀⠀⢀⣀⣤⡤⠄⠀⠘⠃⢸⣿⣿⠇⢹⣿⢻⣧
⠀⠀⠀⢀⣾⣿⡿⠁⠀⠀⠀⠀⠀⠘⠿⠋⠀⠘⡏⠀⢿⡄
⠀⠀⠀⢸⠇⣿⡇⠀⢠⣴⣾⣿⠟⢀⣴⣶⣶⣦⡀⠀⠀⠑
⠀⠀⠀⢸⠀⠻⠁⣠⣿⣿⣿⡏⠀⣾⡟⢿⣿⣿⣿⡄⠀⠈⠛⢿⣶⣦⣄
⠀⠀⠀⠸⠀⢀⣾⣿⣿⡟⢹⠃⠀⢹⠀⢸⣿⠙⣿⣷⣦⡀⠀⠀⢿⣿⡿⣧
⠀⠀⠀⠀⠀⢸⣿⠛⣿⡇⢸⠀⢀⠀⠀⢸⡟⠀⣿⣿⣿⣷⠀⠀⠀⠙⢿⡘
⠀⠀⠀⠀⠀⣾⣿⠀⣿⡇⠀⠀⢾⣿⡀⠘⠇⠀⣿⠀⣿⣿⡇⠀⠀⠀⠸⡇
⠀⠀⠀⠀⠀⢸⣿⠀⢻⠃⠀⣄⣀⣀⠀⠀⠀⠀⠇⠀⣿⣿⠇⠀⠀⠀⠀⡇
⠀⠀⠀⠀⠀⢸⡿⠀⠀⠀⠀⣿⢹⣿⠿⣶⡀⠀⠀⠀⣿⣿⠀⠀⠀⠀⠀⠇
⠀⠀⠀⠀⠀⢸⠇⠀⠀⠀⠀⠃⠘⣿⠀⣿⡷⠀⣧⠀⢹⡇
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠀⢿⠃⢀⣿⣧⣀⠁⣰⡇
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣠⣾⣿⣿⣿⣿⣿⠁
⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠉⠉⠉⠉⠉⠉⠁
            """
            + ANSI.RESET,
            Terminal.AlignmentType.CENTRE,
            Terminal.AlignmentBehaviour.BLOCK
        );
        GameManager.terminal.printLine();
        GameManager.terminal.printLine(
            "We miss you, Willow...",
            Terminal.AlignmentType.CENTRE
        );
        GameManager.terminal.printLine();
        Narrative.promptContinue();

        GameManager.terminal.printLine("""
█▀▀██▀▀█ ▀██                 ▀██▀▀▀▀█               ▀██  ▄█▄
   ██     ██ ▄▄     ▄▄▄▄      ██  ▄    ▄▄ ▄▄▄     ▄▄ ██  ███
   ██     ██▀ ██  ▄█▄▄▄██     ██▀▀█     ██  ██  ▄▀  ▀██  ▀█▀
   ██     ██  ██  ██          ██        ██  ██  █▄   ██   █
  ▄██▄   ▄██▄ ██▄  ▀█▄▄▄▀    ▄██▄▄▄▄▄█ ▄██▄ ██▄ ▀█▄▄▀██▄  ▄
                                                         ▀█▀""",
           Terminal.AlignmentType.CENTRE,
           Terminal.AlignmentBehaviour.BLOCK
        );
        GameManager.terminal.printLine();
        Narrative.promptContinue();
    }
}