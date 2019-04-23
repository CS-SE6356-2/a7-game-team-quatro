//Shannen Barrameda sib170130 CS3354.HON Team 3
//Second Author: Jacob Villarreal
import java.util.*;

public class Deck {    
    ArrayList<Card> deck;
    
    public Deck(){
       deck = new ArrayList<Card>();
       for(int color = 1; color <= 4; color++){
	   deck.add(new Card(0, color));

           for(int val = 1; val <= 9 ; val++){
               deck.add(new Card(val, color));
	       deck.add(new Card(val, color));
           }
	   for (int val = 10; val <= 14; val++)
	   {
	       deck.add(new Card(val, color));
	   }
       }
       for(int i = 1; i <= 4; i++)
       {
	    deck.add(new Card(13, 5));
	    deck.add(new Card(14, 5));
       }
    }

    public void shuffle(){
        Collections.shuffle(deck);
    }
    
    public Card dealTopCard(){
        return dealCard(0);
    }
    
    public Card dealCard(int n){
        if(deck.size() > 0){
            return deck.remove(n);
        }
        else{
            System.out.print("Deck is empty");
            return null;
        }
    }
    
    public void printCards(){
        for(Card c: deck){
            System.out.println(c.toString());
        }
    }
}