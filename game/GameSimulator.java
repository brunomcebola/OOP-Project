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

    /**
     * It searches for all the 33 important hands in this type of variant and
     * returns
     * the indexes of the cards that are going to be swapped. This functions uses a
     * lot
     * of functions from another package that were made taking into account of the
     * importance of each hand, for example, when it started checking for an A, it
     * takes
     * into account that Pairs were already checked, therefore if there is an Ace it
     * assumes
     * that the Ace is alone.
     *
     * @return ArrayList<Integer>, indexes of the cards that are going to be swapped
     */
    public ArrayList<Integer> getAdvice() {

        // TODO: THE LOGIC, BECAUSE THE IFS CAN FUCK THINGS UP..
        // TODO: STILL NEED THE FUNCTIONS TO SUM WHAT'S MISSING

        ArrayList<Integer> hold = new ArrayList<Integer>();
        ArrayList<Integer> swap = new ArrayList<Integer>();

        if ((hold = this.hand.checkStraightFlush(5)).size() != 0) {
            return holdToSwap(hold); // 1
        }
        if ((hold = this.hand.checkFourOfAKind()).size() != 0) {
            return holdToSwap(hold); // 1
        }
        if ((hold = this.hand.checkRoyalFlush(5)).size() != 0) {
            return holdToSwap(hold); // 1
        }

        else if ((hold = this.hand.checkRoyalFlush(4)).size() != 0) {
            return holdToSwap(hold); // 2
        }

        else if ((hold = this.hand.checkThreeAces()).size() != 0) {
            return holdToSwap(hold); // 3
        }

        else if ((hold = this.hand.checkStraight(5)).size() != 0) {
            return holdToSwap(hold); // 4
        } else if ((hold = this.hand.checkFlush('N', 5)).size() != 0) {
            return holdToSwap(hold); // 4
        }

        else if ((hold = this.hand.checkFHouse()).size() != 0) {
            return holdToSwap(hold); // 4
        }

        else if ((hold = this.hand.checkThreeOfAKind()).size() != 0) {
            return holdToSwap(hold); // 5
        }
        // TODO:
        else if ((hold = this.hand.checkStraightFlush(4)).size() != 0) {
            return holdToSwap(hold); // 6
        }

        else if ((hold = this.hand.checkTwoPair()).size() != 0) {
            return holdToSwap(hold); // 7
        }

        else if ((hold = this.hand.checkHighPair()).size() != 0) {
            return holdToSwap(hold); // 8
        }

        else if ((hold = this.hand.checkFlush('N', 4)).size() != 0) {
            return holdToSwap(hold); // 9
        }

        else if ((hold = this.hand.checkRoyalFlush(3)).size() != 0) {
            return holdToSwap(hold); // 10
        }
        // TODO:
        else if ((hold = this.hand.checkOusideStraight(4)).size() != 0) {
            return holdToSwap(hold); // 11
        }

        else if ((hold = this.hand.checkPair()).size() != 0) {
            return holdToSwap(hold); // 12
        }

        else if ((hold = this.hand.checkAKQJUnsuited()).size() != 0) {
            return holdToSwap(hold); // 13 AKQJ unsuited
        }

        else if ((hold = this.hand.checkThreeToStraightFlush(1)).size() != 0) {
            return holdToSwap(hold); // 14
        }
        // TODO:
        else if ((hold = this.hand.checkFourToInsideWithHigh(3)).size() != 0) {
            return holdToSwap(hold); // 15
        }

        else if ((hold = this.hand.checkQJS()).size() != 0) {
            return holdToSwap(hold); // 16 QJ suited
        }

        else if ((hold = this.hand.checkFlush('H', 2)).size() != 0) {
            return holdToSwap(hold); // 17
        }

        else if ((hold = this.hand.checkTwoSHC()).size() != 0) {
            return holdToSwap(hold); // 18 Two suited high cards
        }

        else if ((hold = this.hand.checkFourToInsideWithHigh(2)).size() != 0) {
            return holdToSwap(hold); // 19
        }

        else if ((hold = this.hand.checkThreeToStraightFlush(2)).size() != 0)
            return holdToSwap(hold); // 20

        else if ((hold = this.hand.checkFourToInsideWithHigh(1)).size() != 0) {
            return holdToSwap(hold); // 21
        }

        else if ((hold = this.hand.checkKQJUnsuited()).size() != 0) {
            return holdToSwap(hold); // 22
        }

        else if ((hold = this.hand.checkPairSuits(11, 10, 'S')).size() != 0) {
            return holdToSwap(hold); // 23 check JT suited
        }

        else if ((hold = this.hand.checkPairSuits(12, 11, 'U')).size() != 0) {
            return holdToSwap(hold); // 24 check QJ unsuited
        }

        else if ((hold = this.hand.checkFlush('H', 1)).size() != 0) {
            return holdToSwap(hold); // 25
        }

        else if ((hold = this.hand.checkPairSuits(12, 10, 'S')).size() != 0) {
            return holdToSwap(hold); // 26 check QT suited
        }
        // TODO:
        else if ((hold = this.hand.checkThreeToStraightFlush(3)).size() != 0) {
            return holdToSwap(hold); // 27 type 3
        }

        else if ((hold = this.hand.checkPairSuits(13, 11, 'U')).size() != 0) {
            return holdToSwap(hold); // 28 check KJ unsuited
        } else if ((hold = this.hand.checkPairSuits(13, 12, 'U')).size() != 0) {
            return holdToSwap(hold); // 28 check KQ unsuited
        }

        else if ((hold = this.hand.checkAce()).size() != 0) {
            return holdToSwap(hold); // 29
        }

        else if ((hold = this.hand.checkPairSuits(13, 10, 'S')).size() != 0) {
            return holdToSwap(hold); // 30
        }

        else if ((hold = this.hand.checkForRoyalCard()).size() != 0) {
            return holdToSwap(hold); // 31
        }
        // TODO:
        else if ((hold = this.hand.checkInsideStraight(4)).size() != 0) {
            return holdToSwap(hold); // 32
        }

        else if ((hold = this.hand.checkFlush('N', 3)).size() != 0) {
            return holdToSwap(hold); // 33
        }

        swap.add(0);
        swap.add(1);
        swap.add(2);
        swap.add(3);
        swap.add(4);
        return swap; // discard all
    }

}