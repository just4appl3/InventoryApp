package Classes;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.bson.Document;
import sample.AlertBox;

import java.util.regex.Pattern;

//clasa a fost scrisa pentru a separa functiile statice folosite pe tot parcursul programului
public class DuplicateFunc {

    public ObservableList<Item> duplicate = FXCollections.observableArrayList();
    public ObservableList<User> duplicateU = FXCollections.observableArrayList();
    public MongoCursor<Document> cursor;

    //functie de verificare login. mai exact, compara datele scrise la autentificare
    //cu datele existente in baza de date
    // in cazul in care nu exista o sa se declanseze o fereastra cu mesaj de eroare
    public static boolean verifyLogin(Document uDB, MongoCollection<Document> coll, String message, String title) {
        Document found = coll.find(uDB).first();
        if (found != null) {
            return true;
        } else {
            return AlertBox.display(title, message);
        }
    }

    //verificare daca adresa de mail se incadreaza in standardele specifice (contine @, de ex. yahoo.com)
    public static boolean isValidMail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    //verifica la inregistrare daca parola are litera mare, numere, caracter special si cel putin 8 caractere
    public static boolean isValid(String passwordhere) {

        Pattern specailCharPatten = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Pattern UpperCasePatten = Pattern.compile("[A-Z ]");
        Pattern lowerCasePatten = Pattern.compile("[a-z ]");
        Pattern digitCasePatten = Pattern.compile("[0-9 ]");
        boolean flag = true;
        if (passwordhere.length() < 8 || !(specailCharPatten.matcher(passwordhere).find())
                || !(UpperCasePatten.matcher(passwordhere).find()) || !(lowerCasePatten.matcher(passwordhere).find())
                || !(digitCasePatten.matcher(passwordhere).find()))
            flag = false;

        return flag;
    }

    //folosita pentru a afisa in TableView item-ele din baza de date
    public ObservableList<Item> getItems(MongoCollection coll) {
        try {
            cursor = coll.find().iterator();

            while (cursor.hasNext()) {
                Document doc = cursor.next();
                String name = doc.get("Name").toString();
                int code = Integer.parseInt(doc.get("Code").toString());
                int amount = Integer.parseInt(doc.get("Amount").toString());
                int price = Integer.parseInt(doc.get("Price").toString());
                duplicate.add(new Item(name, code, amount, price));
            }
        } finally {
            cursor.close();
        }

        return duplicate;
    }

    //folosita pentru a afisa in TableView User-ii din baza de date
    public ObservableList<User> getUsers(MongoCollection coll) {

        cursor = coll.find().iterator();
        while (cursor.hasNext()) {
            Document doc = cursor.next();
            String username = doc.get("Username").toString();
            String password = doc.get("Password").toString();
            String LastName = doc.get("Last Name").toString();
            String FirstName = doc.get("First Name").toString();
            String Mail_adress = doc.get("Mail adress").toString();
            int Age = Integer.parseInt(doc.get("Age").toString());
            duplicateU.add(new User(FirstName, LastName, Age, username, password, Mail_adress));
        }

        return duplicateU;
    }
}
