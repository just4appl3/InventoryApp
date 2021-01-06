package sample;

import Classes.*;
import DB.ConnectionDB;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AfterLoginUserController implements Initializable {

    //NECESSARY
    private final User user = new User();
    private final Item item = new Item();
    private final Item itemToUP = new Item();
    private final UserManager um = new UserManager();
    private final AdminManager am = new AdminManager();
    private final DuplicateFunc t = new DuplicateFunc();

    //TEXT
    @FXML
    private final TextField usernameInput = new TextField();
    @FXML
    private final TextField passwordInput = new TextField();
    @FXML
    private final TextField firstnameInput = new TextField();
    @FXML
    private final TextField lastnameInput = new TextField();
    @FXML
    private final TextField mailInput = new TextField();
    @FXML
    private final TextField ageInput = new TextField();


    //Update
    @FXML
    private final TextField nameInputUP = new TextField();
    @FXML
    private final TextField codeInputUP = new TextField();
    @FXML
    private final TextField amountInputUP = new TextField();
    @FXML
    private final TextField priceInputUP = new TextField();
    @FXML
    private final TextField codeInput_to_UP = new TextField();


    @FXML
    private final TextField searchInput = new TextField();
    @FXML
    private final TextArea text = new TextArea();
    @FXML
    private final TextArea text2 = new TextArea();

    //TableView
    @FXML
    private TableView<Item> tableView;

    static void UserAction(User user, TextField usernameInput, TextField passwordInput, TextField mailInput, TextField firstnameInput, TextField lastnameInput, TextField ageInput) {
        user.username = usernameInput.getText().trim();
        user.password = passwordInput.getText().trim();
        user.setMailAdressUser(mailInput.getText().trim());
        user.setFirstName(firstnameInput.getText().trim());
        user.setLastName(lastnameInput.getText().trim());
        user.setAge((Integer.parseInt(ageInput.getText().trim())));
    }

    //CLEAR
    @FXML
    private void clearButtonAction() {
        nameInputUP.clear();
        amountInputUP.clear();
        codeInputUP.clear();
        priceInputUP.clear();
    }

    //UPDATE
    @FXML
    private void updateItemButtonAction() {
        if (codeInput_to_UP.getText().equals(""))
            AlertBox.display("Alert", "You must complete the code to update!");
        else {
            try {
                itemToUP.code = Integer.parseInt(codeInput_to_UP.getText().trim());
                if (!um.findItembyCode(itemToUP)) AlertBox.display("Alert", "Item not found!");
                else {
                    if (nameInputUP.getText().equals("") || codeInputUP.getText().equals("") || amountInputUP.getText().equals("") || priceInputUP.getText().equals(""))
                        AlertBox.display("Alert", "You must complete all fields!");
                    else {
                        item.name = nameInputUP.getText().trim();
                        item.code = Integer.parseInt(codeInputUP.getText().trim());
                        item.amount = Integer.parseInt(amountInputUP.getText().trim());
                        item.price = Integer.parseInt(priceInputUP.getText().trim());
                        um.UpdateItem(itemToUP, item);
                        text2.setText("Item updated!");
                        tableView.getItems().clear();
                        tableView.setItems(t.getItems(ConnectionDB.collectionItem));
                    }
                }
            } catch (NumberFormatException ex) {
                AlertBox.display("Alert", "Error: "
                        + codeInput_to_UP.getText().trim().toUpperCase() + " \n or \n"
                        + codeInputUP.getText().trim().toUpperCase() + " \n or \n"
                        + amountInputUP.getText().trim().toUpperCase()
                        + "\n or \n" + priceInputUP.getText().trim().toUpperCase()
                        + " is not a number!");
            }
        }
    }

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

    //EXIT
    @FXML
    private void singOutButton() throws IOException {
        if (ConfirmBox.display("Alert!", " Are you sure you want to sing out?")) {
            Parent pane = FXMLLoader.load(getClass().getResource("sample.fxml"));
            Main_App.window.getScene().setRoot(pane);
        }
    }

    //DELETE
    @FXML
    private void deleteAccountButton() throws IOException {
        if (usernameInput.getText().equals("") || passwordInput.getText().equals("") || mailInput.getText().equals("") || firstnameInput.getText().equals("")
                || lastnameInput.getText().equals("") || ageInput.getText().equals(""))
            AlertBox.display("Alert", "To delete account you must complete all fields!");
        else {
            try {
                UserAction(user, usernameInput, passwordInput, mailInput, firstnameInput, lastnameInput, ageInput);
                if (DuplicateFunc.isValidMail(mailInput.getText().trim())) {
                    if (am.findUser(user)) {
                        if (ConfirmBox.display("Alert", "Are you sure you want to delete your account?")) {
                            um.DeleteUser(user);
                            AlertBox.display("Alert", "Account deleted!");
                            Parent LoginAdminParent = FXMLLoader.load(getClass().getResource("sample.fxml"));
                            Main_App.window.getScene().setRoot(LoginAdminParent);
                        }
                    } else {
                        AlertBox.display("Alert", "Account doesn't exist!");
                    }
                } else AlertBox.display("Alert", " Wrong mail address!");
            } catch (NumberFormatException ex) {
                AlertBox.display("Alert", "Error: " + ageInput.getText().trim().toUpperCase() + " is not a number!");
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableView.setItems(t.getItems(ConnectionDB.collectionItem));
        tableView.refresh();
    }
}
