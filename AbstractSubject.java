package game;

import exceptions.*;
import plant.Plant;
import plant.Shop;

import java.util.*;

public abstract class AbstractSubject {
    //CONSTANTS
    //Abstract Subject
    // I do not have to give AbstractSubject a list of observers, simply because there will only be 3 observers at all
    // times, that being Cactus Eucalyptus and Sunflower.
    // List of Observers is available in

    protected final Scanner input = new Scanner(System.in);
    protected final int maxPlants = 10;
    protected final int eucalyptusPrice = 30;
    protected final int sunflowerPrice = 20;
    protected final int cactusPrice = 25;
    //FIElDS
    protected String actionInput;

    //EFFECTS, Displays the list of actions a user can take within a particular round
    public static void displayGameActions(ArrayList<Plant> listOfPlants, double shopCredits) {
        System.out.println("Add a plant");
        System.out.println("Remove a plant");
        System.out.println("Add Fertiliser");
        System.out.println("Add Pesticides");
        System.out.println("Add Water");
        System.out.println("Add Sunlight");
    }

    //MODIFIES this, listOfPlant
    //EFFECTS removes Plant for user
    public ArrayList<Plant> removePlants(ArrayList<Plant> p, String ss, int intuserOption)
            throws TooLittlePlantsException {
        if (p.size() - 1 == 0) {
            throw new TooLittlePlantsException("Sorry, you need at least one Plant to continue the ui.game");
        }

        for (int i = 0; i < p.size(); i++) {
            if (intuserOption == i) {
                System.out.println(p.size());
                p.remove(i);
                System.out.println(p.size());
            }
        }
        notifyObserver();
        return p;
    }

    // EFFECTS Reduces one plants health by set amount depending on the plant
    public abstract Plant reducePlantHealth(Plant p, int damage);


    //EFFECTS Increases the health of one of the plants by a set amount depending on the plant
    public abstract Plant addFertiliser(Plant p, Shop s) throws NegativeFertiliserException;


    //MODIFIES listOfPlant,this
    //EFFECTS reduces bugs destroying ability

    public abstract Plant addPesticides(Plant p, Shop s) throws NegativeFertiliserException, NegativePesticideException;

    //MODIFIES listOfPlant,this
    //EFFECTS increases Plants health by amount dependent on plant

    public abstract Plant addWater(Plant p, Shop s) throws NegativeFertiliserException, NegativeWaterException;

    //MODIFIES listOfPlant,this
    //EFFECTS increases Plants health by amount dependent on plant

    public abstract Plant addSunlight(Plant p);

    public void notifyObserver() {
        System.out.println("Modified Plant");
    }




}
