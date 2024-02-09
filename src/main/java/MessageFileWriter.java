import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
public class MessageFileWriter {
    private static final Logger logger = LoggerFactory.getLogger(MessageFileWriter.class);
    private final String filePath;

    public MessageFileWriter(String filePath) {
        this.filePath = filePath;
    }

    public synchronized void appendMessage(int sequenceNumber, String subject, String body) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write("Subject: " + subject);
            writer.newLine();
            writer.write("Body: " + body);
            writer.newLine();
            logger.info("Successful.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
