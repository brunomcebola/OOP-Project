package modes;
import java.nio.file.FileStore;
import java.util.*;
import deck.Card;


public class Simulator implements Mode {

    public static final int N_CARDS_ON_HAND = 5;
    private int rank;
    private int credit;
    ArrayList<Card> myHand;

    Simulator(ArrayList<Card> _startingHand ){
        super();
        rank = 0;
        myHand = _startingHand;
    }

    private void sortHand(){
        Collections.sort( myHand, new Comparator<ArrayList<Card>>() {
            public int compare (Card card1, Card card2) {
                if(card1.getValue() > card2.getValue()){
                    return 1;
                }
                return -1;
            }
        });
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

        //TODO: THE LOGIC, BECAUSE THE IFS CAN FUCK THINGS UP...

        //check rank here
        if(checkFlush('S', 5 , 'N') || checkFourOfAKind() || checkFlush('R', 5, 'N') ) return 1;

        else if(checkFlush('R',4,'N') ) return 2;
        //TODO:
        else if(checkStraight() || checkFlush('N', 5, 'N') || checkFHouse()) return 4;

        else if(checkThreeOfAKind()){

            if(checkThreeAces()) return 3;

            else return 5;
        }

        //TODO:
        else if(checkStraight('S', 4, 'N') ) {

            if( checkFourToInsideWithThreeHigh()) return 15;

            return 6;
        }

        else if(checkPair()){

            if(checkTwoPair()) return 7;

            else if(checkHighPair()) return 8;

            return 12;
        }
        //TODO:
        else if(checkFlush('N',4,'N')) return 9;
        //TODO:
        else if(checkFlush('R',3,'N')) return 10;
        //TODO:
        else if(checkFourtoOusideStraight()) return 11;

        else if(checkAKQJUnsuited()) return 13; // AKQJ unsuited
        //TODO:
        else if(checkThreeToStraightFlush()){
            if(checkTypeOne()) return 14;
            else if(checkTypeTwo()) return 20;
            return 27;// type 3
        }

        else if(checkQJS()) return 16; // GJ suited
        //TODO:CAN DO WITHOUT SORT() 
        else if(checkFlush('N',3,'N')){
            if(checkFlush('N',2,'H')) return 17;
            if(checkFlush('N',1,'H')) return 25;
            return 33;
        }

        else if(checkTwoSHC()) return 18; // Two suited high cards

        //TODO:
        else if(checkFourToInsideStraight()){
            if(checkTwoHighCardsOnStraigh()) return 19;
            if(checkOneHighCardsOnStraigh()) return 21;
            return 32;
        }

        else if(checkKQJUnsuited()) return 22;

        else if(checkPairSuits(11, 10, 'S' )) return 23; // check JT suited

        else if(checkPairSuits(12 , 11 ,'U')) return 24; // check QJ unsuited

        else if(checkPairSuits(12, 10 , 'S')) return 26; // check QT suited

        else if(checkPairSuits( 13, 11, 'U') || checkPairSuits(13, 12, 'U')) return 28; // check KJ and KQ unsuited

        else if(checkAce()) return 29;

        else if(checkPairSuits(13,10,'S')) return 30; // KT  suited

        else if(checkForRoyalCard()) return 31;

        return 34; // discard all
    }
    
    //CHECK, THIS NEEDS TO BE VERIFIED
    private String verifyNextPlay(int rankOfHand){
        String answer = new String("h");
        return answer;
    }

    private void shuffle(){
        System.out.println("ESTOU A DAR SHUFFLE");
    }

    //TODO:
    private boolean checkFlush(char principalType, int secondType, char thirdType){
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


        // STRAIGHT FLUSH
        if(principalType == 'S'){
            for(int i = 0; i < N_CARDS_ON_HAND; i++){
                newCard = myHand.get(i);
                count[newCard.getRank() - 1] +=1;
            }
            
            for(int i = 0; i < 4; i++){
                if(count[i] == secondType)
                    return true;
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
                if(count[i] == secondType)
                    return true;
            }
        }

        // FLUSH / 4 TO FLUSH / 3 TO FLUSH
        if(principalType == 'N' && thirdType == 'N'){
            for(int i = 0; i < N_CARDS_ON_HAND; i++){
                newCard = myHand.get(i);
                count[newCard.getRank() - 1] +=1;
            }
            
            for(int i = 0; i < 4; i++){
                if(count[i] == secondType)
                    return true;
            }
        }

        // 3 TO FLUSH WITH 2/1 OR 0 HIGH CARDS

        if(principalType == 'N' && thirdType == 'H'){
            for(int i = 0; i < N_CARDS_ON_HAND; i++){
                newCard = myHand.get(i);
                count[newCard.getRank() - 1] +=1;
            }

            //s search for our three cards with the same naipe.
            for(int i = 0; i < 4; i++){
                counter = 0;
                if(count[i] == 3) // already known that it's 3 in the case
                    for(int j = 0; j < N_CARDS_ON_HAND; j++){
                        newCard =  myHand.get(i);
                        auxValue = newCard.getValue();
                        if(auxValue == 1 || auxValue == 10 || auxValue == 11 || auxValue == 12 || auxValue == 13){
                            counter++;
                        }
                    }
                if(counter == secondType) return true;
            }
        }


        return false;
    }

