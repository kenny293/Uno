/**
 * Class Card represents the a single Uno card.
 *
 * @author Kenny Chu
 * @version 05/29/2024
 */
public class Card 
{
    public enum Color { RED, YELLOW, GREEN, BLUE, WILD } // All colors of Uno cards possible
    public enum Type { NUMBER, SKIP, REVERSE, DRAW_TWO, WILD, WILD_DRAW_FOUR } // All types of Uno cards possible

    private final Color color; // Color of card
    private final Type type; // Type of card
    private final int number; // Number of card; if not applicable (for special cards), -1

    /**
     * Constructor for objects of class Card
     * 
     * @param  color  the color of the card being made
     * @param  type   the type of the card being made
     * @param  number the number of the card being made, -1 if the card is a special card
     */
    public Card(Color color, Type type, int number) 
    {
        this.color = color;
        this.type = type;
        this.number = number;
    } // constructor Card

    /**
     * Returns the color of the Uno card
     *
     * @return the color of the card
     */
    public Color getColor() 
    {
        return color;
    } // getColor

    /**
     * Returns the type of the Uno card
     *
     * @return the type of the card
     */
    public Type getType() 
    {
        return type;
    } // getType

    /**
     * Returns the number of the Uno card
     *
     * @return the number of the card
     */
    public int getNumber() 
    {
        return number;
    } // getNumber
    public String getImageFilename() 
    {
        if (type == type.NUMBER)
        {
            return "C:\\Users\\Kenny\\CS Project\\Uno\\" + color.toString().toLowerCase() + "_" + number + ".png";
        }
        else if (type == type.DRAW_TWO)
        {
            return "C:\\Users\\Kenny\\CS Project\\Uno\\" + color.toString().toLowerCase() + "_draw_two.png";
        }
        else if (type == type.REVERSE)
        {
            return "C:\\Users\\Kenny\\CS Project\\Uno\\" + color.toString().toLowerCase() + "_reverse.png";
        }
        else if (type == type.SKIP)
        {
            return "C:\\Users\\Kenny\\CS Project\\Uno\\" + color.toString().toLowerCase() + "_skip.png";
        }
        else if (color == color.BLUE || color == color.YELLOW || color == color.RED || color == color.GREEN && type == type.WILD)
        {
            return "C:\\Users\\Kenny\\CS Project\\Uno\\" + color.toString().toLowerCase() + "_wild.png";
        }
        else if (type == type.WILD)
        {
            return "C:\\Users\\Kenny\\CS Project\\Uno\\wild.png";
        }
        else if (type == type.WILD_DRAW_FOUR)
        {
            return "C:\\Users\\Kenny\\CS Project\\Uno\\wild_draw_four.png";
        }
        else
        {
            return null;
        }
    }

    /**
     * Returns the a String description of the card
     * If a number card, "COLOR NUMBER"
     * If a special card, "COLOR TYPE"
     *
     * @return the description of the card
     */

    @Override
    public String toString() 
    {
        return (type == Type.NUMBER ? color + " " + number : color + " " + type);
    }
    // toString

    /* TESTING CLASS CARD
    public static void main(String[] args) 
    {
    // Test all colors for number cards
    Card testCard1 = new Card(Color.RED, Type.NUMBER, 8);
    System.out.println(testCard1.getColor());
    System.out.println(testCard1.getType());
    System.out.println(testCard1.getNumber());
    System.out.println(testCard1.toString());

    Card testCard2 = new Card(Color.YELLOW, Type.NUMBER, 4);
    System.out.println(testCard2.getColor());
    System.out.println(testCard2.getType());
    System.out.println(testCard2.getNumber());
    System.out.println(testCard2.toString());

    Card testCard3 = new Card(Color.GREEN, Type.NUMBER, 9);
    System.out.println(testCard3.getColor());
    System.out.println(testCard3.getType());
    System.out.println(testCard3.getNumber());
    System.out.println(testCard3.toString());

    Card testCard4 = new Card(Color.BLUE, Type.NUMBER, 2);
    System.out.println(testCard4.getColor());
    System.out.println(testCard4.getType());
    System.out.println(testCard4.getNumber());
    System.out.println(testCard4.toString());

    // Test wild cards
    Card testCard5 = new Card(Color.WILD, Type.WILD, -1);
    System.out.println(testCard5.getColor());
    System.out.println(testCard5.getType());
    System.out.println(testCard5.getNumber());
    System.out.println(testCard5.toString());

    Card testCard6 = new Card(Color.WILD, Type.WILD_DRAW_FOUR, -1);
    System.out.println(testCard6.getColor());
    System.out.println(testCard6.getType());
    System.out.println(testCard6.getNumber());
    System.out.println(testCard6.toString());

    // Test other special cards
    Card testCard7 = new Card(Color.BLUE, Type.DRAW_TWO, -1);
    System.out.println(testCard7.getColor());
    System.out.println(testCard7.getType());
    System.out.println(testCard7.getNumber());
    System.out.println(testCard7.toString());

    Card testCard8 = new Card(Color.YELLOW, Type.REVERSE, -1);
    System.out.println(testCard8.getColor());
    System.out.println(testCard8.getType());
    System.out.println(testCard8.getNumber());
    System.out.println(testCard8.toString());

    Card testCard9 = new Card(Color.GREEN , Type.SKIP, -1);
    System.out.println(testCard9.getColor());
    System.out.println(testCard9.getType());
    System.out.println(testCard9.getNumber());
    System.out.println(testCard9.toString());
    }
     */
}// class Card
