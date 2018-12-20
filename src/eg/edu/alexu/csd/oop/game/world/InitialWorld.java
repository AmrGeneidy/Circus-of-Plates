package eg.edu.alexu.csd.oop.game.world;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import eg.edu.alexu.csd.oop.game.GameObject;
import eg.edu.alexu.csd.oop.game.World;
import eg.edu.alexu.csd.oop.game.object.GameObjectImp;
import eg.edu.alexu.csd.oop.game.object.Clown;
import eg.edu.alexu.csd.oop.game.object.Plate;

public class InitialWorld implements World {
    private List<GameObject> constantObjects;
    private List<GameObject> movableObjects;
    private List<GameObject> controlableObjects;
    private int width;
    private int height;
    private int speed;
    private int rightMaxY;
    private int leftMaxY;
    public static BufferedImage img;

    static {
        try {
            img = ImageIO.read(new File("src/Images/background.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public InitialWorld(int width, int height, int speed) {
        this.width = width;
        this.height = height;
        this.speed = speed;
        constantObjects = new ArrayList<>();
        constantObjects.add(new GameObjectImp(img.getWidth(), img.getHeight()) {

            @Override
            public BufferedImage[] getSpriteImages() {
                setVisible(true);
                BufferedImage[] stage = new BufferedImage[1];
                stage[0] = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
                AffineTransform at = new AffineTransform();
                AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
                stage[0] = scaleOp.filter(img, stage[0]);
                return stage;
            }


        });
        movableObjects = new ArrayList<>();
        controlableObjects = new ArrayList<>();
        controlableObjects.add(new Clown());
        leftMaxY = rightMaxY = img.getHeight() - Clown.img.getHeight();
    }

    @Override
    public List<GameObject> getConstantObjects() {
        return constantObjects;
    }

    @Override
    public List<GameObject> getMovableObjects() {
        // TODO Auto-generated method stub
        return movableObjects;
    }

    @Override
    public List<GameObject> getControlableObjects() {
        // TODO Auto-generated method stub
        return controlableObjects;
    }

    @Override
    public int getWidth() {
        // TODO Auto-generated method stub
        return width;
    }

    @Override
    public int getHeight() {
        // TODO Auto-generated method stub
        return height;
    }

    @Override
    public boolean refresh() {
        movableObjects.add(new Plate((int) (Math.random() * img.getWidth()), 10));
        int centerOfPlate;
        int centerOfLeftStick = controlableObjects.get(0).getX() + 20;
        int centerOfRightStick = controlableObjects.get(0).getX() + controlableObjects.get(0).getWidth() - 20;

        for (int i = 0; i < movableObjects.size(); i++) {
            GameObject temp = movableObjects.get(i);
            temp.setY(temp.getY() + 10);
            centerOfPlate = temp.getX() + temp.getWidth() / 2;
            if (temp.getY() >= img.getHeight()) {
                movableObjects.remove(i);
                continue;
            }
            if (Math.abs(centerOfPlate - centerOfLeftStick) <= 20 && Math.abs(leftMaxY - temp.getY()) <= 5) {
                leftMaxY -= 10;
                movableObjects.remove(i);
                ((Plate) temp).setLeft();
                temp.setX(centerOfLeftStick - 25);
                controlableObjects.add(temp);
                continue;
            }
            if (Math.abs(centerOfPlate - centerOfRightStick) <= 20
                    && Math.abs(rightMaxY - temp.getY()) <= 5) {
                rightMaxY -= 10;
                movableObjects.remove(i);
                ((Plate) temp).setRight();
                temp.setX(centerOfRightStick - 25);
                controlableObjects.add(temp);
                continue;
            }
        }
        return true;
    }

    @Override
    public String getStatus() {
        // TODO Auto-generated method stub
        return "Score = " + 0 + "   |   Time = 0";
    }

    @Override
    public int getSpeed() {
        // TODO Auto-generated method stub
        return 30;
    }

    @Override
    public int getControlSpeed() {
        // TODO Auto-generated method stub
        return 10;
    }

}
