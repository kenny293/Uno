import java.util.ArrayList;

/**
 * Class Player represents the a player in a game of Uno. 
 * 
 * @author Kenny Chu
 * @version 05/29/2024
 */
public class Player 
{
    private final ArrayList<Card> hand; // The cards that the player has

    /**
     * Constructor for objects of class Player
     */
    public Player() 
    {
        hand = new ArrayList<>();
    } // constructor Player
    
    

    /**
     * Draws a card from a deck and adds the card to the player's hand 
     * 
     * @param  deck  the deck of cards to draw from
     */
    public void drawCard(Deck deck) 
    {
        hand.add(deck.drawCard());
    } // drawCard

    /**
     * Returns the player's current hand of cards
     * 
     * @return the player's hand
     */
    public ArrayList<Card> getHand() 
    {
        return hand;
    } // getHand
    
    /**
     * Returns whether the player's hand is empty
     * 
     * @return true if the hand is empty, false otherwise
     */
    public boolean hasWon() 
    {
        return hand.isEmpty();
    } // hasWon
    
    /* TESTING CLASS PLAYER
    public static void main(String[] args) 
    {
        Deck testDeck = new Deck();
        Player testPlayer = new Player();
        System.out.println(testPlayer.hasWon()); // should be true
        testPlayer.drawCard(testDeck);
        System.out.println(testPlayer.getHand()); // 1 card
        testPlayer.drawCard(testDeck);
        testPlayer.drawCard(testDeck);
        System.out.println(testPlayer.getHand()); // 3 cards
        System.out.println(testPlayer.hasWon()); // should be false
    }
    */
} // class Player
