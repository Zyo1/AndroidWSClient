package matthieu.merrheim.android.client.entity;


import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class User {


    @Element
    private long id;


    @Element
    private String email;


    @Element
    private String name;



    public User() { }

    public User(long id) {
        this.id = id;
    }

    public User(String email, String name) {
        this.email = email;
        this.name = name;
    }



    public long getId() {
        return id;
    }

    public void setId(long value) {
        this.id = value;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String value) {
        this.email = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

}
