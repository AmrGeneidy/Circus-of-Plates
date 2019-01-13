package eg.edu.alexu.csd.oop.game.object;

import java.util.ArrayList;
import java.util.List;

import eg.edu.alexu.csd.oop.game.GameObject;
import eg.edu.alexu.csd.oop.game.Log;
import eg.edu.alexu.csd.oop.game.MakeSounds;
import eg.edu.alexu.csd.oop.game.World;
import eg.edu.alexu.csd.oop.game.object.shape.Shape;


public class Observable {
    private List<IObserver> observers = new ArrayList<>();
    private int score;
    private int counter;

   public Observable (IObserver s){
	   add(s);
   }
	public void add(IObserver w) {
        observers.add(w);
    }

    public void remove(IObserver w) {
        observers.remove(w);
    }

    public int getScore() {
        return this.score;
    }

    public boolean setScore(List<GameObject> list) {
        boolean isChanged = execute(list);
        if (isChanged) {
			notifyObservers();
		}
		return isChanged;
    }

    public boolean execute(List<GameObject> list) {
        // Iterator Pattern
        Iterator objectIterator = new ObjectIterator(list);
        if (list.size() == 0) return false;
        int counter = 1;
        Shape firstElement = (Shape) objectIterator.next();
        String currentColor = firstElement.getColor();

        while (objectIterator.hasNext()) {
            Shape object = (Shape) objectIterator.next();
            if (object.getColor().equals(currentColor)) counter++;
            else break;
        }
        if (counter == this.counter) {
    		Log.getLoggeer().info("Player got point");
            return true;
        }
        return false;
    }

    public void notifyObservers() {
        for (IObserver w : observers) {
        		w.update();
        }
    }
    
    public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

}
