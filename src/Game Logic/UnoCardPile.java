/* Original Author: Shannen Barrameda
 * Author: Jacob Villarreal */

import java.util.*;

/* Represents any stack of Uno cards used in a game of Uno.
 * Basically, this class and descendents are just wrappers around LinkedList */
public class UnoCardPile
{
	/* Data Members */
	protected LinkedList<UnoCard> cards;
		
	/* Constructor */
	public UnoCardPile()
	{
		this.cards = new LinkedList<UnoCard>();
	}

	/* Wrapper functions */
	public boolean isEmpty()
	{
		return this.cards.size() == 0;		
	}
}