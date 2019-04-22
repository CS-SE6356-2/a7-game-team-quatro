package gui.views.interfaces;

import javafx.scene.canvas.GraphicsContext;

public interface Renderable {
     void render(GraphicsContext gc);
     void tick();
}
