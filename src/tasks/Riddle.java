//Sneh November 7th, Task two Wall of Mystery code
package tasks;

import constants.*;
import contexts.*;
import shared.*;
import util.*;

public class Riddle extends Task { // Start of task
    public void print() {
        GameManager.terminal.clear();
        GameManager.terminal.printLine(
            ANSI.FOREGROUND_GREEN
            + """
█   █ ▄▀▄ █   █     ▄▀▄ █▀   █▄ ▄█ ▀▄▀ ▄▀▀ ▀█▀ ██▀ █▀▄ ▀▄▀
▀▄▀▄▀ █▀█ █▄▄ █▄▄   ▀▄▀ █▀   █ ▀ █  █  ▄██  █  █▄▄ █▀▄  █"""
            + ANSI.RESET,
            Integer.MAX_VALUE,
            Terminal.AlignmentType.CENTRE,
            Terminal.AlignmentBehaviour.BLOCK
        );
        GameManager.terminal.printLine();
        Narrative.promptContinue();
    }

    public void play() { // The task put together 
        print();
        TaskTwo1();
        TaskTwo2();
    }
    
    public void TaskTwo1() { //The first two riddles 
        saveData();

        GameManager.terminal.clear();
        GameManager.terminal.printLineSlow("Something resembling a puzzle appears. \n");
        GameManager.terminal.printLineSlow("The text reads:\n");
        GameManager.terminal.printLineSlow("Lifeblood of the village,");
        GameManager.terminal.printLineSlow("born of soil, sun, and rain.");
        GameManager.terminal.printLineSlow("Blessing or burden,");
        GameManager.terminal.printLineSlow("I'm the reason Bauhinia lives.");
        GameManager.terminal.printLineSlow("What am I?");
        GameManager.terminal.printLineSlow();
        GameManager.terminal.printLineSlow("Rowan: Geez, what the hell, Willow, that could have been dangerous!! \n");
        GameManager.terminal.printLineSlow("Willow: Well, it turned out all right, didn't it?");
        GameManager.terminal.printLineSlow();
        Narrative.promptContinue();

        GameManager.terminal.printLineSlow("Oak: Guys, shouldn't we start focusing on what's at hand? It seems like a riddle. \n");
        GameManager.terminal.printLineSlow(
            "Rowan: Okay, wait, this is a standard puzzle; all we need to do is chant the riddle and the answer. "
            +"However, I'm not sure what the answer here is. \n"
        );
        GameManager.terminal.printLineSlow("Willow: Me neither… What do you think, Oak?");
        GameManager.terminal.printLineSlow();
        Narrative.promptContinue();
        
        // Reprint if nothing is answered
        String answer;
        do {
            GameManager.terminal.printLine(
                "Lifeblood of the village,",
                Terminal.AlignmentType.CENTRE
            );
            GameManager.terminal.printLine(
                "born of soil, sun, and rain.",
                Terminal.AlignmentType.CENTRE
            );
            GameManager.terminal.printLine(
                "Blessing or burden,",
                Terminal.AlignmentType.CENTRE
            );
            GameManager.terminal.printLine(
                "I'm the reason Bauhinia lives.",
                Terminal.AlignmentType.CENTRE
            );
            GameManager.terminal.printLineSlow();
            GameManager.terminal.printLine(
                "What do you think the answer is?",
                Terminal.AlignmentType.CENTRE
            );
            answer = GameManager.terminal.scanner.nextLine();
            GameManager.terminal.clear();
        } while (answer.isBlank());
        GameManager.terminal.printLineSlow("Rowan: Then it's decided, it's a " + answer + ". \n");
        GameManager.terminal.printLineSlow("The party chants: \n");
        GameManager.terminal.printLineSlow("Lifeblood of the village,");
        GameManager.terminal.printLineSlow("born of soil, sun, and rain.");
        GameManager.terminal.printLineSlow("Blessing or burden,");
        GameManager.terminal.printLineSlow("I'm the reason Bauhinia lives.");
        GameManager.terminal.printLineSlow("I am a " + answer + ".");
        GameManager.terminal.printLineSlow();
        Narrative.promptContinue();
        //If condition for if they got it correct
        if (answer.equalsIgnoreCase("tree")) { //To ignore the case
            GameManager.terminal.printLineSlow("The wall crumbles, nothing happens, but another wall appears where the puzzle just was.  \n");
            GameManager.terminal.printLineSlow("Rowan: Wow we got it right! Wait a minute... \n");
            GameManager.terminal.printLineSlow(
                "You're party looks down where the wall just was, with a disarmed dispenser, filled with arrows. \n"
            );
            GameManager.terminal.printLineSlow("Rowan: Thank god, that could have been dangerous. \n");
            GameManager.terminal.printLineSlow("Wllow pauses for a second, swallowing her pride. \n");
            GameManager.terminal.printLineSlow(
                "Willow: Rowan, I'm sorry, you're right, this place was a mistake. I shouldn't have forced us to go."
                );
            GameManager.terminal.printLineSlow();
            Narrative.promptContinue();
        } else {
            GameManager.terminal.printLineSlow("The wall crumbles, and suddenly a barrage of arrows hits your party... \n");
            int took = Math.min(Narrative.HP, 4);
            GameManager.terminal.printLineSlow("🔻 Took " + HeartParser.getHearts(took));
            GameManager.terminal.printLineSlow();
            Narrative.HP -= took;
            GameManager.terminal.printLineSlow("Your HP: " + HeartParser.getHearts(Narrative.HP, Narrative.MAX_HP));
            GameManager.terminal.printLineSlow();
            Narrative.promptContinue();
            if (Narrative.HP <= 0) {
                GameManager.gameplay.gameOver();
                GameManager.gameplay.gameOverInput(null, "riddle");
                return;
            }
            GameManager.terminal.printLineSlow("Rowan: Holy, how are we alive... \n");
            GameManager.terminal.printLineSlow("Willow: I'm so sorry... You were right, this place was a mistake.");
            GameManager.terminal.printLineSlow();
            Narrative.promptContinue();
            Narrative.Intelligence += 2;
        }
        GameManager.terminal.printLineSlow(
            "Rowan: It's okay, what's done is done. Now, judging by what we have seen, it's a sequential style exit. "
            +"Basically, the puzzles start out broad, getting more specific each time. In this style of puzzles, there are typically "
            +"three total puzzles, with each dealing more damage than the last, the last one being an instakill. The logic is that the "
            +"broad puzzles are survivable, because, well, what if the creator forgets, but the last one is so specific that the creator "
            +"shouldn't ever get it wrong. \n"
        );
        GameManager.terminal.printLineSlow("You and Willow nod in understanding. \n");
        Narrative.promptContinue();
        
        int questionSol = 0; 
        while (questionSol != 1 && questionSol !=2) { //To loop if they don't have a valid input
            GameManager.terminal.printLineSlow(
                "You wipe off the dust on the second wall, and four symbols appear, each resembling: a slime, a wyvern, a troll,"
                +" and a goblin respectively. \n"
            );
            GameManager.terminal.printLineSlow(
                "Rowan: Okay yes I see that, I have definitely seen this type of puzzle before. All we need to do is hit the correct brick. \n"
            );
            GameManager.terminal.printLineSlow("Willow: Okay, easy, we hit the strongest creature's brick? \n");
            GameManager.terminal.printLineSlow(
                "Rowan: I don't think so… These types of puzzles are typically made to tell a story or provide "
                +"a convoluted solution. If we work backwards from the problems of the forest, I think we can track the answer to the puzzle."
            );
            GameManager.terminal.printLineSlow();
            GameManager.terminal.printLine(
                "I think the puzzle is referring to:",
                Terminal.AlignmentType.CENTRE
            );
            GameManager.terminal.printLine();
            GameManager.terminal.printLine(
                String.format(
                    "    [%s%s%s] | The new ecological balance of Variegata\n"
                    + "    [%s%s%s] | The lack of resources available in this sector of the Acer kingdom",
                    ANSI.ITALIC + ANSI.FOREGROUND_GREEN, Keybinds.OPTION_1, ANSI.RESET,
                    ANSI.ITALIC + ANSI.FOREGROUND_BLUE, Keybinds.OPTION_2, ANSI.RESET
                ),
                Terminal.AlignmentType.CENTRE,
                Terminal.AlignmentBehaviour.BLOCK
            );
            try {
                String input = GameManager.terminal.scanner.nextLine();
                questionSol = Integer.parseInt(input);
            } catch (Exception e) {
                questionSol = 0;
            }
            switch (questionSol) { //Switch statement for the answer
                case 1 -> {
                    GameManager.terminal.clear();
                    GameManager.terminal.printLineSlow(
                        "Oak: What if it's referring to the new ecosystem in Variegata? Like how it would establish itself? \n"
                    );
                }
                case 2 -> {
                    Narrative.Intelligence++;
                    GameManager.terminal.clear();
                    GameManager.terminal.printLineSlow(
                        "Oak: Well, what if it's the lack of resources in the Acer Kingdom? I mean, maybe it's telling us what we can farm? \n"
                    );
                    GameManager.terminal.printLineSlow(
                        "Rowan: I'm not to sure. I mean the lack of resources makes it nearly impossible to farm the monsters. \n"
                    );
                    GameManager.terminal.printLineSlow(
                        "Willow: What if it's referring to the new ecosystem in Variegata? Like how it would establish itself?  \n"
                    );
                }
            }
        }
       
        GameManager.terminal.printLineSlow("Rowan: Wait, I think you're on to something! \n");
        GameManager.terminal.printLineSlow("Willow: Well, if that's the case, it would have to be the order of the food chain, right?");
        GameManager.terminal.printLineSlow();
        Narrative.promptContinue();

        int puzzle2Ans = 0;
        while (puzzle2Ans != 1 && puzzle2Ans !=2 && puzzle2Ans != 3 && puzzle2Ans !=4) { // Repeat if an invalid input
            GameManager.terminal.printLineSlow(
                "Rowan: Wait, but it's supposed to be a solution? We can't wipe out a species, but we sure can overintroduce one, right? "
                +"Wyvern are incredibly intelligent for wild monsters, heck, they don't fight trolls that often to avoid small injuries that "
                +"add up over time. I don't know how that would play out. \n"
            );
            GameManager.terminal.printLineSlow(
                "Willow: Well, slimes are herbivores; they could kill the plant life, couldn't they? But "
                +"goblins eat slime, and trolls eat slimes and goblins… \n"
            );
            GameManager.terminal.printLineSlow("Rowan: What do you think Oak?");
            GameManager.terminal.printLineSlow();
            GameManager.terminal.printLine(
                "What species should be overintroduced?",
                Terminal.AlignmentType.CENTRE
            );
            GameManager.terminal.printLine();
            GameManager.terminal.printLine(
                String.format(
                "[%s%s%s] | Slimes\n[%s%s%s] | Wyverns\n[%s%s%s] | Trolls\n[%s%s%s] | Goblins",
                    ANSI.ITALIC + ANSI.FOREGROUND_GREEN, Keybinds.OPTION_1, ANSI.RESET,
                    ANSI.ITALIC + ANSI.FOREGROUND_BLUE, Keybinds.OPTION_2, ANSI.RESET,
                    ANSI.ITALIC + ANSI.FOREGROUND_RED, Keybinds.OPTION_3, ANSI.RESET,
                    ANSI.ITALIC + ANSI.FOREGROUND_GREY, Keybinds.OPTION_4, ANSI.RESET
                ),
                Terminal.AlignmentType.CENTRE,
                Terminal.AlignmentBehaviour.BLOCK
            );
            try {
                String input = GameManager.terminal.scanner.nextLine();
                puzzle2Ans = Integer.parseInt(input);
            } catch (Exception e) {
                puzzle2Ans = 0;
            }
            GameManager.terminal.clear();

            switch (puzzle2Ans) { //Check the answer with switch statement
                case 1 -> {
                    GameManager.terminal.printLineSlow(
                        "Oak: I think the answer should be slimes, they kill off the plant matter, they starve, then the goblins and "
                        +"trolls starve, and finally the wyverns die! \n"
                    );
                    GameManager.terminal.printLineSlow("Willow and Rowan silently nod in unsure agreement, push the slime brick, then… \n");
                    Puzzle2Wrong();
                }
                case 2 -> {
                    GameManager.terminal.printLineSlow(
                        "Oak: I think the answer should be wyvern. They eat everything, and once they kill off all possible food sources, "
                        +"they will die of starvation or leave! \n"
                    );
                    GameManager.terminal.printLineSlow("Willow and Rowan silently nod in unsure agreement, push the Wyvern brick, then… \n");
                    Puzzle2Wrong();
                }
                case 3 -> {
                    GameManager.terminal.printLineSlow(
                        "Oak: I think the answer should be trolls. They are mindless enough to overhunt, but powerful enough not to "
                        +"be defeated! \n"
                    );
                    GameManager.terminal.printLineSlow(
                        "Rowan: Plus, I doubt the wyverns would try hunting them, especially a large group of trolls, at least not until "
                        +"everything else is killed off. I think you're right! \n"
                    );
                    GameManager.terminal.printLineSlow("Willow nods in agreement, pushes the Troll brick, then...");
                    GameManager.terminal.printLineSlow();
                    Narrative.promptContinue();

                    GameManager.terminal.printLineSlow("Nothing happens... \n");
                    GameManager.terminal.printLineSlow(
                        "Your party looks down where the trap should be, and there's a volley of arrows in a dispenser. \n"
                    );
                    GameManager.terminal.printLineSlow(
                        "Willow: What the hell, if we got hit by that full barrage, we would be dead by now... I'm really sorry, I really am. \n"
                    );
                    GameManager.terminal.printLineSlow(
                        "Rowan: It's fine, yes, we shouldn't have gotten inside, but there's literally nothing we can do but move forward."
                    );
                    GameManager.terminal.scanner.nextLine(); 
                    GameManager.terminal.printLineSlow();
                    Narrative.promptContinue();
                }
                case 4 -> {
                    GameManager.terminal.printLineSlow(
                        "Oak: I think the answer should be Goblins. They kill off the slime population. Then it's just a matter of time until "
                        +"they die off, and the Wyverns and Trolls are left to fight to the death, with the winner either dying of starvation or"
                        +" leaving. \n"
                    );
                    GameManager.terminal.printLineSlow("Willow and Rowan silently nod in unsure agreement, push the Goblin brick, then… \n");
                    Puzzle2Wrong();
                }
            }
        }
        GameManager.terminal.printLineSlow("The next puzzle is behind a thin wall. Willow hits it down and disappears. \n");
        GameManager.terminal.printLineSlow("Oak: WHAT THE HELL JUST HAPPENED!! \n");
        GameManager.terminal.printLineSlow("Rowan: I- I don't know. I think we just have to follow... \n");
        GameManager.terminal.printLineSlow("Before you can intercept, Rowan does the same. \n");
        GameManager.terminal.printLineSlow("You decide to follow them. \n");
        Narrative.promptContinue();
    }

