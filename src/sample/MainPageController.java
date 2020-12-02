package sample;

import com.sun.tools.javac.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

import javax.swing.text.html.StyleSheet;
import java.io.IOException;

public class MainPageController {
    //GENERAL USE
    @FXML
    private Stage stage = new Stage();

    @FXML
    private Button quitButton = new Button();

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
