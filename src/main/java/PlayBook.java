import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.*;

public class PlayBook {
    // Variables for logs management
    static Logger logger;

    public static void main(String[] args) {
        logger = LogManager.getLogger();

        ServerChatRoom aServerRunnable = new ServerChatRoom(9999);
        Thread aThreadServer = new Thread(aServerRunnable);
        aThreadServer.start();

        ClientChatRoom aClientChatRoom = new ClientChatRoom(9999, "localhost");
        Thread aThreadClient = new Thread(aClientChatRoom);
        aThreadClient.start();

    }
}