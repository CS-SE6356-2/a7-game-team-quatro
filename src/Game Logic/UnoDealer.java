/* Author: Jacob Villarreal */

/* Represents the player who is the dealer for Uno */
public class UnoDealer extends UnoPlayer
{
	public UnoDealer(String cName)
	{
		super(cName);
	}
	
	/* Wrapper */
	public void shuffleDeck(UnoDeck deck)
	{
		deck.shuffle();
	}

	/* Take a card from the deck and give it to a player.
	 * The player could be themselves. */
	public void dealCardToPlayer(UnoDeck deck, UnoDiscardPile discardPile, UnoPlayer player)
	{
		/* Replenish deck if needed */
		if (deck.isEmpty())
		{
			replenishDeck(deck, discardPile);
		}
		UnoCard card = deck.drawTopCard();
		player.addCardToHand(card);
	}

	/* Use the discard pile to replenish the deck when it runs out of cards */
	private void replenishDeck(UnoDeck deck, UnoDiscardPile discardPile)
	{
		deck.replenishDeck(discardPile.removeAllButTopCard());
		shuffleDeck(deck);
	}
	
	/* Have the dealer start the discard pile at the start of the game.
	 * To simplify the game, a special card cannot be the first card */
	public void startDiscardPile(UnoDeck deck, UnoDiscardPile discardPile)
	{
		UnoCard card = deck.peekTopCard();
		while (card.getType() == "skip"|| card.getType() == "reverse" || card.getType() == "draw2"
		       || card.getType() == "wild" || card.getType() == "wild4")
		{
			deck.shuffle();
			card = deck.peekTopCard();
		}

		card = deck.drawTopCard();
		discardPile.addCardToTop(card);
	}
}