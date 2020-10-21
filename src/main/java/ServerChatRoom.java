import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Date;


public class ServerChatRoom implements Runnable{
    // Variables for logs management
    static Logger logger;
    // variables for client status management
    boolean isServerRunning = false;
    // variables for networking management
    private ServerSocket serverSocket;
    private final int serverPort;
    // variables for reading input and output stream
    BufferedReader is;
    PrintStream os;


    public ServerChatRoom(int serverPort) {
        this.serverPort = serverPort;
        logger = LogManager.getLogger();
        setServerConfig(this.serverPort);

    }

    private void setServerConfig(int aPort){
        try {
            serverSocket = new ServerSocket(aPort);
            logger.info("Localhost server is starting at port:"+serverPort);
        } catch (IOException e) {
            logger.error("Failed to start server at <> localhost:"+serverPort);
            logger.error("Error message : "+e.getMessage());
            logger.error("Error stack : "+ Arrays.toString(e.getStackTrace()));
        }

    }


    @Override
    public void run() {

        while (!isServerRunning){
            try {
                Socket socket  = serverSocket.accept();
                OutputStream output = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true);

                writer.println(new Date().toString());

                logger.info("Localhost server is running at port:"+serverPort);
            } catch (IOException e) {
                logger.error("Failed to accept requests at <> localhost:"+serverPort);
                logger.error("Error message : "+e.getMessage());
                logger.error("Error stack : "+ Arrays.toString(e.getStackTrace()));
            }

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                logger.error("Failed to pause at <> localhost:"+serverPort);
                logger.error("Error message : "+e.getMessage());
                logger.error("Error stack : "+ Arrays.toString(e.getStackTrace()));
            }
            finally {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    logger.error("Failed to accept requests at <> localhost:"+serverPort);
                    logger.error("Error message : "+e.getMessage());
                    logger.error("Error stack : "+ Arrays.toString(e.getStackTrace()));
                }
            }
            isServerRunning = true;
        }
        logger.info("ServerRoom is shutting down");

    }
}
