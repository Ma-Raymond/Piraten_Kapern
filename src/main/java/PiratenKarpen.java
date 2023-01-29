import pk.Dice;
import pk.*;

import java.util.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Set;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.*;
public class PiratenKarpen {

    private static final Logger logger = LogManager.getLogger(PiratenKarpen.class);
    public static void main(String[] args) {
        // If user did not add any parameters, I stop them from playing
        if (args.length != 2 && args.length != 3){
            logger.error("Welcome to Piraten Karpen Simulator! Please make sure to add the correct # of arguments to start");
            logger.error("Wrong Number of Inputs. Please add Two Arguments. Args=(playStyle,playStyle,\"trace\") playStyles: \"random\" \"combo\" ");
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
            System.out.println("We are in TRACE MODE");
            Configurator.setAllLevels(loggerConfig.getName(), Level.getLevel("ALL"));
        }

        // Small little introduction of game :)
        System.out.println("Welcome to Piraten Karpen Simulator!");
        System.out.println("------------------------------");
        System.out.println("Player 1 is playing \""+args[0]+"\" strategy");
        System.out.println("Player 2 is playing \""+args[1]+"\" strategy");
        System.out.println("------------------------------");

        // New 2 Players
        Players player1 = new Players();
        Players player2 = new Players();

        // Variables needed for this game --------------------------
        int MAXGAMES = 42; // How many games we are going to play?
        double wins1 = 0; // Double to get a nice percentage at the end.
        double wins2 = 0;
        // ---------------------------------------------------------

        // CARD SYSTEM ------------------------------------------
        CardDrawer drawer = new CardDrawer();

        // Sea Battle Sets
        List<Cards> seaList = Arrays.asList(Cards.SEABATTLE2,Cards.SEABATTLE3,Cards.SEABATTLE4);
        Set<Cards> seaSet = new HashSet<Cards>(seaList);

        // GAME CODE ----------------------------------------------------------------------------------------
        for (int i = 0; i< MAXGAMES; i++){                             // This entails each game
            CardDrawer.currentPile.shuffle();
            while (player1.score <= 6000 && player2.score <= 6000){    // Each game will end when one player hits 6000 points

                // New Card each ROUND for each player!
                player1.card = drawer.draw();
                player2.card = drawer.draw();
                logger.trace("Player 1 has "+player1.card+" card");
                logger.trace("Player 2 has "+player2.card+" card");

                // ------------------ Player 1 Strategy Options based on Cards ------------------
                if (seaSet.contains(player1.card)){
                    // SEA BATTLES IF WE PLAYER GETS THAT CARD
                    player1.score += Strategy.playStyle("SEABATTLE",player1,player1.card);
                }
                else{
                    player1.score += Strategy.playStyle(args[0],player1,player1.card);
                }

                // ------------------ Player 2 Strategy Options based on Cards ------------------
                if (seaSet.contains(player2.card)){
                    // SEA BATTLES IF WE PLAYER GETS THAT CARD
                    player2.score += Strategy.playStyle("SEABATTLE",player2,player2.card);
                }
                else{
                    player2.score += Strategy.playStyle(args[1],player2,player2.card);
                }
            }

            // ------------------ Determining Winners ------------------
            if (player1.score > player2.score){ // Player 1 Wins
                wins1 += 1;
            }
            else if (player1.score < player2.score){ // Player 2 Wins
                wins2 += 1;
            }
            // We want to replay a game if there is a tied game
            else{
                i -= 1;
            }
            // Resets back the points to prepare for next game
            player1.score = 0;
            player2.score = 0;
        }
        // ------------------ PRINTING WINNERS ------------------
        System.out.printf("Player 1: %.2f%% \n",(wins1/MAXGAMES*100));
        System.out.printf("Player 2: %.2f%% \n",(wins2/MAXGAMES*100));
    }
}
