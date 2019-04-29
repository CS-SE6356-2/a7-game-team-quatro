/* Author: Jacob Villarreal */

import java.util.*;

/* Represents the deck in Uno */
public class UnoDeck
{
	/* Data members */
	private LinkedList<UnoCard> cards;
	
	/* Constructor. Creates the cards needed for the pile
	 * to be a standard 108 card Uno deck. */
	public UnoDeck()
	{
		cards = new LinkedList<UnoCard>();
		
		/* Green, Red, Yellow, and Blue cards */
		for (int colorIndex = 0; colorIndex <= 3; ++colorIndex)
		{
			/* One 0, two 1-9 and specials. */
			this.cards.add(new UnoCard("0", UnoCard.cardColors.get(colorIndex)));
			
			for (int typeIndex = 1; typeIndex <= 12; ++typeIndex)
			{
				this.cards.add(new UnoCard(UnoCard.cardTypes.get(typeIndex), UnoCard.cardColors.get(colorIndex)));
				this.cards.add(new UnoCard(UnoCard.cardTypes.get(typeIndex), UnoCard.cardColors.get(colorIndex)));
			}
		}
		
		/* 4 of each kind of wild card */
		for (int counter = 1; counter <= 4; ++counter)
		{
			this.cards.add(new UnoCard("wild", "blue"));
			this.cards.add(new UnoCard("wild4", "blue"));
		}
	}

	/* Wrapper functions */
	public UnoCard drawTopCard()
	{
		return this.cards.pollFirst();
	}
	
	/* Used by the dealer when adding the first card to the discard pile */
	public UnoCard peekTopCard()
	{
		return this.cards.peekFirst();
	}
	
	public void shuffle()
	{
		Collections.shuffle(this.cards);
	}

	/* Used when cards are added from the discard pile
	 * to replenish the deck. */
	public void replenishDeck(LinkedList<UnoCard> newDeck)
	{
		this.cards = newDeck;	
	}
	
	/* Wrapper functions */
	public boolean isEmpty()
	{
		return this.cards.size() == 0;		
	}
}