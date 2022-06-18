package game;

import java.nio.file.FileStore;
import java.util.*;
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

    protected Deck generateDeck() throws Exception {
        Deck deck = new Deck();

        deck.generateCards();

        return deck;
    }

    /**
     * Copies the hand at the time and sorts it, giving an output
     * of the hand sorted.
     *
     * @return ArrayList<Card> which is the hand sorted
     */
    private ArrayList<Card> sortHand() {
        ArrayList<Card> handSorted = this.hand.getAllCards();
        Collections.sort(handSorted, new Comparator<Card>() {
            @Override
            public int compare(Card card1, Card card2) {
                if (card1.getValue() > card2.getValue()) {
                    return 1;
                }
                return -1;
            }
        });

        return handSorted;
    }

    public int getAdvice() {

        // TODO: THE LOGIC, BECAUSE THE IFS CAN FUCK THINGS UP..
        // TODO: STILL NEED THE FUNCTIONS TO SUM WHAT'S MISSING

        ArrayList<Integer> hold = null;

        // check rank here
        /* if (this.hand.checkFlush('S', 5, 'N').size() != 0 || this.hand.checkFourOfAKind().size() != 0
                || this.hand.checkFlush('R', 5, 'N').size() != 0)
            return 1;

        else if (this.hand.checkFlush('R', 4, 'N').size() != 0)
            return 2;

        else if (this.hand.checkThreeAces().size() != 0)
            return 3;

        // TODO:
        else if (this.hand.checkStraight().size() != 0 || this.hand.checkFlush('N', 5, 'N').size() != 0
                || this.hand.checkFHouse().size() != 0)
            return 4;

        else if (this.hand.checkThreeOfAKind().size() != 0)
            return 5;

        // TODO:
        else if (this.hand.checkStraight('S', 4, 'N').size() != 0)
            return 6;

        else if (this.hand.checkTwoPair().size() != 0)
            return 7;

        else if (this.hand.checkHighPair().size() != 0)
            return 8;

        else if (this.hand.checkFlush('N', 4, 'N').size() != 0)
            return 9;

        else if (this.hand.checkFlush('R', 3, 'N').size() != 0)
            return 10;
        // TODO:
        else if (this.hand.checkFourtoOusideStraight().size() != 0)
            return 11;

        else if (this.hand.checkPair().size() != 0)
            return 12;

        else if (this.hand.checkAKQJUnsuited().size() != 0)
            return 13; // AKQJ unsuited

        // TODO:
        else if (this.hand.checkThreeToStraightFlush().size() != 0) {
            if (this.hand.checkTypeOne().size() != 0)
                return 14;
            else if (this.hand.checkTypeTwo().size() != 0)
                return 20;
            return 27;// type 3
        }
        // TODO:
        else if (this.hand.checkFourToInsideWithThreeHigh().size() != 0)
            return 15;

        else if (this.hand.checkQJS().size() != 0)
            return 16; // GJ suited

        else if (this.hand.checkFlush('N', 2, 'H').size() != 0)
            return 17;

        else if (this.hand.checkTwoSHC().size() != 0)
            return 18; // Two suited high cards
        // TODO:
        else if (this.hand.checkFourToInsideStraight().size() != 0) {
            if (this.hand.checkTwoHighCardsOnStraigh().size() != 0)
                return 19;
            if (this.hand.checkOneHighCardsOnStraigh().size() != 0)
                return 21;
            return 32;
        }

        else if (this.hand.checkKQJUnsuited().size() != 0)
            return 22;

        else if (this.hand.checkPairSuits(11, 10, 'S').size() != 0)
            return 23; // check JT suited

        else if (this.hand.checkPairSuits(12, 11, 'U').size() != 0)
            return 24; // check QJ unsuited

        else if (this.hand.checkFlush('N', 1, 'H').size() != 0)
            return 25;

        else if (this.hand.checkPairSuits(12, 10, 'S').size() != 0)
            return 26; // check QT suited

        else if (this.hand.checkPairSuits(13, 11, 'U').size() != 0 || this.hand.checkPairSuits(13, 12, 'U').size() != 0)
            return 28; // check KJ and KQ unsuited

        else if (this.hand.checkAce().size() != 0)
            return 29;

        else if (this.hand.checkPairSuits(13, 10, 'S').size() != 0)
            return 30; // KT suited

        else if (this.hand.checkForRoyalCard().size() != 0)
            return 31;

        else if (this.hand.checkFlush('N', 3, 'N').size() != 0)
            return 33;

        return 34; // discard all */
    }

}