package modes;
import java.nio.file.FileStore;
import java.util.*;
import cards.*;


public class Simulator {
    ArrayList<Card> myHand;

    /**
    * It is the constructor of the class Simulator.
    *
    * @param  ArrayList<Card> hand being played at the time
    */
    Simulator(ArrayList<Card> _startingHand ){
        super();
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

    public String nextPlay() {
        // CHECK RANK AND THEN GIVE FEEDBACK
        return null;
    }

    public Card getNewCard() {
        // GET FROM THE DECK
        return null;
    }

    public String getStatistics() {
        // THIS IS FROM THE DECK I GUESS
        return null;
    }

    public char getAdivce() {
        // SHOULDNT THIS ALSO BE "NEXT PLAY" ??? XD
        return 0;
    }

    private int verifyPoints(){

        //TODO: THE LOGIC, BECAUSE THE IFS CAN FUCK THINGS UP..
        //TODO: STILL NEED THE FUNCTIONS TO SUM WHAT'S MISSING

        //check rank here
        if(checkFlush('S', 5 , 'N').size() != 0 || checkFourOfAKind().size() != 0 || checkFlush('R', 5, 'N').size() != 0 ) return 1;

        else if(checkFlush('R',4,'N').size() != 0 ) return 2;

        else if(checkThreeAces().size() != 0) return 3;

        //TODO:
        else if(checkStraight().size() != 0 || checkFlush('N', 5, 'N').size() != 0 || checkFHouse().size() != 0) return 4;

        else if(checkThreeOfAKind().size() != 0) return 5;

        //TODO:
        else if(checkStraight('S', 4, 'N').size() != 0 ) return 6;
        
        else if(checkTwoPair().size() != 0) return 7;
        
        else if(checkHighPair().size() != 0) return 8;
       
        else if(checkFlush('N',4,'N').size() != 0) return 9;
       
        else if(checkFlush('R',3,'N').size() != 0) return 10;
        //TODO:
        else if(checkFourtoOusideStraight().size() != 0) return 11;
        
        else if(checkPair().size() != 0) return 12;
        
        else if(checkAKQJUnsuited().size() != 0) return 13; // AKQJ unsuited

        //TODO:
        else if(checkThreeToStraightFlush().size() != 0){
            if(checkTypeOne().size() != 0) return 14;
            else if(checkTypeTwo().size() != 0) return 20;
            return 27;// type 3
        }
        //TODO:
        else if( checkFourToInsideWithThreeHigh().size() != 0) return 15;

        else if(checkQJS().size() != 0) return 16; // GJ suited

        else if(checkFlush('N',2,'H').size() != 0) return 17;
        
        else if(checkTwoSHC().size() != 0) return 18; // Two suited high cards        
        //TODO:
        else if(checkFourToInsideStraight().size() != 0){
            if(checkTwoHighCardsOnStraigh().size() != 0) return 19;
            if(checkOneHighCardsOnStraigh().size() != 0) return 21;
            return 32;
        }
        
        else if(checkKQJUnsuited().size() != 0) return 22;
        
        else if(checkPairSuits(11, 10, 'S' ).size() != 0) return 23; // check JT suited
        
        else if(checkPairSuits(12 , 11 ,'U').size() != 0) return 24; // check QJ unsuited
   
        else if(checkFlush('N',1,'H').size() != 0) return 25;

        else if(checkPairSuits(12, 10 , 'S').size() != 0) return 26; // check QT suited

        else if(checkPairSuits( 13, 11, 'U').size() != 0 || checkPairSuits(13, 12, 'U').size() != 0) return 28; // check KJ and KQ unsuited

        else if(checkAce().size() != 0) return 29;

        else if(checkPairSuits(13,10,'S').size() != 0) return 30; // KT  suited

        else if(checkForRoyalCard().size() != 0) return 31;

        else if(checkFlush('N',3,'N').size() != 0) return 33;

        return 34; // discard all
    }
    
    //CHECK, THIS NEEDS TO BE VERIFIED
    private String verifyNextPlay(int rankOfHand){
        String answer = new String("h");
        return answer;
    }

}