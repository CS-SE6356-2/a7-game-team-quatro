import java.util.*;

public class UnoGameInfo {
	public String currentPlayer;
	public LinkedList<String> playerNames;
	public LinkedList<LinkedList<UnoCard>> playerCards;
	public UnoCard discardPileTopCard;
	
	public UnoGameInfo() {
		playerNames = new LinkedList<String>();
		playerCards = new LinkedList<LinkedList<UnoCard>>();
	}
	
	// first line is the top car info: color and type separated by a space
	// second line is the name of the player whose turn it is
	//next lines are each player and their cards: THe player name on one line, and their cards on the next line in a comma separated list
	public String toString() {
		String info = discardPileTopCard.getColor() + " "+ discardPileTopCard.getType() +"\n" +currentPlayer;
		
		for(int i = 0; i < playerNames.size(); i++) {
			info += "\n" + playerNames.get(i) +"\n";
			for(int j = 0; j < playerCards.get(i).size(); j++) {
				info += (j==0?"":",")+ playerCards.get(i).get(j).getColor() +" "+ playerCards.get(i).get(j).getType();
			}
		}
		
		return info;
	}
}
