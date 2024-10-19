import java.util.ArrayList;
import java.util.Collections;

/**
 * Class Deck represents the deck of cards in Uno. 
 * This is the deck of cards that players draw from. 
 *
 * @author Kenny Chu
 * @version 05/29/2024
 */
public class Deck 
{
    private final ArrayList<Card> cards; // The deck of Uno cards

    /**
     * Constructor for objects of class Deck
     */
    public Deck() 
    {
        cards = new ArrayList<>();

        // For each color...
        // create two sets of cards numbered 1-9
        // create one number 0 card
        // create skip, reverse, and draw two cards (2 of each)
        for (Card.Color color : Card.Color.values()) 
        {
            int c = 0;
            if (color == Card.Color.WILD) continue; // Handle wild cards below
            for (int i = 0; i <= 9; i++) 
            {
                cards.add(new Card(color, Card.Type.NUMBER, i)); c++;
                // Add another card if numbered 1-9
                if (i != 0) 
                {
                    cards.add(new Card(color, Card.Type.NUMBER, i)); c++;
                }
            }
            cards.add(new Card(color, Card.Type.SKIP, -1));
            cards.add(new Card(color, Card.Type.SKIP, -1));
            cards.add(new Card(color, Card.Type.REVERSE, -1));
            cards.add(new Card(color, Card.Type.REVERSE, -1));
            cards.add(new Card(color, Card.Type.DRAW_TWO, -1));
            cards.add(new Card(color, Card.Type.DRAW_TWO, -1));
        }
        
        // Create the wild cards
        for (int i = 0; i < 4; i++) 
        {
            cards.add(new Card(Card.Color.WILD, Card.Type.WILD, -1));
            cards.add(new Card(Card.Color.WILD, Card.Type.WILD_DRAW_FOUR, -1));
        }
        
        // Shuffle the cards
        Collections.shuffle(cards);
    } // constructor Deck 
    
    /**
     * Draws a card from the Uno deck
     * 
     * @return the drawn card or null if the deck is empty
     */
    public Card drawCard() 
    {
        if (cards.isEmpty()) 
        {
            return null;
        }
        return cards.remove(cards.size() - 1);
    } // drawCard

    /**
     * Returns whether the deck is empty or not
     * 
     * @return true if the deck is empty, false otherwise
     */
    public boolean isEmpty() 
    {
        return cards.isEmpty();
    }
    
    /* TESTING CLASS DECK
    public static void main(String[] args) 
    {
        int number = 0;
        Deck testDeck = new Deck();
        for (Card card : testDeck.cards) {
            System.out.println(card);
        }
        System.out.println("Size: " + testDeck.cards.size());
    }
    */
} // class Deck
