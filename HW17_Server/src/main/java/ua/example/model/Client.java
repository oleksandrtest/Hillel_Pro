package ua.example.model;

import lombok.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class Client {
    private String id;
    private String clientName;
    private String time;

    public Client(int counter) {
        this.id = UUID.randomUUID().toString();
        this.clientName = "Client - " + counter;
        this.time = new SimpleDateFormat("hh:mm:ss").format(new Date());
    }

}
