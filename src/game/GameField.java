package game;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class GameField extends Circle
{
    private boolean isSnake = false, isFood = false, isWall = false;
    private int coord1, coord2;

    public GameField(double radius, int coord1, int coord2, boolean wall)
    {
        super(radius, Color.web("#00000000"));
        this.coord1 = coord1;
        this.coord2 = coord2;
        if(wall) isWall = true;
    }

    boolean getSnake()
    {
        return isSnake;
    }

    public void setSnake(boolean snake)
    {
        isSnake = snake;
    }

    boolean getFood()
    {
        return isFood;
    }

    public void setFood(boolean food)
    {
        isFood = food;
    }

    boolean getWall()
    {
        return isWall;
    }

    int getCoord1()
    {
        return coord1;
    }

    int getCoord2()
    {
        return coord2;
    }
}