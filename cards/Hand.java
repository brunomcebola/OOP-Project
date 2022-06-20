package cards;

import java.util.*;

//import auxFunctions.aux;

public class Hand extends CardGroup {
    public static final int N_CARDS_ON_HAND = 5;

    public Hand() {
        super();
    }

    public void swapCard(int position, Card card) {
        this.cards.set(position, card);
    }

    public void appendCard(Card card) {
        this.cards.add(card);
    }

    /**
     * TODO:
     * Recovers the indexes of the hand, given the input of a sorted hand.
     * 
     * @param idxOfSortedHand list with all the indexes of the sorted hand that
     *                        are going to be hold
     * @param sortedHand      list of the cards in a sorted way
     * @return ArrayList<Integer> of all the indexes of the cards that are supposed
     *         to be held
     */
    private ArrayList<Integer> auxToIdxOutPut(ArrayList<Integer> idxOfSortedHand, ArrayList<Card> sortedHand) {

        ArrayList<Integer> idxOutPut = new ArrayList<Integer>();
        Card auxCard;

        for (int i : idxOfSortedHand) {
            auxCard = sortedHand.get(i);
            idxOutPut.add(cards.indexOf(auxCard));
        }

        return idxOutPut;
    }

    /**
     * Verifies if there is a Flush in the hand
     * 
     * @param type N if the its the normal flush, or H if it is needed checking on
     *             the number of High Cards
     * @param cap  number of cards of this groups, 5,4 or 3, or the number of High
     *             cards needed checking
     * @return ArrayList<Integer> of all the indexes of the cards that are supposed
     *         to be hold, null if there is not a flush in the hand
     */
    public ArrayList<Integer> checkFlush(char type, int cap) {
        // Principal type and thirdType
        // N -> Normal
        // H -> High cards
        //
        // Cap is basically how much it has to have
        int[] count = new int[] { 0, 0, 0, 0 };
        int counter = 0;

        Card newCard;
        int auxValue = 0;

        ArrayList<Integer> idxOutput = new ArrayList<Integer>();

        // FLUSH / 4 TO FLUSH / 3 TO FLUSH
        if (type == 'N') {
            for (Card card : cards) {
                count[card.getRank() - 1] += 1;
            }

            for (int i = 0; i < 4; i++) {
                if (count[i] == cap) {
                    for (Card card : cards) {
                        if (card.getRank() == i + 1)
                            idxOutput.add(cards.indexOf(card));
                    }
                    return idxOutput;
                }
            }

        }

        // TODO: voltar ca

        // 3 TO FLUSH WITH 2/1/0 HIGH CARDS
        if (type == 'H') {
            for (Card card : cards) {
                count[card.getRank() - 1] += 1;
            }

            // search for our three cards with the same naipe.
            for (int i = 0; i < 4; i++) {
                counter = 0;

                if (count[i] == 3) { // already known that it's 3 in the case
                    for (int j = 0; j < N_CARDS_ON_HAND; j++) {
                        newCard = cards.get(i);
                        auxValue = newCard.getValue();
                        if (auxValue == 1 || auxValue == 10 || auxValue == 11 || auxValue == 12 || auxValue == 13) {
                            counter++;
                        }
                    }
                }

                if (counter == cap) {
                    for (Card card : cards) {
                        if (card.getRank() == i + 1)
                            idxOutput.add(cards.indexOf(card));
                    }
                    return idxOutput;
                }
            }
        }

        return null;
    }
    // TODO: flush - cap = 5, type = N