    private boolean checkAKQJUnsuited(){

        Card firstCard, secondCard, thirdCard, forthCard,auxCard;

        for(int i = 0; i < N_CARDS_ON_HAND; i++){
            auxCard = myHand.get(i);
            if(auxCard.getValue() == 1){
                firstCard = auxCard;
            }
            if(auxCard.getValue() == 11){
                secondCard = auxCard;
            }
            if(auxCard.getValue() == 12){
                thirdCard = auxCard;
            }
            if(auxCard.getValue() == 13){
                forthCard = auxCard;
            }
        }

        if(firstCard == null || secondCard == null || thirdCard == null || forthCard == null) return false;
        if(firstCard.getRank() == secondCard.getRank() && thirdCard.getRank() == forthCard.getRank() && firstCard.getRank() == thirdCard.getRank() ) return true;

        return false;
    }
    
    private boolean checkKQJUnsuited(){

        Card firstCard, secondCard, thirdCard,auxCard;

        for(int i = 0; i < N_CARDS_ON_HAND; i++){
            auxCard = myHand.get(i);
            if(auxCard.getValue() == 11){
                firstCard = auxCard;
            }
            if(auxCard.getValue() == 12){
                secondCard = auxCard;
            }
            if(auxCard.getValue() == 13){
                thirdCard = auxCard;
            }
        }

        if(firstCard == null || secondCard == null || thirdCard == null) return false;
        if(firstCard.getRank() == secondCard.getRank() && secondCard.getRank() == thirdCard.getRank() ) return true;

        return false;
    }
    
