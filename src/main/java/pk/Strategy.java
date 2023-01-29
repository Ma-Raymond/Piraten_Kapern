package pk;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class Strategy {
    static Dice die = new Dice();

    private static final Logger logger = LogManager.getLogger(Strategy.class);

    // This is the overarching play Style
    public static int playStyle(String style, Players player,Cards card){
        if (style.equals("random")){
            return playRandom(player);
        }
        else if (style.equals("combo")){
            return playCombos(player);
        }
        else if (style.equals("SEABATTLE")){
            return playSeaBattles(player,card);
        }
        else if (style.equals("MONKEYBUS")){
            return playMonkeyBusiness(player);
        }
        else{
            logger.error("Bad input"); // Shouldn't happen but catches it if it does happen
            return 0;
        }
    }
    public static int playRandom(Players player){
        Random random = new Random(); // Random Number
        boolean reroll = random.nextBoolean(); // If we should be rerolling at all or not

        // This set is used to ensure 2 different dice are rolled
        Set<Integer> set = new HashSet<Integer>();

        player.roll8();             // Initial Roll 8 dices
        while (reroll){             // If we want to continue re rolling
            set.clear();
            while (set.size() < 2){      // Retry if only one dice was rolled
                for (int i=0; i <8; i++){
                    boolean roll = random.nextBoolean(); // if indiviually should be rerolling or not?
                    if (player.dices[i] == Faces.SKULL){
                        continue;
                    }
                    if (roll && !set.contains(i)){     // This is used to make sure the one we are rerolling hasn't been before if we are retrying
                        set.add(i);
                        player.dices[i] = die.roll();
                    }
                }
            }
            if (player.howManySkulls() >= 3){             // Check how many skulls conviently
                break;
            }
            reroll = random.nextBoolean();      // Make random decision for if we want to continue rerolling or not
        }
        return player.getPoints();
    }

    // Basically how this play combos works is that it will reroll every dice thats not earning money for the maximum amount of combos

    public static int playCombos(Players player){
        player.roll8();
        Map<Faces,Boolean> combos;
        boolean maxCombos = true;
        List<Integer> rolls = new ArrayList<Integer>();   // Keep track of rolls that arent a combo

        // This maximum combo algorithm basically searches for any current combo eg. 3 of a kind, 4..5...etc.
        // This algorithm will reroll everything that ISNT making profit. I skip over diamonds and gold since they make money.
        // This way, it maximized the amount of money we get and also gets the money of a Treasure Chest
        while (maxCombos) {
            combos = player.getCombos();
            rolls.clear();
            for (int i=0; i< 8; i++){
                if (player.dices[i] == Faces.DIAMOND || player.dices[i] == Faces.GOLD || player.dices[i] == Faces.SKULL){
                    continue;
                }
                if (!combos.get(player.dices[i])){      // Will determine if eg. Monkeys is a current combo, if its false then we reroll
                    rolls.add(i);                  // we dont want to roll immedaitely, I need to determine if its even worth it. I save the index
                }
            }
            if (rolls.size() < 2){                      // If there is just one dice that is not a combo, and nothing else, I determined the current roll is the maximum amount of combos and i exit.
                maxCombos = false;      // Set to false and stops rerolling since it is a trade-off if ONLY one dice is not a combo
            }                           // It is more worth to keep the ones in hand then reroll at least two in which one is already in a combo
            else{
                for (Integer i : rolls){
                    player.dices[i] = die.roll();
                }
            }
            // Check how many skulls. Break the loop and end the turn
            if (player.howManySkulls() >= 3){
                break;
            }
        }
        return player.getPoints();
    }

    // Here is the Sea Battles Strategy we roll to get the correct amount of Sabors
    public static int playSeaBattles(Players player,Cards card){
        player.roll8();
        // Since there are three variations of sea battle, I need the details of all three cards
        // Card Details: {Type of Sea Battle : [# of sabors needed, bonus score]}
        Map<Cards,Integer[]> saborMap = Map.of(
                Cards.SEABATTLE2,new Integer[] {2,300},
                Cards.SEABATTLE3,new Integer[] {3,500},
                Cards.SEABATTLE4,new Integer[] {4,1000});

        // Determine how many sabors are needed
        int saborsNeeded = saborMap.get(card)[0];
        while (player.getMap().get(Faces.SABER) < saborsNeeded){
            // Base Case if we hit 3 skulls
            if (player.howManySkulls() >= 3){
                // "If you fail, however, your dice are ignored and you lose the indicated bonus points."
                return -saborMap.get(card)[1];
            }

            for (int i=0; i< 8; i++){
                // We dont want to roll current sabors and we cant roll skulls regardless
                if (player.dices[i] == Faces.SKULL || player.dices[i] == Faces.SABER){
                    continue;
                }
                // This will always roll at least 2 dices since 3 skulls will immediately fail and highest sabors needed is 4, meaning there is enough gap for least 2 dice to be rolled
                player.dices[i] = die.roll(); // Forced roll until we get sabors because we dont want to lose points
            }
        }
        // Get the original winnings + bonus!
        return player.getPoints() + saborMap.get(card)[1];
    }


    // MONKEY BUSINESS STRATEGY ----------------------------------------------
    static List<Faces> skip = Arrays.asList(Faces.SKULL,Faces.MONKEY,Faces.PARROT);
    Set<Faces> skipSet = new HashSet<Faces>(skip);
    public static int playMonkeyBusiness(Players player){
        player.roll8();
        Set<Integer> set = new HashSet<Integer>();
        boolean reroll = true;
        while (reroll){
            // Set used to check which dices are not MONKEYS PARROTS OR SKULLS and we want to reroll them
            // However, if theres only one that is not that, since the rule is we need to roll at least 2 when rerolling, it is more worth to not roll two
            set.clear();
            for (int i=0; i< 8; i++){
                if (skip.contains(player.dices[i])){
                    continue;
                }
                set.add(i); // Adding the ones that are not PARROT MONKEY OR SKULLS
            }
            if (set.size() <2){
                reroll = false;     // This is a case of only 1 dice is not a MONKEY SKULL OR PARROT, trade-off, no need to reroll anymore
            }
            else{          // More than 2 dices that are not a PARROT MONKEY OR SKULL reroll them.
                for (Integer i : set){
                    player.dices[i] = die.roll();
                }
            }
            // Check for how many skulls, if 3 or more, return 0 money
            if (player.howManySkulls() >= 3){
                return 0;
            }
        }
        return player.getPoints();
    }

}
