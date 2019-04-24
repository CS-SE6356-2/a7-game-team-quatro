/* Author: Jacob Villarreal */

import java.util.*;

/* Represents the discard pile in Uno */
public class UnoDiscardPile extends UnoCardPile
{
	/* Wrapper functions */
	public void addCardToTop(UnoCard card)
	{
		this.cards.addFirst(card);
	}

	public UnoCard peekAtTopCard()
	{
		return this.cards.peek();
	}
	
	/* Used when the deck runs out of cards.
	 * Takes all the cards except the top card out of the discard pile *
	 * and returns them.
	public LinkedList<UnoCard> removeAllButTopCard()
	{
		UnoCard topCard = this.cards.pollFirst();
		LinkedList<UnoCard> returnList = this.cards;

		this.cards = new LinkedList<UnoCard>();
		this.addCardToTop(topCard);
		
		return returnList;
	}
}