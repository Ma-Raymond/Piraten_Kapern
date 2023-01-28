import pk.Dice;
import pk.*;

import java.util.Arrays;
import java.util.Scanner;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.*;
public class PiratenKarpen {

    private static final Logger logger = LogManager.getLogger(PiratenKarpen.class);
    public static void main(String[] args) {
        // If user did not add any parameters, I stop them.
        if (args.length != 2 && args.length != 3){
            logger.error("Wrong Number of Inputs. Please add Two Arguments. (playStyle,playStyle,trace) Accepted Arguments:\"random\" \"combo\" optional:\"trace\" ");
            System.exit(1);
        }

        // If user inputs anything other than random or combo, I stop them.
        if ((!args[0].equals("random") && !args[0].equals("combo")) || (!args[1].equals("random") && !args[1].equals("combo"))){
            logger.error("Bad user Input, please try again. Accepted playStyles: \"random\" \"combo\" ");
            System.exit(1);
        }
        // Setting the Original Logger to be with Level type Error
        // This will not show debug parts, only the major errors, in which I would want to know if there's major erorrs.
        Logger loggerConfig = LogManager.getRootLogger();
        Configurator.setAllLevels(loggerConfig.getName(), Level.getLevel("ERROR"));

        // Checking if player wants to trace or not since that is my format.
        if (args.length == 3 && args[2].equals("trace")) {
                Configurator.setAllLevels(loggerConfig.getName(), Level.getLevel("ALL"));
        }

        // Small little introduction of game :)
        System.out.println("Welcome to Piraten Karpen Simulator!");
        System.out.println("------------------------------");
        int howManyFaces = Faces.values().length;
        System.out.println("  (DEBUG) there are " + howManyFaces + " faces");
        System.out.println("  (DEBUG) " + Arrays.toString(Faces.values()));
        System.out.println("------------------------------");

        // New 2 Players
        // Input Strategy Class to use to get the playStyle objects
        Players player1 = new Players();
        Players player2 = new Players();
        Strategy strategy1 = new Strategy();

        // Variables needed for this game
        int MAXGAMES = 42;
        double wins1 = 0; // Double to get
        double wins2 = 0;

        // Here I can control how many games I'm playing.
        // You can use the variable MAXGAMES to how many games you want to play
        for (int i = 0; i< MAXGAMES; i++){              // This entails each game
            while (player1.score <= 6000 && player2.score <= 6000){    // Each game will end when one player hits 6000 points
                // This will depend on what kind of playStyle the user gives. It's either
                player1.score += strategy1.playStyle(args[0],player1);
                player2.score += strategy1.playStyle(args[1],player2);
            }
            if (player1.score > player2.score){
                wins1 += 1;
            }
            else if (player1.score < player2.score){
                wins2 += 1;
            }
            // This is to replay the tied game
            else{
                i -= 1;
            }
            // If they tie, no on gets a point

            // Resets back the points to prepare for next game
            player1.score = 0;
            player2.score = 0;
        }
        System.out.printf("Player 1: %.2f%% \n",(wins1/MAXGAMES*100));
        System.out.printf("Player 2: %.2f%% \n",(wins2/MAXGAMES*100));
    }
}
