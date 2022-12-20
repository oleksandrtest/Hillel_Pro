package ua.example;

import org.junit.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Semaphore;

import static org.junit.Assert.assertEquals;

public class ServerTest {

    private DataOutputStream serverOut;
    private DataInputStream serverIn;
    private ServerSocket serverSocket;
    private Semaphore lock = new Semaphore(0);



    @Test
    public void getConnection() throws Exception {
        serverSocket = new ServerSocket(9999);
        listen(serverSocket);

        Socket client = new Socket("localhost",9999);
        DataOutputStream clientOut = new DataOutputStream(client.getOutputStream());
        DataInputStream clientIn = new DataInputStream(client.getInputStream());

        System.out.println("Waiting for lock");
        lock.acquire();
        System.out.println("Acquired lock");

        write(clientOut,"Hi");
        assertRead(serverIn,"Hi");

        write(serverOut,"Hello");
        assertRead(clientIn,"Hello");

        printWrite(clientOut,"Test printWrite");
        assertRead(serverIn,"Test printWrite");

        printWrite(serverOut,"Test printWrite again");
        assertRead(clientIn,"Test printWrite again");

        client.close();
        serverSocket.close();
    }

    private void write(OutputStream out, String str) throws IOException {
        out.write(str.getBytes());
        out.flush();
    }

    private void printWrite(OutputStream out, String str) throws IOException {
        PrintWriter pw = new PrintWriter(out);
        pw.print(str);
        pw.flush();
    }

    private void assertRead(InputStream in, String expected) throws IOException {
        assertEquals("Too few bytes available for reading: ", expected.length(), in.available());

        byte[] buf = new byte[expected.length()];
        in.read(buf);
        assertEquals(expected, new String(buf));
    }


    private void listen(ServerSocket server) {
        new Thread(() -> {
            try {
                Socket socket = server.accept();
                System.out.println("Incoming connection: " + socket);

                serverOut = new DataOutputStream(socket.getOutputStream());
                serverIn = new DataInputStream(socket.getInputStream());

                lock.release();
                System.out.println("Released lock");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

}