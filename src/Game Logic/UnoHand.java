/* Author: Jacob Villarreal */

import java.util.*;

/* Represents the cards a player has in Uno */

public class UnoHand extends UnoCardPile
{
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

	public UnoCard playCard(int index)
	{
		return this.cards.remove(index);
	}

	/* Used when checking if player needs to call Uno */
	public boolean hasOneCard()
	{
		return this.cards.size() == 1;
	}

	/* Used when checking if a player can legally play a
	 * wild draw four card. Returns true if player has at least
	 * one card of the specified color in their hand */
	public boolean hasColor(int color)
	{
		boolean result = false;
		for (UnoCard card : this.cards)
		{
			if (card.getColor() == color)
			{
				result = true;
				break;
			}	
		}
		return result;		
	}
}