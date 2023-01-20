import pk.Dice;
import pk.*;

import java.util.Arrays;
import java.util.Scanner;

public class PiratenKarpen {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Piraten Karpen Simulator!");
        System.out.println("------------------------------");
        int howManyFaces = Faces.values().length;
        System.out.println("  (DEBUG) there are " + howManyFaces + " faces");
        System.out.println("  (DEBUG) " + Arrays.toString(Faces.values()));
        System.out.println("------------------------------");

        Players player1 = new Players();
        Players player2 = new Players();
        Strategy strategy1 = new Strategy();
        double wins1 = 0;
        double wins2 = 0;

        for (int i = 0; i< 42; i++){
            while (player1.score <= 6000 && player2.score <= 6000){
                player1.score += strategy1.playRandom(player1);
                player2.score += strategy1.playRandom(player2);
            }
            if (player1.score > player2.score){
                wins1 += 1;
            }
            else{
                wins2 += 1;
            }
            player1.score = 0; // Resets back the points for next game
            player2.score = 0;
        }
        System.out.printf("%.2f%% \n",(wins1/42*100));
        System.out.printf("%.2f%% \n",(wins2/42*100));
    }
}
