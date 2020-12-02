package sample;

import Classes.*;
import DB.ConnectionDB;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class publicController implements Initializable {
    //NECESSARY
    @FXML
    private Stage stage = new Stage();
    private Item item = new Item();
    private UserManager um = new UserManager();
    private DuplicateFunc t = new DuplicateFunc();


    @FXML
    private TextField searchInput = new TextField();
    @FXML
    private TextArea text = new TextArea();

    //TableView
    @FXML
    private TableView<Item> tableView;


    //SEARCH
    @FXML
    private void search() {
        if (searchInput.getText().equals("")) AlertBox.display("Alert", "You must complete all fields!");
        else {
            item.name = searchInput.getText().trim();
            if (!um.findItem(item)) text.setText("Item not found!");
            else text.setText("Your item is: \n" + um.displayItem(item));
        }
    }

    @FXML
    private void backButoonAction() throws IOException {
        Parent LoginAdminParent = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
        Main_App.window.getScene().setRoot(LoginAdminParent);
    }

    //QUIT BUTTON ACTION
    @FXML
    private Button quitButton = new Button();

    @FXML
    private void quitButtonAction() {
        if (ConfirmBox.display("Confirm Quit", "Are you sure you want to quit?")) stage.close();
        else stage.getScene().getWindow();
    }


    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableView.setItems(t.getItems(ConnectionDB.collectionItem));
    }
}
