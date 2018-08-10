package game;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.util.ArrayList;
import java.io.IOException;
import main.Main;
import static difficulty.DifficultyController.*;

public class GameController
{
    private static ArrayList<String> snakeList;
    public static void setSnake()
    {
        snakeList = new ArrayList<>();
        for(int i = 3; i <= 5; i++)
        {
            snake[i][7].setSnake(true);
            snakeList.add(i+".7");
        }
    }
    private void movement(String direction)
    {
        String[] strings = snakeList.remove(0).split("\\.");
        snake[Integer.parseInt(strings[0])][Integer.parseInt(strings[1])].setSnake(false);
        strings = snakeList.get(snakeList.size()-1).split("\\.");
        int coord1 = Integer.parseInt(strings[0]);
        int coord2 = Integer.parseInt(strings[1]);
        switch(direction)
        {
            case "left":
                snakeList.add(coord1 - 1 + "." + coord2);
                snake[coord1 - 1][coord2].setSnake(true);
                break;
            case "right":
                snakeList.add(coord1 + 1 + "." + coord2);
                snake[coord1 + 1][coord2].setSnake(true);
                break;
            case "up":
                snakeList.add(coord1 + "." + (coord2 - 1));
                snake[coord1][coord2 - 1].setSnake(true);
                break;
            case "down":
                snakeList.add(coord1 + "." + (coord2 + 1));
                snake[coord1][coord2 + 1].setSnake(true);
                break;
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
    @FXML private void handleMenuClick() throws IOException
    {
        Main.stage.setTitle("Snake Game");
        Parent parent = FXMLLoader.load(getClass().getResource("../menu/menu.fxml"));
        Scene menuScene = new Scene(parent, 540, 420);
        Main.stage.setScene(menuScene);
    }
    @FXML private void handlePauseClick()
    {
        System.out.println("Pause");
    }
}
