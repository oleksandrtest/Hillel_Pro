package ua.example.service;

import com.fasterxml.jackson.databind.json.JsonMapper;
import ua.example.model.Animal;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class AnimalSerializer {
    private final JsonMapper jsonMapper = new JsonMapper();
    private final String tmpdir = System.getProperty("java.io.tmpdir");
    private final String path = new File(tmpdir + "animals.json").getAbsolutePath();

    public void serialize(List<Animal> animals) {
        try {
            jsonMapper.writeValue(new File(path), animals);
        } catch (Exception ex) {
            System.out.println("File can`t created!!!");
        }
    }

    public List<Animal> deserialize() {
        try {
            if (!path.isEmpty()) {
                return List.of(jsonMapper.readValue(new File(path), Animal[].class));
            }
        } catch (IOException ignored) {
        }
        return Collections.emptyList();
    }
}
