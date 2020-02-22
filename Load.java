package ui;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import plant.Plant;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import plant.Shop;

public class Load {


    public static ArrayList<Plant> returnArray() throws IOException, ParseException {
        ArrayList<Plant> p = new ArrayList<>();
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader("save.json"));
        JSONArray jsonObjects = (JSONArray) obj;
        for (Object j : jsonObjects) {
            JSONObject plantObject = (JSONObject) j;
            addToSaveFile(p, plantObject);
        }
        return p;
    }

    public static void addToSaveFile(ArrayList<Plant> p, JSONObject plantObject) {
        p.add(new Plant(formatIntegerException(plantObject.get("Health").toString()),
                formatDoubleException(plantObject.get("Growth Rate").toString()),
                formatDoubleException(plantObject.get("Max Growth Capacity").toString()),
                plantObject.get("Plant Type").toString(),
                plantObject.get("ID").toString()));
    }

    public static Shop returnShop() throws IOException, ParseException {
        Shop s = new Shop(0,0,0,0);
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader("saveShop.json"));
        JSONArray jsonObjects = (JSONArray) obj;
        for (Object j : jsonObjects) {
            JSONObject plantObject = (JSONObject) j;
            addShopToSaveFile(s, plantObject);
        }
        return s;
    }

    public static void addShopToSaveFile(Shop s, JSONObject plantObject) {
        s.setShopCredits(formatIntegerException(plantObject.get("Shop Credits").toString()));
        s.setWaterAmount(formatIntegerException(plantObject.get("Water").toString()));
        s.setFertilizerAmount(formatIntegerException(plantObject.get("Fertiliser").toString()));
        s.setPesticideAmount(formatIntegerException(plantObject.get("Pesticides").toString()));
    }

    public static int formatIntegerException(String s1) {
        int number = 0;
        try {
            number = Integer.parseInt(s1);
        } catch (NumberFormatException e) {
            number = 0;
        }
        return number;

    }

    public static double formatDoubleException(String s2) {
        double number = 0.0;
        try {
            number = Double.parseDouble(s2);
        } catch (NumberFormatException e) {
            number = 0.0;
        }
        return number;
    }

}

