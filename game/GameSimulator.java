package game;

import java.util.*;
//mport auxFunctions.aux;
import cards.*;

public class GameSimulator extends Game {
    /**
     * It is the constructor of the class Simulator.
     *
     * @param credits initial credits
     */
    public GameSimulator(int credits) throws Exception {
        super(credits);
    }

    protected ArrayList<Card> getCardsList() throws Exception {
        return Card.generateAllCards();
    }

    

}