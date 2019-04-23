//Shannen Barrameda sib170130 CS3354.HON Team 3
//Second Author: Jacob Villarreal
import java.util.*;

public class Pile 
{
	ArrayList<Card> cards;
	public Pile()
	{
		cards = new ArrayList<Card>();
    	}

	/* Called on the pile you want to be the deck. Fills the deck
	 * with the standard 108 card Uno deck. */
    	public generateDeck()
	{
		/* Red, Blue, Yellow, and Green cards */
		for(int color = 1; color <= 4; color++)
		{
			/* One 0, Two 1-9 and specials.
			cards.add(new Card(0, color));
	
			for(int val = 1; val <= 14 ; val++)
			{
				cards.add(new Card(val, color));
				cards.add(new Card(val, color));
 	      		}
       		}
		/* 4 of each kind of wild card */
       		for(int i = 1; i <= 4; i++)
       		{
		    cards.add(new Card(13, 5));
		    cards.add(new Card(14, 5));
       		}
   	}

   	public void shuffle()
	{
        	Collections.shuffle(cards);
    	}
    
    	public Card dealTopCard()
	{
        	return dealCard(0);
    	}
    
    	public Card dealCard(int n)
	{
        	if(deck.size() > 0)
		{
            		return cards.remove(n);
        	}
        	else
		{
            		System.out.print("Deck is empty");
          	  	return null;
        	}
    	}
    
    	public void printCards()
	{
        	for(Card c: deck)
		{
            		System.out.println(c.toString());
		}
        }
}