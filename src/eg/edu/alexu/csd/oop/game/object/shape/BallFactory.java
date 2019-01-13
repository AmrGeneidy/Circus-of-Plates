package eg.edu.alexu.csd.oop.game.object.shape;

import eg.edu.alexu.csd.oop.game.Log;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BallFactory implements AbstractFactory {
    protected BufferedImage[][] ballImages;
    private String [] color;
	private static String ballClassName = "eg.edu.alexu.csd.oop.game.object.shape.Ball";


    public BallFactory() {
        try {
            BufferedImage red = ImageIO.read(new File("src/Images/redball2.png"));
            BufferedImage blue = ImageIO.read(new File("src/Images/blueball2.png"));
            BufferedImage green = ImageIO.read(new File("src/Images/greenball2.png"));
            ballImages = new BufferedImage[3][1];
            ballImages[0][0] = red;
            ballImages[1][0] = blue;
            ballImages[2][0] = green;
            color = new String[] { "red", "blue", "green" };
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Shape getRandomShape() {
    	int colorId = (int) (Math.random() * 3);
		Shape RandomBall = DynamicLinkageShape.load(ballClassName, ballImages[colorId], color[colorId]);
		Log.getLoggeer().info("Create "+ (String) RandomBall.getName());
        return RandomBall;
    }
}
