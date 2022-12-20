package ua.example;

import lombok.RequiredArgsConstructor;
import ua.example.model.Client;
import ua.example.service.Serializer;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
public class Server {

    private ServerSocket serverSocket;
    private Socket socket;
    private ArrayList<SessionHandler> sessions;
    private final Serializer serializer = new Serializer();


    public Server(ServerSocket socket) {
        this.serverSocket = socket;
        sessions = new ArrayList<>();
    }


    public void getConnections() throws IOException {

        while (true)
        {
            try
            {
                socket = serverSocket.accept();
                System.out.println("A new client is connected : " + socket);
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                System.out.println("Assigning new thread for this client");
                Thread thread = new  SessionHandler(socket, dataInputStream,dataOutputStream,this);
                thread.start();

            }
            catch (Exception e){
                socket.close();
                e.printStackTrace();
            }
        }

    }

    public synchronized void broadcast(SessionHandler from, String msg) throws IOException {

        for (SessionHandler session: sessions) {
            if (session != from) {
                session.send(msg);
            }
        }
    }

    public synchronized void addSession(SessionHandler session) {

        if (!serializer.deserialize().isEmpty()) {
            List<Client> clients = new ArrayList<Client>(serializer.deserialize());
            clients.add(session.getClient());
            serializer.serialize(clients);
        }  else {
            serializer.serialize(new ArrayList<>(List.of(session.getClient())));
        }
        sessions.add(session);
    }

    public synchronized void removeSession(SessionHandler session) {
        sessions.remove(session);

        serializer.serialize(serializer.deserialize().stream()
                .filter(c-> c.getClientName().equals(session.getClient().getClientName()))
                .collect(Collectors.toList()));
    }

    public void receiveFile(String fileName, DataInputStream input) throws Exception{
        int bytes = 0;
        // path for any file transfer
        Path path = Paths.get(fileName);
        FileOutputStream fileOutputStream = new FileOutputStream(path.getFileName().toString());
        long size = input.readLong();
        byte[] buffer = new byte[4 * 1024];
        while (size > 0 && (bytes = input.read(buffer, 0, (int)Math.min(buffer.length, size))) != -1) {
            fileOutputStream.write(buffer,0,bytes);
            size -= bytes;
        }
        fileOutputStream.close();
    }
}




