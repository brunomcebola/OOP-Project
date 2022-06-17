package modes;
import java.nio.file.FileStore;
import java.util.*;
import deck.Card;


public class Simulator implements Mode {

    public static final int N_CARDS_ON_HAND = 5;
    private int rank;
    private int credit;
    ArrayList<Card> myHand;

    /**
    * It is the constructor of the class Simulator.
    *
    * @param  ArrayList<Card> hand being played at the time
    */
    Simulator(ArrayList<Card> _startingHand ){
        super();
        rank = 50; // starts high because the lower the rank the better
        myHand = _startingHand;
    }

    /**
    * Copies the hand at the time and sorts it, giving an output
    * of the hand sorted.
    *
    * @return      ArrayList<Card> which is the hand sorted
    */
    private ArrayList<Card> sortHand(){
        ArrayList<Card> handSorted = myHand;
        Collections.sort( handSorted, new Comparator<ArrayList<Card>>() {
            public int compare (Card card1, Card card2) {
                if(card1.getValue() > card2.getValue()){
                    return 1;
                }
                return -1;
            }
        });

        return handSorted;
    }

    @Override
    public char nextPlay() {
        // CHECK RANK AND THEN GIVE FEEDBACK
        return null;
    }

    @Override
    public Card getNewCard() {
        // GET FROM THE DECK
        return null;
    }

    @Override
    public String getStatistics() {
        // THIS IS FROM THE DECK I GUESS
        return null;
    }

    @Override
    public char getAdivce() {
        // SHOULDNT THIS ALSO BE "NEXT PLAY" ??? XD
        return 0;
    }

    @Override
    public int getCredit() {
        return credit;
    }

    @Override
    public void createDeck() {
        shuffle();
        
    }

    private int verifyPoints(){

        //TODO: THE LOGIC, BECAUSE THE IFS CAN FUCK THINGS UP..
        //TODO: STILL NEED THE FUNCTIONS TO SUM WHAT'S MISSING

        //check rank here
        if(checkFlush('S', 5 , 'N').size() != 0 || checkFourOfAKind().size() != 0 || checkFlush('R', 5, 'N').size() != 0 ) return 1;

        if(checkFlush('R',4,'N').size() != 0 ) return 2;

        if(checkThreeAces().size() != 0) return 3;

        //TODO:
        if(checkStraight().size() != 0 || checkFlush('N', 5, 'N').size() != 0 || checkFHouse().size() != 0) return 4;

        if(checkThreeOfAKind().size() != 0) return 5;

        //TODO:
        if(checkStraight('S', 4, 'N').size() != 0 ) return 6;
        
        if(checkTwoPair().size() != 0) return 7;
        
        if(checkHighPair().size() != 0) return 8;
       
        if(checkFlush('N',4,'N').size() != 0) return 9;
       
        if(checkFlush('R',3,'N').size() != 0) return 10;
        //TODO:
        if(checkFourtoOusideStraight().size() != 0) return 11;
        
        if(checkPair().size() != 0) return 12;
        
        if(checkAKQJUnsuited().size() != 0) return 13; // AKQJ unsuited

        //TODO:
        if(checkThreeToStraightFlush().size() != 0){
            if(checkTypeOne().size() != 0) return 14;
            else if(checkTypeTwo().size() != 0) return 20;
            return 27;// type 3
        }
        //TODO:
        if( checkFourToInsideWithThreeHigh().size() != 0) return 15;

        if(checkQJS().size() != 0) return 16; // GJ suited

        if(checkFlush('N',2,'H').size() != 0) return 17;
        
        if(checkTwoSHC().size() != 0) return 18; // Two suited high cards        
        //TODO:
        if(checkFourToInsideStraight().size() != 0){
            if(checkTwoHighCardsOnStraigh().size() != 0) return 19;
            if(checkOneHighCardsOnStraigh().size() != 0) return 21;
            return 32;
        }
        
        if(checkKQJUnsuited().size() != 0) return 22;
        
        if(checkPairSuits(11, 10, 'S' ).size() != 0) return 23; // check JT suited
        
        if(checkPairSuits(12 , 11 ,'U').size() != 0) return 24; // check QJ unsuited
   
        if(checkFlush('N',1,'H').size() != 0) return 25;

        if(checkPairSuits(12, 10 , 'S').size() != 0) return 26; // check QT suited

        if(checkPairSuits( 13, 11, 'U').size() != 0 || checkPairSuits(13, 12, 'U').size() != 0) return 28; // check KJ and KQ unsuited

        if(checkAce().size() != 0) return 29;

        if(checkPairSuits(13,10,'S').size() != 0) return 30; // KT  suited

        if(checkForRoyalCard().size() != 0) return 31;

        if(checkFlush('N',3,'N').size() != 0) return 33;

        return 34; // discard all
    }
    
