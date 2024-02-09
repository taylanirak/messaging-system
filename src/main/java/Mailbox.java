
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Mailbox {
    private static final Logger logger = LoggerFactory.getLogger(Mailbox.class);

    private final List<Folder> folders;
    private final String name;

    public String getName() {
        return name;
    }

    public Mailbox(String name) {
        this.name = name;

        this.folders = new ArrayList<>();
        this.folders.add(new Folder("Inbox"));
        this.folders.add(new Folder("Sent"));
    }

    public void sendInbox(String folderName, Message message) {
        Folder inbox = getFolderByName(folderName);
        if (inbox != null) {
            inbox.addMessage(message);
            String filePath = "inbox" + name + ".txt";
            MessageFileWriter writer = new MessageFileWriter(filePath);
            writer.appendMessage(message.getSequenceNumber(), message.getSubject(), message.getBody());
        } else {
            logger.info("Folder cannot be found: " + folderName);
        }
    }
//SENT
    public void sendSent(String folderName, Message message) {
        Folder sent = getFolderByName(folderName);
        if (sent != null) {
            sent.addMessage(message);
            String filePath = "sent_" + name + ".txt";
            MessageFileWriter writer = new MessageFileWriter(filePath);
            writer.appendMessage(message.getSequenceNumber(), message.getSubject(), message.getBody());
        } else {
            logger.info("Folder cannot be found: " + folderName);
        }
    }

    Folder getFolderByName(String folderName) {
        for (Folder folder : folders) {
            if (folder.getFolderName().equals(folderName)) {
                return folder;
            }
        }
        return null;
    }

    public List<Folder> getFolders() {
        return folders;
    }
}

