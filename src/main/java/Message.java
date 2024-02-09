import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;
public class Message {
    private static final Logger logger = LoggerFactory.getLogger(Message.class);
    private static AtomicInteger counter = new AtomicInteger(0);
    private String subject;
    private String body;
    private final int sequenceNumber;

    public Message(String subject, String body) {
        this.subject = subject;
        this.body = body;
        this.sequenceNumber = counter.incrementAndGet();
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public static AtomicInteger getCounter() {
        return counter;
    }

    public static void setCounter(AtomicInteger counter) {
        Message.counter = counter;
    }

    public String toString() {
        return STR."Message{subject='\{subject}\{'\''}, body='\{body}\{'\''}, sequenceNumber=\{sequenceNumber}\{'}'}";
    }
}

