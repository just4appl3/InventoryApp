package Classes;
//clasa Admin este folosita pentru drepturile de administrare ca Admin, adica se pot manipula resursele umane cat si obiectele
public class Admin extends Person {

    //datele de mai jos sunt datele pe care le-am rezut necesare pentru o astfel de persoana
    public int loginSerial;
    public String adminID;
    public String password;
    private String mailAdress;

    //constructor
    public Admin(String firstName, String lastName, int age, int loginSerial, String adminID, String password, String mail_adress) {
        super(firstName, lastName, age);
        this.loginSerial = loginSerial;
        this.adminID = adminID;
        this.password = password;
        this.mailAdress = mail_adress;
    }

    //constructor
    public Admin() {

    }

    public String getMailAdress() {
        return mailAdress;
    }

    public void setMailAdress(String mailAdress) {
        this.mailAdress = mailAdress;
    }

}
