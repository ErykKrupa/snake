package menu;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;
import static main.Main.*;

public class MenuController
{
    @FXML private void handlePlayClick() throws Exception
    {
        stage.setTitle("Difficulty");
        Parent parent = FXMLLoader.load(getClass().getResource("../difficulty/difficulty.fxml"));
        Scene difficultyScene = new Scene(parent);
        stage.setScene(difficultyScene);
    }

    @FXML private void handleCreditClick()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Credit");
        alert.setHeaderText(null);
        alert.setContentText("Author: Eryk Krupa.");
        alert.showAndWait();
    }

    @FXML private void handleExitClick()
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure, that you want to exit?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK)
            System.exit(0);
    }
}
