package gui.components.imageloaders;

import javafx.scene.image.Image;

import java.io.File;
import java.io.FileNotFoundException;

public class ImageLoader {

    public static Image loadImageFromFileSystem(String path) throws FileNotFoundException {
        final FileNotFoundException FNFE = new FileNotFoundException(String.format("Unable to load resource at %s.", path));
        try {
            final String readPath = String.format("file:%s", path);

            if (!new File(path).exists())
                throw FNFE;
            return new Image(readPath);
        } catch (Exception e) {
            throw FNFE;
        }
    }

}
