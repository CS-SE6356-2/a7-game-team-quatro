//Shannen Barrameda sib170130 CS3354.HON Team 3
import java.util.*;

public class Deck implements Pile {    
    ArrayList<Card> deck;
    int numCards = 0;
    
    public Deck(){
       deck = new ArrayList<Card>();
       for(int color = 1; color <= 4; color++){
	   deck.add(new Card(0, color));
	   numCards++;

           for(int val = 1; val <= 9 ; val++){
               deck.add(new Card(val, color));
	       deck.add(new Card(val, color));
               numCards += 2;
           }
	   for (int val = 10; val <= 14; val++)
	   {
	       deck.add(new Card(val, color));
	       numCards++;
	   }
       }
       for(int i = 1; i <= 4; i++)
       {
	    deck.add(new Card(13, 5));
	    deck.add(new Card(14, 5));
       }
    }

    @Override
    public void pushCard(Card c) {
        deck.add(c);
    }

    public void shuffle(){
        Collections.shuffle(deck);
    }
    
    public int cardsLeft(){
        return numCards;
    }
    
    public Card dealTopCard(){
        return dealCard(0);
    }
    
    public Card dealCard(int n){
        if(cardsLeft() > 0){
            numCards--;
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