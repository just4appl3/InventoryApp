package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import java.io.IOException;

public class MainPageController {
    //GENERAL USE
    @FXML
    private final Stage stage = new Stage();

    @FXML
    private void publicButoonAction() throws IOException {
        Parent LoginAdminParent = FXMLLoader.load(getClass().getResource("public.fxml"));
        Main_App.window.getScene().setRoot(LoginAdminParent);
    }
    @FXML
    private void loginButoonAction() throws IOException {
        Parent LoginAdminParent = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Main_App.window.getScene().setRoot(LoginAdminParent);
    }

    @FXML
    private void quitButtonAction() {
        if (ConfirmBox.display("Confirm Quit", "Are you sure you want to quit?")) Main_App.window.close();
        else stage.getScene().getWindow();
    }
}