    //CHECK, THIS NEEDS TO BE VERIFIED
    private String verifyNextPlay(int rankOfHand){
        String answer = new String("h");
        return answer;
    }

    private void shuffle(){
        System.out.println("ESTOU A DAR SHUFFLE");
        verifyNextPlay(rank);
    }

    //TODO:NEEDS COMMENTS FOR JAVA DOCS
    //TODO:needs checking for the idxoutput
    private ArrayList<Integer> checkFlush(char principalType, int secondType, char thirdType){
        //Principal type and thirdType
        // N -> None
        // R -> Royal
        // S -> Straight
        //Second Type
        // basically, normal flush, 4 to flush, 3 to flush...
        // so options are 3,4,5
        int[] count= new int[]{0,0,0,0};
        int myRank = -1;
        int counter = 0;

        Card newCard;
        int auxValue = 0;

        ArrayList<Integer> idxOutput = new ArrayList<Integer>();

        // STRAIGHT FLUSH TODO: BRUNO'S HELP
        if(principalType == 'S'){
            for(int i = 0; i < N_CARDS_ON_HAND; i++){
                newCard = myHand.get(i);
                count[newCard.getRank() - 1] +=1;
            }
            
            for(int i = 0; i < 4; i++){
                if(count[i] == secondType){

                    for(int j = 0; j < secondType; j++){
                        idxOutput.add(j);
                    }
                    return idxOutput;
                }
            }
        }

        // ROYAL FLUSH
        if(principalType == 'R'){
            for(int i = 0; i < N_CARDS_ON_HAND; i++){
                newCard = myHand.get(i);
                auxValue = newCard.getValue();
                if(auxValue == 1 || auxValue == 10 || auxValue == 11 || auxValue == 12 || auxValue == 13)
                    count[newCard.getRank() - 1] +=1;
            }
            
            for(int i = 0; i < 4; i++){
                if(count[i] == secondType){
                    for(int j = 0; j < N_CARDS_ON_HAND; j++){
                        newCard = myHand.get(j);
                        if((auxValue == 1 || auxValue == 10 || auxValue == 11 || auxValue == 12 || auxValue == 13) 
                        && newCard.getRank() == (cout[i] + 1) ){
                            idxOutput.add(j);
                        }
                    }
                    return idxOutput;
                }
            }
        }

        // FLUSH / 4 TO FLUSH / 3 TO FLUSH
        if(principalType == 'N' && thirdType == 'N'){
            for(int i = 0; i < N_CARDS_ON_HAND; i++){
                newCard = myHand.get(i);
                count[newCard.getRank() - 1] +=1;
            }
            
            for(int i = 0; i < 4; i++){
                if(count[i] == secondType){
                    for(int j = 0; j < N_CARDS_ON_HAND; j++){
                        newCard = myHand.get(j);
                        if(newCard.getRank() == (count[i] + 1) )
                            idxOutput.add(j);
                    }
                    return idxOutput;
                }
            }
        }

        // 3 TO FLUSH WITH 2/1 OR 0 HIGH CARDS

        if(principalType == 'N' && thirdType == 'H'){
            for(int i = 0; i < N_CARDS_ON_HAND; i++){
                newCard = myHand.get(i);
                count[newCard.getRank() - 1] +=1;
            }

            // search for our three cards with the same naipe.
            for(int i = 0; i < 4; i++){
                counter = 1;
                if(count[i] == 3){ // already known that it's 3 in the case
                    for(int j = 0; j < N_CARDS_ON_HAND; j++){
                        newCard =  myHand.get(i);
                        auxValue = newCard.getValue();
                        if(auxValue == 1 || auxValue == 10 || auxValue == 11 || auxValue == 12 || auxValue == 13){
                            counter++;
                        }
                    }
                }
                if(counter == secondType){
                    for(int j = 0; j < N_CARDS_ON_HAND; j++){
                        newCard = myHand.get(j);
                        if(newCard.getRank() == (count[i] + 1) ) idxOutput.add(j);
                        
                    }
                    return idxOutput;
                } 
            }
        }


        return null;
    }

