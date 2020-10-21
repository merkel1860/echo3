import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Arrays;

public class ClientChatRoom implements Runnable {

    // Variables for logs management
    static  Logger logger;
    // variables for client status management
    boolean isClientRunning = false;
    // variables for networking management
    private Socket clientSocket;
    private final int serverPort;
    private final String serverIP;
    // variables for reading input and output stream
    BufferedReader is;
    PrintStream os;


    public ClientChatRoom(int serverPort, String serverIP) {
        logger = LogManager.getLogger();
        this.serverPort = serverPort;
        this.serverIP = serverIP;
        setNewConnectionToServer();

    }

    private void setNewConnectionToServer() {
        try {
            clientSocket = new Socket(serverIP,serverPort);
            logger.info("Connecting to server at : "+serverIP+ ":"+serverPort);
        } catch (IOException e) {
            logger.error("Failed to connect to server at <> "+serverIP+ ":"+serverPort);
            logger.error("Error message : "+e.getMessage());
            logger.error("Error stack : "+ Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    public void run() {
        while (!isClientRunning){
            is = readPacketsFromNetwork();
            try {
                parsingBytesToString(is);
            } catch (IOException e) {
                logger.error("Reading from parsingBytesToString failed");
                logger.error(e.getMessage());
                logger.error(e.getStackTrace());
            }
            isClientRunning = true;
        }
        logger.info("ClientRoom is shutting down");
    }

    private void parsingBytesToString(BufferedReader input) throws IOException {
        int i=0;
        String line = input.readLine();
        // we use EOF as a trick to mark end of file or stream.
        while (line.compareToIgnoreCase("EOF") != 0) {
            System.out.println("Received From Server ...");
            System.out.println("ClientRoom : "+Thread.currentThread().getName());
            System.out.println("Line "+ i +" :"+line);
            i++;
            // fetch next line for checking loop state
            line = input.readLine();
        }
        logger.info("Ending reading from inputStream");
    }

    private BufferedReader readPacketsFromNetwork() {
        BufferedReader tempBufferedReader = null;
        try {
            tempBufferedReader = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            logger.info("Reading bytes from inputStream...");
        } catch (IOException e) {
            logger.error("Reading from inputStream failed");
            logger.error(e.getMessage());
            logger.error(e.getStackTrace());
        }
        return  tempBufferedReader;
    }

    private void logAnyException(Exception e) {
        // IOException
        // InterruptedException
        logger.error("Failed to accept requests at <> localhost:"+serverPort);
        logger.error("Error message : "+e.getMessage());
        logger.error("Error stack : "+ Arrays.toString(e.getStackTrace()));
    }
}
