package cards;

import java.util.ArrayList;

import exceptions.cardsExceptions.cardExcepetions.*;

/*
 * Card explanation:
 * 
 * value => 1, 2, ..., 12, 13 maps to A, 2, ..., Q, K
 * rank => 1, 2, 3, 4 maps to Spades, Hearts, Clubs, Diamonds
 * 
 */

public class Card {
  private static final String[] ranks = { "S", "H", "C", "D" };

  private final int value;
  private final int rank;

  /**
     * Constructor of the Class which has Exceptions
     */
  public Card(int value, int rank)
      throws InvalidCardValueException, InvalidCardRankException, InvalidCardValueAndRankException {
    boolean invalidValue = false;
    boolean invalidRank = false;

    // Check if specified card value is valid
    if (value < 1 || value > 13)
      invalidValue = true;

    // Check if specified card rank is valid
    if (rank < 1 || rank > 4)
      invalidRank = true;

    if (invalidValue && invalidRank)
      throw new InvalidCardValueAndRankException();

    if (invalidValue)
      throw new InvalidCardValueException();

    if (invalidRank)
      throw new InvalidCardRankException();

    this.value = value;
    this.rank = rank;
  }
  /**
     * Overload of the main constructor
     */
  public Card(char value, char rank)
      throws InvalidCardValueException, InvalidCardRankException, InvalidCardValueAndRankException {
    this(valueToInt(value), rankToInt(rank));

  }

  /**
     * Override of the toString function to show
     * the cards in a right way
     */
  @Override
  public String toString() {
    if (value >= 2 && value <= 9)
      return value + "" + ranks[rank - 1];

    if (value == 1)
      return "A" + ranks[rank - 1];

    if (value == 10)
      return "T" + ranks[rank - 1];

    if (value == 11)
      return "J" + ranks[rank - 1];

    if (value == 12)
      return "Q" + ranks[rank - 1];

    return "K" + ranks[rank - 1];
  }
  /**
     * Override of the hashCode to verify equals
     * in the right way
     */
  @Override
  public int hashCode() {
    int res = 1;
    res = res * 31 + Math.min(value, rank);
    res = res * 31 + Math.max(value, rank);
    return res;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;

    if (obj == null)
      return false;

    if (!(obj instanceof Card))
      return false;

    Card other = (Card) obj;

    if (rank != other.rank)
      return false;

    if (value != other.value)
      return false;

    return true;
  }

  /**
   * Generate all 52 cards and return them in an ArrayList.
   * 
   * @return An ArrayList of Card objects.
   */
  public static ArrayList<Card> generateAllCards()
      throws InvalidCardValueException, InvalidCardRankException, InvalidCardValueAndRankException {

    ArrayList<Card> cardsList = new ArrayList<Card>();

    for (int value = 1; value <= 13; value++) {
      for (int rank = 1; rank <= 4; rank++) {
        cardsList.add(new Card(value, rank));
      }
    }

    return cardsList;
  }

  /**
   * If the rank is a spade, return 1. If the rank is a heart, return 2. If the rank is a club, return
   * 3. If the rank is a diamond, return 4. Otherwise, throw an exception.
   * 
   * @param rank The rank of the card.
   * @return The rank of the card as an integer.
   */
  static private int rankToInt(char rank) throws InvalidCardRankException {
    if (rank == 'S')
      return 1;

    if (rank == 'H')
      return 2;

    if (rank == 'C')
      return 3;

    if (rank == 'D')
      return 4;

    throw new InvalidCardRankException();
  }

  /**
   * This function returns the value of the variable value.
   * 
   * @return The value of the enum.
   */
  public int getValue() {
    return this.value;
  }

  /**
   * This function returns the rank of the card.
   * 
   * @return The rank of the card.
   */
  public int getRank() {
    return this.rank;
  }

  /**
   * It converts a card value to an integer
   * 
   * @param value The value of the card.
   * @return The value of the card.
   */
  static private int valueToInt(char value) throws InvalidCardValueException {
    if (value == 'A')
      return 1;

    if (value == 'T')
      return 10;

    if (value == 'J')
      return 11;

    if (value == 'Q')
      return 12;

    if (value == 'K')
      return 13;

    if (value - '0' >= 2 && value - '0' <= 9)
      return value - '0';

    throw new InvalidCardValueException();
  }
}
