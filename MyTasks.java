package com.example;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

@Component
public class MyTasks {

    private static int id = 1;
    private RestTemplate restTemplate = new RestTemplate();

    private String[] randomMake = new String[] {
            "Chevy Cruze", "Ford Fiesta", "Subaru Forrester", "Toyota Corolla", "Honda Civic", "Tesla Model 3",
            "BMW 2 series", "Volkswagen Jetta", "Nissan Altima", "Infinity Q50", "Audi R8"
    };


    private Vehicle randomVehicle(int cId){
        Random r = new Random();
        Vehicle v = new Vehicle(cId, randomMake[r.nextInt(11)], r.nextInt(2016)+1986, r.nextInt(45000)+15000);
        id++;
        return v;
    }

    @Scheduled(fixedRate = 5000)
    public void addVehicle(){
        String url = "http://localhost:8080/addVehicle";
        restTemplate.postForObject(url, randomVehicle(id), Vehicle.class);
    }

    @Scheduled(fixedRate = 15000)
    public void deleteVehicle(){
        Random r = new Random();
        String url = "http://localhost:8080/deleteVehicle";
        restTemplate.delete(url, r.nextInt(id), Vehicle.class);
    }

    @Scheduled(fixedRate = 7000)
    public void updateVehicle(){
        Random r = new Random();
        String url = "http://localhost:8080/updateVehicle";
        Vehicle v = randomVehicle(r.nextInt(id));
        String url2 = "http://localhost:8080/getVehicle{"+v.getId()+"}";
        restTemplate.put(url, v, Vehicle.class);
        restTemplate.getForObject(url2, Vehicle.class);
    }


    @Scheduled(fixedDelay = 6000)
    @create
    public void beCreative(){
        System.out.println("This is the peak of my creativity");
    }

    @Scheduled(fixedDelay = 6000)
    @art
    public void realityCheck(){
        System.out.println("I dont have an ounce of creativity in my body");
    }

}
