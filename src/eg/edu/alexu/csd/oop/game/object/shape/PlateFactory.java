package eg.edu.alexu.csd.oop.game.object.shape;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import eg.edu.alexu.csd.oop.game.Log;

public class PlateFactory implements AbstractFactory {
	private BufferedImage[][] plateImages;
	private String[] colors;
	private static String plateClassName = "eg.edu.alexu.csd.oop.game.object.shape.Plate";

	public PlateFactory() {
		try {
			BufferedImage red = ImageIO.read(new File("src/Images/redplate.png"));
			BufferedImage blue = ImageIO.read(new File("src/Images/blueplate.png"));
			BufferedImage green = ImageIO.read(new File("src/Images/greenplate.png"));
			plateImages = new BufferedImage[3][1];
			plateImages[0][0] = red;
			plateImages[1][0] = blue;
			plateImages[2][0] = green;
			colors = new String[] { "red", "blue", "green" };
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Shape getRandomShape() {
		int colorId = (int) (Math.random() * 3);
		Shape RandomPlate = DynamicLinkageShape.load(plateClassName, plateImages[colorId], colors[colorId]);
		Log.getLoggeer().info("Create "+ (String) RandomPlate.getName());
		return RandomPlate;
	}

}