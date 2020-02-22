package plant;

import network.WeatherAPI;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;
import java.util.Random;

public class Plant implements Serializable {

    private transient Scanner input = new Scanner(System.in);
    private static final long serialVersionUID = 2417179859345790793L;
    private int plantHealth;
    private double growthRate;
    private double maxGrowthCapacity;
    private String plantName;
    private String randomID;
    public static final Random random = new Random();

//    private Scanner plantInp;


    //MODIFIES this
    //EFFECTS assigns values to the a Plants health, growth Rate, max Growth Capacity, plant name,

    public Plant(int plantHealth, double growthRate, double maxGrowthCapacity, String plantName, String id) {
        this.plantHealth = plantHealth;
        this.growthRate = growthRate;
        this.maxGrowthCapacity = maxGrowthCapacity;
        this.plantName = plantName;
        this.randomID = id;
    }

    //EFFECTS sets attribute of a Plant Object

    public Plant chooseSP() throws IOException, ParseException {
        WeatherAPI api = new WeatherAPI();
        Double weatherinfo = api.receiveWeatherInfo();
        Plant p = null;
        if (weatherinfo >= 280.0 && weatherinfo <= 290.0) {
            p = initialiseSunflower();
        } else if (weatherinfo >= 260.0 && weatherinfo <= 279.0) {
            p = initialiseEucalyptus();
        } else if (weatherinfo >= 240 && weatherinfo <= 259.0) {
            p = initialiseCactus();
        } else {
            p = initialiseSunflower();
        }
        return p;
    }

    public Plant invalid() throws IOException, ParseException {
        System.out.println("Please try again, you may have misspelled a word");
        System.out.println("Select the plant you want to purchase");
        System.out.println("Sunflower");
        System.out.println("Cactus");
        System.out.println("Eucalyptus");
        return chooseSP();

    }

    //MODIFIES this
    // EFFECTS adds a new sunflower to the ui.game

    public Plant initialiseSunflower() {
        this.randomID = randomIDGenerator(random,"Sunflower");
        System.out.println("You have selected the Sunflower plant");
        return new Plant(100, 7.5, 200, "Sunflower", randomID);
    }
    //MODIFIES this
    // EFFECTS adds a new Cactus into the ui.game

    public Plant initialiseCactus() {
        this.randomID = randomIDGenerator(random,"Cactus");
        System.out.println("You have selected the Cactus plant");
        return new Plant(100, 7.5, 200, "Sunflower", randomID);

    }
    //MODIFIES this
    // EFFECTS adds a new Cactus into the ui.game

    public Plant initialiseEucalyptus() {
        this.randomID = randomIDGenerator(random,"Eucalyptus");
        System.out.println("You have selected the Eucalyptus plant");
        return new Plant(100, 7.5, 200, "Sunflower", randomID);

    }
    // EFFECTS damages each of a plant in the ui.game

    public void setDamage(double damage) {
        this.plantHealth -= damage;
    }
    // EFFECTS increases health of one plant in the ui.game

//    public void setPlantHealth(double health) {
//        this.plantHealth += health;
//    }
//    // --> Ask TA, whether this this is effects because it is dependent on whether or not it influences the public
//    //method

    public int getPlantHealth() {
        return plantHealth;
    }

    public void setPlantHealth(int healthChange) {
        this.plantHealth = healthChange;
    }

    public double getMaxGrowthCapacity() {
        return maxGrowthCapacity;
    }

    public double getGrowthRate() {
        return growthRate;
    }

    public String getPlantName() {
        return plantName;
    }

    //TAKEN FROM STACKOVERFLOWWWW
    public static String randomIDGenerator(Random randomNumber, String inputID) {
        char[] charArray = new char[5];
        for (int i = 0; i < charArray.length; i++) {
            charArray[i] = inputID.charAt(randomNumber.nextInt(inputID.length()));
        }
        return new String(charArray);
    }

    public String getID() {
        return randomID;
    }

}


