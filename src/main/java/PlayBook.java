import java.io.*;
import java.net.*;

public class PlayBook {

    public static void main(String[] args) {
        ServerChatRoom aServerRunnable = new ServerChatRoom(9999);
        Thread aThreadServer = new Thread(aServerRunnable);
        aThreadServer.start();

        ClientChatRoom aClientChatRoom = new ClientChatRoom(9999, "localhost");
        Thread aThreadClient = new Thread(aClientChatRoom);
        aThreadClient.start();



    }
}