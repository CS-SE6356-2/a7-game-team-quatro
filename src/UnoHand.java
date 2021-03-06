/* Author: Jacob Villarreal */

import java.util.*;

/* Represents the cards a player has in Uno */

public class UnoHand
{
	/* Data members */
	private LinkedList<UnoCard> cards;
	
	public UnoHand()
	{
		cards = new LinkedList<UnoCard>();
	}
	
	/* Wrapper functions */
	public void addCard(UnoCard card)
	{
		this.cards.addFirst(card);
	}

	public int size()
	{
		return this.cards.size();
	}

	public UnoCard peekAtCard(int index)
	{
		return this.cards.get(index);
	}

	public int indexOf(UnoCard card)
	{
		for(int i = 0; i < this.cards.size(); i++) {//iterate over each index
			if(this.cards.get(i).getColor().equals(card.getColor()) && this.cards.get(i).getType().equals(card.getType())) {//if the index contains that card
				return i;
			}
		}
		
		return -1;//otherwise not found
	}

	public UnoCard playCard(int index)
	{
		return this.cards.remove(index);
	}

	/* Used when checking if a player can legally play a
	 * wild draw four card. Returns true if player has at least
	 * one card of the specified color in their hand */
	public boolean hasMatch(UnoCard card)
	{
		boolean result = false;
		for (UnoCard handCard : this.cards)
		{
			if (handCard.matches(card))
			{
				result = true;
				break;
			}	
		}
		return result;		
	}
}