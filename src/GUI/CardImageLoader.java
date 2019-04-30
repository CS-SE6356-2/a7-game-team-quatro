import javafx.scene.image.Image;
import java.io.FileNotFoundException;

public class CardImageLoader extends ImageLoader {

    public static final String assetsFolder = "assets";
    public static final String pngFolder = "png";

    private CardImageLoader() {
    }

    public static Image LoadCardImage(UnoCard card) {
        final String imagePath = String.format("%s/%s/%s_of_%s.png", assetsFolder, pngFolder, card.getType().toLowerCase(), card.getColor().toLowerCase());
        try {
            return ImageLoader.loadImageFromFileSystem(imagePath);
        } catch (FileNotFoundException fnfe) {
            System.out.print("File not found");
            return null;
        }
    }

    public static Image LoadCardImage(String align) {
        final String imagePath = String.format("%s/%s/%s.png", assetsFolder, pngFolder, align.toLowerCase());
        try {
            return ImageLoader.loadImageFromFileSystem(imagePath);
        } catch (FileNotFoundException fnfe) {
            System.out.print("File not found");
            return null;
        }
    }

}
