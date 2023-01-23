package pk;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Random;

public class Strategy {
    static Dice die = new Dice();
    public static int playRandom(Players player){
        Random random = new Random(); // Random Number
        boolean reroll = random.nextBoolean(); // If we should be rerolling at all or not

        player.roll8();
        while (reroll){
            for (int i=0; i <8; i++){
                boolean roll = random.nextBoolean(); // if indeiviually should be rerolling or not?
                if (player.dices[i] == Faces.SKULL){
                    continue;
                }
                if (roll){
                    player.dices[i] = die.roll();
                }
            }
            if (player.howManySkulls() >= 3){             // Check how many skulls conviently
                break;
            }
            reroll = random.nextBoolean();
        }
        return player.getPoints();
    }

    // Basically how this play combos works is that it will reroll every dice thats not earning money for the maximum amount of combos

    public static int playCombos(Players player){
        player.roll8();
        Map<Faces,Integer> counts = player.getMap();
        Map<Faces,Boolean> combos = player.getCombos();
        Faces[] faceArray = player.faceArray;
        boolean maxCombos = true;
        while (maxCombos) {
            combos = player.getCombos();
            boolean holder = true;
            for (int i=0; i< 8; i++){
                if (player.dices[i] == Faces.DIAMOND || player.dices[i] == Faces.GOLD || player.dices[i] == Faces.SKULL){
                    continue;
                }
                if (!combos.get(player.dices[i])){
                    holder = false;
                    player.dices[i] = die.roll();
                }
            }
            if (player.howManySkulls() >= 3){             // Check how many skulls because at 3 its automatically out
                break;
            }
            maxCombos = !holder;
        }

        return player.getPoints();
    }

}
