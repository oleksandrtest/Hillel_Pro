package ua.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import ua.example.model.Client;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class Serializer {
    private final ObjectMapper objectMapper= new ObjectMapper();
    private final String tmpdir = System.getProperty("java.io.tmpdir");
    private final String path = new File(tmpdir + "Clients.json").getAbsolutePath();

    public void serialize(List<Client> clients) {
        try {
            objectMapper.writeValue(new File(path), clients);
        } catch (Exception ignored) {
            System.out.println("File can`t created!!!");
        }
    }

    public List<Client> deserialize() {
        try {
            return List.of(objectMapper.readValue(new File(path), Client[].class));
        } catch (IOException ignored) {
        }
        return Collections.emptyList();
    }
}
