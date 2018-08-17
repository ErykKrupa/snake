package game;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import static main.Main.stage;
import static difficulty.DifficultyController.*;

public class GameController implements Runnable
{
    public static GameField[][] gameFields = new GameField[18][14];
    public static ArrayList<GameField> snake;
    private static String name;
    private static int time;
    private static int level;
    private static int coord1, coord2;
    private static String direction = "right";
    private static String illegalDirection = "left";
    private static Random random = new Random();
    private static boolean isSnakeStuffed = false;
    private static int score = 0;
    private boolean running = true;
    private boolean sleeping = false;

    public void run()
    {
        while(running)
        {
            try
            {
                Thread.sleep(time);
            }
            catch (InterruptedException ie)
            {
                System.err.println("InterruptedException: " + ie.getMessage());
            }
            while(sleeping)
            {
                try
                {
                    Thread.sleep(10);
                }
                catch (InterruptedException ie)
                {
                    System.err.println("InterruptedException: " + ie.getMessage());
                }
            }
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
                        String filePath = "highscores\\highscores" + level + ".txt";
                        StringBuilder beforeResult = new StringBuilder();
                        StringBuilder afterResult = new StringBuilder();
                        try(BufferedReader reader = new BufferedReader(new FileReader(filePath)))
                        {
                            String line = reader.readLine();
                            while(line != null)
                            {
                                int result = Integer.parseInt(line);
                                if (result >= score)
                                {
                                    beforeResult.append(result).append(System.lineSeparator());
                                    beforeResult.append(reader.readLine()).append(System.lineSeparator());
                                }
                                else
                                {
                                    afterResult.append(result).append(System.lineSeparator());
                                    afterResult.append(reader.readLine()).append(System.lineSeparator());
                                }
                                line = reader.readLine();
                            }
                        }
                        catch(IOException ioe)
                        {
                            System.err.println("IOException: " + ioe.getMessage());
                        }
                        try(FileWriter writer = new FileWriter(filePath))
                        {
                            writer.write(beforeResult + Integer.toString(score) + System.lineSeparator()
                                    + name + System.lineSeparator() + afterResult);
                        }
                        catch(IOException ioe)
                        {
                            System.err.println("IOException: " + ioe.getMessage());
                        }
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Game Over");
                        alert.setHeaderText(null);
                        alert.setContentText("Game over! Your score is " + score + ".");
                        alert.showAndWait();
                        stage.setTitle("Snake Game");
                        try
                        {
                            Parent parent = FXMLLoader.load(getClass().getResource("../menu/menu.fxml"));
                            stage.setScene(new Scene(parent, 540, 420));
                        }
                        catch (IOException ioe)
                        {
                            System.err.println("IOException: " + ioe.getMessage());
                        }
                    }
                }
            });
        }
        direction = "right";
        illegalDirection = "left";
        score = 0;
    }

    public static void setName(String name)
    {
        GameController.name = name;
    }

    public static void setTime(int time)
    {
        GameController.time = time;
    }

    public static void setLevel(int level)
    {
        GameController.level = level;
    }

    public void setDirection(String direction)
    {
        if(!direction.equals(illegalDirection))
        {
            GameController.direction = direction;
            sleeping = false;
            pauseLabel.setVisible(false);
        }
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
        isSnakeStuffed = true;
        scoreLabel.setText(Integer.toString(++score));
        GameField food;
        do food = gameFields[random.nextInt(16) + 1][random.nextInt(12) + 1];
        while (food.getSnake());
        food.setFood(true);
        food.setFill(Color.web("999999"));
    }

    public void terminate()
    {
        running = false;
    }

    public void changeSleeping()
    {
        sleeping = !sleeping;
        pauseLabel.setVisible(sleeping);
    }

    public boolean getSleeping()
    {
        return sleeping;
    }

}