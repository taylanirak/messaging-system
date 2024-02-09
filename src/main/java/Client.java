import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final Logger logger = LoggerFactory.getLogger(Client.class);
    public static void main(String[] args) {
        Socket socket = null;
        InputStreamReader inputStreamReader = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        BufferedWriter responseWriter = null;
        try {
            socket = new Socket("localhost", 1234);

            inputStreamReader = new InputStreamReader(socket.getInputStream());
            outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
            bufferedReader = new BufferedReader(inputStreamReader);
            bufferedWriter = new BufferedWriter(outputStreamWriter);
            Scanner scanner = new Scanner(System.in);

            responseWriter = new BufferedWriter(new FileWriter("server_responses.txt"));

            ClientHandler clientHandler = new ClientHandler(bufferedReader,responseWriter);
            new Thread(clientHandler).start();



            logger.info("What is your username? ");
            String userName = scanner.nextLine();
            bufferedWriter.write(userName);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            while (true) {
                logger.info("What do you want to do? ");
                String command = scanner.nextLine();
                bufferedWriter.write(command);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null)
                    socket.close();
                if (inputStreamReader != null)
                    inputStreamReader.close();
                if (outputStreamWriter != null)
                    outputStreamWriter.close();
                if (bufferedReader != null)
                    bufferedReader.close();
                if (bufferedWriter != null)
                    bufferedWriter.close();
                if (responseWriter != null)
                    responseWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}



