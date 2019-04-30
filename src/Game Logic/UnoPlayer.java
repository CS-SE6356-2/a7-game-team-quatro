/* Original Author: Team 3 */
/* Author: Jacob Villarreal */


/* Represents a person playing Uno */
public class UnoPlayer
{
	/* Data members */
	private UnoHand hand;
	private String name;
	
	/* Constructor */
	public UnoPlayer(String cName)
	{
		this.hand = new UnoHand();
		this.name = cName;
	}

	/* Wrappers */
	public void addCardToHand(UnoCard card)
	{
		this.hand.addCard(card);
	}

	public boolean outOfCards()
	{
		return this.hand.size() == 0;
	}

	public boolean hasMatchInHand(UnoCard card)
	{
		return this.hand.hasMatch(card);
	}
	
	public UnoCard peekAtCard(int index)
	{
		return this.hand.peekAtCard(index);
	}
	
	public int numberOfCards()
	{
		return this.hand.size();
	}

	/* Play a card to the discard pile */
	public boolean playCard(UnoCard card, UnoDiscardPile discardPile)
	{
		/* Handle wild cards */
		String cardType = card.getType();
		String cardColor = "";
		boolean isWild = cardType.equals("wild") || cardType.equals("wild4");
		
		if (isWild)
		{
			/* Get the color the player wants the wildcard to act as */
			cardColor = card.getColor();
			card = new UnoCard(cardType, "wild");
		}
		
		/* Verify that the card can be legally played */
		if (card.matches(discardPile.peekAtTopCard()))
		{
			/* Find the card in the player's hand */
			int cardIndex = this.hand.indexOf(card);
			if (cardIndex == -1)
			{
				/* Card isn't in the player's hand */
				return false;
			}
			/* Card being played is a wildcard */
			if (isWild)
			{
				this.hand.playCard(cardIndex);
				discardPile.addWildcardToTop(card, cardColor);
				return true;
			}
			else
			{
				this.hand.playCard(cardIndex);
				discardPile.addCardToTop(card);
				return true;
			}
		}
		else
		{
			/* Can't play this card */
			return false;
		}
	}
	
	public String getName()
	{
		return this.name;
	}
}