    private ArrayList<Integer> checkAKQJUnsuited(){

        Card firstCard, secondCard, thirdCard, forthCard,auxCard;
        ArrayList<Integer> idxOutput = new ArrayList<Integer>();

        for(int i = 0; i < N_CARDS_ON_HAND; i++){
            auxCard = myHand.get(i);
            if(auxCard.getValue() == 1){
                firstCard = auxCard;
                idxOutput.add(i);
            }
            if(auxCard.getValue() == 11){
                secondCard = auxCard;
                idxOutput.add(i);
            }
            if(auxCard.getValue() == 12){
                thirdCard = auxCard;
                idxOutput.add(i);
            }
            if(auxCard.getValue() == 13){
                forthCard = auxCard;
                idxOutput.add(i);
            }
        }

        if(firstCard == null || secondCard == null || thirdCard == null || forthCard == null) return null;
        if(firstCard.getRank() == secondCard.getRank() && thirdCard.getRank() == forthCard.getRank() && firstCard.getRank() == thirdCard.getRank() ) return idxOutput;

        return null;
    }
    
    private ArrayList<Integer> checkKQJUnsuited(){

        Card firstCard, secondCard, thirdCard,auxCard;
        ArrayList<Integer> idxOutput = new ArrayList<Integer>();

        for(int i = 0; i < N_CARDS_ON_HAND; i++){
            auxCard = myHand.get(i);
            if(auxCard.getValue() == 11){
                firstCard = auxCard;
                idxOutput.add(i);
            }
            if(auxCard.getValue() == 12){
                secondCard = auxCard;
                idxOutput.add(i);
            }
            if(auxCard.getValue() == 13){
                thirdCard = auxCard;
                idxOutput.add(i);
            }
        }

        if(firstCard == null || secondCard == null || thirdCard == null) return null;
        if(firstCard.getRank() == secondCard.getRank() && secondCard.getRank() == thirdCard.getRank() ) return idxOutput;

        return null;
    }
    
    private ArrayList<Integer> checkFHouse(){
        int counter = 1;
        int[] idx = new int[]{-1,-1,-1};
        ArrayList<Integer> idxOutput = new ArrayList<Integer>();

        boolean flagTrioIdx = false;
        Card myCard, auxCard;

        //search for trio
        for(int i = 0; i < 3; i++){
            counter = 1;
            myCard = myHand.get(i);
            idxOutput.add(i);
            for(int j = 0; j < N_CARDS_ON_HAND; j++){
                if( i == j ) continue ;

                auxCard = myHand.get(j);

                if(myCard.getValue() == auxCard.getValue()){
                    idx[counter - 1] = i;
                    counter++;
                    idxOutput.add(j);
                }
            }
            if(counter == 3) break;
            idxOutput.clear();
        }
        
        // search for pair
        if(counter == 3){
            for(int i = 0; i < N_CARDS_ON_HAND - 1 ; i++){
                
                // is my card part of the trio ?
                flagTrioIdx = false;
                for(int idxs = 0; idxs < 3; idxs++){
                    if( i == idx[idxs]){
                        flagTrioIdx = true;
                    }
                }
                // if it is don't need to check
                if(flagTrioIdx) continue;

                //if it is not search for pair
                counter = 1;
                myCard = myHand.get(i);
                idxOutput.add(i);
                for(int j = 0; j < N_CARDS_ON_HAND; j++){
                    if( i == j ) continue ;
    
                    auxCard = myHand.get(j);
    
                    if(myCard.getValue() == auxCard.getValue()){
                        counter++;
                        idxOutput.add(j);
                    }
                }
                if(counter == 2) return idxOutput;
                idxOutput.remove(Integer.valueOf(i));
            }
        }
        
        
        return null;
    }

