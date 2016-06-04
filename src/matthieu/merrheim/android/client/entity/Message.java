package matthieu.merrheim.android.client.entity;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * @author Matthieu MERRHEIM
 */
@Root
public class Message {

	@Element
	private long id;

	@Element
	private String subject;

	@Element
	private String text;

	public Message() {
	}

	public Message(long id, String subject, String text) {
		this.id = id;
		this.subject = subject;
		this.text = text;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return this.id;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return this.text;
	}


}
