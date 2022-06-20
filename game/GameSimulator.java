package game;

import java.util.*;
//mport auxFunctions.aux;
import cards.*;

public class GameSimulator extends Game {
    private int bet;
    private int nbdeals;

    private boolean verbose;

    public GameSimulator(int credits, int bet, int nbdeals) throws Exception {
        super(credits);

        this.bet = bet;
        this.nbdeals = nbdeals;

        this.verbose = false;
    }

    protected ArrayList<Card> getCardsList() throws Exception {
        return Card.generateAllCards();
    }

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