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

import main.Main;
import static main.Main.*;


public class DifficultyController
{
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
                if(i == 0 || i == 17 || j == 0 || j == 13)
                    rectangles[i][j].setFill(Color.web("#326647"));
                else if((i + j) % 2 == 0)
                    rectangles[i][j].setFill(Color.web("#19a56f45"));
                else rectangles[i][j].setFill(Color.web("#19a56f6f"));
                GridPane.setConstraints(rectangles[i][j], i, j);
                gridPane.getChildren().add(rectangles[i][j]);
            }
        }
        Pane pane;
        try
        {
            pane = FXMLLoader.load(getClass().getResource("../game/game.fxml"));
            pane.setBackground(new Background(new BackgroundFill(Color.web("#326647"), CornerRadii.EMPTY, Insets.EMPTY)));
            vbox.getChildren().addAll(gridPane, pane);
        }
        catch(IOException ignored){}
        Scene gameScene = new Scene(vbox);
        stage.setScene(gameScene);
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
