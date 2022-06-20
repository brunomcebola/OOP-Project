package game;

import java.io.*;
import java.util.*;

import cards.*;

public class AdviceTest {
    private File cardFile;

    public AdviceTest(String cardFilePath) throws Exception {

        cardFile = new File(cardFilePath);
    }

    public void run() throws Exception {
        Scanner cardReader = new Scanner(cardFile);

        int i = 1;

        while (cardReader.hasNextLine()) {
            Hand hand = new Hand();

            String[] cardList = cardReader.nextLine().split(" ");

            for (String s : cardList) {
                hand.appendCard(new Card(s.charAt(0), s.charAt(1)));
            }

            System.out.println(i++ + ".");
            System.out.println(getAdvice(hand));
        }

        cardReader.close();
    }

    public String getAdvice(Hand hand) {
        
        if (hand.checkStraightFlush(5) != null) {
            return "1. Straight flush, four of a kind, royal flush"; // 1
        }

        if (hand.checkFourOfAKind() != null) {
            return "1. Straight flush, four of a kind, royal flush"; // 1
        }

        if (hand.checkRoyalFlush(5) != null) {
            return "1. Straight flush, four of a kind, royal flush"; // 1
        }

        if (hand.checkRoyalFlush(4) != null) {
            return "2. 4 to a royal flush"; // 2
        }
        
        if (hand.checkThreeAces() != null) {
            return "3. Three aces"; // 3
        }

        if (hand.checkStraight(5) != null) {
            return "4. Straight, flush, full house"; // 4
        }

        if (hand.checkFlush('N', 5) != null) {
            return "4. Straight, flush, full house"; // 4
        }

        if (hand.checkFullHouse() != null) {
            return "4. Straight, flush, full house"; // 4
        }
        
        if (hand.checkThreeOfAKind() != null) {
            return "5. Three of a kind (except aces)"; // 5
        }

        if (hand.checkStraightFlush(4) != null) {
            return "6. 4 to a straight flush"; // 6
        }

        if (hand.checkTwoPair() != null) {
            return "7. Two pair"; // 7
        }

        if (hand.checkHighPair() != null) {
            return "8. High pair"; // 8
        }
        
        if (hand.checkFlush('N', 4) != null) {
            return "9. 4 to a flush"; // 9
        }

        if (hand.checkRoyalFlush(3) != null) {
            return "10. 3 to a royal flush"; // 10
        }

        if (hand.checkOusideStraight(4) != null) {
            return "11. 4 to an outside straight"; // 11
        }
        
        if (hand.checkPair() != null) {
            return "12. Low pair"; // 12
        }
        
        if (hand.checkAKQJUnsuited() != null) {
            return "13. AKQJ unsuited"; // 13
        }
        
        if (hand.checkThreeToStraightFlush(1) != null) {
            return "14. 3 to a straight flush (type 1)"; // 14
        }
        if (hand.checkFourToInsideWithHigh(3) != null) {
            return "15. 4 to an inside straight with 3 high cards"; // 15
        }
        if (hand.checkQJS() != null) {
            return "16. QJ suited"; // 16
        }

        if (hand.checkFlush('H', 2) != null) {
            return "17. 3 to a flush with 2 high cards"; // 17
        }

        if (hand.checkTwoSHC() != null) {
            return "18. 2 suited high cards"; // 18
        }

        if (hand.checkFourToInsideWithHigh(2) != null) {
            return "19. 4 to an inside straight with 2 high cards"; // 19
        }

        if (hand.checkThreeToStraightFlush(2) != null)
            return "20. 3 to a straight flush (type 2)"; // 20

        if (hand.checkFourToInsideWithHigh(1) != null) {
            return "21. 4 to an inside straight with 1 high card"; // 21
        }

        if (hand.checkKQJUnsuited() != null) {
            return "22. KQJ unsuited"; // 22
        }

        if (hand.checkPairSuits(11, 10, 'S') != null) {
            return "23. JT suited"; // 23
        }

        if (hand.checkPairSuits(12, 11, 'U') != null) {
            return "24. QJ unsuited"; // 24
        }

        if (hand.checkFlush('H', 1) != null) {
            return "25. 3 to a flush with 1 high card"; // 25
        }

        if (hand.checkPairSuits(12, 10, 'S') != null) {
            return "26. QT suited"; // 26
        }

        if (hand.checkThreeToStraightFlush(3) != null) {
            return "27. 3 to a straight flush (type 3)"; // 27
        }

        if (hand.checkPairSuits(13, 11, 'U') != null) {
            return "28. KQ, KJ unsuited"; // 28
        }

        if (hand.checkPairSuits(13, 12, 'U') != null) {
            return "28. KQ, KJ unsuited"; // 28
        }

        if (hand.checkAce() != null) {
            return "29. Ace"; // 29
        }

        if (hand.checkPairSuits(13, 10, 'S') != null) {
            return "30. KT suited"; // 30
        }

        if (hand.checkForRoyalCard() != null) {
            return "31. Jack, Queen or King"; // 31
        }

        if (hand.checkInsideStraight(4) != null) {
            return "32. 4 to an inside straight with no high cards"; // 32
        }

        if (hand.checkFlush('N', 3) != null) {
            return "33. 3 to a flush with no high cards"; // 33
        }

        return "34. Discard everything"; // 34
    }

}