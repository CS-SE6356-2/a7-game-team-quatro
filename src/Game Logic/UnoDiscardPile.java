/* Author: Jacob Villarreal */

import java.util.*;

/* Represents the discard pile in Uno */
public class UnoDiscardPile
{
	/* Data members */
	private LinkedList<UnoCard> cards;
	
	/* Represents the logical card on top. This normally is exactly
	 * the same as the card that is actually on top, but in the case
	 * of a wild card will have a different color */
	UnoCard topCard;
	
	/* Constructor */
	public UnoDiscardPile()
	{
		cards = new LinkedList<UnoCard>();
		topCard = null;
	}

	/* Wrapper functions */
	public void addCardToTop(UnoCard card)
	{
		this.cards.addFirst(card);
		topCard = card;
	}

	/* used when a wildcard has been played */
	public void addWildcardToTop(UnoCard card, String newColor)
	{
		this.cards.addFirst(card);
		this.topCard = new UnoCard(card.getType(), newColor);
	}

	public UnoCard peekAtTopCard()
	{
		return topCard;
	}
	
	/* Used when the deck runs out of cards.
	 * Takes all the cards except the top card out of the discard pile
	 * and returns them. */
	public LinkedList<UnoCard> removeAllButTopCard()
	{
		UnoCard firstCard = this.cards.pollFirst();
		LinkedList<UnoCard> returnList = this.cards;

		this.cards = new LinkedList<UnoCard>();
		this.cards.addFirst(firstCard);
		
		return returnList;
	}
}
