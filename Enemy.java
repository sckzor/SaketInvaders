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

public class Enemy extends Player {
	
	int SPEED = (score/5)+2;
	int stage = 0;
	static final double SIDE_STEP = 0.2;
	static final double DOWN_STEP = 0.1;
	static final int CONST_SPEED = 100;
	int currentCycle = 0;
	int coefficentOfSpeed;
	
	public Enemy(int posX, int posY, int size, int speed, Image image, Image deadImage) {
		super(posX, posY, size, image, deadImage);
		coefficentOfSpeed = speed;
	}
	
	public void update() {
		if(!destroyed) 
		{
			if(stage == 0)
			{
				posX += SIDE_STEP * coefficentOfSpeed;
				if(currentCycle == CONST_SPEED)
				{
					currentCycle = 0;
					stage = 1;
				}
				currentCycle += 1;
			}
			else if (stage == 1)
			{
				posY += DOWN_STEP * coefficentOfSpeed;
				if(currentCycle == CONST_SPEED)
				{
					currentCycle = 0;
					stage = 2;
				}
				currentCycle += 1;
			}
			else if (stage == 2)
			{
				posX -= SIDE_STEP * coefficentOfSpeed;
				if(currentCycle == CONST_SPEED)
				{
					currentCycle = 0;
					stage = 0;
				}
				currentCycle += 1;
			}	
		}
		if(posY > SpaceInvaders.HEIGHT) destroyed = true;
	}
}
