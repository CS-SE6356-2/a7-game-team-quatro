//Shannen Barrameda sib170130 CS3354.HON Team 3
package models;
public class Card{
    /*A = 1, J = 11, Q = 12, K = 13*/
    public final static int ACE = 1;
    public final static int JACK = 11;
    public final static int QUEEN = 12;
    public final static int KING = 13;    
    
    /*SUITS*/
    public final static int SPADES = 1;
    public final static int HEARTS = 2;
    public final static int DIAMONDS = 3;
    public final static int CLUBS = 4;
    public final static int JOKER = 5;
    
    int value, suit;
    
    public Card(){
       value = -1;
       suit = JOKER;
    }
    
    public Card(int value, int suit){
        if(value >= 1 && value <= 13){
            this.value = value;
        }
        else{
            throw new IllegalArgumentException("Invalid Card Value");
        }
        
        if(suit != SPADES && suit != HEARTS && suit != DIAMONDS && suit != CLUBS 
            && suit != JOKER){
            throw new IllegalArgumentException("Invalid Card Suit");
        }
        else{
            this.suit = suit;
        }
    }
    
    public int getValue(){
        return value;
    }
    
    public int getSuit(){
        return suit;
    }
    public String valueAsString(){
        switch(value){
            case 1: return "Ace";
            case 11: return "Jack";
            case 12: return "Queen";
            case 13: return "King";
            default: return Integer.toString(value);                
        }
    }
    
    public String suitAsString(){
        switch(suit){
            case 1: return "Spades";
            case 2: return "Hearts";
            case 3: return "Diamonds";
            case 4: return "Clubs";
            default: return "Joker";
        }
    }
    public String toString(){
        if(suit == JOKER)
            return "Joker";
        else
            return valueAsString() + " of " + suitAsString();
    }
}