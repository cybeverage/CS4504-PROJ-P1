import java.io.*;
import java.net.*;

public class TCPServerRouter {
  public static void main(String[] args) throws IOException {

    // Comments by - Leiko Niwano
    // The TCP Server Router is in charge of accepting connections from the TCP Client/Server
    // and creating threads for each connection. It also creates a routing
    // table for the TCP Client/Server to use.

    // A socket is an endpoint for communication between two machines.
    // Here, Java has already provided an Object with optional methods for adjusting the behavior.
    Socket clientSocket = null; // socket for the thread
    // Routing table 
    // The routing table is a 2D array with 10 rows and 2 columns.
    
    Socket clientSocket = null; // socket for the thread. Thomas. Client refers to both TCPclient and TCPserver.
    Object[][] RoutingTable = new Object[10][2]; // routing table
    // Note that a port and a socket are different things. A port is a number that identifies a
    // specific process to which an incoming network message is to be delivered while a socket is
    // an endpoint for communication.
    int SockNum = 5555; // port number
    Boolean Running = true;
    int ind = 0; // indext in the routing table

    // Accepting connections
    // The ServerSocket class is used to create a server socket. A server socket waits for requests
    // to come in over the network. It performs some operation based on that request, and then
    // possibly returns a result to the requester.

    ServerSocket serverSocket = null; // server socket for accepting connections
    try {
      serverSocket = new ServerSocket(5555); //Thomas. the router is listening for broadcast signals with its device name.
      System.out.println("ServerRouter is Listening on port: 5555.");
    } catch (IOException e) {
      System.err.println("Could not listen on port: 5555.");
      System.exit(1);
    }

    // Creating threads with accepted connections
    // while the server is running, it will accept connections and create threads for each
    // It prints out the address of the client/server that connected to the server router.
    // If it can't connect, it runs into catch and exits
    while (Running == true) {
      try {
        clientSocket = serverSocket.accept(); //Thomas. Successful connection.
        SThread t =
            new SThread(RoutingTable, clientSocket, ind); // creates a thread with a random port
        t.start(); // starts the thread
        ind++; // increments the index
        System.out.println(
            "ServerRouter connected with Client/Server: "
                + clientSocket.getInetAddress().getHostAddress());
      } catch (IOException e) {
        System.err.println("Client/Server failed to connect.");
        System.exit(1);
      }
    } // end while

    // closing connections
    clientSocket.close();
    serverSocket.close();
  }
}