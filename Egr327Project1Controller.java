package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.CharEncoding;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@RestController
public class Egr327Project1Controller {

    @Timed
    @RequestMapping(value="/addVehicle", method = RequestMethod.POST)
    public Vehicle addVehicle(@RequestBody Vehicle newVehicle) throws IOException {
        // Object Mapper provides f
        // unctionality for reading and writing JSON
        ObjectMapper mapper = new ObjectMapper();

        // Create a FileWrite to write to inventory.txt and APPEND mode is true
        FileWriter output = new FileWriter("./inventory.txt", true);

        // Serialize greeting object to JSON and write it to file
        mapper.writeValue(output, newVehicle);

        // Append a new line character to the file
        // The above FileWriter ("output") is automatically closed by the mapper.
        FileUtils.writeStringToFile(new File("./inventory.txt"),
                System.lineSeparator(),
                CharEncoding.UTF_8,
                true);

        return newVehicle;
    }

    @Timed
    @RequestMapping(value = "/getVehicle/{id}", method = RequestMethod.GET)
    public Vehicle getVehicle(@PathVariable("id") int id) throws IOException {
        // Object Mapper provides functionality for reading and writing JSON
        ObjectMapper mapper = new ObjectMapper();

        List<String> inventory = FileUtils.readLines(new File("./inventory.txt"), CharEncoding.UTF_8);
        Vehicle vehicle;

        for(String item : inventory) {
            // Deserialize JSON to greeting object
            vehicle = mapper.readValue(item, Vehicle.class);
            if(id == vehicle.getId()) {
                return vehicle;
            }
        }
        return null;
    }

    @Timed
    @RequestMapping(value = "/updateVehicle", method = RequestMethod.PUT)
    public Vehicle updateVehicle(@RequestBody Vehicle vehicle) throws IOException {
        File ogFile = new File("./inventory.txt");
        File newFile = new File("./inventory2.txt");

        // Object Mapper provides functionality for reading and writing JSON
        ObjectMapper mapper = new ObjectMapper();

        List<String> inventory = FileUtils.readLines(ogFile, CharEncoding.UTF_8);
        int index = 0;
        Vehicle invVehicle;
        FileWriter output;

        for(int i = 0; i < inventory.size(); i++) {
            // Deserialize JSON to greeting object
            invVehicle = mapper.readValue(inventory.get(i), Vehicle.class);

            // Create a FileWrite to write to inventory2.txt and APPEND mode is true
            output = new FileWriter(newFile.getPath(), true);

            if(vehicle.getId() == invVehicle.getId()) {
                // Serialize greeting object to JSON and write it to file
                mapper.writeValue(output, vehicle);
                index = i;
            } else {
                // Serialize greeting object to JSON and write it to file
                mapper.writeValue(output, invVehicle);
            }
            // Append a new line character to the file
            // The above FileWriter ("output") is automatically closed by the mapper.
            FileUtils.writeStringToFile(newFile,
                    System.lineSeparator(),
                    CharEncoding.UTF_8,
                    true);
        }

        //Now copy from source to copy, the delete source.
        org.apache.commons.io.FileUtils.copyFile(newFile, ogFile);
        org.apache.commons.io.FileUtils.deleteQuietly(newFile);

        Vehicle newVehicle = mapper.readValue(
                FileUtils.readLines(ogFile, CharEncoding.UTF_8).get(index),
                Vehicle.class);

        return newVehicle;
    }

    @Timed
    @RequestMapping(value = "/deleteVehicle/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteVehicle(@PathVariable("id") int id) throws IOException {
        File ogFile = new File("./inventory.txt");
        File newFile = new File("./inventory2.txt");

        // Object Mapper provides functionality for reading and writing JSON
        ObjectMapper mapper = new ObjectMapper();

        List<String> inventory = FileUtils.readLines(ogFile, CharEncoding.UTF_8);
        Vehicle vehicle;
        FileWriter output;
        Boolean found = false;

        for(int i = 0; i < inventory.size(); i++) {
            // Deserialize JSON to greeting object
            vehicle = mapper.readValue(inventory.get(i), Vehicle.class);

            // Create a FileWrite to write to inventory.txt and APPEND mode is true
            output = new FileWriter(newFile.getPath(), true);

            if(id != vehicle.getId()) {
                // Serialize greeting object to JSON and write it to file
                mapper.writeValue(output, vehicle);
                found = true;
            }

            // Append a new line character to the file
            // The above FileWriter ("output") is automatically closed by the mapper.
            FileUtils.writeStringToFile(newFile,
                    System.lineSeparator(),
                    CharEncoding.UTF_8,
                    true);
        }

        //Now copy from source to copy, the delete source.
        org.apache.commons.io.FileUtils.copyFile(newFile, ogFile);
        org.apache.commons.io.FileUtils.deleteQuietly(newFile);

        if(found) {
            return ResponseEntity.ok("Vehicle deleted.");
        } else {
            return ResponseEntity.ok("Drug with id: " + id + "not found :(");
        }
    }
}
