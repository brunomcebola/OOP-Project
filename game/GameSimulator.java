package game;

import java.util.*;
//mport auxFunctions.aux;
import cards.*;

public class GameSimulator extends Game {
    private int bet;
    private int nbdeals;

    public GameSimulator(int credits, int bet, int nbdeals) throws Exception {
        super(credits);

        this.bet = bet;
        this.nbdeals = nbdeals;
    }

    protected ArrayList<Card> getCardsList() throws Exception {
        return Card.generateAllCards();
    }

    public void run() throws Exception {
        ArrayList<Integer> swap = new ArrayList<Integer>();

        this.setVerbose(true);

        this.createDeck();

        for (int i = 0; i < this.nbdeals; i++) {
            System.out.println("\n\nGame " + (i + 1) + "\n");

            this.shuffleDeck();

            System.out.println("-cmd b");

            try {
                this.placeBet(this.bet);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            System.out.println();

            System.out.println("-cmd d");

            try {
                this.dealHand();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            System.out.println();

            System.out.println("-cmd a");

            try {
                swap = this.getAdvice();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            System.out.println();

            System.out.println("-cmd h");

            try {
                this.swapCards(swap);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            System.out.println();

            this.endRound();

            System.out.println();

        }

        System.out.println("-cmd s");

        this.printStatistics();
    }

}