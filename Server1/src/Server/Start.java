package Server;


import Networking.WaitForConnection;

public class Start {
    public static void main(String[] args) {
        (new Thread(new WaitForConnection())).start();
        System.out.println("Server Starts running...");
    }
}
