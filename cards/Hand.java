package cards;

import java.util.*;
import cards.Card;

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
     * Copies the hand at the time and sorts it, giving an output
     * of the hand sorted.
     *
     * @return ArrayList<Card> which is the hand sorted
     */
    private ArrayList<Card> sortHand() {
      ArrayList<Card> handSorted = cards;
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

    private ArrayList<Integer> auxToIdxOutPut(ArrayList<Integer> idxOfSortedHand){

      return null;
    }

    // TODO:NEEDS COMMENTS FOR JAVA DOCS
    // TODO:needs checking for the idxoutput
    // TODO:SEPARATE ROYAL STRAIGHT AND FLUSH
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
            for (int i = 0; i < N_CARDS_ON_HAND; i++) {
                newCard = cards.get(i);
                count[newCard.getRank() - 1] += 1;
            }

            for (int i = 0; i < 4; i++) {
                if (count[i] == cap) {
                    for (int j = 0; j < N_CARDS_ON_HAND; j++) {
                        newCard = cards.get(j);
                        if (newCard.getRank() == (count[i] + 1))
                            idxOutput.add(j);
                    }
                    return idxOutput;
                }
            }
        }

        // 3 TO FLUSH WITH 2/1 OR 0 HIGH CARDS

        if (type == 'H') {
            for (int i = 0; i < N_CARDS_ON_HAND; i++) {
                newCard = cards.get(i);
                count[newCard.getRank() - 1] += 1;
            }

            // search for our three cards with the same naipe.
            for (int i = 0; i < 4; i++) {
                counter = 1;
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
                    for (int j = 0; j < N_CARDS_ON_HAND; j++) {
                        newCard = cards.get(j);
                        if (newCard.getRank() == (count[i] + 1))
                            idxOutput.add(j);

                    }
                    return idxOutput;
                }
            }
        }

        return null;
    }

    public ArrayList<Integer> checkStraightFlush(int cap){
      ArrayList<Integer> idxOutput = new ArrayList<Integer>();
      Card newCard;
      int[] count = new int[]{0,0,0,0};

      for (int i = 0; i < N_CARDS_ON_HAND; i++) {
        newCard = cards.get(i);
        count[newCard.getRank() - 1] += 1;
      }

      for (int i = 0; i < 4; i++) {
        if (count[i] == cap) {

        for (int j = 0; j < cap; j++) {
          idxOutput.add(j);
        }
          return idxOutput;
        }
      }
      return null;
    }

    public ArrayList<Integer> checkRoyalFlush(int cap){
      ArrayList<Integer> idxOutput = new ArrayList<Integer>();
      int[] count = new int[]{0,0,0,0};
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

    public ArrayList<Integer> checkStraight(int cap){
      ArrayList<Integer> aux = new ArrayList<Integer>();

      Card newCard, auxCard;
      int count = 1;
      int seq = 0;
      ArrayList<Card> handSorted = sortHand();

      //if need to have straight, it needs 5 seq, therefore
      //it just needs to check once, if it is 4 to straight
      // it only needs to check twice, not all hands
      for( int i = 0; i < N_CARDS_ON_HAND - cap + 1; i++){
        count = 1;
        newCard = handSorted.get(i);

        seq = newCard.getValue();
        aux.add(i);
        //if it is an ace check if it's "counted as high card"
        if(seq == 1 ){
          seq = 9; // starts on 9 because the next high card 
                   //is a 10, and since cards are sorted
                   //it as to be like this.
          for(int j = i; j < N_CARDS_ON_HAND - i; j++){
            seq++;
            auxCard = handSorted.get(j);
            if(seq == auxCard.getValue()){
              count ++;
              aux.add(j);
            }
          }

          seq = newCard.getValue();
          if(count == cap){
            return auxToIdxOutPut(aux);
          }
          aux.clear();
        }

       

        for(int j = i; j < N_CARDS_ON_HAND - i; j++){
          seq++;
          auxCard = handSorted.get(j);
          if(seq == auxCard.getValue()){
            count ++;
            aux.add(j);
          }
        }

        if(count == cap){
          return auxToIdxOutPut(aux);
        }
        aux.clear();
        
      }


      return null;
    }

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
        if (firstCard.getRank() == secondCard.getRank() && thirdCard.getRank() == forthCard.getRank()
                && firstCard.getRank() == thirdCard.getRank())
            return idxOutput;

        return null;
    }

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
        if (firstCard.getRank() == secondCard.getRank() && secondCard.getRank() == thirdCard.getRank())
            return idxOutput;

        return null;
    }

    public ArrayList<Integer> checkFHouse() {
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

    public ArrayList<Integer> checkTwoPair() {
        int[] idx = new int[] { -1, -1 };
        int counter = 1;
        Card myCard, auxCard;

        ArrayList<Integer> idxOutput = new ArrayList<Integer>();

        // search first pair and keep idx of them
        for (int i = 0; i < N_CARDS_ON_HAND - 1; i++) {
            counter = 1;
            myCard = cards.get(i);
            idxOutput.add(i);
            for (int j = 0; j < N_CARDS_ON_HAND; j++) {
                if (i == j)
                    continue;

                auxCard = cards.get(j);

                if (myCard.getValue() == auxCard.getValue()) {
                    idx[0] = i;
                    idx[1] = j;
                    counter++;
                    idxOutput.add(j);
                }
            }
            if (counter == 2)
                break;
            idxOutput.clear();
        }

        // search second pair
        for (int i = 0; i < N_CARDS_ON_HAND - 1; i++) {
            counter = 1;
            myCard = cards.get(i);
            idxOutput.add(i);
            for (int j = 0; j < N_CARDS_ON_HAND; j++) {
                if (i == j || j == idx[0] || j == idx[1])
                    continue;

                auxCard = cards.get(j);

                if (myCard.getValue() == auxCard.getValue()) {
                    counter++;
                }
            }
            if (counter == 2)
                break;
            idxOutput.remove(Integer.valueOf(i));
        }

        return null;
    }

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

    public ArrayList<Integer> checkThreeAces() {
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
                    break;
                }

            }
            if (counter == 3 && cards.get(i).getValue() == 1)
                return idxOutput;
            idxOutput.clear();
        }

        return null;
    }

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

}
