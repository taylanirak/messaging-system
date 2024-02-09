import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private static final Logger logger = LoggerFactory.getLogger(Server.class);
    private static final Map<String, Mailbox> mailboxMap = loadMailboxMap();

    public static Map<String, Mailbox> getMailboxMap() {
        return mailboxMap;
    }

    public static void main(String[] args) throws IOException {

        Socket socket = null;
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(1234);
            while (true) {
                socket = serverSocket.accept();

                ServerHandler handler = new ServerHandler(socket, mailboxMap);
                new Thread(handler).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (serverSocket != null)
                serverSocket.close();
        }
    }

    private static Map<String, Mailbox> loadMailboxMap() {
        Map<String, Mailbox> map = new HashMap<>();
        map.put("Taylan", new Mailbox("Taylan"));
        map.put("Cevher", new Mailbox("Cevher"));
        map.put("İsmet", new Mailbox("İsmet"));
        map.put("anybody you want to add/ names from any database", new Mailbox("anybody you want to add/ names from any database"));

        return map;

    }
}
