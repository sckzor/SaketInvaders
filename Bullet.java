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

public class Bullet 
{
		
	public boolean toRemove;

	double posX;
	double posY;
	double speed = 10;
	static final int size = 6;
		
	public Bullet(double posX, double posY) {
		this.posX = posX;
		this.posY = posY;
	}

	public void update() {
		posY-=speed;
	}
	

	public void draw(GraphicsContext gc) {
		gc.setFill(Color.RED);
		gc.fillOval(posX, posY, size, size);
	}
	
	public boolean collide(Player Rocket) {
		int distance = SpaceInvaders.distance(this.posX + size / 2, this.posY + size / 2, Rocket.posX + Rocket.size / 2, Rocket.posY + Rocket.size / 2);
		return distance  < Rocket.size / 2 + size / 2;
	} 
		
		
}
