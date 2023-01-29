package pk;

import java.util.Arrays;
import java.util.HashMap;
import java.util.*;

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
        return dices;           // The list of dices
    }

    static Map<Faces,Integer> counts = new HashMap<Faces,Integer>(); // This count variable stores the frequency of each face
    static Faces[] faceArray = Faces.values(); // This is the values of all the faces of the game

    // This function will return how many skulls are in the
    public int howManySkulls(){
        return getMap().get(Faces.SKULL);
    }

    // Hashmap of frequency of each face ----------------------------
    public Map<Faces,Integer> getMap(){
        for (Faces i : faceArray){
            counts.put(i,0);
        }
        for (Faces i : dices){
            counts.put(i,counts.getOrDefault(i,0)+1);
        }
        return counts;
    }
    // COMBOS SYSTEM ------------------------------------------
    static Map<Faces,Boolean> combos = new HashMap<Faces,Boolean>();
    // This getCombos will allow the user to get which
    public Map<Faces,Boolean> getCombos(){
        for (Faces i : faceArray){          // Makes a Dictionary for the combos
            combos.put(i,false);
        }
        counts = getMap();
        // For every face, check if theres a combo ( amount >= 3 )
        // Make a hashmap based on the combos if its true or not
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
        if (howManySkulls() > 0){       // Base Case: any hand with a skull is immediately not considered for a treasure chest
            return false;
        }
        // If there isn't a combo, return false immediately
        combos = getCombos();
        for (Faces i : dices){
            if (i == Faces.DIAMOND || i == Faces.GOLD || i == Faces.SKULL){
                continue;
            }
            if (!combos.get(i)){
                return false;       // There is a dice thats not making money
            }
        }
        return true;
    }

    // POINTS SYSTEM ------------------------------------------
    Map<Integer,Integer> comboChart = Map.of(
            3,100,
            4,200,
            5,500,
            6,1000,
            7,2000,
            8,4000
    );
    public int getPoints(){
        int value = 0;
        counts = getMap();
        // This is for the Monkey Business Card, basically combining the two counts together
        if (card == Cards.MONKEYBUS){
            counts.put(Faces.MONKEY,counts.get(Faces.MONKEY)+counts.get(Faces.PARROT)); // Add those two together
            counts.put(Faces.PARROT,0);  // Set the parrot to 0 since we added it to monkey
        }
        // For each face there is, check the amount for each 3 or more of one face means a combo and bonus points
        for (Faces key : faceArray){
            if (counts.get(key) >= 3){
                value += comboChart.get(counts.get(key));
            }
            if (key == Faces.DIAMOND || key == Faces.GOLD){
                value += counts.get(key)*100;
            }
        }
        // Based on the rules will recieve an extra 500
        if (treasureChest()){ value += 500; }

        // Safety Net to check if there are 3 Skulls returns 0
        if (counts.get(Faces.SKULL) >= 3){ return 0; }

        return value;
    }

    // Card System ------------------------------------
    public Cards card;
}
