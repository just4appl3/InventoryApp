package Classes;

import DB.ConnectionDB;
import org.bson.Document;
import org.bson.conversions.Bson;

import static Classes.UserManager.getString;

//interfata este utilizata pentru a clarifica atributiile unui Admin si pentru a le separa de cele ale unui User
interface IAdmin {
    void AddItem(Item item);

    void DeleteItem(Item item);

    boolean findItem(Item item);

    String displayItem(Item item);

    void UpdateItem(Item item, Item item_up);

    void DeleteUser(User user);

    void AddUser(User user);

    void UpdateUser(User user, User user_up);

    boolean findUser(User user);

    String displayUser(User user);
}

public class AdminManager implements IAdmin {

    //variabila pentru a folosi functii care sunt la comun pentruAdmin si User
    ManagerDuplicate managerDuplicate = new ManagerDuplicate();

    //adaugare item in baza de date
    @Override
    public void AddItem(Item item) {
        Document d = new Document("Name", item.name)
                .append("Code", item.code)
                .append("Amount", item.amount)
                .append("Price", item.price);
        ConnectionDB.collectionItem.insertOne(d);
    }

    //stergere item din baza de date
    @Override
    public void DeleteItem(Item item) {
        Document d = new Document("Name", item.name)
                .append("Code", item.code)
                .append("Amount", item.amount)
                .append("Price", item.price);
        Document found = (Document) ConnectionDB.collectionItem.find(d).first();
        if (found != null) {
            ConnectionDB.collectionItem.deleteOne(d);
        }
    }

    //functie pentru a gasi un item in baza de date
    @Override
    public boolean findItem(Item item) {
        boolean flag = true;
        Document d = new Document("Name", item.name);
        if (ConnectionDB.collectionItem.find(d).first() == null)
            flag = false;
        return flag;
    }

    //afisare item intr-un frame
    @Override
    public String displayItem(Item item) {
        return getString(item);
    }

    //functii comuna cu User, se gasesc in clasa ManagerDuplicate
    @Override
    public void UpdateItem(Item item, Item item_up) {
        managerDuplicate.UpdateItem(item, item_up);
    }

    @Override
    public void DeleteUser(User user) {
        managerDuplicate.DeleteUser(user);
    }

    //adaugare User in baza de date
    @Override
    public void AddUser(User user) {
        Document d = new Document("First Name", user.getFirstName())
                .append("Last Name", user.getLastName())
                .append("Age", user.getAge())
                .append("Username", user.username)
                .append("Password", user.password)
                .append("Mail adress", user.getMailAdressUser());
        ConnectionDB.collectionLogin.insertOne(d);
    }

    //Modificare User in baza de date
    @Override
    public void UpdateUser(User user, User user_up) {
        Document query = new Document("Username", user.username);
        Document found = (Document) ConnectionDB.collectionLogin.find(query).first();

        if (found != null) {
            Bson updatedvalue = new Document("First Name", user_up.getFirstName())
                    .append("Last Name", user_up.getLastName())
                    .append("Age", user_up.getAge())
                    .append("Username", user_up.username)
                    .append("Password", user_up.password)
                    .append("Mail adress", user_up.getMailAdressUser());
            Bson updateoperation = new Document("$set", updatedvalue);
            ConnectionDB.collectionLogin.updateOne(found, updateoperation);
        }
    }

    @Override
    public boolean findUser(User user) {
        boolean flag = false;
        Document d = new Document("Username", user.username);
        if (ConnectionDB.collectionLogin.find(d).first() != null)
            flag = true;
        return flag;
    }

    @Override
    public String displayUser(User user) {
        Document d = new Document("Username", user.username);
        Document found = (Document) ConnectionDB.collectionLogin.find(d).first();
        if (found != null) {
            user.username = found.get("Username").toString();
            user.password = found.get("Password").toString();
            user.setMailAdressUser(found.get("Mail adress").toString());
            user.setFirstName(found.get("First Name").toString());
            user.setLastName(found.get("Last Name").toString());
            user.setAge(Integer.parseInt(found.get("Age").toString()));
        }
        return user.toString();
    }
}

//toate functiile de adaugare, modificare si stergere sunt scrise pentru specificul bazei de date folosite, MongoDB
