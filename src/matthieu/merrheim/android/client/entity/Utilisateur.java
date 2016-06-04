package matthieu.merrheim.android.client.entity;



import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


@Root
public class Utilisateur {

    @Element
    private String email;
    @Element
    private String password;

    public Utilisateur() {
    }

    public Utilisateur(String email, String password) {

        this.email = email;
        this.password = password;
    }



    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }


}


