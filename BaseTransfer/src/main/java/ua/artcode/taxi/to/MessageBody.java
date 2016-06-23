package ua.artcode.taxi.to;

/**
 * Created by serhii on 05.06.16.
 */
public class MessageBody {

    private String phone;
    private String pass;

    public MessageBody(String phone, String pass) {
        this.phone = phone;
        this.pass = pass;
    }

    public MessageBody() {
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
