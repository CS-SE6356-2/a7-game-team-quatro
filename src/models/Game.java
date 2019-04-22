package models;

import java.util.ArrayList;

//handles program logic and updates models
//called when action done on the gui
public abstract class Game {
    Deck startDeck;
    ArrayList<Pile> piles;
    ArrayList<Player> players;
    ArrayList<Round> rounds;
    Round currentRound;

    public Game() {
        players = new ArrayList<Player>();
        piles = new ArrayList<Pile>();
        rounds = new ArrayList<Round>();
        currentRound = null;
    }

    abstract void dealCards();

    abstract int calcScore(Player p);

    abstract int evaluateScore();

    Pile getPile(int num) {
        if (num < piles.size()) {
            return piles.get(num);
        }
        return null;
    }

    Player getPlayer(int num) {
        if (num < piles.size()) {
            return players.get(num);
        }
        return null;
    }

}
