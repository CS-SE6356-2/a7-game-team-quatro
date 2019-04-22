package gui.components.imageloaders;

import gui.components.card.model.CardNumber;
import gui.components.card.model.Suit;
import javafx.scene.image.Image;

import javax.smartcardio.Card;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class CardImageLoader extends ImageLoader {

    public static final String assetsFolder = "assets";
    public static final String pngFolder = "png";

    private CardImageLoader() {
    }

    public static Image LoadCardImage(Suit suit, CardNumber num) {
        final String imagePath = String.format("%s/%s/%s_of_%s.png", assetsFolder, pngFolder, CardNumber.NumberToString(num), suit.name().toLowerCase());
        try {
            return ImageLoader.loadImageFromFileSystem(imagePath);
        } catch (FileNotFoundException fnfe) {
            return null;
        }
    }

}
