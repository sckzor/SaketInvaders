

all: SpaceInvaders.class Enemy.class Bullet.java Player.java

SpaceInvaders.class: SpaceInvaders.java
	javac --module-path /usr/share/openjfx/lib --add-modules javafx.swing,javafx.graphics,javafx.fxml,javafx.media,javafx.controls SpaceInvaders.java

Enemy.class: Enemy.java
	javac --module-path /usr/share/openjfx/lib --add-modules javafx.swing,javafx.graphics,javafx.fxml,javafx.media,javafx.controls Enemy.java
	
Bullet.class: Bullet.java
	javac --module-path /usr/share/openjfx/lib --add-modules javafx.swing,javafx.graphics,javafx.fxml,javafx.media,javafx.controls Bullet.java

Player.class: Player.java
	javac --module-path /usr/share/openjfx/lib --add-modules javafx.swing,javafx.graphics,javafx.fxml,javafx.media,javafx.controls Player.java


test: all
	java --module-path /usr/share/openjfx/lib --add-modules javafx.swing,javafx.graphics,javafx.fxml,javafx.media,javafx.controls SpaceInvaders

clean:
	rm -rf *.class
