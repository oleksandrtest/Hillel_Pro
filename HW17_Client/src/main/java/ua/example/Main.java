package ua.example;

import ua.example.service.Client;

public class Main {

    public static void main(String[] args) throws Exception {
        new Client(8090).Connection();
    }

}
