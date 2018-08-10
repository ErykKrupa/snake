package difficulty;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class SnakePart extends Circle
{
    private boolean isWall;
    private boolean isSnake;

    SnakePart(double radius, boolean wall)
    {
        super(radius, Color.web("#00000000"));
        if(wall) isWall = true;
    }

    public boolean isWall()
    {
        return isWall;
    }

    public boolean isSnake()
    {
        return isSnake;
    }

    public void setSnake(boolean snake)
    {
        isSnake = snake;
        if(isSnake) setFill(Color.web("#000000ff"));
        else setFill(Color.web("#00000000"));
    }

}