    // Player messed up
    public void Puzzle2Wrong() {
        // This actually means they are foolish (higher is worse)
        Narrative.Intelligence+=2;
        GameManager.terminal.printLineSlow("Suddenly, a barrage of arrows hits your party. \n");
        int took = Math.min(Narrative.HP, 4);
        GameManager.terminal.printLineSlow("🔻 Took " + HeartParser.getHearts(took));
        GameManager.terminal.printLineSlow();
        // Take damage, die, return to last save
        Narrative.HP -= took;
        GameManager.terminal.printLineSlow("Your HP: " + HeartParser.getHearts(Narrative.HP, Narrative.MAX_HP));
        if (Narrative.HP <= 0) {
            Narrative.promptContinue();
            GameManager.gameplay.gameOver();
            GameManager.gameplay.gameOverInput(null, "riddle");
            return;
        }
        GameManager.terminal.printLineSlow("Rowan: Holy, how are we alive... \n");
        GameManager.terminal.printLineSlow(
            "Willow: You were so right... How was I so foolish to believe we could beat it? This is the escape, and we are struggling this much. "
            + "I don't even want to imagine what the seventh floor would look like.  \n"
        );
        GameManager.terminal.printLineSlow(
            "Rowan: It's fine, yes, we shouldn't have gotten inside, but there's literally nothing we can do but move forward."
        );
        GameManager.terminal.printLineSlow();
        Narrative.promptContinue();
    }
    public void TaskTwo2() {
        saveData();

        // Calculate strength based on health
        Narrative.Strength = -Narrative.HP + 6;
        int Characteristic = 0;
        String charName = "";
       
        if (Narrative.Strength == 0 && Narrative.Morality == 0 && Narrative.Intelligence == 0) {
            //All are nothing, puzzle three skipped
        } else if (Narrative.Strength > Narrative.Intelligence && Narrative.Strength > Narrative.Morality) {
            Characteristic=1;
            charName = "strength";
        } else if (Narrative.Morality >= Narrative.Strength && Narrative.Morality >= Narrative.Intelligence) {
            Characteristic=2;
            charName = "morality";
        } else {
            Characteristic=3;
            charName = "intelligence";
        }
        GameManager.terminal.clear();
        if (Characteristic == 0) {
            GameManager.terminal.printLineSlow(
                "All alone now, you look around you with nothing but a wall in front of you. You can't comprehend the void surrounding it "
                +"so you don't try.", 40l
            );
            GameManager.terminal.printLineSlow("From what you make out, the wall asks,\n", 80l);
            GameManager.terminal.printSlow(" \"You are perfect, aren't you?\"\n", 80l);
            GameManager.terminal.printLineSlow("You are a bit confused, but nod in agreement with the wall.\n", 80l);
            GameManager.terminal.printLineSlow("The wall crumbles, and you see a passage appear where the wall was and walk through.", 40l);
        } else {
            GameManager.terminal.printLineSlow(
                "All alone now, you look around you with nothing but a wall in front of you. You can't comprehend the void surrounding it so "
                +"you don't try.", 40l
            );
            GameManager.terminal.printLineSlow();
            Narrative.promptContinue();
            
            int charchoice = 0;
            while (charchoice != 1 && charchoice !=2 && charchoice !=3) {
                GameManager.terminal.printLineSlow("From what you make out, the wall asks,\n", 80l);
                GameManager.terminal.printLineSlow("\"What are you...?\"", 120l);
                GameManager.terminal.printLineSlow("\"Weak?\"", 120l);
                GameManager.terminal.printLineSlow("\"Immoral?\"", 120l);
                GameManager.terminal.printLineSlow("\"Foolish?\"", 120l);
                GameManager.terminal.printLineSlow();
                GameManager.terminal.printLineSlow(
                    "You take a second to think. With your pride on the sidelines, you realize maybe you're not so perfect. Maybe you're more "
                    +"flawed than perfections...", 40l
                );
                GameManager.terminal.printLineSlow();
                GameManager.terminal.printLine(
                    "What is your confession?",
                    Terminal.AlignmentType.CENTRE
                );
                GameManager.terminal.printLine();
                // Same idea as other files to print centred and aligned using one block of text
                GameManager.terminal.printLine(
                    String.format(
                        "[%s%s%s] | I am weak...\n"
                        + "[%s%s%s] | I am immoral...\n"
                        + "[%s%s%s] | I am foolish...",
                        ANSI.ITALIC + ANSI.FOREGROUND_BLUE, Keybinds.OPTION_1, ANSI.RESET,
                        ANSI.ITALIC + ANSI.FOREGROUND_RED, Keybinds.OPTION_2, ANSI.RESET,
                        ANSI.ITALIC + ANSI.FOREGROUND_GREEN, Keybinds.OPTION_3, ANSI.RESET
                    ),
                    Terminal.AlignmentType.CENTRE,
                    Terminal.AlignmentBehaviour.BLOCK
                );
                try {
                    String input = GameManager.terminal.scanner.nextLine();
                    charchoice = Integer.parseInt(input);
                } catch (Exception e) {
                    charchoice = 0;
                }
                GameManager.terminal.clear();

                if (charchoice == Characteristic) {
                    GameManager.terminal.printLineSlow(
                        "The wall crumbles, and you see a passage appear where the wall was. You walk through it.");
                } else if (charchoice == 1 || charchoice ==2 || charchoice ==3) {
                    GameManager.terminal.clear();
                    // Print slow
                    GameManager.terminal.printLineSlow(
                        "Suddenly, a trap activates, and you feel a sharp pain in your side. "
                        + "Everything turns to black and...",
                        40l
                    );
                    GameManager.terminal.printLineSlow();
                    Narrative.promptContinue();
                    GameManager.gameplay.gameOver();
                    GameManager.gameplay.gameOverInput(null, "taskTwo2");
                    return;
                }
            }
        }
        GameManager.terminal.printLineSlow();
        Narrative.promptContinue();

        GameManager.terminal.printLineSlow(
            "You emerge back where the wall was, it now being crumbled and look over to see your friends are also here. \n"
        );
        GameManager.terminal.printLineSlow(
            "Rowan: What was that... I've never seen anything like that. It's true, though, I am a coward... How does it know? \n");
        GameManager.terminal.printLineSlow(
            "Willow: I- I don't know. The wall was right, I'm stupid and rash, and I'm the reason any of us are here. I don't know "
            + "how many times I've apologized, but let me thank you. Oak, Rowan, you're the only reasons I'm still alive, and I've been "
            + "the reason we are in danger. I've been like this for years, and yet I don't know why I can't be better. \n"
        );
        GameManager.terminal.printLineSlow(
            "Rowan: Willow, please, it's okay. I don't want to be here, but you're right. We need the gold, yes, you've been obsessed, but "
            + "you're right, it's the only way we can change anything."
        );
        GameManager.terminal.printLineSlow();
        Narrative.promptContinue();

        if (Characteristic == 0) {
            GameManager.terminal.printLineSlow(
                "Oak: We have all made mistakes, but Rowan is right; all we can do is move forward, plus "
                + "I know you were just looking out for the village. And Rowan, why are you so harsh on yourself? You're not cowardly, you're "
                + "cautious, and you're always the voice of reason. Don't you have a brother to come home to? Keep your head up, pal. \n"
            );
        } else {
            GameManager.terminal.printLineSlow("You speak up for once, adding on your own struggle.\n");
            GameManager.terminal.printLineSlow("Oak: It's not like you're the only two with flaws. I frankly lack any real " + charName
                + ", and it's cost me more times than I can count. But in some twisted way, this dungeon has helped us; maybe that's why we are here. \n"
            );
        }
        GameManager.terminal.printLineSlow(
            "All that's left in front of you is a thin wall. You all glance at each other one more time, and now, as a group, smash it down."
        );
        GameManager.terminal.printLineSlow();
        Narrative.promptContinue();
    }
}