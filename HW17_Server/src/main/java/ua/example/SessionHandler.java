package ua.example;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ua.example.model.Client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
class  SessionHandler extends Thread
{
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    DateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
    final DataInputStream dataInputStream;
    final DataOutputStream dataOutputStream;
    final Socket socket;
    private Server server;
    private ua.example.model.Client client;
    private static int counter = 1;

    public SessionHandler(Socket socket, DataInputStream dataInputStream, DataOutputStream dataOutputStream, Server server) throws IOException {
        this.socket = socket;
        client = new Client(counter++);
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;
        this.server = server;
        server.addSession(this);
    }

    @Override
    public void run()
    {
        String response;

        while (true)
        {
            try {

                dataOutputStream.writeUTF("Please make your choose [Date | Time | File | Exit]");

                response = dataInputStream.readUTF();

                server.broadcast(this, "New client -"+ this.getClient().getClientName() + " connected!!!" );

                if(response.equals("Exit"))
                {
                    System.out.println("Client " + this.socket + " sends exit...");
                    System.out.println("Closing this connection.");
                    server.broadcast(this, this.getName() + " closed connection");
                    this.socket.close();
                    System.out.println("Connection closed");
                    server.removeSession(this);
                    break;
                }

                Date date = new Date();

                switch (response) {
                    case "Date" -> {
                        dataOutputStream.writeUTF(dateFormat.format(date));
                    }
                    case "Time" -> {
                        dataOutputStream.writeUTF(timeFormat.format(date));
                    }
                    case "File" -> {
                        server.receiveFile(dataInputStream.readUTF(), dataInputStream);
                        dataOutputStream.writeUTF("File send");
                    }
                    default -> {
                        dataOutputStream.writeUTF("Invalid input");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try
        {

            this.dataInputStream.close();
            this.dataOutputStream.close();

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void send (String msg) throws IOException {
        dataOutputStream.writeUTF(msg);
    }
}