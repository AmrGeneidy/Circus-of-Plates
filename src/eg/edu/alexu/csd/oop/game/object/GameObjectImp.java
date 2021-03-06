package eg.edu.alexu.csd.oop.game.object;

import java.awt.image.BufferedImage;

import eg.edu.alexu.csd.oop.game.GameObject;
import eg.edu.alexu.csd.oop.game.object.movingStrategy.MovingHorizontal;
import eg.edu.alexu.csd.oop.game.object.movingStrategy.MovingStrategy;
import eg.edu.alexu.csd.oop.game.object.shape.Shape;

public class GameObjectImp implements GameObject {

    private boolean isVisible;
    protected BufferedImage[] spriteImages;
    protected MovingStrategy movingStrategy;

    public GameObjectImp(BufferedImage[] spriteImages) {
        this.spriteImages = spriteImages;
    }

    @Override
    public int getX() {
        return movingStrategy.getX();
    }

    @Override
    public void setX(int x) {
        movingStrategy.setX(x);
    }

    @Override
    public int getY() {
        return movingStrategy.getY();
    }

    @Override
    public void setY(int y) {
        movingStrategy.setY(y);

    }

    @Override
    public int getWidth() {
        return spriteImages[0].getWidth();
    }

    @Override
    public int getHeight() {
        return spriteImages[0].getHeight();
    }

    @Override
    public boolean isVisible() {
        return isVisible;
    }

    @Override
    public BufferedImage[] getSpriteImages() {
        return spriteImages;

    }

    @Override
    public Shape clone() {
        return null;
    }

    //TODO not in interface
    public void setVisible(boolean visible) {
        isVisible = visible;
    }



}
