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

public class SpaceInvaders extends Application {
	
	//variables
	static final int WIDTH = 800;
	static final int HEIGHT = 600;
	private static final int PLAYER_SIZE = 90;
	static final Image PLAYER_IMG = new Image("./Player.png"); 
	static final int PLAYER_SPEED = 2;
	private static final int ENEMY_SIZE = 60;
	static final Image ENEMY_IMG = new Image("./Enemy.png");
	static final Image DEAD_ENEMY_IMG = new Image("./DeadEnemy.png");
	
	int speed = 1;
	
	final int MAX_BULLETS = 3;
	boolean gameOver = false;
	private GraphicsContext gc;
	
	Player player;
	List<Bullet> shots;
	Enemy[][] enemys = new Enemy[3][5];

	//start
	public void start(Stage stage) throws Exception {
		Canvas canvas = new Canvas(WIDTH, HEIGHT);	
		gc = canvas.getGraphicsContext2D();
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), e -> run(gc)));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();

		canvas.setOnKeyPressed(e -> {
			player.direction = 0;
			if(!gameOver)
			{
				if (e.getCode().equals(KeyCode.A)) {
				    player.direction -= 1;
				}
				if (e.getCode().equals(KeyCode.D)) {
				    player.direction += 1;
				}
				if (e.getCode().equals(KeyCode.W)) {
					if(shots.size() < MAX_BULLETS) 
						shots.add(player.shoot());
				}
			}
			
			if(gameOver && e.getCode().equals(KeyCode.SPACE)) { 
				gameOver = false;
				setup();
			}
		});
		
		canvas.setOnKeyReleased(e -> {
			if (e.getCode().equals(KeyCode.A)) {
			    player.direction += 1;
			}
			if (e.getCode().equals(KeyCode.D)) {
			    player.direction -= 1;
			}
		});
		
		setup();
		stage.setScene(new Scene(new StackPane(canvas)));
		stage.setTitle("Saket INVADES!");
		stage.show();
		canvas.requestFocus();
		
	}

	//setup the game
	private void setup() {

		shots = new ArrayList<>();
		speed = 1;
		player = new Player(WIDTH / 2, HEIGHT - PLAYER_SIZE, PLAYER_SIZE, PLAYER_IMG, PLAYER_IMG);
		spawnGrid();
	}
	
	//run Graphics
	private void run(GraphicsContext gc) {
		gc.setFill(Color.grayRgb(20));
		gc.fillRect(0, 0, WIDTH, HEIGHT);
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setFont(Font.font(20));
		gc.setFill(Color.WHITE);
		gc.fillText("Sakets Killed: " + player.score, WIDTH/2,  20);
	
		
		if(gameOver) {
			gc.setFont(Font.font(30));
			gc.setFill(Color.WHITE);
			gc.fillText("Saket Envys You! \n You slayed Saket in cold blood " + player.score + " times\n Press the space bar to play again", WIDTH / 2, HEIGHT /2.5);
		//	return;
		}
	
		player.draw(gc);
		
		player.posX += player.direction * PLAYER_SPEED;
		
		for(int i = 0; i < enemys.length; i++)
		{
			for(int j = 0; j < enemys[0].length; j++)
			{
				enemys[i][j].update();
				if(!gameOver)
				{
					enemys[i][j].draw(gc);
				}
				if(player.collide(enemys[i][j]) && !player.destroyed)
				{
					player.kill();
				}
			}
		}
		
		for (int i = shots.size() - 1; i >=0 ; i--) {
			Bullet shot = shots.get(i);
			if(shot.posY < 0 || shot.toRemove)  { 
				shots.remove(i);
				continue;
			}
			shot.update();
			shot.draw(gc);
			for(int j = 0; j < enemys.length; j++)
			{
				for(int k = 0; k < enemys[0].length; k++)
				{
					if(shot.collide(enemys[j][k]) && !enemys[j][k].destroyed) {
						player.score++;
						enemys[j][k].kill();
						shot.toRemove = true;
					}
				}
			}
		}

		gameOver = player.destroyed;
		
		boolean allDead = true;
		
		for(int i = 0; i < enemys.length; i++)
		{
			for(int j = 0; j < enemys[0].length; j++)
			{
				if(!enemys[i][j].destroyed)
				{
					allDead = false;
				}
				if(enemys[i][j].posY > HEIGHT)
				{
					gameOver = true;
				}
			}
		}
		
		if(allDead)
		{
			spawnGrid();
		}
	}
	
	static int distance(double x1, double y1, double x2, double y2) {
		return (int) Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
	}
	
	private void spawnGrid()
	{
		speed += 2;
		int Xoffset = 50;
		int Yoffset = 20;
		
		for(int i = 0; i < enemys.length; i++)
		{
			for(int j = 0; j < enemys[0].length; j++)
			{
				enemys[i][j] = new Enemy(Xoffset, Yoffset, ENEMY_SIZE, speed, ENEMY_IMG, DEAD_ENEMY_IMG);
				Xoffset += (WIDTH - 100) / enemys[0].length;
			}
			
			Yoffset += (HEIGHT - 200) / enemys.length;
			Xoffset = 50;
		}
	}
	
	
	public static void main(String[] args) {
		launch();
	}
}
