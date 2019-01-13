package eg.edu.alexu.csd.oop.game.world;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import eg.edu.alexu.csd.oop.game.GameObject;
import eg.edu.alexu.csd.oop.game.STATIC_VARS;
import eg.edu.alexu.csd.oop.game.Snapshot;
import eg.edu.alexu.csd.oop.game.World;
import eg.edu.alexu.csd.oop.game.object.*;
import eg.edu.alexu.csd.oop.game.object.shape.AbstractFactory;
import eg.edu.alexu.csd.oop.game.object.shape.BallFactory;
import eg.edu.alexu.csd.oop.game.object.shape.PlateFactory;
import eg.edu.alexu.csd.oop.game.object.shape.Shape;
import eg.edu.alexu.csd.oop.game.object.shape.state.State;
import eg.edu.alexu.csd.oop.game.world.levels.LevelDifficulty;

public class InitialWorld implements World, IObserver {
    private List<GameObject> constantObjects;
    private List<GameObject> movableObjects;
    private List<GameObject> controlableObjects;
    private List<GameObject> onRightStick;
    private List<GameObject> onLeftStick;

    private Observable observable;
    private int width;
    private int height;
    private LevelDifficulty difficulty;
    private int score;
    private long time;

    public static BufferedImage img;
    private AbstractFactory plateFactory;
    private AbstractFactory ballFactory;

    static {
        try {
            img = ImageIO.read(new File("src/Images/background.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public InitialWorld(int width, int height, LevelDifficulty difficulty, Observable observable) {
        time = System.currentTimeMillis();
        this.observable = observable;
        this.observable.add(this);
        this.width = width;
        this.height = height;
        this.difficulty = difficulty;
        observable.setCounter(difficulty.getNumOfShapesNeededToScore());
        constantObjects = new ArrayList<>();
        BufferedImage[] backGround = new BufferedImage[1];
        backGround[0] = img;
        constantObjects.add(new Background(backGround));
        movableObjects = new ArrayList<>();
        controlableObjects = new ArrayList<>();
        onLeftStick = new ArrayList<>();
        onRightStick = new ArrayList<>();
        controlableObjects.add(Clown.getClown());
        Clown.getClown().setLeftMaxY(img.getHeight() - Clown.getClown().getHeight());
        Clown.getClown().setRightMaxY(img.getHeight() - Clown.getClown().getHeight());
        plateFactory = new PlateFactory();
        ballFactory = new BallFactory();

    }

    @Override
    public List<GameObject> getConstantObjects() {
        return constantObjects;
    }

    @Override
    public List<GameObject> getMovableObjects() {
        return movableObjects;
    }

    @Override
    public List<GameObject> getControlableObjects() {
        return controlableObjects;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public boolean refresh() {
        if (difficulty.getMoreShapes()) {
            if ((int) (Math.random() * 2) == 1) movableObjects.add(plateFactory.getRandomShape());
            else movableObjects.add(ballFactory.getRandomShape());
        }
        for (int i = 0; i < movableObjects.size(); i++) {
            Shape tmp = (Shape) movableObjects.get(i);
            State state = tmp.goNextState();
            if (state.equals(State.OUTOFBOUNDRIES)) {
                movableObjects.remove(i);
                continue;
            } else if (state.equals(State.ONLEFTSTICK)) {
                controlableObjects.add(movableObjects.remove(i));
                onLeftStick.add(tmp);
                if (observable.setScore(onLeftStick)) {
                    for (int j = 0; j < difficulty.getNumOfShapesNeededToScore(); j++) {
                        GameObject objectToRemove = onLeftStick.remove(onLeftStick.size() - 1);
                        controlableObjects.remove(objectToRemove);
                        Clown.getClown().setLeftMaxY(Clown.getClown().getLeftMaxY() + objectToRemove.getHeight());
                    }
                }
            } else if (state.equals(State.ONRIGHTSTICK)) {
                controlableObjects.add(movableObjects.remove(i));
                onRightStick.add(tmp);
                if (observable.setScore(onRightStick)) {
                    for (int j = 0; j < difficulty.getNumOfShapesNeededToScore(); j++) {
                        GameObject objectToRemove = onRightStick.remove(onRightStick.size() - 1);
                        controlableObjects.remove(objectToRemove);
                        Clown.getClown().setRightMaxY(Clown.getClown().getRightMaxY() + objectToRemove.getHeight());
                    }
                }
            }
        }
        return !isGameOver();
    }

    @Override
    public String getStatus() {
    	return !isGameOver()?"Score = " + score + "   |   Time = "
    						+ (STATIC_VARS.TOTALTIME - (System.currentTimeMillis() - time) / 1000) : "Score = " + score;
    }

    @Override
    public int getSpeed() {
        return difficulty.getWroldSpeed();
    }

    @Override
    public int getControlSpeed() {
        return difficulty.getControlSpeed();
    }

    @Override
    public void update() {
        this.score++;
    }

    private boolean isGameOver() {
    	return Clown.getClown().getRightMaxY() <= 25 || Clown.getClown().getLeftMaxY() <= 25
    							|| (STATIC_VARS.TOTALTIME - (System.currentTimeMillis() - time) / 1000) <= 0;

    }

    public Snapshot getState() {
        Snapshot snapshot = new Snapshot();
        snapshot.setMovableObjects(movableObjects);
        snapshot.setOnRightStick(onRightStick);
        snapshot.setOnLeftStick(onLeftStick);
        snapshot.setControlableObjects(controlableObjects);
        snapshot.setTime((System.currentTimeMillis() - time));
        snapshot.setScore(score);
        return snapshot;
    }

    public void setState(Snapshot snapshot) {
        controlableObjects = snapshot.getControlableObjects();
        movableObjects = snapshot.getMovableObjects();
        onRightStick = snapshot.getOnRightStick();
        onLeftStick = snapshot.getOnLeftStick();
        time = System.currentTimeMillis() - snapshot.getTime();
        score = snapshot.getScore();
    }
}
