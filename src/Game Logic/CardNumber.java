public class CardNumber {
    private int value;

    public static final int ACE = 1;
    public static final int JACK = 11;
    public static final int QUEEN = 12;
    public static final int KING = 13;


    public CardNumber(int value) {
        this.value = value;
    }

    public static String NumberToString(CardNumber card) {
        int value = card.value;
        if (value == ACE)
            return "ace";
        else if (value >= 2 && value <= 10) return Integer.toString(value);
        else if (value == JACK) return "jack";
        else if (value == QUEEN) return "queen";
        else if (value == KING) return "king";
        else return null;
    }
}
