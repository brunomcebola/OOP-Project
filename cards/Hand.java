package cards;

import java.util.*;

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

  //TODO:NEEDS COMMENTS FOR JAVA DOCS
  //TODO:needs checking for the idxoutput
  //TODO:SEPARATE ROYAL STRAIGHT AND FLUSH
  public ArrayList<Integer> checkFlush(char principalType, int secondType, char thirdType){
      //Principal type and thirdType
      // N -> Normal
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
              newCard = cards.get(i);
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
              newCard = cards.get(i);
              auxValue = newCard.getValue();
              if(auxValue == 1 || auxValue == 10 || auxValue == 11 || auxValue == 12 || auxValue == 13)
                  count[newCard.getRank() - 1] +=1;
          }
          
          for(int i = 0; i < 4; i++){
              if(count[i] == secondType){
                  for(int j = 0; j < N_CARDS_ON_HAND; j++){
                      newCard = cards.get(j);
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
              newCard = cards.get(i);
              count[newCard.getRank() - 1] +=1;
          }
          
          for(int i = 0; i < 4; i++){
              if(count[i] == secondType){
                  for(int j = 0; j < N_CARDS_ON_HAND; j++){
                      newCard = cards.get(j);
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
              newCard = cards.get(i);
              count[newCard.getRank() - 1] +=1;
          }

          // search for our three cards with the same naipe.
          for(int i = 0; i < 4; i++){
              counter = 1;
              if(count[i] == 3){ // already known that it's 3 in the case
                  for(int j = 0; j < N_CARDS_ON_HAND; j++){
                      newCard =  cards.get(i);
                      auxValue = newCard.getValue();
                      if(auxValue == 1 || auxValue == 10 || auxValue == 11 || auxValue == 12 || auxValue == 13){
                          counter++;
                      }
                  }
              }
              if(counter == secondType){
                  for(int j = 0; j < N_CARDS_ON_HAND; j++){
                      newCard = cards.get(j);
                      if(newCard.getRank() == (count[i] + 1) ) idxOutput.add(j);
                      
                  }
                  return idxOutput;
              } 
          }
      }


      return null;
  }

  public ArrayList<Integer> checkAKQJUnsuited(){

      Card firstCard, secondCard, thirdCard, forthCard,auxCard;
      ArrayList<Integer> idxOutput = new ArrayList<Integer>();

      for(int i = 0; i < N_CARDS_ON_HAND; i++){
          auxCard = cards.get(i);
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
  
  public ArrayList<Integer> checkKQJUnsuited(){

      Card firstCard = null, secondCard = null, thirdCard = null, auxCard = null;
      ArrayList<Integer> idxOutput = new ArrayList<Integer>();

      for(int i = 0; i < N_CARDS_ON_HAND; i++){
          auxCard = cards.get(i);
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
  
  public ArrayList<Integer> checkFHouse(){
      int counter = 1;
      int[] idx = new int[]{-1,-1,-1};
      ArrayList<Integer> idxOutput = new ArrayList<Integer>();

      boolean flagTrioIdx = false;
      Card myCard, auxCard;

      //search for trio
      for(int i = 0; i < 3; i++){
          counter = 1;
          myCard = cards.get(i);
          idxOutput.add(i);
          for(int j = 0; j < N_CARDS_ON_HAND; j++){
              if( i == j ) continue ;

              auxCard = cards.get(j);

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
              myCard = cards.get(i);
              idxOutput.add(i);
              for(int j = 0; j < N_CARDS_ON_HAND; j++){
                  if( i == j ) continue ;
  
                  auxCard = cards.get(j);
  
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

  public ArrayList<Integer> checkFourOfAKind(){
      int counter = 1;

      ArrayList<Integer> idxOutput = new ArrayList<Integer>();
      Card myCard, auxCard;

      for(int i = 0; i < 2; i++){
          counter = 1;
          myCard = cards.get(i);
          idxOutput.add(i);
          for(int j = 0; j < N_CARDS_ON_HAND; j++){
              if( i == j ) continue ;

              auxCard = cards.get(j);

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

  public ArrayList<Integer> checkThreeOfAKind(){
      int counter = 1;
      Card myCard, auxCard;
      ArrayList<Integer> idxOutput = new ArrayList<Integer>();

      for(int i = 0; i < 3 ; i++){
          counter = 1;
          myCard = cards.get(i);
          idxOutput.add(i);
          for(int j = 0; j < N_CARDS_ON_HAND; j++){
              if( i == j ) continue ;

              auxCard = cards.get(j);

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

  public ArrayList<Integer> checkPair(){
      int counter = 1;
      Card myCard, auxCard;
      ArrayList<Integer> idxOutput = new ArrayList<Integer>();

      for(int i = 0; i < N_CARDS_ON_HAND - 1 ; i++){
          counter = 1;
          myCard = cards.get(i);
          idxOutput.add(i);
          for(int j = 0; j < N_CARDS_ON_HAND; j++){
              if( i == j ) continue ;

              auxCard = cards.get(j);

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

  public ArrayList<Integer> checkTwoPair(){
      int[] idx = new int[]{-1,-1};
      int counter = 1;
      Card myCard, auxCard;

      ArrayList<Integer> idxOutput = new ArrayList<Integer>();

      //search first pair and keep idx of them
      for(int i = 0; i < N_CARDS_ON_HAND - 1 ; i++){
          counter = 1;
          myCard = cards.get(i);
          idxOutput.add(i);
          for(int j = 0; j < N_CARDS_ON_HAND; j++){
              if( i == j ) continue ;

              auxCard = cards.get(j);

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
          myCard = cards.get(i);
          idxOutput.add(i);
          for(int j = 0; j < N_CARDS_ON_HAND; j++){
              if( i == j || j == idx[0] || j == idx[1]) continue ;

              auxCard = cards.get(j);

              if(myCard.getValue() == auxCard.getValue()){
                  counter++;
              }
          }
          if(counter == 2) break;
          idxOutput.remove(Integer.valueOf(i));
      }
      
      return null;
  }

  public ArrayList<Integer> checkHighPair(){
      int counter = 1;
      Card myCard, auxCard;
      
      ArrayList<Integer> idxOutput = new ArrayList<Integer>();

      for(int i = 0; i < N_CARDS_ON_HAND - 1 ; i++){
          counter = 1;
          myCard = cards.get(i);
          idxOutput.add(i);
          for(int j = 0; j < N_CARDS_ON_HAND; j++){
              if( i == j ) continue ;

              auxCard = cards.get(j);

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

  public ArrayList<Integer> checkThreeAces(){
      int counter = 1;
      Card myCard, auxCard;

      ArrayList<Integer> idxOutput = new ArrayList<Integer>();

      for(int i = 0; i < 3; i++){
          counter = 1;
          myCard = cards.get(i);
          idxOutput.add(i);
          for(int j = 0; j < N_CARDS_ON_HAND; j++){
              if( i == j ) continue ;

              auxCard = cards.get(j);

              if(myCard.getValue() == auxCard.getValue()){
                  counter++;
                  idxOutput.add(j);
                  break;
              }
          
          }
          if(counter == 3 && cards.get(i).getValue() == 1 ) return idxOutput;
          idxOutput.clear();
      }

      return null;
  }

  public ArrayList<Integer> checkQJS(){

      Card firstCard = null, secondCard = null, auxCard = null;

      ArrayList<Integer> idxOutput = new ArrayList<Integer>();

      for(int i = 0; i < N_CARDS_ON_HAND; i++){
          auxCard = cards.get(i);
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

  public ArrayList<Integer> checkTwoSHC(){
      Card firstCard = null, secondCard = null, auxCard = null;
      
      ArrayList<Integer> idxOutput = new ArrayList<Integer>();

      for(int i = 0; i < N_CARDS_ON_HAND; i++){
          auxCard = cards.get(i);
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

  public ArrayList<Integer> checkPairSuits(int value1, int value2, char type){
      //note if there's a pair of some value, this counts the last one
      //but since this is used after the pair function this does not 
      //need to be checked
      Card firstCard = null, secondCard = null, auxCard = null;
      ArrayList<Integer> idxOutput = new ArrayList<Integer>();
  
      if(type == 'S'){
          for(int i = 0; i < N_CARDS_ON_HAND; i++){
              auxCard = cards.get(i);
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

      else if(type == 'U'){
          for(int i = 0; i < N_CARDS_ON_HAND; i++){
              auxCard = cards.get(i);
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

  public ArrayList<Integer> checkForRoyalCard(){
      Card newCard;
      ArrayList<Integer> idxOutput = new ArrayList<Integer>();

      for(int i = 0; i < N_CARDS_ON_HAND; i++){
          newCard = cards.get(i);
          if(newCard.getValue() == 11 ||newCard.getValue() == 12 || newCard.getValue() == 13 ){
              idxOutput.add(i);
              return idxOutput;
          }
      }
      return null;
  }

  public ArrayList<Integer> checkAce(){
      Card newCard;
      ArrayList<Integer> idxOutput = new ArrayList<Integer>();

      for(int i = 0; i < N_CARDS_ON_HAND; i++){
          newCard = cards.get(i);
          if(newCard.getValue() == 1 ){
              idxOutput.add(i);
              return idxOutput;
          }
      }
      return null;
  }

}
