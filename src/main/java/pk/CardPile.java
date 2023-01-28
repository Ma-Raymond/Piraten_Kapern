package pk;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import java.util.*;
public class CardPile implements Source{
    private static final Logger logger = LogManager.getLogger(CardPile.class);
    Random rand = new Random();

    // I chose to use an ArrayList for convenient removing of cards
    List<Cards> pile = new ArrayList<Cards>();
    HashMap<Cards, Integer> hash_map = new HashMap<Cards, Integer>(); // Organized and Accessible hash map of card rules

    public CardPile(){
        // Setting the Original Logger to be with Level type Error
        // This will not show debug parts, only the major errors, in which I would want to know if there's major erorrs.
        Logger loggerConfig = LogManager.getRootLogger();
        Configurator.setAllLevels(loggerConfig.getName(), Level.getLevel("ALL"));

        // CARD VARIABLES ---------
        // Set of Rules of Cards in the Deck put nicey in a hashmap to change with ease
        hash_map.put(Cards.SEABATTLE,6);
        hash_map.put(Cards.NOP,29);
        // ------------------------

        // Shuffle to get a new deck of cards
        shuffle();
    }

    // To check if the pile is empty
    public boolean isEmpty(){
        return pile.isEmpty();
    }
    // Draw function to get a card. if there is nothing left in the deck, it will shuffle the cards
    public Cards draw(){
        if (isEmpty()){
            shuffle();
        }
        int randomIndexToTake = rand.nextInt(pile.size());
        Cards item = pile.get(randomIndexToTake);
        pile.remove(randomIndexToTake);

        // Traces to check if it was removed
        logger.trace("Drawed: "+item+" at index"+randomIndexToTake);
        logger.trace("After Draw:"+Arrays.toString(pile.toArray()));
        return item;
    }

    // This shuffle function will be triggered when we run out of cards or a new game has started.
    public void shuffle(){
        // Clearing the old cards.
        pile.clear();
        // Adding the rules into a set of a deck. Using the deck as an array for easy access.
        // Using a List for conviently adding and
        for (Cards card : hash_map.keySet()){
            for (int i =0; i < hash_map.get(card); i++){
                pile.add(card);
            }
        }
        // Randomizes the order of the List containing the Cards
        for (int i = 0; i < pile.size(); i++) {
            int randomIndexToSwap = rand.nextInt(pile.size());
            Cards temp = pile.get(randomIndexToSwap);
            pile.set(randomIndexToSwap,pile.get(i));
            pile.set(i,temp);
        }
        // Trace to see the shuffled cards
        logger.trace("Shuffled:"+Arrays.toString(pile.toArray()));
    }
}
