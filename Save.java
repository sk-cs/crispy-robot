package ui;

import org.json.simple.parser.ParseException;
import plant.Plant;
import java.io.*;
import java.nio.file.Files;
import java.util.*;
import plant.Shop;

import org.json.JSONObject;
import org.json.JSONArray;

public class Save {
        // These are the links used for the Save and Load classes
        // https://www.baeldung.com/java-org-json --> Used this website to help, Learn about Map and JSONObject
        // https://restfulapi.net/json-array/ --> Used website to learn about JSONArray
        // Read the java file documentation to find out about the File Class
        // Tutorial which helped me solve this https://stackoverflow.com/questions/5015844/parsing-json-object-in-java

    public static void saveFile(ArrayList<Plant> p) throws IOException {
        Map<String, String> plantMap = new HashMap<>();
      //  FileWriter file = new FileWriter("save.json");
        File realFile = new File("/Users/saurishkapoor/IdeaProjects/project_r2l2b/save.json");
        JSONArray listOfPlants = new JSONArray();
        JSONObject plantObj;

        for (int i = 0; i < p.size(); i++) {
            try {
                Integer damage = p.get(i).getPlantHealth();
                Double growthRate = p.get(i).getGrowthRate();
                Double maxCapacity = p.get(i).getMaxGrowthCapacity();
                String plantType = p.get(i).getPlantName();
                String id = p.get(i).getID();
                plantObj = putInMap(damage, growthRate, maxCapacity, plantType, id);
                listOfPlants.put(plantObj);
            } catch (NullPointerException e) {
                System.out.println("Oh noo all your plants died");
            }
        }
        Files.write(realFile.toPath(), listOfPlants.toString().getBytes());
    }

    public static JSONObject putInMap(Integer s1, Double s2, Double s3, String s4, String s5) {
        Map<String, String> plantMap = new HashMap<>();
        plantMap.put("Health", s1.toString());
        plantMap.put("Growth Rate", s2.toString());
        plantMap.put("Max Growth Capacity", s3.toString());
        plantMap.put("Plant Type", s4);
        plantMap.put("ID", s5);
        return new JSONObject(plantMap);
    }


    public static void saveShop(Shop s) throws IOException {
        Map<String, String> plantMap = new HashMap<>();
        FileWriter file = new FileWriter("saveShop.json");
        File realFile = new File("/Users/saurishkapoor/IdeaProjects/project_r2l2b/saveShop.json");
        JSONArray listOfShop = new JSONArray();
        JSONObject plantObj;
        try {
            Integer shopCredits = s.getShopCredits();
            Integer water = s.getWaterAmount();
            Integer fertiliser = s.getFertilizerAmount();
            Integer pesticides = s.getPesticideAmount();
            plantObj = putInShop(shopCredits,water,fertiliser,pesticides);
            listOfShop.put(plantObj);
        } catch (NullPointerException e) {
            s.setShopCredits(0);
        }
        Files.write(realFile.toPath(), listOfShop.toString().getBytes());
    }

    public static JSONObject putInShop(Integer s1, Integer s2, Integer s3, Integer s4) {
        Map<String, String> plantMap = new HashMap<>();
        plantMap.put("Shop Credits", s1.toString());
        plantMap.put("Water", s2.toString());
        plantMap.put("Fertiliser", s3.toString());
        plantMap.put("Pesticides", s4.toString());
        return new JSONObject(plantMap);
    }


    public static ArrayList<Plant> loadFile() throws IOException, ParseException {
        ArrayList<Plant> newList = Load.returnArray();
        return newList;
    }

    public static Shop loadShopFile() throws IOException, ParseException {
        Shop s = Load.returnShop();
        return s;
    }

}