    /**
     * Verifies if there is a Straight Flush in the hand
     *
     * @param cap number of cards of this groups, 5,4 or 3
     * @return ArrayList<Integer> of all the indexes of the cards that are supposed
     *         to be hold, null if there is not a Straight FLush in the hand
     */
    public ArrayList<Integer> checkStraightFlush(int cap) {
        ArrayList<Integer> idxOutput1 = new ArrayList<Integer>();
        ArrayList<Integer> idxOutput2 = new ArrayList<Integer>();

        int count = 0;

        idxOutput1 = checkFlush('N', cap);

        if (idxOutput1 == null) {
            return null;
        }

        idxOutput2 = checkStraight(cap);

        if (idxOutput2 != null) {
            count = 0;
            for (int i : idxOutput1) {
                for (int j : idxOutput2) {
                    if (i == j)
                        count++;
                }

            }
            if (count == cap)
                return idxOutput1;

        }

        idxOutput2 = checkInsideStraight(cap);
        if (idxOutput2 != null) {
            count = 0;
            for (int i : idxOutput1) {
                for (int j : idxOutput2) {
                    if (i == j)
                        count++;
                }

            }
            if (count == cap)
                return idxOutput1;

        }

        idxOutput2 = checkOusideStraight(cap);
        if (idxOutput2 != null) {

            count = 0;
            for (int i : idxOutput1) {
                for (int j : idxOutput2) {
                    if (i == j)
                        count++;
                }

            }
            if (count == cap)
                return idxOutput1;

        }

        return null;
    }
    // TODO: straight flush - cap = 5

    /**
     * Verifies if there is a Outside Straight Flush in the hand
     * 
     * @param cap number of cards in the Outside Straight group wanted
     * @return ArrayList<Integer> of all the indexes of the cards that are supposed
     *         to be hold, null if there is not a Outside Straight in the hand
     */
    public ArrayList<Integer> checkOusideStraight(int cap) {
        // TODO:

        ArrayList<Integer> idxOutput = new ArrayList<Integer>();

        idxOutput = checkStraight(cap);
        if (idxOutput == null)
            return null;

        for (int i : idxOutput) {
            if (cards.get(i).getValue() == 1)
                return null;
        }

        return idxOutput;
    }

    /**
     * Verifies if there is a Inside Straight Flush in the hand
     * 
     * @param cap number of cards in the Inside Straight group wanted
     * @return ArrayList<Integer> of all the indexes of the cards that are supposed
     *         to be hold, null if there is not a Inside Straight in the hand
     */
    public ArrayList<Integer> checkInsideStraight(int cap) {
        // TODO:

        ArrayList<Integer> aux = new ArrayList<Integer>();

        int idx1;
        int idx2;

        Card newCard, auxCard;
        int count = 1;
        int seq = 0, auxSeq = 0;
        ArrayList<Card> sortedHand = getSortedCards();

        // check for edges
        aux = checkStraight(cap);
        if (aux != null) {
            for (int i : aux) {
                if (cards.get(i).getValue() == 1)
                    return aux;
            }
        }

        // reset auxiliar variable
        aux = new ArrayList<Integer>();


        for (Card card1 : sortedHand) {
            idx1 = sortedHand.indexOf(card1);
            if (idx1 > sortedHand.size() - cap)
                break;

            seq = card1.getValue();
            aux.add(idx1);

            // if it is an ace check if it's "counted as high card"
            if (seq == 1) {

                seq = 9; // starts on 9 because the next high card
                         // is a 10, and since cards are sorted
                         // it as to be like this.

                for (Card card2 : sortedHand) {
                    idx2 = sortedHand.indexOf(card2);
                    if (idx2 <= idx1)
                        continue;

                    seq++;
                    if (seq != card2.getValue()) {
                        aux.add(idx2);
                    }
                    
                }

                if (aux.size() == cap) {
                    return auxToIdxOutPut(aux, sortedHand);
                }
                aux.clear();

                seq = card1.getValue();
                aux.add(idx1);

            }

            for (Card card2 : sortedHand) {
                idx2 = sortedHand.indexOf(card2);
                if (idx2 <= idx1)
                    continue;

                seq++;
                if (seq != card2.getValue()) {
                    aux.add(idx2);
                }
                
            }

            if (aux.size() == cap) {
                return auxToIdxOutPut(aux, sortedHand);
            }

            aux.clear();
        }

        return null;


        /* 
        // check for seq, but this time
        for (int i = 0; i < N_CARDS_ON_HAND - cap + 1; i++) {
            count = 1;
            newCard = sortedHand.get(i);

            seq = newCard.getValue();
            aux.add(i);
            // if it is an ace check if it's "counted as high card"
            if (seq == 1) {
                auxSeq = 9; // starts on 9 because the next high card
                // is a 10, and since cards are sorted
                // it as to be like this.

                for (int j = auxSeq + 1; j < auxSeq + 5; j++) {
                    for (int k = i + 1; k < N_CARDS_ON_HAND; k++) {
                        auxCard = sortedHand.get(k);

                        if (auxCard.getValue() == j) {
                            count++;
                            aux.add(k);
                        }
                    }
                }

                if (count == cap) {
                    return auxToIdxOutPut(aux, sortedHand);
                }
                aux.clear();
                seq = newCard.getValue();
                aux.add(i);
                count = 1;
            }

            for (int j = seq + 1; j < seq + 5; j++) {
                for (int k = 0; k < N_CARDS_ON_HAND; k++) {
                    if (k == i)
                        continue;
                    auxCard = sortedHand.get(k);
                    if (j == 13) {
                        if (auxCard.getValue() == 1) {
                            count++;
                            aux.add(k);
                        }
                    }
                    if (auxCard.getValue() == j) {
                        count++;
                        aux.add(k);
                    }
                }
            }

            if (count == 4) {
                return auxToIdxOutPut(aux, sortedHand);
            }
            aux.clear();

        }
        return null;
        */
    }

