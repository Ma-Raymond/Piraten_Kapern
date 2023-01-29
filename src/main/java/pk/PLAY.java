package pk;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PLAY {
    private static final Logger logger = LogManager.getLogger(PLAY.class);

    public PLAY(int MAXGAMES,String arg0, String arg1){
        // New 2 Players ---------------------------
        Players player1 = new Players();
        Players player2 = new Players();
        double wins1 = 0; // Double to get decimals
        double wins2 = 0;

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
                else if (player1.card == Cards.MONKEYBUS){
                    player1.score += Strategy.playStyle("MONKEYBUS",player1,player1.card);
                }
                else{
                    player1.score += Strategy.playStyle(arg0,player1,player1.card);
                }

                // ------------------ Player 2 Strategy Options based on Cards ------------------
                if (seaSet.contains(player2.card)){
                    // SEA BATTLES IF WE PLAYER GETS THAT CARD
                    player2.score += Strategy.playStyle("SEABATTLE",player2,player2.card);
                }
                else if (player2.card == Cards.MONKEYBUS){
                    player2.score += Strategy.playStyle("MONKEYBUS",player2,player2.card);
                }
                else{
                    player2.score += Strategy.playStyle(arg1,player2,player2.card);
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
        // ------------------ PRINTING WINNERS ------------------ //
        System.out.printf("Player 1: %.2f%% \n",(wins1/MAXGAMES*100));
        System.out.printf("Player 2: %.2f%% \n",(wins2/MAXGAMES*100));
    }
}