    private ArrayList<Integer> checkFourOfAKind(){
        int counter = 1;

        ArrayList<Integer> idxOutput = new ArrayList<Integer>();
        Card myCard, auxCard;

        for(int i = 0; i < 2; i++){
            counter = 1;
            myCard = myHand.get(i);
            idxOutput.add(i);
            for(int j = 0; j < N_CARDS_ON_HAND; j++){
                if( i == j ) continue ;

                auxCard = myHand.get(j);

                if(myCard.getValue() == auxCard.getValue()){
                    counter++;
                    idxOutput.add(j);
                }

                if(counter == 4) return idxOutput;
            }
            idxOutput.remove(Integer.valueOf(i));
        }

        return null;
    }

    private ArrayList<Integer> checkThreeOfAKind(){
        int counter = 1;
        Card myCard, auxCard;
        ArrayList<Integer> idxOutput = new ArrayList<Integer>();

        for(int i = 0; i < 3 ; i++){
            counter = 1;
            myCard = myHand.get(i);
            idxOutput.add(i);
            for(int j = 0; j < N_CARDS_ON_HAND; j++){
                if( i == j ) continue ;

                auxCard = myHand.get(j);

                if(myCard.getValue() == auxCard.getValue()){
                    counter++;
                    idxOutput.add(j);
                }
            }
            if(counter == 3) return idxOutput;
            idxOutput.clear();
        }
        
        return null;
    }

    private ArrayList<Integer> checkPair(){
        int counter = 1;
        Card myCard, auxCard;
        ArrayList<Integer> idxOutput = new ArrayList<Integer>();

        for(int i = 0; i < N_CARDS_ON_HAND - 1 ; i++){
            counter = 1;
            myCard = myHand.get(i);
            idxOutput.add(i);
            for(int j = 0; j < N_CARDS_ON_HAND; j++){
                if( i == j ) continue ;

                auxCard = myHand.get(j);

                if(myCard.getValue() == auxCard.getValue()){
                    counter++;
                    idxOutput.add(j);
                }
            }
            if(counter == 2) return idxOutput;
            idxOutput.clear();
        }
        
        return null;
    }

    private ArrayList<Integer> checkTwoPair(){
        int[] idx = new int[]{-1,-1};
        int counter = 1;
        Card myCard, auxCard;

        ArrayList<Integer> idxOutput = new ArrayList<Integer>();

        //search first pair and keep idx of them
        for(int i = 0; i < N_CARDS_ON_HAND - 1 ; i++){
            counter = 1;
            myCard = myHand.get(i);
            idxOutput.add(i);
            for(int j = 0; j < N_CARDS_ON_HAND; j++){
                if( i == j ) continue ;

                auxCard = myHand.get(j);

                if(myCard.getValue() == auxCard.getValue()){
                    idx[0] = i;
                    idx[1] = j;
                    counter++;
                    idxOutput.add(j);
                }
            }
            if(counter == 2) break;
            idxOutput.clear();
        }

        //search second pair
        for(int i = 0; i < N_CARDS_ON_HAND - 1 ; i++){
            counter = 1;
            myCard = myHand.get(i);
            idxOutput.add(i);
            for(int j = 0; j < N_CARDS_ON_HAND; j++){
                if( i == j || j == idx[0] || j == idx[1]) continue ;

                auxCard = myHand.get(j);

                if(myCard.getValue() == auxCard.getValue()){
                    counter++;
                }
            }
            if(counter == 2) break;
            idxOutput.remove(Integer.valueOf(i));
        }
        
        return null;
    }

    private ArrayList<Integer> checkHighPair(){
        int counter = 1;
        Card myCard, auxCard;
        
        ArrayList<Integer> idxOutput = new ArrayList<Integer>();

        for(int i = 0; i < N_CARDS_ON_HAND - 1 ; i++){
            counter = 1;
            myCard = myHand.get(i);
            idxOutput.add(i);
            for(int j = 0; j < N_CARDS_ON_HAND; j++){
                if( i == j ) continue ;

                auxCard = myHand.get(j);

                if(myCard.getValue() == auxCard.getValue()){
                    counter++;
                    idxOutput.add(j);
                }
            }
            if(counter == 2 && (myCard.getValue() == 1 || myCard.getValue() == 11 | myCard.getValue() == 12 || myCard.getValue() == 13 )) 
                return idxOutput;
            idxOutput.clear();
        }
        
        return null;
    }