    /**
     * Verifies if there is a Four to Inside With High Cards in the hand
     * 
     * @param highCards number of High cards wanted
     * @return ArrayList<Integer> of all the indexes of the cards that are supposed
     *         to be hold, null if there is not a Four to Inside With High card in
     *         the hand
     */
    public ArrayList<Integer> checkFourToInsideWithHigh(int highCards) {
        ArrayList<Integer> idxOutput = new ArrayList<Integer>();
        int count = 0, newValue = 0;
        Card newCard;

        idxOutput = checkInsideStraight(4);

        if(idxOutput != null){
            for (int idx : idxOutput) {
                newCard = cards.get(idx);
                newValue = newCard.getValue();
                if (newValue == 1 || newValue == 11 || newValue == 12 || newValue == 13)
                    count++;
    
            }
            if (count == highCards)
                return idxOutput;
        }

        return null;
    }

    /**
     * Verifies if there is a Royal Flush in the hand
     * 
     * @param cap number of cards in the Royal FLush group wanted
     * @return ArrayList<Integer> of all the indexes of the cards that are supposed
     *         to be hold, null if there is not a Royal FLush in the hand
     */
    public ArrayList<Integer> checkRoyalFlush(int cap) {
        ArrayList<Integer> idxOutput = new ArrayList<Integer>();
        int[] count = new int[] { 0, 0, 0, 0 };
        Card newCard;
        int auxValue;

        for (int i = 0; i < N_CARDS_ON_HAND; i++) {
            newCard = cards.get(i);
            auxValue = newCard.getValue();
            if (auxValue == 1 || auxValue == 10 || auxValue == 11 || auxValue == 12 || auxValue == 13)
                count[newCard.getRank() - 1] += 1;
        }

        for (int i = 0; i < 4; i++) {
            if (count[i] == cap) {
                for (int j = 0; j < N_CARDS_ON_HAND; j++) {
                    newCard = cards.get(j);
                    auxValue = newCard.getValue();
                    if ((auxValue == 1 || auxValue == 10 || auxValue == 11 || auxValue == 12 || auxValue == 13)
                            && newCard.getRank() == (i + 1)) {
                        idxOutput.add(j);
                    }
                }
                return idxOutput;
            }
        }
        return null;
    }
    // TODO: royal flush - cap = 5

