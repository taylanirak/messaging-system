import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Map;
public class ServerHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(ServerHandler.class);
    public static String userName;

    private final Socket socket;
    private final Map<String, Mailbox> mailboxMap;
    private final BufferedReader bufferedReader;
    private final BufferedWriter bufferedWriter;

    public ServerHandler(Socket socket, Map<String, Mailbox> mailboxMap) throws IOException {
        this.socket = socket;
        this.mailboxMap = mailboxMap;
        this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

    }

    public void run() {
        try {
            String userName = bufferedReader.readLine();
            System.out.println("User: " + userName);


            String command;
            while ((command = bufferedReader.readLine()) != null) {
                logger.info("Requested command: " + command);
                String[] parts = command.split(":");
                String opName=parts[0];
                logger.info("Incoming operation: "+ opName);
                switch (parts[0]) {
                    case "send" -> {
                        String mailReceiver = parts[1];
                        String subject = parts[2];
                        String body = parts[3];

                        Mailbox receiverMailbox = mailboxMap.get(mailReceiver);
                        Mailbox senderMailbox = mailboxMap.get(userName);
                        receiverMailbox.sendInbox("Inbox", new Message(subject, body));
                        senderMailbox.sendSent("Sent", new Message(subject, body));
                    }
                    case "list" -> {
                        String folderName = parts[1];
                        Mailbox userMailbox = mailboxMap.get(userName);
                        Folder folder = userMailbox.getFolderByName(folderName);
                        if (folder != null) {
                            StringBuilder result = new StringBuilder();
                            List<Message> messages = folder.getMessages();
                            for (Message message : messages) {
                                result.append("Subject: ").append(message.getSubject()).append("\n");
                            }
                            bufferedWriter.write(result.toString());
                            logger.info(result.toString());
                            logger.info(messages.toString());
                        } else {
                            bufferedWriter.write("Folder not found.\n");
                        }
                        bufferedWriter.flush();
                    }
                    case "read" -> {
                        int requestedSequenceNumber = Integer.parseInt(parts[1]);
                        Mailbox userMailbox = mailboxMap.get(userName);
                        Message requestedMessage = null;

                        for (Folder folder : userMailbox.getFolders()) {
                            requestedMessage = folder.getMessageBySequenceNumber(requestedSequenceNumber);
                            if (requestedMessage != null) {
                                break;
                            }
                        }

                        if (requestedMessage != null) {
                            bufferedWriter.write("Subject: " + requestedMessage.getSubject() + "\n");
                            bufferedWriter.write("Body: " + requestedMessage.getBody() + "\n");
                            logger.info("Subject: " + requestedMessage.getSubject() + "\n");
                            logger.info("Subject: " + requestedMessage.getBody() + "\n");

                        } else {
                            bufferedWriter.write("Message not found.\n");
                        }
                        bufferedWriter.flush();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
    }

    private void closeConnections() {
        try {
            if (bufferedReader != null) bufferedReader.close();
            if (bufferedWriter != null) bufferedWriter.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



