package modes;
import deck.Card;

public interface Mode {

    public char nextPlay();
    public Card getNewCard();
    public String getStatistics();
    public char getAdivce();
    public int getCredit();
    public void createDeck();

}