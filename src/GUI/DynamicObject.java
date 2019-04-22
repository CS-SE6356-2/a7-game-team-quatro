import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DynamicObject implements Renderable {
    int x = 0, y = 0;
    final int xmov = 5, ymov = 5;
    int xvel = 5, yvel = 5;

    public DynamicObject(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void render(GraphicsContext gc) {
        gc.setFill(Color.BLUE);
        gc.fillRect(x - 2, y - 2, 10, 10);

    }

    public void tick() {
        if (x == Main.WIDTH) xvel = -xmov;
        else if (x == 0) xvel = xmov;

        if (y == Main.HEIGHT) yvel = -yvel;
        else if (y == 0) yvel = ymov;

        x += xvel;
        y += yvel;
    }
}
