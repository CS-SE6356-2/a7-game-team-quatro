package test.models;

import models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class RoundTest {
    static int countLimit = 10;
    static int viewableLimit = 10;
    Round round;
    int viewableCalls = 0;
    int playerCalls = 0;

    class PlayerStub extends Player implements Pile {
        public void pushCard(Card c) {}
        public Card drawCard() { return new Card(); }
        public void discardCard() {}
        public void bid() {}
        public void splitDeck(Deck deck) {}
        public void giveCards(Player p) {}
        public<T> Turn<T> takeTurn(List<Turn<T>> hist) {
            assertTrue(hist.size() <= viewableLimit);
            return new Turn<T>(this, this, null);
        }
    }

    @BeforeEach
    void setup() {
        round = new Round<Integer>() {
            @Override
            public Player nextPlayer() {
                playerCalls++;
                if (playerCalls < countLimit) {
                    return new PlayerStub();
                } else {
                    return null;
                }
            }

            @Override
            public int numViewable() {
                viewableCalls++;
                return viewableLimit;
            }
        };
    }

    @Test
    void testHitLimit() {
        countLimit = 10;
        viewableLimit = 20;
        round.playRound();
        assertEquals(countLimit, viewableCalls);
        assertEquals(countLimit, playerCalls);
    }

    @Test
    void testViewableLimit() {
        countLimit = 20;
        viewableLimit = 10;
        round.playRound();
    }

}
