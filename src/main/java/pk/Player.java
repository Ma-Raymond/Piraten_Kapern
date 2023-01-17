package pk;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Player {
    int points = 0;
    Dice d1 = new Dice();
    Dice d2 = new Dice();
    Dice d3 = new Dice();
    Dice d4 = new Dice();
    Dice d5 = new Dice();
    Dice d6 = new Dice();
    Dice d7 = new Dice();
    Dice d8 = new Dice();

    Dice[] dices = {d1,d2,d3,d4,d5,d6,d7,d8};

    public void addPoint(int num){
        points += num;
    }
    public void initalRoll() {
        for ( Dice i : dices){
            i.current = i.roll();
        }
        printDices();
        checker();
    }
    public void printDices(){
        int o = 1;
        System.out.println("---------CURRENT DICES---------");
        for (Dice i : dices){
            System.out.println("Dice "+o+": "+i.current);
            o+= 1;
        }
        System.out.println("------------------------------");
    }

    public void reroll(String roll){
        String[] sp = roll.split(" ");
        int[] in = new int[sp.length];
        for(int i = 0; i< sp.length;i++){
            in[i] = Integer.parseInt(sp[i]);
        }
        for (int i : in){
            if (dices[i-1].current == Faces.SKULL){
                System.out.println("Illegal Reroll, Don't try and reroll a Skull");
                return;
            }
        }
        for (int i : in){
            dices[i-1].current = dices[i-1].roll();
        }
        printDices();
        checker();
    }
    int value = 0;
    public void checker(){
        value = 0;
        Map<Faces,Integer> counts = new HashMap<Faces,Integer>();
        Faces[] faceArray = Faces.values();
        for (Faces i : faceArray){
            counts.put(i,0);
        }
        for (Dice i : dices){
            counts.put(i.current,counts.getOrDefault(i.current,0)+1);
        }
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
        if (counts.get(Faces.SKULL) >= 3){
            System.out.println("YOU DEAD!!!! NO POINTS THIS ROUND! GO NEXT! ");
            System.out.println("CURRENT GAME POINTS: "+points);
            value = 0;
            return;
        }
        System.out.println("CURRENT GAME POINTS: "+points+"\t || \tCURRENT HAND POINTS: "+value);
    }
    public void cash(){
        points += value;
        value = 0;
    }

}