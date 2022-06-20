package game;

import java.util.*;
//mport auxFunctions.aux;
import cards.*;

public class GameSimulator extends Game {
    private int bet;
    private int nbdeals;

    private boolean verbose;

    /**
     * The constructor of the class.
     * 
     * @param credits credits that the user begins with
     * @param bet     bet per round
     * @param nbdeals number of rounds
     */
    public GameSimulator(int credits, int bet, int nbdeals) throws Exception {
        super(credits);

        this.bet = bet;
        this.nbdeals = nbdeals;

        this.verbose = false;

    }

    /**
     * It returns an ArrayList of all the cards in a deck
     * 
     * @return An ArrayList of Card objects.
     */
    protected ArrayList<Card> getCardsList() throws Exception {
        return Card.generateAllCards();
    }

    /**
     * The function runs the game by creating a deck, shuffling it, placing a bet,
     * dealing a hand,
     * getting advice, swapping cards, ending the round, and printing the statistics
     */
    public void run() throws Exception {
        ArrayList<Integer> swap = new ArrayList<Integer>();

        this.setVerbose(this.verbose);

        this.createDeck();

        for (int i = 0; i < this.nbdeals; i++) {
            if (this.verbose)
                System.out.println("\n\nGame " + (i + 1) + "\n");

            this.shuffleDeck();

            if (this.verbose)
                System.out.println("-cmd b");

            try {
                this.placeBet(this.bet);
            } catch (Exception e) {
                if (this.verbose)
                    System.out.println(e.getMessage());
            }

            if (this.verbose)
                System.out.println("\n-cmd d");

            try {
                this.dealHand();
            } catch (Exception e) {
                if (this.verbose)
                    System.out.println(e.getMessage());
            }

            if (this.verbose)
                System.out.println("\n-cmd a");

            try {
                swap = this.getAdvice();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            if (this.verbose)
                System.out.println("\n-cmd h");

            try {
                this.swapCards(swap);
            } catch (Exception e) {
                if (this.verbose)
                    System.out.println(e.getMessage());
            }

            if (this.verbose)
                System.out.println();

            this.endRound();

            if (this.verbose)
                System.out.println();

        }

        if (this.verbose)
            System.out.println("-cmd s");

        this.printStatistics();
    }

}