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

        // Variables needed for this game --------------------------
        int MAXGAMES = 42; // How many games we are going to play?

        // This will play the game
        new PLAY(MAXGAMES,args[0],args[1]);

    }
}
