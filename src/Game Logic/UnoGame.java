/* Author: Jacob Villarreal */

import java.util.*;

/* Represents a game of Uno */
public class UnoGame
{
	/* Data members */
	private LinkedList<UnoPlayer> players;
	private UnoDealer dealer;
	private UnoTurnOrder turnOrder;
	
	private UnoDeck deck;
	private UnoDiscardPile discardPile;
	
	/* Constructor */
	public UnoGame(LinkedList<String> playerNames)
	{
		this.players = new LinkedList<UnoPlayer>();
		
		/* Create dealer */
		if (!playerNames.isEmpty())
		{
			this.dealer = new UnoDealer(playerNames.get(0));
			this.players.addLast(dealer);
		}
		
		/* Create the other players */
		for (int i = 1; i < playerNames.size(); ++i)
		{
			this.players.addLast(new UnoPlayer(playerNames.get(i)));
		}
		
		/* Initialize other members */
		this.turnOrder = new UnoTurnOrder(players);
		this.deck = new UnoDeck();
		this.discardPile = new UnoDiscardPile();
	}
	
	/* Handles the pre-first move stuff like dealing and starting the discard pile */
	public void init()
	{
		this.dealOutCards();
		this.dealer.startDiscardPile(this.deck, this.discardPile);
	
		/* Player after dealer goes first */
		turnOrder.goToNextPlayer();
		
		/* If the player has no cards that match, they are dealt cards until they do */
		while (!this.turnOrder.getCurrentPlayer().hasMatchInHand(this.discardPile.peekAtTopCard()))
		{
				dealer.dealCardToPlayer(this.deck, this.discardPile, this.turnOrder.getCurrentPlayer());
		}
	}
	
	/* Progress the game by one move using the given input.
	 * Returns true if the game successfully progressed */
	public boolean tick(String input)
	{	
		String result = this.takeTurn(input);
		if (result == "ERROR")
		{
			return false;
		}
		else
		{
			/* If a skip card was played, the next player's turn is skipped */
			if (result == "skip")
			{
				this.turnOrder.goToNextPlayer();
				this.turnOrder.goToNextPlayer();
			}
			
			/* If a reverse card was played, the turn order is reversed */
			else if (result == "reverse")
			{
				this.turnOrder.reverseOrder();
				this.turnOrder.goToNextPlayer();
			}
			
			/* if a draw 2 card was played, the next player is dealt 2 cards */
			else if (result == "draw2")
			{
				this.turnOrder.goToNextPlayer();
				this.dealer.dealCardToPlayer(this.deck, this.discardPile, turnOrder.getCurrentPlayer());
				this.dealer.dealCardToPlayer(this.deck, this.discardPile, turnOrder.getCurrentPlayer());
			}
			
			/* if a draw 4 card was played, the next player is dealt 4 cards */
			else if (result == "wild4")
			{
				this.turnOrder.goToNextPlayer();
				this.dealer.dealCardToPlayer(this.deck, this.discardPile, turnOrder.getCurrentPlayer());
				this.dealer.dealCardToPlayer(this.deck, this.discardPile, turnOrder.getCurrentPlayer());
				this.dealer.dealCardToPlayer(this.deck, this.discardPile, turnOrder.getCurrentPlayer());
				this.dealer.dealCardToPlayer(this.deck, this.discardPile, turnOrder.getCurrentPlayer());
			}
			
			/* If the next player has no cards that match, they are dealt cards until they do */
			while (!this.turnOrder.getCurrentPlayer().hasMatchInHand(this.discardPile.peekAtTopCard()))
			{
					dealer.dealCardToPlayer(this.deck, this.discardPile, this.turnOrder.getCurrentPlayer());
			}
			
			return true;
		}
	}
	
	/* Returns name of winning player if there is one. Otherwise, returns null */
	public String checkForWinCondition()
	{
		for (int i = 0; i < this.players.size(); ++i)
		{
			if (this.players.get(i).outOfCards())
			{
				return this.players.get(i).getName();
			}
		}
		return null;
	}
	
	/* Gives any information about the current state of the game that would be useful to a GUI as a string. */
	public UnoGameInfo getGameInfo()
	{
		UnoGameInfo gameInfo = new UnoGameInfo();
		gameInfo.currentPlayer = this.turnOrder.getCurrentPlayer().getName();
		gameInfo.discardPileTopCard = this.discardPile.peekAtTopCard();
		for (int i = 0; i < this.players.size(); ++i)
		{
			for (int j = 0; j < this.players.get(i).numberOfCards(); ++j)
			{
				gameInfo.playerCards.get(i).addLast(this.players.get(i).peekAtCard(j));
			}
		}
		
		return gameInfo;
	}
	
	/* Have the current player make the passed in move.
	 * Returns "ERROR" if unsuccessful, otherwise returns the type of the card played */
	private String takeTurn(String input)
	{
		Scanner scanner = new Scanner(input);
		String cardColor;
		String cardType;
		
		/* Reject if input is empty */
		if (scanner.hasNext())
		{
			cardColor = scanner.next();
			cardColor.toLowerCase();
			
			/* Reject if no card type is given */
			if (scanner.hasNext())
			{
				cardType = scanner.next();
				cardType.toLowerCase();
			}
			else
			{
				scanner.close();
				return "ERROR";
			}
			scanner.close();
		}
		else
		{
			scanner.close();
			return "ERROR";
		}
		
		/* Try to make a card from the input */
		UnoCard card = new UnoCard(cardType, cardColor);
		
		/* Invalid card */
		if (card.getType() == "ERROR")
		{
			return "ERROR";
		}
		
		boolean successful = this.turnOrder.getCurrentPlayer().playCard(card, this.discardPile);
		if (successful)
		{
			return card.getType();
		}
		else
		{
			return "ERROR";
		}
	}

	/* Get the dealer to deal out the cards to everyone */
	private void dealOutCards()
	{
		this.dealer.shuffleDeck(this.deck);
		
		/* 7 cards are dealt to each player */
		for (int i = 0; i < 7; ++i)
		{
			for (int j = 0; j < this.players.size(); ++j)
			{
				this.dealer.dealCardToPlayer(this.deck, this.discardPile, this.players.get(j));
			}
		}
	}
}