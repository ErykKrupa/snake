package difficulty;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.io.IOException;
import java.util.ArrayList;
import main.Main;
import game.GameController;
import game.GameField;
import static main.Main.*;
import static game.GameController.*;

public class DifficultyController
{
    private static GameController gameController = new GameController();
    private static Label difficultyLabel = new Label();
    private static Label pointsLabel = new Label("Points:");
    public static Label scoreLabel = new Label();

    private void game()
    {
        stage.setTitle("Snake");
        VBox vbox = new VBox();
        GridPane gridPane = new GridPane();
        Rectangle[][] rectangles = new Rectangle[18][14];
        for(int i = 0; i <rectangles.length ; i++)
        {
            for (int j = 0; j < rectangles[i].length; j++)
            {
                rectangles[i][j] = new Rectangle(30, 30);
                if (i == 0 || i == 17 || j == 0 || j == 13)
                {
                    rectangles[i][j].setFill(Color.web("#006633"));
                    gameFields[i][j] = new GameField(15, i, j,true);
                }
                else
                {
                    if ((i + j) % 2 == 0) rectangles[i][j].setFill(Color.web("#19a56f45"));
                    else rectangles[i][j].setFill(Color.web("#19a56f6f"));
                    gameFields[i][j] = new GameField(15, i, j, false);
                }
                GridPane.setConstraints(rectangles[i][j], i, j);
                GridPane.setConstraints(gameFields[i][j], i, j);
                gridPane.getChildren().addAll(rectangles[i][j], gameFields[i][j]);
            }
        }
        difficultyLabel.relocate(50, 4);
        difficultyLabel.setAlignment(Pos.CENTER);
        difficultyLabel.setFont(new Font("Monotype Corsiva", 23));
        pointsLabel.relocate(406, 4);
        pointsLabel.setFont(new Font("Monotype Corsiva", 23));
        scoreLabel.setText("0");
        scoreLabel.relocate(470, 5);
        scoreLabel.setFont(new Font("Monotype Corsiva", 23));
        Pane pane = new Pane(difficultyLabel, pointsLabel, scoreLabel);
        pane.setPrefSize(540, 40);
        pane.setBackground(new Background(new BackgroundFill(Color.web("#006633df"), CornerRadii.EMPTY, Insets.EMPTY)));
        vbox.getChildren().addAll(gridPane, pane);
        Scene scene = new Scene(vbox);
        stage.setScene(scene);
        snake  = new ArrayList<>();
        for(int i = 3; i <= 5; i++)
        {
            snake.add(gameFields[i][7]);
            gameFields[i][7].setSnake(true);
            gameFields[i][7].setFill(Color.web("#000000"));
        }
        gameFields[13][7].setFood(true);
        gameFields[13][7].setFill(Color.web("#999999"));
        KeyCombination kLeft = new KeyCodeCombination(KeyCode.LEFT);
        KeyCombination kRight = new KeyCodeCombination(KeyCode.RIGHT);
        KeyCombination kUp = new KeyCodeCombination(KeyCode.UP);
        KeyCombination kDown = new KeyCodeCombination(KeyCode.DOWN);
        KeyCombination kSpace = new KeyCodeCombination(KeyCode.SPACE);
        KeyCombination kEscape = new KeyCodeCombination(KeyCode.ESCAPE);
        Runnable rLeft = ()-> gameController.setDirection("left");
        Runnable rRight = ()-> gameController.setDirection("right");
        Runnable rUp = ()-> gameController.setDirection("up");
        Runnable rDown = ()-> gameController.setDirection("down");
        Runnable rSpace = () -> System.out.println("here will be pause");
        Runnable rEscape = () -> System.out.println("here will be back to menu");
        scene.getAccelerators().put(kLeft, rLeft);
        scene.getAccelerators().put(kRight, rRight);
        scene.getAccelerators().put(kUp, rUp);
        scene.getAccelerators().put(kDown, rDown);
        scene.getAccelerators().put(kSpace, rSpace);
        scene.getAccelerators().put(kEscape, rEscape);
        new Thread(new GameController()).start();
    }

    @FXML private void handleEasyClick()
    {
        difficultyLabel.setText("Difficulty: Easy");
        setTime(300);
        game();
    }

    @FXML private void handleMediumClick()
    {
        difficultyLabel.setText("Difficulty: Medium");
        setTime(200);
        game();
    }

    @FXML private void handleHardClick()
    {
        difficultyLabel.setText("Difficulty: Hard");
        setTime(100);
        game();
    }

    @FXML private void handleMenuClick() throws IOException
    {
        Main.stage.setTitle("Snake Game");
        Parent parent = FXMLLoader.load(getClass().getResource("../menu/menu.fxml"));
        Scene menuScene = new Scene(parent, 540, 420);
        Main.stage.setScene(menuScene);
    }
}