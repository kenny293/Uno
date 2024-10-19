import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.HashMap;
import java.util.Map;

/**
 * Class UnoFrame represents the game of Uno
 * 
 * @author Kenny Chu
 * @version 05/29/2024
 */
public class UnoFrame extends JFrame 
{
    private Deck deck; // The Uno deck of cards
    private Player player; // The player
    private Player computer; // The CPU
    private JPanel handPanel;
    private JLabel discardPileLabel;
    private Card topCard; // The card at the top of the discard pile
    private boolean playerTurn = true; // Boolean to determine if it's the player's turn; players always go first
    /**
     * Constructor for class UnoFrame
     */
    public UnoFrame() 
    {
        setTitle("Uno Game");
        setSize(1500, 1500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set up variables
        deck = new Deck();
        player = new Player();
        computer = new Player();

        // Each player starts with 7 cards
        for (int i = 0; i < 7; i++) 
        {
            player.drawCard(deck);
            computer.drawCard(deck);
        }

        topCard = deck.drawCard();  // Start with one card in the discard pile
        if (topCard.getColor() == Card.Color.WILD)
        {
            topCard=deck.drawCard();
        }

        // Create main panel and panel for player's hand
        JPanel mainPanel = new JPanel(new BorderLayout());
        handPanel = new JPanel();
        handPanel.setLayout(new FlowLayout());

        // Display top card
        discardPileLabel = new JLabel(new ImageIcon(topCard.getImageFilename()));
        discardPileLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // Display draw card button
        JButton drawButton = new JButton("Draw Card");
        drawButton.addActionListener(new ActionListener() 
            {
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    // When the player presses the draw button, draw a card from the deck
                    // and update the player's hand
                    player.drawCard(deck);
                    updateHand();
                }
            });

        // Add all elements to main panel
        mainPanel.add(discardPileLabel, BorderLayout.NORTH);
        mainPanel.add(handPanel, BorderLayout.CENTER);
        mainPanel.add(drawButton, BorderLayout.SOUTH);

        add(mainPanel);
        updateHand(); // Show the player's hand
    } // constructor UnoFrame

    /**
     * Update the hand displayed to the player
     */
    private void updateHand() 
    {
        // Remove existing hand
        handPanel.removeAll();

        // Display all cards in the player's hand
        for (Card card : player.getHand()) 
        {
            // Each card is a button
            
            JButton cardButton = new JButton(new ImageIcon(card.getImageFilename()));
            cardButton.addActionListener(new ActionListener() 
                {
                    @Override
                    public void actionPerformed(ActionEvent e) 
                    {
                        if (playerTurn && canPlayCard(card)) 
                        {
                            playCard(card);
                            handPanel.remove(cardButton);
                            handPanel.revalidate();
                            handPanel.repaint();

                            // Check if the player has won
                            if (player.hasWon()) 
                            {
                                JOptionPane.showMessageDialog(UnoFrame.this, "You won!");
                                System.exit(0);
                            }

                            playerTurn = false; // Player turn is done...
                            handleSpecialCard(card); // ...but still check if the card that was put down does something special
                            computerTurn(); // Let the CPU go
                        } 
                        else if (!playerTurn) {
                            JOptionPane.showMessageDialog(UnoFrame.this, "It's not your turn!");
                        }
                        else if (!canPlayCard(card)) {
                            JOptionPane.showMessageDialog(UnoFrame.this, "You can't play this card!");
                        }
                    }
                });
            handPanel.add(cardButton);
        }
        handPanel.revalidate();
        handPanel.repaint();
    } // updateHand

    /**
     * Returns whether a player can play the card in a game of Uno
     * 
     * @param  card  the card to be tested
     */
    private boolean canPlayCard(Card card) 
    {
        // Handle wild cards
        if (card.getColor() == Card.Color.WILD) 
        {
            return true;
        }
        // Handle special action cards
        else if ((card.getType() == Card.Type.REVERSE) || (card.getType() == Card.Type.SKIP) || (card.getType() == Card.Type.DRAW_TWO))
        {
            // Must match the top card type
            if (card.getType() == topCard.getType())
            {
                return true;
            }
        }
        // Handle number card
        else if (card.getType() == Card.Type.NUMBER)
        {
            // Must match the top card number
            if (card.getNumber() == topCard.getNumber())
            {
                return true;
            }
        }
        // Otherwise, return if the color matches
        return card.getColor() == topCard.getColor();
    } // canPlayCard

