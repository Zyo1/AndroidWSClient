package matthieu.merrheim.android.client.entity;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


@Root(name = "serverresponse")
public class ServerResponse {
    @Element
    private String serverResponse;

    @Element
    private User user;

    @Element
    private boolean exist;


    public ServerResponse() {
    }

    public ServerResponse(String serverResponse, User user, boolean exist) {
        this.serverResponse = serverResponse;
        this.user = user;
        this.exist = exist;
    }

    public String getServerResponse() {
        return serverResponse;
    }

    public void setServerResponse(String serverResponse) {
        this.serverResponse = serverResponse;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean getExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }



}
