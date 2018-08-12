package difficulty;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import java.io.IOException;
import java.util.ArrayList;
import main.Main;
import static main.Main.*;
import static game.GameController.*;


public class DifficultyController
{
    public static GameField[][] gameFields = new GameField[18][14];
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
        Pane pane;
        try
        {
            pane = FXMLLoader.load(getClass().getResource("../game/game.fxml"));
            pane.setBackground(new Background(new BackgroundFill(Color.web("#006633df"), CornerRadii.EMPTY, Insets.EMPTY)));
            vbox.getChildren().addAll(gridPane, pane);
        }
        catch(IOException ignored){}
        stage.setScene(new Scene(vbox));
        snake  = new ArrayList<>();
        for(int i = 3; i <= 5; i++)
        {
            snake.add(gameFields[i][7]);
            gameFields[i][7].setSnake(true);
            gameFields[i][7].setFill(Color.web("#000000"));
        }
        gameFields[13][7].setFood(true);
        gameFields[13][7].setFill(Color.web("#999999"));
        scoreToZero();
    }
    @FXML private void handleEasyClick()
    {
        game();
    }
    @FXML private void handleMediumClick()
    {
        game();
    }
    @FXML private void handleHardClick()
    {
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
