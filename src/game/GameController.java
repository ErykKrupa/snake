package game;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Random;
import java.io.IOException;
import main.Main;
import static difficulty.DifficultyController.*;

public class GameController implements Runnable
{
    public static GameField[][] gameFields = new GameField[18][14];
    public static ArrayList<GameField> snake;
    private static boolean isSnakeStuffed = false;
    private static Random random = new Random();
    private static String direction = "right";
    private static String illegalDirection = "left";
    private static int score = 0;
    private static int coord1, coord2;
    private boolean running = true;
    private static int time;

    public static void setTime(int time)
    {
        GameController.time = time;
    }

    public void setDirection(String direction)
    {
        if(!direction.equals(illegalDirection))
            GameController.direction = direction;
    }

    private void terminate()
    {
        running = false;
    }

    public void run()
    {
        while(running)
        {
            try
            {
                Thread.sleep(time);
            }
            catch (InterruptedException ignore) {}
            Platform.runLater(() ->
            {
                if (running)
                {
                    if (!isSnakeStuffed)
                        removeTailSnake(gameFields[snake.get(0).getCoord1()][snake.remove(0).getCoord2()]);
                    isSnakeStuffed = false;
                    coord1 = snake.get(snake.size()-1).getCoord1();
                    coord2 = snake.get(snake.size()-1).getCoord2();
                    switch (direction)
                    {
                        case "left":
                            coord1--;
                            illegalDirection = "right";
                            break;
                        case "right":
                            coord1++;
                            illegalDirection = "left";
                            break;
                        case "up":
                            coord2--;
                            illegalDirection = "down";
                            break;
                        case "down":
                            coord2++;
                            illegalDirection = "up";
                            break;
                    }
                    snake.add(gameFields[coord1][coord2]);
                    if (!setHeadSnake(gameFields[coord1][coord2]))
                    {
                        terminate();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Game Over");
                        alert.setHeaderText(null);
                        alert.setContentText("Game over! You score is "+score+".");
                        alert.showAndWait();
                        Main.stage.setTitle("Snake Game");
                        try
                        {
                            Parent parent = FXMLLoader.load(getClass().getResource("../menu/menu.fxml"));
                            Scene menuScene = new Scene(parent, 540, 420);
                            Main.stage.setScene(menuScene);
                        }
                        catch (IOException ignored) {}
                    }
                }
            });
        }
        direction = "right";
        illegalDirection = "left";
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
}