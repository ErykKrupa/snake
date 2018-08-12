package game;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Random;
import java.io.IOException;
import main.Main;
import difficulty.GameField;
import static difficulty.DifficultyController.*;

public class GameController
{
    public static ArrayList<GameField> snake;
    private boolean isSnakeStuffed = false;
    private static int score = 0;
    private static Random random = new Random();
    @FXML private Label scoreLabel;
    public static void scoreToZero()
    {
        score = 0;
    }
    private void removeTailSnake(GameField gf)
    {
        gf.setSnake(false);
        gf.setFill(Color.web("#00000000"));
    }
    private boolean setHeadSnake(GameField gf)
    {
        if(gf.getSnake() || gf.getWall())
        {
            gf.setFill(Color.web("#ff0000"));
            return false;
        }
        else
        {
            gf.setSnake(true);
            if(gf.getFood())
            {
                gf.setFood(false);
                eatFood();
            }
            gf.setFill(Color.web("#000000ff"));
            return true;
        }
    }
    private void eatFood()
    {
        scoreLabel.setText(Integer.toString(++score));
        GameField food;
        do food = gameFields[random.nextInt(16) + 1][random.nextInt(12) + 1];
        while (food.getSnake());
        food.setFood(true);
        food.setFill(Color.web("999999"));
        isSnakeStuffed = true;
    }
    private void movement(String direction)
    {
        if(!isSnakeStuffed)
            removeTailSnake(gameFields[snake.get(0).getCoord1()][snake.remove(0).getCoord2()]);
        isSnakeStuffed = false;
        int coord1 = snake.get(snake.size() - 1).getCoord1();
        int coord2 = snake.get(snake.size() - 1).getCoord2();
        switch(direction)
        {
            case "left": coord1--; break;
            case "right": coord1++; break;
            case "up": coord2--; break;
            case "down": coord2++; break;
        }
        snake.add(gameFields[coord1][coord2]);
        if(!setHeadSnake(gameFields[coord1][coord2]))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game Over");
            alert.setHeaderText(null);
            alert.setContentText("Game over! You score is " + score + ".");
            alert.showAndWait();
            handleMenuClick();
        }
    }
    @FXML private void handleLeftClick()
    {
        movement("left");
    }
    @FXML private void handleRightClick()
    {
        movement("right");
    }
    @FXML private void handleUpClick()
    {
        movement("up");
    }
    @FXML private void handleDownClick()
    {
        movement("down");
    }
    @FXML private void handleMenuClick()
    {
        Main.stage.setTitle("Snake Game");
        try
        {
            Parent parent = FXMLLoader.load(getClass().getResource("../menu/menu.fxml"));
            Scene menuScene = new Scene(parent, 540, 420);
            Main.stage.setScene(menuScene);
        }
        catch(IOException ignored){}
    }
    @FXML private void handlePauseClick()
    {
        System.out.println("Pause");
    }
}
