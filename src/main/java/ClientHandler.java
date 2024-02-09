
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class ClientHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ClientHandler.class);
    private final BufferedReader bufferedReader;


    public ClientHandler(BufferedReader bufferedReader, BufferedWriter responseWriter) {
        this.bufferedReader = bufferedReader;

    }


    public void run() {
        try {
            String response;
            while ((response = bufferedReader.readLine()) != null) {
                logger.info(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


