package pk;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Strategy {

    public static int playRandom(Players player){
        Random random = new Random(); // Random Number
        boolean reroll = random.nextBoolean(); // If we should be rerolling at all or not
        Dice die = new Dice();
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

}
