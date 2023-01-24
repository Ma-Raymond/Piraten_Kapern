import pk.Dice;
import pk.*;

import java.util.Arrays;
import java.util.Scanner;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class PiratenKarpen {

    private static final Logger logger = LogManager.getLogger(PiratenKarpen.class);
    public static void main(String[] args) {
        if (args.length != 2){
            logger.error("Wrong Number of Inputs. Please add Two Arguments. Accepted Arguments:\"random\" \"combo\" ");
            System.exit(1);
        }
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
        int MAXGAMES = 42;
        int SCORE_TO_ENDGAME = 6000;
        double wins1 = 0;
        double wins2 = 0;

        for (int i = 0; i< MAXGAMES; i++){              // This entails each game
            while (player1.score <= 6000 && player2.score <= 6000){
                player1.score += strategy1.playStyle(args[0],player1);
                player2.score += strategy1.playStyle(args[1],player2);
            }
            if (player1.score > player2.score){
                wins1 += 1;
            }
            else{
                wins2 += 1;}
            player1.score = 0; // Resets back the points for next game
            player2.score = 0;
        }
        System.out.printf("Player 1: %.2f%% \n",(wins1/MAXGAMES*100));
        System.out.printf("Player 2: %.2f%% \n",(wins2/MAXGAMES*100));

    }
}
