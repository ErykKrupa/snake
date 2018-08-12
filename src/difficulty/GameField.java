package difficulty;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class GameField extends Circle
{
    private boolean isSnake = false, isFood = false, isWall = false;
    private int coord1, coord2;

    GameField(double radius, int coord1, int coord2, boolean wall)
    {
        super(radius, Color.web("#00000000"));
        this.coord1 = coord1;
        this.coord2 = coord2;
        if(wall) isWall = true;
    }

    public boolean getSnake()
    {
        return isSnake;
    }

    public void setSnake(boolean snake)
    {
        isSnake = snake;
    }

    public boolean getFood()
    {
        return isFood;
    }

    public void setFood(boolean food)
    {
        isFood = food;
    }

    public boolean getWall()
    {
        return isWall;
    }

    public int getCoord1()
    {
        return coord1;
    }

    public int getCoord2()
    {
        return coord2;
    }


}
