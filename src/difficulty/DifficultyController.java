package difficulty;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import java.io.IOException;
import java.util.ArrayList;
import game.*;
import static main.Main.*;
import static game.GameController.*;

public class DifficultyController
{
    private static GameController gameController = new GameController();
    private static Label difficultyLabel = new Label();
    public static Label pauseLabel = new Label("Pause");
    public static Label scoreLabel = new Label();
    @FXML private TextField nameTextField = new TextField();

    @FXML private void easyClick()
    {
        checkName("Easy", 300, 1);
    }

    @FXML private void mediumClick()
    {
        checkName("Medium", 200, 2);
    }

    @FXML private void hardClick()
    {
        checkName("Hard", 100, 3);
    }

    private void checkName(String difficulty, int time, int level)
    {
        if(nameTextField.getText().equals(""))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Firstly set your name.");
            alert.showAndWait();
        }
        else
        {
            setName(nameTextField.getText());
            difficultyLabel.setText("Difficulty: " + difficulty);
            setTime(time);
            setLevel(level);
            game();
        }
    }

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
        Font font = new Font("Monotype Corsiva", 23);
        difficultyLabel.relocate(50, 4);
        difficultyLabel.setAlignment(Pos.CENTER);
        difficultyLabel.setFont(font);
        pauseLabel.relocate(275, 4);
        pauseLabel.setFont(Font.font("Monotype Corsiva", FontPosture.ITALIC, 23));
        pauseLabel.setVisible(false);
        Label pointsLabel = new Label("Points:");
        pointsLabel.relocate(406, 4);
        pointsLabel.setFont(font);
        scoreLabel.setText("0");
        scoreLabel.relocate(470, 5);
        scoreLabel.setFont(font);
        Pane pane = new Pane(difficultyLabel, pauseLabel, pointsLabel, scoreLabel);
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
        Runnable rSpace = () -> gameController.changeSleeping();
        Runnable rEscape = this::menu;
        scene.getAccelerators().put(kLeft, rLeft);
        scene.getAccelerators().put(kRight, rRight);
        scene.getAccelerators().put(kUp, rUp);
        scene.getAccelerators().put(kDown, rDown);
        scene.getAccelerators().put(kSpace, rSpace);
        scene.getAccelerators().put(kEscape, rEscape);
        Thread thread = new Thread(gameController = new GameController());
        thread.setDaemon(true);
        thread.start();
    }

    @FXML private void menu()
    {
        try
        {
            gameController.terminate();
            if(gameController.getSleeping()) gameController.changeSleeping();
            stage.setTitle("Snake Game");
            Parent parent = FXMLLoader.load(getClass().getResource("../menu/menu.fxml"));
            stage.setScene(new Scene(parent, 540, 420));
        }
        catch (IOException ioe)
        {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }
}