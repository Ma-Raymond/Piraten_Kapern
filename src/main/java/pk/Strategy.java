package pk;

import java.util.*;

public class Strategy {
    static Dice die = new Dice();

    public static int playStyle(String style, Players player){
        if (style.equals("random")){
            return playRandom(player);
        }
        else if (style.equals("combo")){
            return playCombos(player);
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
            if (player.howManySkulls() >= 3){             // Check how many skulls because at 3 its automatically out
                break;
            }
            rolls = arrlistObj.toArray(rolls);          // Assigns the rolls back as an Array instead of an ArrayList
            if (rolls.length < 2){                      // If there is just one dice that is not a combo, and nothing else, I determined the current roll is the maximum amount of rolls.
                maxCombos = false;
            }
            else{
                for (Integer i : rolls){
                    player.dices[i] = die.roll();
                }
                maxCombos = !holder;
            }
        }
        return player.getPoints();
    }

}
