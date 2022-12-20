package ua.example;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {
    public static void main(String[] args) throws IOException {
        new Server(new ServerSocket(8090)).getConnections();
    }
}