    private ArrayList<Integer> checkThreeAces(){
        int counter = 1;
        Card myCard, auxCard;

        ArrayList<Integer> idxOutput = new ArrayList<Integer>();

        for(int i = 0; i < 3; i++){
            counter = 1;
            myCard = myHand.get(i);
            idxOutput.add(i);
            for(int j = 0; j < N_CARDS_ON_HAND; j++){
                if( i == j ) continue ;

                auxCard = myHand.get(j);

                if(myCard.getValue() == auxCard.getValue()){
                    counter++;
                    idxOutput.add(j);
                    break;
                }
            
            }
            if(counter == 3 && myHand.get(i).getValue() == 1 ) return idxOutput;
            idxOutput.clear();
        }

        return null;
    }

    private ArrayList<Integer> checkQJS(){

        Card firstCard, secondCard, auxCard;

        ArrayList<Integer> idxOutput = new ArrayList<Integer>();

        for(int i = 0; i < N_CARDS_ON_HAND; i++){
            auxCard = myHand.get(i);
            if(auxCard.getValue() == 12){
                firstCard = auxCard;
                idxOutput.add(i);
            }
            if(auxCard.getValue() == 11){
                secondCard = auxCard;
                idxOutput.add(i);
            }
        }

        if(firstCard == null || secondCard == null) return null;
        if(firstCard.getRank() == secondCard.getRank()) return idxOutput;

        return null;
    }

    private ArrayList<Integer> checkTwoSHC(){
        Card firstCard, secondCard, auxCard;
        
        ArrayList<Integer> idxOutput = new ArrayList<Integer>();

        for(int i = 0; i < N_CARDS_ON_HAND; i++){
            auxCard = myHand.get(i);
            if(auxCard.getValue() == 1 || auxCard.getValue() == 11 ||auxCard.getValue() == 12 || auxCard.getValue() == 13 ){
                if (firstCard == null){
                    idxOutput.add(i);
                    firstCard = auxCard;
                }
                else{
                    idxOutput.add(i);
                    secondCard = auxCard;
                }
            }
            
        }

        if(firstCard == null || secondCard == null) return null;
        if(firstCard.getRank() == secondCard.getRank()) return idxOutput;

        return null;
    }

    private ArrayList<Integer> checkPairSuits(int value1, int value2, char type){
        //note if there's a pair of some value, this counts the last one
        //but since this is used after the pair function this does not 
        //need to be checked
        Card firstCard, secondCard, auxCard;
        ArrayList<Integer> idxOutput = new ArrayList<Integer>();
    
        if(type == 'S'){
            for(int i = 0; i < N_CARDS_ON_HAND; i++){
                auxCard = myHand.get(i);
                if(auxCard.getValue() == value1){
                    firstCard = auxCard;
                    idxOutput.add(i);
                    
                }
                if(auxCard.getValue() == value2){
                    secondCard = auxCard;
                    idxOutput.add(i);
                }
            }
            if(firstCard == null || secondCard == null) return null;
            if(firstCard.getRank() == secondCard.getRank()) return idxOutput;

        }

        if(type == 'U'){
            for(int i = 0; i < N_CARDS_ON_HAND; i++){
                auxCard = myHand.get(i);
                if(auxCard.getValue() == value1){
                    firstCard = auxCard;
                }
                if(auxCard.getValue() == value2){
                    secondCard = auxCard;
                }
            }
            if(firstCard == null || secondCard == null) return null;
            if(firstCard.getRank() != secondCard.getRank()) return idxOutput;
        }

        return null;
    }

    private ArrayList<Integer> checkForRoyalCard(){
        Card newCard;
        ArrayList<Integer> idxOutput = new ArrayList<Integer>();

        for(int i = 0; i < N_CARDS_ON_HAND; i++){
            newCard = myHand.get(i);
            if(newCard.getValue() == 11 ||newCard.getValue() == 12 || newCard.getValue() == 13 ){
                idxOutput.add(i);
                return idxOutput;
            }
        }
        return null;
    }

    private ArrayList<Integer> checkAce(){
        Card newCard;
        ArrayList<Integer> idxOutput = new ArrayList<Integer>();

        for(int i = 0; i < N_CARDS_ON_HAND; i++){
            newCard = myHand.get(i);
            if(newCard.getValue() == 1 ){
                idxOutput.add(i);
                return idxOutput;
            }
        }
        return null;
    }

}