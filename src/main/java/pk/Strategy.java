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
        else{
            System.out.println("Bad input"); // ----- need to update for logging.
            return 0;
        }
    }
    public static int playRandom(Players player){
        Random random = new Random(); // Random Number
        boolean reroll = random.nextBoolean(); // If we should be rerolling at all or not

        player.roll8();
        int atleast2 = 0;
        while (reroll){
            for (int i=0; i <8; i++){
                boolean roll = random.nextBoolean(); // if indeiviually should be rerolling or not?
                if (player.dices[i] == Faces.SKULL){
                    continue;
                }
                if (roll){
                    atleast2 += 1;
                    player.dices[i] = die.roll();
                }
            }
            if (player.howManySkulls() >= 3){             // Check how many skulls conviently
                break;
            }
            reroll = random.nextBoolean();
            if (atleast2 < 2){          // Force another random reroll if there isn't at least 2
                reroll = true;
            }
        }
        return player.getPoints();
    }

    // Basically how this play combos works is that it will reroll every dice thats not earning money for the maximum amount of combos

    public static int playCombos(Players player){
        player.roll8();
        Map<Faces,Boolean> combos;
        boolean maxCombos = true;
        while (maxCombos) {
            combos = player.getCombos();
            boolean holder = true;
            Integer[] rolls = {};
            List<Integer> arrlistObj = new ArrayList<Integer>(Arrays.asList(rolls));
            for (int i=0; i< 8; i++){
                if (player.dices[i] == Faces.DIAMOND || player.dices[i] == Faces.GOLD || player.dices[i] == Faces.SKULL){
                    continue;
                }
                if (!combos.get(player.dices[i])){      // Will determine if eg. Monkeys is a current combo, if its false then we reroll
                    holder = false;                     // If its not a combo, we want to keep rerolling until we maximize number of combos
                    arrlistObj.add(i);                  // we dont want to roll immedaitely, I need to determine if its even worth it. I save the index
                }
            }
            rolls = arrlistObj.toArray(rolls);          // Assigns the rolls back as an Array instead of an ArrayList
            if (rolls.length < 2){                      // If there is just one dice that is not a combo, and nothing else, I determined the current roll is the maximum amount of combos and i exit.
                maxCombos = false;
            }
            else{
                for (Integer i : rolls){
                    player.dices[i] = die.roll();
                }
                maxCombos = !holder;
            }
            // Check how many skulls. We want to automatically determine if we can continue
            if (player.howManySkulls() >= 3){
                break;
            }
        }
        return player.getPoints();
    }

    // Here is the Sea Battles Strategy we roll to get the correct amount of Sabors
    public static int playSeaBattles(Players player,Cards card){
        player.roll8();
        Map<Cards,Integer[]> saborMap = Map.of(
                Cards.SEABATTLE2,new Integer[] {2,300},
                Cards.SEABATTLE3,new Integer[] {3,500},
                Cards.SEABATTLE4,new Integer[] {4,1000});

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
                player.dices[i] = die.roll(); // Forced roll until we get sabors because we dont want to lose points
            }
        }
        // Get the original winnings + bonus!
        return player.getPoints() + saborMap.get(card)[1];
    }

}
