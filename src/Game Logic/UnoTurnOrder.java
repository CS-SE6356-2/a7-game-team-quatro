/* Author: Jacob Villarreal */

import java.util.*;

/* Used to keep track of whose turn it is */
public class UnoTurnOrder
{
	/* Data members */
	private LinkedList<UnoPlayer> players;
	private boolean clockwise;
	private int currentIndex;

	/* Constructor */
	public UnoTurnOrder(LinkedList<UnoPlayer> cPlayers)
	{
		this.players = cPlayers;
		this.clockwise = true;
		this.currentIndex = 0;
	}

	/* Sets the current player to the next one */
	public void goToNextPlayer()
	{
		if (this.clockwise)
		{
			++this.currentIndex;
			if (this.currentIndex == this.players.size())
			{
				this.currentIndex = 0;
			}
		}
		else
		{
			--this.currentIndex;
			if (this.currentIndex < 0)
			{
				this.currentIndex = this.players.size() - 1;
			}
		}
	}

	/* Reverses the turn order. Applied when a reverse card is played */
	public void reverseOrder()
	{
		this.clockwise = !this.clockwise;	
	}

	/* Getters */
	public UnoPlayer getCurrentPlayer()
	{
		return this.players.get(this.currentIndex);
	}
}