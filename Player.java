import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.input.KeyCode;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Player 
{

	int score = 0;
	int direction = 0;
	double posX;
	double posY;
	int size;
	boolean destroyed = false;
	Image img;
	Image deadImg;
	
	public Player(int posX, int posY, int size,  Image image,  Image deadImage) {
		this.posX = posX;
		this.posY = posY;
		this.size = size;
		img = image;
		deadImg = deadImage;
	}
	
	public Bullet shoot() {
		return new Bullet(posX + size / 2 - Bullet.size / 2, posY - Bullet.size);
	}
	
	public void draw(GraphicsContext gc) {
		if(!destroyed)
		{
			gc.drawImage(img, posX, posY, size, size);
		}
		else
		{
			gc.drawImage(deadImg, posX, posY, size, size);
		}
	}

	public boolean collide(Player other) {
		int d = SpaceInvaders.distance(this.posX + size / 2, this.posY + size /2,  other.posX + other.size / 2, other.posY + other.size / 2);
		return d < other.size / 2 + this.size / 2 ;
	}
	
	public void kill() {
		destroyed = true;
	}

}
