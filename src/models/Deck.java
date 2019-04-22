//Shannen Barrameda sib170130 CS3354.HON Team 3
package models;
import java.util.*;

public class Deck implements Pile {
    ArrayList<Card> deck;
    int numCards = 0;
    
    public Deck(){
       deck = new ArrayList<Card>();
       for(int suit = 1; suit < 6; suit++){
           for(int val = 1; val < 14; val++){
                deck.add(new Card(val, suit));
                numCards++;
           }
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