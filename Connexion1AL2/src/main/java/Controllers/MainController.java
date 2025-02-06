package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MainController {
    @FXML
    private BorderPane mainPane;

    @FXML
    private void showUserManagement() { loadView("/Views/UserView.fxml"); }

    @FXML
    private void showNoteManagement() { loadView("/Views/NoteView.fxml"); }

    // Méthodes similaires pour les autres vues...

    private void loadView(String fxmlPath) {
        try {
            Parent view = FXMLLoader.load(getClass().getResource(fxmlPath));
            mainPane.setCenter(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}