    /**
     * Handles when the CPU plays a special card
     * 
     * @param  card  the special card
     */
    private void computerHandleSpecialCard(Card card) 
    {
        // Wild cards
        if (card.getColor() == Card.Color.WILD) 
        {
            // Draw four cards for the player
            if (card.getType() == Card.Type.WILD_DRAW_FOUR) 
            {
                for (int i = 0; i < 4; i++) 
                {
                    player.drawCard(deck);
                }
                JOptionPane.showMessageDialog(UnoFrame.this, "Player drew 4 cards! Computer turn.");
                playerTurn = false;
                updateHand();
            }
            else 
            {
                playerTurn = true; // If it's just a wild card, it is no longer the CPU's turn
            }
            topCard = new Card(chooseColorForComputer(), Card.Type.WILD, -1);
            JOptionPane.showMessageDialog(UnoFrame.this, "Computer changed the color to: " + chooseColorForComputer());
            ImageIcon image = new ImageIcon(topCard.getImageFilename());
            discardPileLabel.setIcon(image);
        }
        // Reverse card: In this two-player only game, reverse will just skip the player's turn
        else if (card.getType() == Card.Type.REVERSE)
        {
            JOptionPane.showMessageDialog(UnoFrame.this, "Direction reversed! Computer turn.");
            playerTurn = false;
        }
        // Skip card: Skip the player's turn
        else if (card.getType() == Card.Type.SKIP)
        {
            JOptionPane.showMessageDialog(UnoFrame.this, "Player turn skipped! Computer turn.");
            playerTurn = false;
        }
        // Draw two: Draw two cards for the player
        else if (card.getType() == Card.Type.DRAW_TWO)
        {
            for (int i = 0; i < 2; i++)
            {
                player.drawCard(deck);
            }
            JOptionPane.showMessageDialog(UnoFrame.this, "Player drew 2 cards! Computer turn.");
            playerTurn = false;
            updateHand();
        }
        // Otherwise, we do nothing
    } // computerHandleSpecialCard

    /**
     * Handles when a player plays a special card
     * 
     * @param  card  the special card
     */
    private void handleSpecialCard(Card card) 
    {
        // Wild cards
        if (card.getColor() == Card.Color.WILD) 
        {
            // Draw four cards for the computer
            if (card.getType() == Card.Type.WILD_DRAW_FOUR) 
            {
                for (int i = 0; i < 4; i++) 
                {
                    computer.drawCard(deck);
                }
                JOptionPane.showMessageDialog(UnoFrame.this, "Computer drew 4 cards! Player turn.");
                playerTurn = true;
            }
            chooseColor();
            //System.out.println("after wild: " + computer.getHand());
        }
        // Reverse card: In this two-player only game, reverse will just skip the computer's turn
        else if (card.getType() == Card.Type.REVERSE)
        {
            JOptionPane.showMessageDialog(UnoFrame.this, "Direction reversed! Player turn.");
            playerTurn = true;
            //System.out.println("after reverse: " + computer.getHand());  TODO
        }
        // Skip card: Skip the computer's turn
        else if (card.getType() == Card.Type.SKIP)
        {
            JOptionPane.showMessageDialog(UnoFrame.this, "Computer turn skipped! Player turn.");
            playerTurn = true;
            //System.out.println("after skip: " + computer.getHand()); // TODO
        }
        // Draw two: Draw two cards for the computer
        else if (card.getType() == Card.Type.DRAW_TWO)
        {
            for (int i = 0; i < 2; i++)
            {
                computer.drawCard(deck);
            }
            JOptionPane.showMessageDialog(UnoFrame.this, "Computer drew 2 cards! Player turn.");
            playerTurn = true;
            //System.out.println("after draw 2: " + computer.getHand()); // TODO
        }
        // Otherwise, we do nothing
    } // handleSpecialCard

