package gui.components;

import gui.views.interfaces.Renderable;


public abstract class ViewComponent implements Renderable {
    protected int x, y;

    public ViewComponent() {
        x = y = 0;
    }

    public ViewComponent(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
