import java.io.*;
import java.net.*;

public class PlayBook {
    public static void main(String args[]) {
// declaration section:
// declare a server socket and a client socket for the server
// declare an input and an output stream
        System.out.println("Main thread beginning...");
        ServerSocket echoServer = null;
        String line;
        // DataInputStream is;
        BufferedReader is; // Replacement of DataInputStream
        PrintStream os;
        Socket clientSocket = null;
// Try to open a server socket on port 9999
// Note that we can't choose a port less than 1023 if we are not
// privileged users (root)
        try {
            echoServer = new ServerSocket(9999);
            System.out.println("Starting ServerSocket : "+echoServer.toString());
        }
        catch (IOException e) {
            System.out.println(e);
        }
// Create a socket object from the ServerSocket to listen and accept
// connections.
// Open input and output streams
        try {
            clientSocket = echoServer.accept();
            System.out.println("Client socket : "+clientSocket.toString());

            // is = new DataInputStream(clientSocket.getInputStream());
            is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            os = new PrintStream(clientSocket.getOutputStream());
// As long as we receive data, echo that data back to the client.
            while (true) {
                line = is.readLine();
                os.println(line);
                if (line.equalsIgnoreCase("EOF")){
                    os.println("Ok - Good to go...");
                }
            }


        }catch (IOException e) {
            System.out.println(e);
        }
        System.out.println("Main thread ending...");
    }
}