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
     * Reaganges the Hand (ArrayList<Card>) in a way that its sorted
     * by is value. Notice that the Ace has value of 1 
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
    
    /**TODO:
     * Recoveres the indexes of the hand, given the input of a sorted hand.
     * 
     * @param ArrayList<Integer> list with all the indexes of the sorted hand that are going to be hold
     * @param ArrayList<Card> list of the cards in a sorted way
     * @return ArrayList<Integer> of all the indexes of the cards that are supposed to be hold
     */
    private ArrayList<Integer> auxToIdxOutPut(ArrayList<Integer> idxOfSortedHand, ArrayList<Card> sortedHand){

        ArrayList<Integer> idxOutPut = new ArrayList<Integer>();
        Card auxCard;

        for(int i : idxOfSortedHand ){
            auxCard = sortedHand.get( i );
            idxOutPut.add(cards.indexOf(auxCard));
        }

        return idxOutPut;
    }

    /**
     * Verifies if there is a Flush in the hand
     * 
     * @param char N if the its the normal flush, or H if it is needed checking on the number of High Cards
     * @param int number of cards of this groups, 5,4 or 3, or the number of High cards needed checking
     * @return ArrayList<Integer> of all the indexes of the cards that are supposed to be hold, null if there is not a flush in the hand
     */
    public ArrayList<Integer> checkFlush(char type, int cap) {
        // TODO:needs checking for the idxoutput

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

    /**
     * Verifies if there is a Straight Flush in the hand
     *
     * @param int number of cards of this groups, 5,4 or 3
     * @return ArrayList<Integer> of all the indexes of the cards that are supposed to be hold,  null if there is not a Straight FLush in the hand
     */
    public ArrayList<Integer> checkStraightFlush(int cap){
        //TODO: needs thinling

        ArrayList<Integer> idxOutput1 = new ArrayList<Integer>();
        ArrayList<Integer> idxOutput2= new ArrayList<Integer>();

        int lenght1 = 0, lenght2 = 0,count = 0;

        //TODO: needs to check outside and inside straights
        idxOutput1 = checkFlush('N', cap);
        idxOutput2 = checkStraight(cap);
        
        lenght1 = idxOutput1.size();
        lenght2 = idxOutput2.size();

        if(lenght1!= 0 && lenght2 != 0 ){
            count = 0;
            for(int i : idxOutput1){
                for(int j : idxOutput2){
                    if(i == j) count++;
                }
                
            }
            if(count == cap) return idxOutput1;
        }

        idxOutput2 = checkInsideStraight(cap);
        lenght2 = idxOutput2.size();

        if(lenght1!= 0 && lenght2 != 0 ){
            count = 0;
            for(int i : idxOutput1){
                for(int j : idxOutput2){
                    if(i == j) count++;
                }
                
            }
            if(count == cap) return idxOutput1;
        }

        idxOutput2 = checkOusideStraight(cap);
        lenght2 = idxOutput2.size();

        if(lenght1!= 0 && lenght2 != 0 ){
            count = 0;
            for(int i : idxOutput1){
                for(int j : idxOutput2){
                    if(i == j) count++;
                }
                
            }
            if(count == cap) return idxOutput1;
        }

        return null;
    }

    /**
     * Verifies if there is a Outside Straight Flush in the hand
     * @param int number of cards in the Outside Straight group wanted
     * @return ArrayList<Integer> of all the indexes of the cards that are supposed to be hold, null if there is not a Outside Straight in the hand
     */
    public ArrayList<Integer> checkOusideStraight(int cap){
        //TODO:

        ArrayList<Integer> idxOutput = new ArrayList<Integer>();

        idxOutput = checkStraight(cap);
        if(idxOutput == null) return null;

        for(int i: idxOutput){
            if(cards.get(i).getValue() == 1 ) return null;
        }

        return idxOutput;
    }
    
    /**
     * Verifies if there is a Inside Straight Flush in the hand
     * @param int number of cards in the Inside Straight group wanted
     * @return ArrayList<Integer> of all the indexes of the cards that are supposed to be hold, null if there is not a Inside Straight in the hand
     */
    public ArrayList<Integer> checkInsideStraight(int cap){
        //TODO:

        ArrayList<Integer> aux = new ArrayList<Integer>();

        Card newCard, auxCard;
        int count = 1;
        int seq = 0, auxSeq = 0;
        ArrayList<Card> handSorted = sortHand();

        //check for edges
        aux = checkStraight(cap);
        for(int i: aux){
            if(cards.get(i).getValue() == 1 ) return aux;
        }
        //reset auxiliar variable
        aux = new ArrayList<Integer>();

        //check for seq, but this time
        for( int i = 0; i < N_CARDS_ON_HAND - cap + 1; i++){
            count = 1;
            newCard = handSorted.get(i);

            seq = newCard.getValue();
            aux.add(i);
            //if it is an ace check if it's "counted as high card"
            if(seq == 1 ){
                auxSeq = 9; // starts on 9 because the next high card 
                        //is a 10, and since cards are sorted
                        //it as to be like this.

                for(int j = auxSeq + 1; j < auxSeq+5; j++){
                    for(int k = i+1; k < N_CARDS_ON_HAND; k++){
                        auxCard = handSorted.get(k);
                        
                        if(auxCard.getValue() == j){ 
                            count++;
                            aux.add(k);
                        }
                    }
                }

                if(count == cap){
                    return auxToIdxOutPut(aux, handSorted);
                }
                aux.clear();
                seq = newCard.getValue();
                aux.add(i);
                count = 1;
            }
                
            for(int j = seq + 1; j < seq+5; j++){
                for(int k = 0; k < N_CARDS_ON_HAND; k++){
                    if(k == i) continue;
                    auxCard = handSorted.get(k);
                    if(j == 13){
                        if(auxCard.getValue() == 1){
                            count++;
                            aux.add(k);
                        }
                    }
                    if(auxCard.getValue() == j){ 
                        count++;
                        aux.add(k);
                    }
                }
            }

            if(count == 4){
                return auxToIdxOutPut(aux, handSorted);
            }
            aux.clear();
            
        }
        return null;
    }

    /**
     * Verifies if there is a Four to Inside With High Cards in the hand
     * @param int number of High cards wanted
     * @return ArrayList<Integer> of all the indexes of the cards that are supposed to be hold, null if there is not a Four to Inside With High card in the hand
     */
    public ArrayList<Integer> checkFourToInsideWithHigh(int highCards){
        ArrayList<Integer> idxOutput = new ArrayList<Integer>();
        int count = 0, newValue = 0;
        Card newCard;

        idxOutput = checkInsideStraight(4);

        for(int idx : idxOutput){
            newCard = cards.get(idx);
            newValue = newCard.getValue();
            if(newValue == 1 || newValue == 11 || newValue == 12 || newValue == 13) count++;
            
        }
        if(count == highCards) return idxOutput;

        return null;
    }

    /**
     * Verifies if there is a Royal Flush in the hand
     * @param int number of cards in the Royal FLush group wanted
     * @return ArrayList<Integer> of all the indexes of the cards that are supposed to be hold, null if there is not a Royal FLush in the hand
     */
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
    
    /**
     * Verifies if there is a Straight in the hand
     * @param int number of cards in the Straight group wanted
     * @return ArrayList<Integer> of all the indexes of the cards that are supposed to be hold, null if there is not a Straight in the hand
     */
    public ArrayList<Integer> checkStraight(int cap){
        //TODO: verify everything
        ArrayList<Integer> aux = new ArrayList<Integer>();

        Card newCard, auxCard;
        int count = 1;
        int seq = 0;
        ArrayList<Card> handSorted = sortHand();

        //if need to have straight, it needs 5 seq, therefore
        //it just needs to check once, if it is 4 to straight
        // it only needs to check twice, not all hands
        for( int i = 0; i < N_CARDS_ON_HAND - cap + 1; i++){
            count = 0;
            newCard = handSorted.get(i);

            seq = newCard.getValue();
            aux.add(i);
            //if it is an ace check if it's "counted as high card"
            if(seq == 1 ){
            seq = 9; // starts on 9 because the next high card 
                    //is a 10, and since cards are sorted
                    //it as to be like this.
            for(int j = i + 1; j < N_CARDS_ON_HAND; j++){
                seq++;
                auxCard = handSorted.get(j);
                if(seq == auxCard.getValue()){
                    count ++;
                }
                aux.add(j);
            }

            seq = newCard.getValue();
            if(count == cap){
                return auxToIdxOutPut(aux, handSorted);
            }
            aux.clear();
            }

        

            for(int j = i + 1; j < N_CARDS_ON_HAND; j++){
                seq++;
                auxCard = handSorted.get(j);
                if(seq == auxCard.getValue()){
                    count ++;
                }
                aux.add(j);
            }

            if(count == cap){
                return auxToIdxOutPut(aux, handSorted);
            }
            aux.clear();
            
        }
        return null;      
    }

    /**
     * Verifies if there is a Three to Straight Flush in the hand
     * @param  int types of the ThreeToStraight, type 1, 2 and 3
     * @return ArrayList<Integer> of all the indexes of the cards that are supposed to be hold, null if there is not a Three to Straight FLush in the hand
     */
    public ArrayList<Integer> checkThreeToStraightFlush(int type){
        //TODO: FALTA FAZER ISSTOOOO
        ArrayList<Integer> aux = new ArrayList<Integer>();

        ArrayList<Card> handSorted = new ArrayList<Card>();
        ArrayList<Card> auxHand = new ArrayList<Card>();
        handSorted = sortHand();

        int gap = 0;
        int numberOfHighCards = 0;
        int idxMinOne = 50, idxMinTwo = 50;

        //mudar um bocado o straight flush, para aceitar o cap...
        //mudar o inside  o outside para cap
        aux = checkStraightFlush(3);
        if( aux == null) return null;
        auxHand.add(cards.get(aux.get(0)));
        auxHand.add(cards.get(aux.get(1)));
        auxHand.add(cards.get(aux.get(2)));
        //check for first and second minimiums
        for(int i = 0; i < auxHand.size(); i++){
            for(Card card : auxHand){
                if(card.getValue() <= idxMinOne){
                    idxMinOne = card.getValue();
                    continue;
                }
                if(card.getValue() <= idxMinTwo){
                    idxMinTwo = card.getValue();
                }
            }
        }

        // Gap is basically the difference between the first two minimums
        gap =idxMinTwo - idxMinOne ; 
        //high cards for(count high cards)
        for(Card card : auxHand){
            if(card.getValue() == 1 ||card.getValue() == 10 || card.getValue() == 11 || card.getValue() == 12){
                numberOfHighCards++;
            }
        }
        // if high >= gap type 1 true
        if(type == 1 && numberOfHighCards >= gap) 
            return auxToIdxOutPut(aux, handSorted);
        // if high < gap type 2 true
        if(type == 2 && numberOfHighCards < gap) 
            return auxToIdxOutPut(aux, handSorted);
        // if 2 gaps and 0 high cards type 3 true
        if(type == 1 && gap == 2 && numberOfHighCards == 0) 
            return auxToIdxOutPut(aux, handSorted);

        return null;
    }

    /**
     * Verifies if there is a A K Q J Unsuited in the hand
     *
     * @return ArrayList<Integer> of all the indexes of the cards that are supposed to be hold, null if there is not a A K Q J Unsuited in the hand
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
        if (firstCard.getRank() == secondCard.getRank() && thirdCard.getRank() == forthCard.getRank()
                && firstCard.getRank() == thirdCard.getRank())
            return idxOutput;

        return null;
    }

    /**
     * Verifies if there is a K Q J Unsuited in the hand
     *
     * @return ArrayList<Integer> of all the indexes of the cards that are supposed to be hold, null if there is not a K Q J unsuited in the hand
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
        if (firstCard.getRank() == secondCard.getRank() && secondCard.getRank() == thirdCard.getRank())
            return idxOutput;

        return null;
    }

    /**
     * Verifies if there is a Full House in the hand
     *
     * @return ArrayList<Integer> of all the indexes of the cards that are supposed to be hold, null if there is not a Full House in the hand
     */
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

    /**
     * Verifies if there is a Four of a Kind in the hand
     *
     * @return ArrayList<Integer> of all the indexes of the cards that are supposed to be hold, null if there is not a Four of a Kind in the hand
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

    /**
     * Verifies if there is a Three of a Kind in the hand
     *
     * @return ArrayList<Integer> of all the indexes of the cards that are supposed to be hold, null if there is not a Three of a Kind in the hand
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

    /**
     * Verifies if there is a Pair in the hand
     *
     * @return ArrayList<Integer> of all the indexes of the cards that are supposed to be hold, null if there is not a Pair in the hand
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
     * @return ArrayList<Integer> of all the indexes of the cards that are supposed to be hold, null if there is not a Two Pairs in the hand
     */
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

    /**
     * Verifies if there is a High Pair in the hand
     *
     * @return ArrayList<Integer> of all the indexes of the cards that are supposed to be hold, null if there is not a High Pair in the hand
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

    /**
     * Verifies if there is a Three Aces in the hand
     *
     * @return ArrayList<Integer> of all the indexes of the cards that are supposed to be hold, null if there is not a Three Aces in the hand
     */
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

    /**
     * Verifies if there is a Q and J Suited in the hand
     *
     * @return ArrayList<Integer> of all the indexes of the cards that are supposed to be hold, null if there is not a Q and J Suited in the hand
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
     * @return ArrayList<Integer> of all the indexes of the cards that are supposed to be hold,null if there is not Two Suited High Cards in the hand
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
     * @param int first value wanted to be checked
     * @param int second value wanted to be checked
     * @param char to know if it is wanted to check suited or unsuited cards
     * @return ArrayList<Integer> of all the indexes of the cards that are supposed to be hold, null if there is not two specific cards suited or unsuited in the hand
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
     * @return ArrayList<Integer> of all the indexes of the cards that are supposed to be hold, null if there is not a Royal Card in the hand
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
     * @return ArrayList<Integer> of all the indexes of the cards that are supposed to be hold, null if there is not an Ace in the hand
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

}
