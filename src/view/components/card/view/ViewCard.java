package gui.components.card.view;

import gui.components.ViewComponent;
import gui.components.card.model.CardNumber;
import gui.components.card.model.Suit;
import gui.components.imageloaders.CardImageLoader;
import gui.views.interfaces.Renderable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import javax.smartcardio.Card;

public class ViewCard extends ViewComponent {
    private Suit suit;
    private CardNumber number;
    private Image img;

    public ViewCard(Suit suit, int number) {
        this.suit = suit;
        this.number = new CardNumber(number);

        img = CardImageLoader.LoadCardImage(suit, this.number);

        x = y = 0;
    }

    public ViewCard(Suit suit, int number, int x, int y) {
        super(x, y);
        this.suit = suit;
        this.number = new CardNumber(number);

        img = CardImageLoader.LoadCardImage(suit, this.number);
    }

    @Override
    public void tick() {
        // nothing for now
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }
}
