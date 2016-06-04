package matthieu.merrheim.android.client.entity;


import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


@Root(name = "conversation")
public class Conversation {

    @Element
    private String id;

    @Element
    private String message;

    @Element
    private int nbVotes;


    public Conversation() {
    }

    public Conversation(String message) {
        this.message = message;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getNbVotes() {
        return nbVotes;
    }

    public void setNbVotes(int nbVotes) {
        this.nbVotes = nbVotes;
    }

    public void incrNbVotes(){
        this.nbVotes++;
    }

}