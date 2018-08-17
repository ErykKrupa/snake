package menu;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import java.io.*;
import java.util.Optional;
import static main.Main.*;

public class MenuController
{
    @FXML private void playClick() throws IOException
    {
        stage.setTitle("Difficulty");
        Parent parent = FXMLLoader.load(getClass().getResource("../difficulty/difficulty.fxml"));
        stage.setScene(new Scene(parent));
    }

    @FXML private void highScoresClick() throws IOException
    {
        stage.setTitle("High Scores");
        Pane pane = FXMLLoader.load(getClass().getResource("../highscores/highscores.fxml"));
        GridPane gridPane = new GridPane();
        for(int i = 1; i <=6; i++)
            if(i % 2 == 1) gridPane.getColumnConstraints().add(new ColumnConstraints(130));
            else gridPane.getColumnConstraints().add(new ColumnConstraints(50));
        Label[][] labels = new Label[6][10];
        for(int i = 0; i < 6; i += 2)
        {
            String filePath = "highscores\\highscores" + (i / 2 + 1) + ".txt";
            try(BufferedReader reader = new BufferedReader(new FileReader(filePath)))
            {
                for(int j = 0; j<10; j++)
                {
                    String result = reader.readLine();
                    if(result != null)
                    {
                        labels[i][j] = new Label(j + 1 + ". " + reader.readLine());
                        labels[i + 1][j] = new Label(result);
                    }
                    else
                    {
                        labels[i][j] = new Label(j + 1 + ". ....................");
                        labels[i + 1][j] = new Label("......");
                    }
                }
            }
            catch(FileNotFoundException ioe)
            {
                System.err.println("File not found.");
                for(int j = 0; j < 10; j++)
                {
                    labels[i][j] = new Label(j + 1 + ". ....................");
                    labels[i+1][j] = new Label("......");
                }
            }
            catch(IOException ioe)
            {
                System.err.println("IOException: " + ioe.getMessage());
            }
            for(int j = 0; j<10; j++)
            {
                labels[i][j].setFont(new Font("Monotype Corsiva", 18));
                labels[i+1][j].setFont(new Font("Monotype Corsiva", 18));
                GridPane.setConstraints(labels[i][j], i, j);
                GridPane.setConstraints(labels[i+1][j], i + 1, j);
                GridPane.setMargin(labels[i][j], new Insets(5, 10, 5, 10));
                GridPane.setMargin(labels[i + 1][j], new Insets(5, 10, 5, 10));
                gridPane.getChildren().addAll(labels[i][j], labels[i + 1][j]);
            }
        }

        Button button = new Button("OK");
        button.setFont(new Font("Monotype Corsiva", 18));
        button.setMaxSize(100, 20);
        button.setOnAction(event ->
        {
            try
            {
                stage.setTitle("Snake Game");
                Parent parent = FXMLLoader.load(getClass().getResource("../menu/menu.fxml"));
                stage.setScene(new Scene(parent, 540, 420));
                stage.show();
            }
            catch(IOException ioe)
            {
                System.err.println("IOException: " + ioe.getMessage());
            }
        });
        GridPane.setConstraints(button, 0, 10, 6, 1, HPos.CENTER, VPos.CENTER);
        GridPane.setMargin(button, new Insets(4));
        gridPane.getChildren().add(button);
        stage.setScene(new Scene(new VBox(pane, gridPane)));
    }

    @FXML private void creditClick()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Credit");
        alert.setHeaderText(null);
        alert.setContentText("Author: Eryk Krupa.");
        alert.showAndWait();
    }

    @FXML private void exitClick()
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure, that you want to exit?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK)
            System.exit(0);
    }
}
