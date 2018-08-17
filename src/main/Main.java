package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application
{
    public static Stage stage = new Stage();
    @Override
    public void start(Stage stage) throws IOException
    {
        Main.stage.setTitle("Snake Game");
        Parent parent = FXMLLoader.load(getClass().getResource("../menu/menu.fxml"));
        Main.stage.setScene(new Scene(parent, 540, 420));
        Main.stage.setResizable(false);
        Main.stage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
