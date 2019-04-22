package gui.views;

import gui.views.interfaces.Renderable;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class ViewHandler {
    private ArrayList<Renderable> drawObjs;

    public ViewHandler() {
        drawObjs = new ArrayList<>();
    }

    public void addRenderable(Renderable r) {
        drawObjs.add(r);
    }

    public boolean removeRenderable(Renderable r) {
        return drawObjs.remove(r);
    }

    public void tick() {
        for (Renderable r : drawObjs) r.tick();
    }


    public void render(GraphicsContext gc) {
        for (Renderable r : drawObjs) r.render(gc);
    }
}