    /**
     * Effectively plays the card
     * 
     * @param  card  the card to be played
     */
    private void playCard(Card card) 
    {
        // To play a card, the top card needs to become the card, and the card must be removed from the player's hand
        topCard = card;
        ImageIcon image = new ImageIcon(topCard.getImageFilename());
        discardPileLabel.setIcon(image);
        player.getHand().remove(card);
    } // playCard

    /**
     * When the player puts down a wild card, this function allows them to choose the color
     */
    private void chooseColor() 
    {
        // Show option panel to player
        String[] colors = {"RED", "YELLOW", "GREEN", "BLUE"};
        String chosenColor = (String) JOptionPane.showInputDialog(this, "Choose a color", "Wild Card Color", JOptionPane.PLAIN_MESSAGE, null, colors, colors[0]);

        // Display the chosen color
        if (chosenColor != null) 
        {
            if (chosenColor == "RED") 
            {
                topCard = new Card(Card.Color.RED, Card.Type.WILD, -1);
            }
            else if(chosenColor == "YELLOW")
            {
                topCard = new Card(Card.Color.YELLOW, Card.Type.WILD, -1);
            }
            else if(chosenColor == "GREEN")
            {
                topCard = new Card(Card.Color.GREEN, Card.Type.WILD, -1);
            }
            else if(chosenColor == "BLUE")
            {
                topCard = new Card(Card.Color.BLUE, Card.Type.WILD, -1);
            }
            ImageIcon image = new ImageIcon(topCard.getImageFilename());
            discardPileLabel.setIcon(image);
        }
    } // chooseColor

    /**
     * Chooses a color for the computer when it puts down a wild card
     * 
     * @return the color
     */
    private Card.Color chooseColorForComputer() 
    {
        Map<Card.Color, Integer> colorCount = new HashMap<>();
        // Count how many of each card color the CPU has in its hand
        for (Card.Color color : Card.Color.values()) 
        {
            colorCount.put(color, 0);
        }
        for (Card card : computer.getHand()) 
        {
            if (card.getColor() != Card.Color.WILD) 
            {
                colorCount.put(card.getColor(), colorCount.get(card.getColor()) + 1);
            }
        }

        Card.Color bestColor = Card.Color.RED; // Default to red
        int maxCount = 0;
        // Find the highest count
        for (Map.Entry<Card.Color, Integer> entry : colorCount.entrySet()) 
        {
            if (entry.getValue() > maxCount) 
            {
                maxCount = entry.getValue();
                bestColor = entry.getKey();
            }
        }

        return bestColor;
    } // chooseColorForComputer

    private void computerTurn() 
    {
        // The CPU only goes when it is not the player's turn
        System.out.println("before turn: " + computer.getHand());
        while (playerTurn == false)
        {   
            Card cardToPlay = null;
            // See if the computer's current hand has a card that can be played
            for (Card card : computer.getHand()) 
            {
                if (canPlayCard(card)) 
                {
                    cardToPlay = card;
                    break;
                }
            }

            // If we found a card to play, play it
            if (cardToPlay != null) 
            {
                // Play the card
                topCard = cardToPlay;
                ImageIcon image = new ImageIcon(topCard.getImageFilename());
                discardPileLabel.setIcon(image);

                // Remove the card from the CPU's hand
                computer.getHand().remove(cardToPlay);
                
                //Check if computer has uno
                if (computer.getHand().size() == 1)
                {
                    JOptionPane.showMessageDialog(UnoFrame.this, "The computer has Uno");
                }
                // Check if the computer has won
                if (computer.hasWon()) 
                {
                    JOptionPane.showMessageDialog(UnoFrame.this, "Computer won!");
                    System.exit(0);
                }

                playerTurn = true; // It's now the player's turn...
                computerHandleSpecialCard(cardToPlay); // ...but still check if the card that was played was a special card
            } 
            // Otherwise, CPU must draw a card
            else 
            {
                computer.drawCard(deck);
            }
        }
        System.out.println("after turn: " + computer.getHand());
    } // computerTurn

    /**
     * Function main runs the Uno game
     */
    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(new Runnable() 
            {
                @Override
                public void run() 
                {
                    // Play the Uno game
                    UnoFrame frame = new UnoFrame();
                    frame.setVisible(true);
                }
            });
    } // main
} // class UnoFrame
