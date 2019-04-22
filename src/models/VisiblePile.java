package models;

import java.util.Optional;
import java.util.List;

interface VisiblePile extends Pile {
    public Card peek();

    public Optional<List<Card>> getRange(int start, int end);
}