    /**
     * Verifies if there is a Straight in the hand
     * 
     * @param cap number of cards in the Straight group wanted
     * @return ArrayList<Integer> of all the indexes of the cards that are supposed
     *         to be hold, null if there is not a Straight in the hand
     */
    // !
    public ArrayList<Integer> checkStraight(int cap) {
        ArrayList<Integer> aux = new ArrayList<Integer>();

        ArrayList<Card> sortedHand = this.getSortedCards();

        int idx1;
        int idx2;

        int seq = 0;

        // if need to have straight, it needs 5 seq, therefore
        // it just needs to check once, if it is 4 to straight
        // it only needs to check twice, not all hands
        for (Card card1 : sortedHand) {
            idx1 = sortedHand.indexOf(card1);
            if (idx1 > sortedHand.size() - cap)
                break;

            seq = card1.getValue();
            aux.add(idx1);

            // if it is an ace check if it's "counted as high card"
            if (seq == 1) {

                seq = 9; // starts on 9 because the next high card
                         // is a 10, and since cards are sorted
                         // it as to be like this.

                for (Card card2 : sortedHand) {
                    idx2 = sortedHand.indexOf(card2);
                    if (idx2 <= idx1)
                        continue;

                    seq++;
                    if (seq != card2.getValue()) {
                        break;
                    }
                    aux.add(idx2);
                }

                if (aux.size() == cap) {
                    return auxToIdxOutPut(aux, sortedHand);
                }
                aux.clear();

                seq = card1.getValue();
                aux.add(idx1);

            }

            for (Card card2 : sortedHand) {
                idx2 = sortedHand.indexOf(card2);
                if (idx2 <= idx1)
                    continue;

                seq++;
                if (seq != card2.getValue()) {
                    break;
                }
                aux.add(idx2);
            }

            if (aux.size() == cap) {
                return auxToIdxOutPut(aux, sortedHand);
            }

            aux.clear();
        }

        return null;

    }
    // TODO: straight - cap = 5

    /**
     * Verifies if there is a Three to Straight Flush in the hand
     * 
     * @param type types of the ThreeToStraight, type 1, 2 and 3
     * @return ArrayList<Integer> of all the indexes of the cards that are supposed
     *         to be hold, null if there is not a Three to Straight FLush in the
     *         hand
     */
    public ArrayList<Integer> checkThreeToStraightFlush(int type) {
        ArrayList<Integer> aux = new ArrayList<Integer>();

        ArrayList<Card> sortedHand = new ArrayList<Card>();
        ArrayList<Card> auxHand = new ArrayList<Card>();
        sortedHand = getSortedCards();

        int gap = 0;
        int auxGap = 0;
        int numberOfHighCards = 0;

        int minOne = 50, minTwo = 50, minThree = 50;
        int idxMinOne = 0, idxMinTwo = 0, idxMinThree = 0;

        // mudar um bocado o straight flush, para aceitar o cap...
        // mudar o inside o outside para cap
        aux = checkStraightFlush(3);

        if (aux == null)
            return null;

        auxHand.add(cards.get(aux.get(0)));
        auxHand.add(cards.get(aux.get(1)));
        auxHand.add(cards.get(aux.get(2)));

        // check for 1st, 2nd and 3rd minimiums
        for (Card card : auxHand) {
            if (card.getValue() <= minOne) {
                minOne = card.getValue();
                idxMinOne = auxHand.indexOf(card);
            } else if (card.getValue() <= minTwo) {
                minTwo = card.getValue();
                idxMinTwo = auxHand.indexOf(card);
            } else if (card.getValue() <= minThree) {
                minThree = card.getValue();
                idxMinThree = auxHand.indexOf(card);
            }
        }

        // Gap basically the differences between cards
        auxGap = (idxMinThree - idxMinTwo - 1);
        gap =  + (idxMinTwo - idxMinOne - 1);

        gap = gap + auxGap;

        // high cards for(count high cards)
        for (Card card : auxHand) {
            if (card.getValue() == 1 || card.getValue() == 10 || card.getValue() == 11 || card.getValue() == 12) {
                numberOfHighCards++;
            }
        }
        // if high >= gap type 1 true
        if (type == 1 && numberOfHighCards >= gap) {

            if ((auxHand.get(idxMinOne).getValue() == 1 && auxHand.get(idxMinTwo).getValue() >= 2
                    && auxHand.get(idxMinTwo).getValue() <= 4) || auxHand.get(idxMinOne).getValue() == 2) {
                return null;
            }

            return auxToIdxOutPut(aux, sortedHand);
        }

        // if high < gap type 2 true
        if (type == 2 && numberOfHighCards < gap)
            return auxToIdxOutPut(aux, sortedHand);
        // if 2 gaps and 0 high cards type 3 true
        if (type == 3 && gap == 2 && numberOfHighCards == 0)
            return auxToIdxOutPut(aux, sortedHand);

        return null;
    }

