package models;

import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import static java.util.Collections.unmodifiableList;
import java.util.List;

public abstract class Round<T> {

    private ArrayList<Turn<T>> record = new ArrayList<>();

    /**
     * should return null if the round is over
     */
    public abstract Player nextPlayer();

    /**
     * returns the number of cards played in the last round a given player
     * should be able to see in order to inform their next turn
     */
    public abstract int numViewable();


    /**
     * retrieves the last numViewable() turns.
     */
    @NotNull
    private List<Turn<T>> getRecord() {
        int sz = record.size();
        int start = Integer.max(sz - numViewable(), 0);
        return unmodifiableList(record.subList(sz - start, sz));
    }

    // Each of the abstract functions should be called exactly once per player
    // turn based on the default implementation
    private boolean takeTurn() {
        Player next = nextPlayer();
        if (next != null) record.add(next.takeTurn(getRecord()));
        return next != null;
    }

    public List<Turn<T>> playRound() {
        while (takeTurn());
        return record.subList(0, record.size());
    }
}
