package matthieu.merrheim.android.client.entity;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;


@Root(name = "conversations")
public class ConversationList {

    @ElementList(inline = true)
    private List<Conversation> conversation;

    public ConversationList() {
    }

    public ConversationList(List<Conversation> conversation) {
        this.conversation = conversation;
    }

    public List<Conversation> getConversations() {
        return this.conversation;
    }

    public void setConversation(List<Conversation> conversation) {
        this.conversation = conversation;
    }
}