    /**
     * Verifies if there is a A K Q J Unsuited in the hand
     *
     * @return ArrayList<Integer> of all the indexes of the cards that are supposed
     *         to be hold, null if there is not a A K Q J Unsuited in the hand
     */
    public ArrayList<Integer> checkAKQJUnsuited() {

        Card firstCard = null, secondCard = null, thirdCard = null, forthCard = null, auxCard = null;
        ArrayList<Integer> idxOutput = new ArrayList<Integer>();

        for (int i = 0; i < N_CARDS_ON_HAND; i++) {
            auxCard = cards.get(i);
            if (auxCard.getValue() == 1) {
                firstCard = auxCard;
                idxOutput.add(i);
            }
            if (auxCard.getValue() == 11) {
                secondCard = auxCard;
                idxOutput.add(i);
            }
            if (auxCard.getValue() == 12) {
                thirdCard = auxCard;
                idxOutput.add(i);
            }
            if (auxCard.getValue() == 13) {
                forthCard = auxCard;
                idxOutput.add(i);
            }
        }

        if (firstCard == null || secondCard == null || thirdCard == null || forthCard == null)
            return null;
        if (firstCard.getRank() != secondCard.getRank() || thirdCard.getRank() != forthCard.getRank()
                || firstCard.getRank() != thirdCard.getRank())
            return idxOutput;

        return null;
    }

    /**
     * Verifies if there is a K Q J Unsuited in the hand
     *
     * @return ArrayList<Integer> of all the indexes of the cards that are supposed
     *         to be hold, null if there is not a K Q J unsuited in the hand
     */
    public ArrayList<Integer> checkKQJUnsuited() {

        Card firstCard = null, secondCard = null, thirdCard = null, auxCard = null;
        ArrayList<Integer> idxOutput = new ArrayList<Integer>();

        for (int i = 0; i < N_CARDS_ON_HAND; i++) {
            auxCard = cards.get(i);
            if (auxCard.getValue() == 11) {
                firstCard = auxCard;
                idxOutput.add(i);
            }
            if (auxCard.getValue() == 12) {
                secondCard = auxCard;
                idxOutput.add(i);
            }
            if (auxCard.getValue() == 13) {
                thirdCard = auxCard;
                idxOutput.add(i);
            }
        }

        if (firstCard == null || secondCard == null || thirdCard == null)
            return null;
        if (firstCard.getRank() != secondCard.getRank() || secondCard.getRank() != thirdCard.getRank())
            return idxOutput;

        return null;
    }

    /**
     * Verifies if there is a Full House in the hand
     *
     * @return ArrayList<Integer> of all the indexes of the cards that are supposed
     *         to be hold, null if there is not a Full House in the hand
     */
    public ArrayList<Integer> checkFullHouse() {
        int counter = 1;
        int[] idx = new int[] { -1, -1, -1 };
        ArrayList<Integer> idxOutput = new ArrayList<Integer>();

        boolean flagTrioIdx = false;
        Card myCard, auxCard;

        // search for trio
        for (int i = 0; i < 3; i++) {
            counter = 1;
            myCard = cards.get(i);
            idxOutput.add(i);
            for (int j = 0; j < N_CARDS_ON_HAND; j++) {
                if (i == j)
                    continue;

                auxCard = cards.get(j);

                if (myCard.getValue() == auxCard.getValue()) {
                    idx[counter - 1] = i;
                    counter++;
                    idxOutput.add(j);
                }
            }
            if (counter == 3)
                break;
            idxOutput.clear();
        }

        // search for pair
        if (counter == 3) {
            for (int i = 0; i < N_CARDS_ON_HAND - 1; i++) {

                // is my card part of the trio ?
                flagTrioIdx = false;
                for (int idxs = 0; idxs < 3; idxs++) {
                    if (i == idx[idxs]) {
                        flagTrioIdx = true;
                    }
                }
                // if it is don't need to check
                if (flagTrioIdx)
                    continue;

                // if it is not search for pair
                counter = 1;
                myCard = cards.get(i);
                idxOutput.add(i);
                for (int j = 0; j < N_CARDS_ON_HAND; j++) {
                    if (i == j)
                        continue;

                    auxCard = cards.get(j);

                    if (myCard.getValue() == auxCard.getValue()) {
                        counter++;
                        idxOutput.add(j);
                    }
                }
                if (counter == 2)
                    return idxOutput;
                idxOutput.remove(Integer.valueOf(i));
            }
        }

        return null;
    }
    // TODO: full house

