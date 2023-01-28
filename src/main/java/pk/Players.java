package pk;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Players {


    // This is the score of the Player, I will keep it updated everytime.
    public int score = 0;
    Faces[] dices = new Faces[8];
    Dice die = new Dice(); // Just used one dice to save memory and storage


    // This rolls 8 Dices, this is the initall roll.
    public Faces[] roll8(){
        for (int i=0; i < 8; i++){
            dices[i] = die.roll();
        }
        return dices;
    }

    static Map<Faces,Integer> counts = new HashMap<Faces,Integer>();
    static Faces[] faceArray = Faces.values();

    // This function will return how many skulls are in the
    public int howManySkulls(){
        return getMap().get(Faces.SKULL);
    }
    public Map<Faces,Integer> getMap(){
        for (Faces i : faceArray){
            counts.put(i,0);
        }
        for (Faces i : dices){
            counts.put(i,counts.getOrDefault(i,0)+1);
        }
        return counts;
    }
    // CARD SYSTEM ------------------------------------------
    CardDrawer drawer = new CardDrawer();

    // COMBOS SYSTEM ------------------------------------------
    static Map<Faces,Boolean> combos = new HashMap<Faces,Boolean>();
    // This getCombos will allow the user to get which
    public Map<Faces,Boolean> getCombos(){
        for (Faces i : faceArray){          // Makes a Dictionary for the combos
            combos.put(i,false);
        }
        counts = getMap();
        for (Faces i : faceArray){
            if (i == Faces.SKULL){
                continue;
            }
            if (counts.get(i) >= 3){
                combos.put(i,true);
            }
        }
        return combos;
    }
    // This function will determine if the roll will have the extra treasure chest points
    // TREASURE CHEST SYSTEM ------------------------------------------
    public boolean treasureChest(){
        if (howManySkulls() > 0){
            return false;
        }
        combos = getCombos();
        for (Faces i : dices){
            if (i == Faces.DIAMOND || i == Faces.GOLD || i == Faces.SKULL){
                continue;
            }
            if (!combos.get(i)){
                return false;
            }
        }
        return true;
    }

    // POINTS SYSTEM ------------------------------------------
    public int getPoints(){
            int value = 0;
            counts = getMap();
            for (Faces key : faceArray){
                if (counts.get(key) == 3){
                    value += 100;
                }
                else if (counts.get(key) == 4){
                    value += 200;
                }
                else if (counts.get(key) == 5){
                    value += 500;
                }
                else if (counts.get(key) == 6){
                    value += 1000;
                }
                else if (counts.get(key) == 7){
                    value += 2000;
                }
                else if (counts.get(key) == 8){
                    value += 4000;
                }
                if (key == Faces.DIAMOND || key == Faces.GOLD){
                    value += counts.get(key)*100;
                }
            }
            if (treasureChest()){           // Based on the rules will recieve 500
                value += 500;
            }
            if (counts.get(Faces.SKULL) >= 3){          // Safety net at the end
                value = 0;
            }
            return value;
    }

}
