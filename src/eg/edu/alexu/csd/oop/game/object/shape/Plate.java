package eg.edu.alexu.csd.oop.game.object.shape;

import java.awt.image.BufferedImage;

import eg.edu.alexu.csd.oop.game.STATIC_VARS;
import eg.edu.alexu.csd.oop.game.object.GameObjectImp;
import eg.edu.alexu.csd.oop.game.object.movingStrategy.MovingPlatesOnSticks;
import eg.edu.alexu.csd.oop.game.object.movingStrategy.MovingVertical;
import eg.edu.alexu.csd.oop.game.object.shape.state.*;


public class Plate extends Shape {
    public Plate(BufferedImage[] spriteImages, String color) {
        super(spriteImages, color);
    }
    
    @Override
    public int getWidth() {
        return STATIC_VARS.PLATE_WIDTH;
    }

    @Override
    public int getHeight() {
        return STATIC_VARS.PLATE_HEIGHT;
    }

    @Override
    public Object getName() {
        return color + "Plate";
    }

	@Override
	public Shape clone() {
		Plate x = new Plate(getSpriteImages(), getColor());
		if (currentState.getState().equals(State.FREEFALL)) {
			x.currentState = new FreeFall(getX(), getY());
			
		}else if (currentState.getState().equals(State.ONLEFTSTICK)||currentState.getState().equals(State.ONRIGHTSTICK)) {
			x.currentState = new OnStickState(currentState.getState(), new MovingPlatesOnSticks(getX(), getY(), currentState.getState().equals(State.ONRIGHTSTICK), getWidth()), getWidth());
            x.movingStrategy = ((AbstractShapeState)x.currentState).getMovingStrategy();
        }else {
			x.currentState = new OutOfBoundries(new MovingVertical(getX(), getY()));
        }
		return x;
	}
}