    /**
     * Verifies if there is a Four of a Kind in the hand
     *
     * @return ArrayList<Integer> of all the indexes of the cards that are supposed
     *         to be hold, null if there is not a Four of a Kind in the hand
     */
    public ArrayList<Integer> checkFourOfAKind() {
        int counter = 1;

        ArrayList<Integer> idxOutput = new ArrayList<Integer>();
        Card myCard, auxCard;

        for (int i = 0; i < 2; i++) {
            counter = 1;
            myCard = cards.get(i);
            idxOutput.add(i);
            for (int j = 0; j < N_CARDS_ON_HAND; j++) {
                if (i == j)
                    continue;

                auxCard = cards.get(j);

                if (myCard.getValue() == auxCard.getValue()) {
                    counter++;
                    idxOutput.add(j);
                }

                if (counter == 4)
                    return idxOutput;
            }
            idxOutput.remove(Integer.valueOf(i));
        }

        return null;
    }
    // TODO: four of a kind

    /**
     * Verifies if there is a Three of a Kind in the hand
     *
     * @return ArrayList<Integer> of all the indexes of the cards that are supposed
     *         to be hold, null if there is not a Three of a Kind in the hand
     */
    public ArrayList<Integer> checkThreeOfAKind() {
        int counter = 1;
        Card myCard, auxCard;
        ArrayList<Integer> idxOutput = new ArrayList<Integer>();

        for (int i = 0; i < 3; i++) {
            counter = 1;
            myCard = cards.get(i);
            idxOutput.add(i);
            for (int j = 0; j < N_CARDS_ON_HAND; j++) {
                if (i == j)
                    continue;

                auxCard = cards.get(j);

                if (myCard.getValue() == auxCard.getValue()) {
                    counter++;
                    idxOutput.add(j);
                }
            }
            if (counter == 3)
                return idxOutput;
            idxOutput.clear();
        }

        return null;
    }
    // TODO: three of a Kind

    /**
     * Verifies if there is a Pair in the hand
     *
     * @return ArrayList<Integer> of all the indexes of the cards that are supposed
     *         to be hold, null if there is not a Pair in the hand
     */
    public ArrayList<Integer> checkPair() {
        int counter = 1;
        Card myCard, auxCard;
        ArrayList<Integer> idxOutput = new ArrayList<Integer>();

        for (int i = 0; i < N_CARDS_ON_HAND - 1; i++) {
            counter = 1;
            myCard = cards.get(i);
            idxOutput.add(i);
            for (int j = 0; j < N_CARDS_ON_HAND; j++) {
                if (i == j)
                    continue;

                auxCard = cards.get(j);

                if (myCard.getValue() == auxCard.getValue()) {
                    counter++;
                    idxOutput.add(j);
                }
            }
            if (counter == 2)
                return idxOutput;
            idxOutput.clear();
        }

        return null;
    }

    /**
     * Verifies if there is a Two Pair in the hand
     *
     * @return ArrayList<Integer> of all the indexes of the cards that are supposed
     *         to be hold, null if there is not a Two Pairs in the hand
     */
    public ArrayList<Integer> checkTwoPair() {
        int idx1;
        int idx2;

        ArrayList<Integer> idxOutput = new ArrayList<Integer>();

        for (Card card1 : cards) {
            idx1 = cards.indexOf(card1);

            if (idxOutput.contains(idx1))
                continue;

            for (Card card2 : cards) {
                idx2 = cards.indexOf(card2);

                if (idx1 == idx2)
                    continue;

                if (card1.getValue() == card2.getValue()) {
                    idxOutput.add(idx1);
                    idxOutput.add(idx2);
                }
            }
        }

        if (idxOutput.size() == 4) {
            return idxOutput;
        } else {
            return null;
        }
    }
    // TODO: two pair

