package eg.edu.alexu.csd.oop.game;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import eg.edu.alexu.csd.oop.game.object.IObserver;

import java.io.File;

public class MakeSounds implements IObserver {

	private final String fileName = "src/Images/pop.wav";
	@Override
    public void update() {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(fileName)));
            clip.start();
        } catch (Exception exc) {
            exc.printStackTrace(System.out);
        }
    }
	 public static void play(String fileName) {
	        try {
	            Clip clip = AudioSystem.getClip();
	            clip.open(AudioSystem.getAudioInputStream(new File(fileName)));
	            clip.start();
	        } catch (Exception exc) {
	            exc.printStackTrace(System.out);
	        }
	    }
}
