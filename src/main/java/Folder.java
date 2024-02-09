import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Folder {
    private static final Logger logger = LoggerFactory.getLogger(Folder.class);
    private final List<Message> messages;
    private final String folderName;

    public Folder(String folderName) {
        this.folderName = folderName;
        this.messages = new ArrayList<>();
    }

    public List<Message> getMessages() {
        return messages;
    }

    public Message getMessageBySequenceNumber(int sequenceNumber) {
        for (Message message : messages) {
            if (message.getSequenceNumber() == sequenceNumber) {
                return message;
            }
        }
        return null;
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public String getFolderName() {
        return folderName;
    }
}