    /**
     * Verifies if there is a High Pair in the hand
     *
     * @return ArrayList<Integer> of all the indexes of the cards that are supposed
     *         to be hold, null if there is not a High Pair in the hand
     */
    public ArrayList<Integer> checkHighPair() {
        int counter = 1;
        Card myCard, auxCard;

        ArrayList<Integer> idxOutput = new ArrayList<Integer>();

        for (int i = 0; i < N_CARDS_ON_HAND - 1; i++) {
            counter = 1;
            myCard = cards.get(i);
            idxOutput.add(i);
            for (int j = 0; j < N_CARDS_ON_HAND; j++) {
                if (i == j)
                    continue;

                auxCard = cards.get(j);

                if (myCard.getValue() == auxCard.getValue()) {
                    counter++;
                    idxOutput.add(j);
                }
            }
            if (counter == 2 && (myCard.getValue() == 1 || myCard.getValue() == 11 | myCard.getValue() == 12
                    || myCard.getValue() == 13))
                return idxOutput;
            idxOutput.clear();
        }

        return null;
    }
    // TODO: jacks or better

    /**
     * Verifies if there is a Three Aces in the hand
     *
     * @return ArrayList<Integer> of all the indexes of the cards that are supposed
     *         to be hold, null if there is not a Three Aces in the hand
     */
    public ArrayList<Integer> checkThreeAces() {
        int counter = 1;
        Card myCard, auxCard;

        ArrayList<Integer> idxOutput = new ArrayList<Integer>();

        idxOutput = checkThreeOfAKind();

        if(idxOutput != null){
            if(cards.get(idxOutput.get(0)).getValue() == 1 )
                return idxOutput;
        }
        else 
            return null;

        for (int i = 0; i < 3; i++) {
            counter = 1;
            myCard = cards.get(i);
            idxOutput.add(i);
            for (int j = 0; j < N_CARDS_ON_HAND; j++) {
                if (i == j)
                    continue;

                auxCard = cards.get(j);

                if (myCard.getValue() == auxCard.getValue()) {
                    counter++;
                    idxOutput.add(j);
                    break;
                }

            }
            if (counter == 3 && cards.get(i).getValue() == 1)
                return idxOutput;
            idxOutput.clear();
        }

        return null;
    }

    /**
     * Verifies if there is a Q and J Suited in the hand
     *
     * @return ArrayList<Integer> of all the indexes of the cards that are supposed
     *         to be hold, null if there is not a Q and J Suited in the hand
     */
    public ArrayList<Integer> checkQJS() {

        Card firstCard = null, secondCard = null, auxCard = null;

        ArrayList<Integer> idxOutput = new ArrayList<Integer>();

        for (int i = 0; i < N_CARDS_ON_HAND; i++) {
            auxCard = cards.get(i);
            if (auxCard.getValue() == 12) {
                firstCard = auxCard;
                idxOutput.add(i);
            }
            if (auxCard.getValue() == 11) {
                secondCard = auxCard;
                idxOutput.add(i);
            }
        }

        if (firstCard == null || secondCard == null)
            return null;
        if (firstCard.getRank() == secondCard.getRank())
            return idxOutput;

        return null;
    }

    /**
     * Verifies if there is a Two Suited High Cards in the hand
     *
     * @return ArrayList<Integer> of all the indexes of the cards that are supposed
     *         to be hold,null if there is not Two Suited High Cards in the hand
     */
    public ArrayList<Integer> checkTwoSHC() {
        Card firstCard = null, secondCard = null, auxCard = null;

        ArrayList<Integer> idxOutput = new ArrayList<Integer>();

        for (int i = 0; i < N_CARDS_ON_HAND; i++) {
            auxCard = cards.get(i);
            if (auxCard.getValue() == 1 || auxCard.getValue() == 11 || auxCard.getValue() == 12
                    || auxCard.getValue() == 13) {
                if (firstCard == null) {
                    idxOutput.add(i);
                    firstCard = auxCard;
                } else {
                    idxOutput.add(i);
                    secondCard = auxCard;
                }
            }

        }

        if (firstCard == null || secondCard == null)
            return null;
        if (firstCard.getRank() == secondCard.getRank())
            return idxOutput;

        return null;
    }