    private boolean checkFHouse(){
        int counter = 1;
        int[] idx = new int[]{-1,-1,-1};

        boolean flagTrioIdx = false;
        Card myCard, auxCard;

        //search for trio
        for(int i = 0; i < 3; i++){
            counter = 1;
            myCard = myHand.get(i);
            for(int j = 0; j < N_CARDS_ON_HAND; j++){
                if( i == j ) continue ;

                auxCard = myHand.get(j);

                if(myCard.getValue() == auxCard.getValue()){
                    idx[counter - 1] = i;
                    counter++;
                }
            }
            if(counter == 3) break;
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

                for(int j = 0; j < N_CARDS_ON_HAND; j++){
                    if( i == j ) continue ;
    
                    auxCard = myHand.get(j);
    
                    if(myCard.getValue() == auxCard.getValue()){
                        counter++;
                    }
                }
                if(counter == 2) return true;
            }
        }
        
        
        return false;
    }

    private boolean checkFourOfAKind(){
        int counter = 1;
        Card myCard;
        Card auxCard;
        for(int i = 0; i < 2; i++){
            counter = 1;
            myCard = myHand.get(i);
            for(int j = 0; j < N_CARDS_ON_HAND; j++){
                if( i == j ) continue ;

                auxCard = myHand.get(j);

                if(myCard.getValue() == auxCard.getValue()){
                    counter++;
                }

                if(counter == 4) return true;
            }
        }

        return false;
    }

    private boolean checkThreeOfAKind(){
        int counter = 1;
        Card myCard, auxCard;

        for(int i = 0; i < 3 ; i++){
            counter = 1;
            myCard = myHand.get(i);
            for(int j = 0; j < N_CARDS_ON_HAND; j++){
                if( i == j ) continue ;

                auxCard = myHand.get(j);

                if(myCard.getValue() == auxCard.getValue()){
                    counter++;
                }
            }
            if(counter == 3) return true;
        }
        
        return false;
    }

    private boolean checkPair(){
        int counter = 1;
        Card myCard, auxCard;

        for(int i = 0; i < N_CARDS_ON_HAND - 1 ; i++){
            counter = 1;
            myCard = myHand.get(i);
            for(int j = 0; j < N_CARDS_ON_HAND; j++){
                if( i == j ) continue ;

                auxCard = myHand.get(j);

                if(myCard.getValue() == auxCard.getValue()){
                    counter++;
                }
            }
            if(counter == 2) return true;
        }
        
        return false;
    }

    private boolean checkTwoPair(){
        int[] idx = new int[]{-1,-1};
        int counter = 1;
        Card myCard, auxCard;

        //search first pair and keep idx of them
        for(int i = 0; i < N_CARDS_ON_HAND - 1 ; i++){
            counter = 1;
            myCard = myHand.get(i);
            for(int j = 0; j < N_CARDS_ON_HAND; j++){
                if( i == j ) continue ;

                auxCard = myHand.get(j);

                if(myCard.getValue() == auxCard.getValue()){
                    idx[0] = i;
                    idx[1] = j;
                    counter++;
                }
            }
            if(counter == 2) break;
        }

        //search second pair
        for(int i = 0; i < N_CARDS_ON_HAND - 1 ; i++){
            counter = 1;
            myCard = myHand.get(i);
            for(int j = 0; j < N_CARDS_ON_HAND; j++){
                if( i == j || j == idx[0] || j == idx[1]) continue ;

                auxCard = myHand.get(j);

                if(myCard.getValue() == auxCard.getValue()){
                    counter++;
                }
            }
            if(counter == 2) break;
        }
        
        return false;
    }

    private boolean checkHighPair(){
        int counter = 1;
        Card myCard, auxCard;

        for(int i = 0; i < N_CARDS_ON_HAND - 1 ; i++){
            counter = 1;
            myCard = myHand.get(i);
            for(int j = 0; j < N_CARDS_ON_HAND; j++){
                if( i == j ) continue ;

                auxCard = myHand.get(j);

                if(myCard.getValue() == auxCard.getValue()){
                    counter++;
                }
            }
            if(counter == 2 && (myCard.getValue() == 1 || myCard.getValue() == 11 | myCard.getValue() == 12 || myCard.getValue() == 13 )) 
                return true;
        }
        
        return false;
    }

    private boolean checkThreeAces(){
        int idx = 0;
        int counter = 1;
        Card myCard, auxCard;

        for(int i = 0; i < 3; i++){
            counter = 1;
            myCard = myHand.get(i);
            for(int j = 0; j < N_CARDS_ON_HAND; j++){
                if( i == j ) continue ;

                auxCard = myHand.get(j);

                if(myCard.getValue() == auxCard.getValue()){
                    counter++;
                    idx = j;
                    break;
                }
            
            }
            if(counter == 3 && myHand.get(i).getValue() == 1 ) return true;
        }

       

        return false;
    }

    private boolean checkQJS(){

        Card firstCard, secondCard, auxCard;

        for(int i = 0; i < N_CARDS_ON_HAND; i++){
            auxCard = myHand.get(i);
            if(auxCard.getValue() == 12){
                firstCard = auxCard;
            }
            if(auxCard.getValue() == 11){
                secondCard = auxCard;
            }
        }

        if(firstCard == null || secondCard == null) return false;
        if(firstCard.getRank() == secondCard.getRank()) return true;

        return false;
    }

    private boolean checkTwoSHC(){
        Card firstCard, secondCard, auxCard;

        for(int i = 0; i < N_CARDS_ON_HAND; i++){
            auxCard = myHand.get(i);
            if(auxCard.getValue() == 1 || auxCard.getValue() == 11 ||auxCard.getValue() == 12 || auxCard.getValue() == 13 ){
                if (firstCard == null)
                    firstCard = auxCard;
                else
                    secondCard = auxCard;
            }
            
        }

        if(firstCard == null || secondCard == null) return false;
        if(firstCard.getRank() == secondCard.getRank()) return true;

        return false;
    }

    private boolean checkPairSuits(int value1, int value2, char type){
        //note if there's a pair of some value, this counts the last one
        //but since this is used after the pair function this does not 
        //need to be checked
        Card firstCard, secondCard, auxCard;
    
        if(type == 'S'){
            for(int i = 0; i < N_CARDS_ON_HAND; i++){
                auxCard = myHand.get(i);
                if(auxCard.getValue() == value1){
                    firstCard = auxCard;
                }
                if(auxCard.getValue() == value2){
                    secondCard = auxCard;
                }
            }
            if(firstCard == null || secondCard == null) return false;
            if(firstCard.getRank() == secondCard.getRank()) return true;

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
            if(firstCard == null || secondCard == null) return false;
            if(firstCard.getRank() != secondCard.getRank()) return true;
        }

        return false;
    }

    private boolean checkForRoyalCard(){
        Card newCard;
        for(int i = 0; i < N_CARDS_ON_HAND; i++){
            newCard = myHand.get(i);
            if(newCard.getValue() == 11 ||newCard.getValue() == 12 || newCard.getValue() == 13 )  return true;
        }
        return false;
    }

    private boolean checkAce(){
        Card newCard;
        for(int i = 0; i < N_CARDS_ON_HAND; i++){
            newCard = myHand.get(i);
            if(newCard.getValue() == 1 )  return true;
        }
        return false;
    }
}
