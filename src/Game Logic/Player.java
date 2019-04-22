import java.util.ArrayList;
import java.util.List;

public abstract class Player {
    ArrayList<Card> myHand;
    boolean dealer;
    int score;

    public Player() {
        myHand = new ArrayList<Card>();
        score = 0;
    }

    public abstract Card drawCard();

    public abstract void discardCard();

    public abstract void bid();

    public abstract void splitDeck(Deck deck);

    public abstract void giveCards(Player p);

    public abstract <T> Turn<T> takeTurn(List<Turn<T>> hist);

    public int getScore() {
        return score;
    }

    public ArrayList<Card> getHand() {
        return myHand;
    }

    public boolean isDealer() {
        return dealer;
    }

    private void setDealer(boolean dealer) {
        this.dealer = dealer;
    }


}