    /**
     * Verifies if there are two specific cards, suited or unsuited, in the hand
     * 
     * @param value1 first value wanted to be checked
     * @param value2 second value wanted to be checked
     * @param type   to know if it is wanted to check suited or unsuited cards
     * @return ArrayList<Integer> of all the indexes of the cards that are supposed
     *         to be hold, null if there is not two specific cards suited or
     *         unsuited in the hand
     */
    public ArrayList<Integer> checkPairSuits(int value1, int value2, char type) {
        // note if there's a pair of some value, this counts the last one
        // but since this is used after the pair function this does not
        // need to be checked
        Card firstCard = null, secondCard = null, auxCard = null;
        ArrayList<Integer> idxOutput = new ArrayList<Integer>();

        if (type == 'S') {
            for (int i = 0; i < N_CARDS_ON_HAND; i++) {
                auxCard = cards.get(i);
                if (auxCard.getValue() == value1) {
                    firstCard = auxCard;
                    idxOutput.add(i);

                }
                if (auxCard.getValue() == value2) {
                    secondCard = auxCard;
                    idxOutput.add(i);
                }
            }
            if (firstCard == null || secondCard == null)
                return null;
            if (firstCard.getRank() == secondCard.getRank())
                return idxOutput;

        }

        else if (type == 'U') {
            for (int i = 0; i < N_CARDS_ON_HAND; i++) {
                auxCard = cards.get(i);
                if (auxCard.getValue() == value1) {
                    firstCard = auxCard;
                }
                if (auxCard.getValue() == value2) {
                    secondCard = auxCard;
                }
            }
            if (firstCard == null || secondCard == null)
                return null;
            if (firstCard.getRank() != secondCard.getRank())
                return idxOutput;
        }

        return null;
    }

    /**
     * Verifies if there is a Royal Card, K , Q or J, in the hand
     *
     * @return ArrayList<Integer> of all the indexes of the cards that are supposed
     *         to be hold, null if there is not a Royal Card in the hand
     */
    public ArrayList<Integer> checkForRoyalCard() {
        Card newCard;
        ArrayList<Integer> idxOutput = new ArrayList<Integer>();

        for (int i = 0; i < N_CARDS_ON_HAND; i++) {
            newCard = cards.get(i);
            if (newCard.getValue() == 11 || newCard.getValue() == 12 || newCard.getValue() == 13) {
                idxOutput.add(i);
                return idxOutput;
            }
        }
        return null;
    }

    /**
     * Verifies if there is a Ace in the hand
     *
     * @return ArrayList<Integer> of all the indexes of the cards that are supposed
     *         to be hold, null if there is not an Ace in the hand
     */
    public ArrayList<Integer> checkAce() {
        Card newCard;
        ArrayList<Integer> idxOutput = new ArrayList<Integer>();

        for (int i = 0; i < N_CARDS_ON_HAND; i++) {
            newCard = cards.get(i);
            if (newCard.getValue() == 1) {
                idxOutput.add(i);
                return idxOutput;
            }
        }
        return null;
    }

    public int classify() {
        /*
         * 1 -> Royal Flush
         * 2 -> Straight Flush
         * 3 -> Four Aces
         * 4 -> Four 2–4
         * 5 -> Four 5–K
         * 6 -> Full House
         * 7 -> Flush
         * 8 -> Straight
         * 9 -> Three of a Kind
         * 10 -> Two Pair
         * 11 -> Jacks or Better
         * 12 -> Other
         */

        ArrayList<Integer> idx;

        if (checkRoyalFlush(5) != null) {
            return 1;
        } else if (checkStraightFlush(5) != null) {
            return 2;
        } else if ((idx = checkFourOfAKind()) != null) {
            if (this.cards.get(idx.get(0)).getValue() == 1) {
                return 3;
            } else if (this.cards.get(idx.get(0)).getValue() >= 2 && this.cards.get(idx.get(0)).getValue() <= 4) {
                return 4;
            } else {
                return 5;
            }
        } else if (checkFullHouse() != null) {
            return 6;
        } else if (checkFlush('N', 5) != null) {
            return 7;
        } else if (checkStraight(5) != null) {
            return 8;
        } else if (checkThreeOfAKind() != null) {
            return 9;
        } else if (checkTwoPair() != null) {
            return 10;
        } else if (checkHighPair() != null) {
            return 11;
        } else {
            return 12;
        }
    }

    // TODO: apagar aqui para baixo

    public void testStuff() {

    }

}
