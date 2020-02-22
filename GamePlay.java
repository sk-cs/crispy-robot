package ui;

import exceptions.*;
import game.CactusActions;
import game.EucalytusActions;
import game.AbstractSubject;
import game.SunflowerActions;
import org.json.simple.parser.ParseException;
import plant.*;

import java.io.IOException;

import plant.Cactus;
import plant.Eucalyptus;
import plant.AbstractObserver;
import plant.Sunflower;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;


public class GamePlay {
    //CONSTANTS
    private final int minNoOfPlants = 1;
    private final int maxRoundNumber = 5;
    private final int increment = 2;
    private final Random bugAttack = new Random();
    private final Shop gameShop = new Shop();


    private AbstractObserver abstractPlant;

    private AbstractSubject abstractActions;
    private Shop mutateShop = new Shop(0, 0, 0, 0);

    public ArrayList<Plant> getListOfPlants() {
        return listOfPlants;
    }

    private ArrayList<Plant> listOfPlants = new ArrayList<>(); // LIST OF OBSERVERS;

    //FIELDS
    private Plant newPlant;
    private Plant modifiedPlant;
    private Plant effectPlant;
    private String userOption;
    private int intuserOption;
    private Scanner userInput;
    public static int roundNumber;
    private int minDamageBugs;
    private int maxDamageBugs;


    //MODIFIES this
    //EFFECTS Receives user input, Display's users option

    public void startGame() throws IOException, IllegalPurchaseException, TooLittlePlantsException,
            NegativeWaterException, NegativePesticideException, NegativeFertiliserException, ParseException {
        userInput = new Scanner(System.in);
//        startNewGame(newP);
    }

    public void startNewGame() throws IOException, ParseException {
         // This will have to be replaced with onclick, instead of
        Plant newP = new Plant(0, 0.0, 0.0, "", "");
        System.out.println("Choosing Plant based on weather");
        System.out.println("Loading...");
        newPlant = newP.chooseSP(); // Need to change this --------------->> ALERT/ALERT
        listOfPlants.add(newPlant); // Need to change this --------->> ALERT/ALERT
        newRound(listOfPlants, mutateShop);
    }
    //Extremely low cohesion:
    // Multiple functionalities, or game shopping, starting a game, providing game options.



    //MODIFIES this, listOfPlant
    //EFFECTS give users OPTIONS to Go Shop, or Tend to plants,
    // ... After that is resets the round from the beginning;

    public void newRound(ArrayList<Plant> p, Shop s) throws IOException {
        try {
            newRoundHelper(p,s);
        } catch (NegativeWaterException e) {
            newRound(p, s);
        } catch (NegativeFertiliserException e) {
            newRound(p, s);
        } catch (NegativePesticideException e) {
            newRound(p, s);
        } catch (TooLittlePlantsException e) {
            System.out.println("Sorry you need at least one Plant to countinue Playing");
            newRound(p,s);
        } catch (IllegalPurchaseException e) {
            System.out.println("Sorry you do not have enough shop credits to purchase Plant");
            newRound(p,s);
        } catch (NullPointerException e) {
            System.out.println("Oh noo all your plants died Better luck next time");
        }
    }


// SHOULD BE REFACTORED WITH SUB-HELPER METHODS
    public void newRoundHelper(ArrayList<Plant> p, Shop s) throws NegativeWaterException, IllegalPurchaseException,
            IOException, NegativeFertiliserException, NegativePesticideException, TooLittlePlantsException {
        userInput = new Scanner(System.in);
        s.setShopCredits(s.getShopCredits() + 60);
        if (roundNumber <= maxRoundNumber && p.size() >= minNoOfPlants) {
            modifyUserInput(p, s);
        } else if (p.size() == 0) {
            System.out.println("Oh noo, All of your Plants died, You lose!");
            System.exit(0);
        } else {
            endingScreen();
        }
        getNextRound(p, s);
    }

    private void modifyUserInput(ArrayList<Plant> p, Shop s) throws IllegalPurchaseException, TooLittlePlantsException,
            NegativeWaterException, NegativePesticideException, NegativeFertiliserException, IOException {
        System.out.println("The next round has started" + "1: Maintain Plants " + "2. Shop Items");
        intuserOption = userInput.nextInt();
        roundNumber++;
        checkCondition(p, s);
    }

    private void getNextRound(ArrayList<Plant> p, Shop s) throws IOException {
        listOfPlants = bugsAttack(p); // This keeps on increasing damage, as I haven't added a healing
        Save.saveFile(p);
        Save.saveShop(s);
        newRound(listOfPlants, s);
    }


    // REQUIRES roundNumber > maxRoundNumber
    // EFFECTS Tells the player that he has won, quits the ui.game.

    public void endingScreen() {
        System.out.println("Congratulations, you win this Game \n Thanks for playing!");
        System.exit(0);
    }

    public void checkCondition(ArrayList<Plant> p, Shop s) throws IllegalPurchaseException, TooLittlePlantsException,
            NegativeWaterException, NegativePesticideException, NegativeFertiliserException, IOException {
        if (intuserOption == 1) {
            System.out.println(p.get(0).getPlantHealth());
            makePlantChanges(p, mutateShop);
        } else if (intuserOption == 2) {
            System.out.println(p.get(0).getPlantHealth());
            displayShopOptions(p, mutateShop);
        }
    }

    //REQUIRES listOfPlants.size() >= 1
    //MODIFIES newPlant,listOfPlant // I think it doesn't modify this, because it is outside the scope of the method
    public ArrayList<Plant> bugsAttack(ArrayList<Plant> listOfPlants) {
        userInput = new Scanner(System.in);
        minDamageBugs = 5;
        maxDamageBugs = 15;
        int nextBugAttack = bugAttack.nextInt((maxDamageBugs - minDamageBugs) + minDamageBugs);
        for (int i = 0; i < listOfPlants.size(); i++) {
            if (changeAttack(listOfPlants.get(i), nextBugAttack) == null) {
                System.out.println("How unfortunate...");
                listOfPlants.remove(i);
            } else {
                listOfPlants.set(i, changeAttack(listOfPlants.get(i), nextBugAttack));
            }
        }
        minDamageBugs = minDamageBugs + increment;
        maxDamageBugs = maxRoundNumber + increment;
        return listOfPlants;
    }


    public void makePlantChanges(ArrayList<Plant> p, Shop s) throws
            TooLittlePlantsException, IllegalPurchaseException, NegativeWaterException,
            NegativePesticideException, NegativeFertiliserException, IOException {
        int intuserOption = makePlantChangesHelper();
        makePlantChangesHelper2(p, s, intuserOption);
    }


    public int makePlantChangesHelper() {
        userInput = new Scanner(System.in);
        System.out.println("1. Add Plant 2. Remove Plant, 3. Maintain Plant");
        intuserOption = userInput.nextInt();
        return intuserOption;
    }

    public void makePlantChangesHelper2(ArrayList<Plant> p, Shop s, int intuserOption)
            throws TooLittlePlantsException, IllegalPurchaseException, NegativeWaterException,
            NegativePesticideException, NegativeFertiliserException, IOException {
        switch (intuserOption) {
            case 1: {
                addPlantCase(p, s);
                break;
            }
            case 2: {
                removePlantCase(p, s);
                break;
            }
            case 3: {
                addHealthCase(p, s);
                break;
            }
            default: {
                handleRecursion(p, s);
            }
        }
    }

    public void addPlantCase(ArrayList<Plant> p, Shop s) throws IllegalPurchaseException {
        displayList(p);
        addPlant(p, s);
    }

    public void removePlantCase(ArrayList<Plant> p, Shop s) throws TooLittlePlantsException {
        displayList(p);
        removePlantCaseHelper(p, s);
    }

    public void addHealthCase(ArrayList<Plant> p, Shop s) throws NegativeWaterException,
            NegativePesticideException, NegativeFertiliserException {
        displayList(p);
        System.out.println("Choose the Plant you want to maintain");
        addHealthCaseHelper(p, s);
    }


    public void removePlantCaseHelper(ArrayList<Plant> p, Shop s) throws TooLittlePlantsException {
        intuserOption = userInput.nextInt();
        for (int i = 0; i < p.size(); i++) {
            if (intuserOption == i) {
                removePlant(p, p.get(i), intuserOption, s);
            }
        }
    }

    public void addHealthCaseHelper(ArrayList<Plant> p, Shop s) throws NegativeWaterException,
            NegativePesticideException, NegativeFertiliserException {
        intuserOption = userInput.nextInt();
        for (int i = 0; i < p.size(); i++) {
            if (intuserOption == i) {
                System.out.println("1. Add Water 2. Add Sun 3. Add Fertiliser 4. Add Pesticides");
                effectPlant = maintainOptions(p.get(i), s);
                p.set(i, effectPlant);
                System.out.println("You have successfully maintained:" + effectPlant.getPlantName());
            }
        }

    }

    public void handleRecursion(ArrayList<Plant> p, Shop s) throws IllegalPurchaseException, TooLittlePlantsException,
            NegativeWaterException, NegativePesticideException, NegativeFertiliserException, IOException {
        System.out.println("Please try another option:");
        makePlantChanges(p, s);
    }

    public static void displayList(ArrayList<Plant> p) {
        System.out.println("Choose the number associated with plant");
        int n = 0;
        for (Plant somePlant : p) {
            System.out.println(n++ + ".\t" + "Plant Health" + "\t" + somePlant.getPlantHealth() + "Plant Type"
                    + "\t"  + somePlant.getPlantName());
        }
    }


    public Plant changeAttack(Plant p, int anotherAttack) {
        effectPlant = attackPlantHelper(p);
        switch (p.getPlantName()) {
            case "Sunflower": {
                reduceSunflowerHealth(p, anotherAttack);
                break;
            }
            case "Cactus": {
                reduceCactusHealth(p, anotherAttack);
                break;
            }
            case "Eucalyptus": {
                reduceEucalyptusHealth(p, anotherAttack);
                break;
            }
            default: {
            }
        }
        // Change back to modifiedPlant
        return modifiedPlant;
    }

    public void reduceEucalyptusHealth(Plant p, int anotherAttack) {
        abstractActions = new EucalytusActions();
        modifiedPlant = abstractActions.reducePlantHealth(p, anotherAttack);
    }

    public void reduceCactusHealth(Plant p, int anotherAttack) {
        abstractActions = new CactusActions();
        modifiedPlant = abstractActions.reducePlantHealth(p, anotherAttack);
    }

    public void reduceSunflowerHealth(Plant p, int anotherAttack) {
        abstractActions = new SunflowerActions();
        modifiedPlant = abstractActions.reducePlantHealth(p, anotherAttack);
    }

    Plant attackPlantHelper(Plant p) {
        userInput = new Scanner(System.in);
        effectPlant = new Plant(p.getPlantHealth(), p.getGrowthRate(), p.getMaxGrowthCapacity(),
                p.getPlantName(), p.getID());
        return effectPlant;
    }

    public ArrayList<Plant> addPlant(ArrayList<Plant> p, Shop s) throws
            IllegalPurchaseException {
        ArrayList<Plant> updatedList = addPlantHelper(p, s);
        switch (userOption) {
            case "Cactus": {
                updatedList = addCactus(p, s);
                break;
            }
            case "Sunflower": {
                updatedList = addSunflower(p, s);
                break;
            }
            case "Eucalyptus": {
                updatedList = addEucalyptus(p, s);
                break;
            }
            default: {
            }
        }
        return updatedList;

    }

    public ArrayList<Plant> addEucalyptus(ArrayList<Plant> p, Shop s) throws IllegalPurchaseException {
        ArrayList<Plant> updatedList;
        abstractPlant = new Eucalyptus();
        updatedList = abstractPlant.purchasePlant(p, s);
        return updatedList;
    }

    public ArrayList<Plant> addSunflower(ArrayList<Plant> p, Shop s) throws IllegalPurchaseException {
        ArrayList<Plant> updatedList;
        abstractPlant = new Sunflower();
        updatedList = abstractPlant.purchasePlant(p, s);
        return updatedList;
    }

    public ArrayList<Plant> addCactus(ArrayList<Plant> p, Shop s) throws IllegalPurchaseException {
        ArrayList<Plant> updatedList;
        abstractPlant = new Cactus();
        updatedList = abstractPlant.purchasePlant(p, s);
        return updatedList;
    }

    public ArrayList<Plant> addPlantHelper(ArrayList<Plant> p, Shop s) {
        System.out.println("Add a Cactus, Eucalyptus or a Sunflower");
        userInput = new Scanner(System.in);
        userOption = userInput.next();
        ArrayList<Plant> updatedList = new ArrayList<>();
        for (Plant j: p) {
            updatedList.add(j);
        }
        return updatedList;
    }

    public ArrayList<Plant> removePlant(ArrayList<Plant> p, Plant j, int intuserOption, Shop s) throws
             TooLittlePlantsException {
        ArrayList<Plant> updatedList = removePlantHelper(p,j);
        switch (j.getPlantName()) {
            case "Cactus": {
                updatedList = getCactus(p, j, intuserOption);
                break;
            }
            case "Sunflower": {
                updatedList = getSunflower(p, j, intuserOption);
                break;
            }
            case "Eucalyptus": {
                updatedList = getEucalyptus(p, j, intuserOption);
                break;
            }
            default: {

            }
        }
        return updatedList;
    }

    public ArrayList<Plant> getEucalyptus(ArrayList<Plant> p, Plant j, int intuserOption) throws
            TooLittlePlantsException {
        ArrayList<Plant> updatedList;
        abstractActions = new EucalytusActions();
        updatedList = abstractActions.removePlants(p,j.getID(), intuserOption);
        return updatedList;
    }

    public ArrayList<Plant> getSunflower(ArrayList<Plant> p, Plant j, int intuserOption) throws
            TooLittlePlantsException {
        ArrayList<Plant> updatedList;
        abstractActions = new SunflowerActions();
        updatedList = abstractActions.removePlants(p,j.getID(), intuserOption);
        return updatedList;
    }

    public ArrayList<Plant> getCactus(ArrayList<Plant> p, Plant j, int intuserOption) throws TooLittlePlantsException {
        ArrayList<Plant> updatedList;
        abstractActions = new CactusActions();
        updatedList = abstractActions.removePlants(p,j.getID(), intuserOption);
        return updatedList;
    }

    public ArrayList<Plant> removePlantHelper(ArrayList<Plant> p, Plant j) {
        userInput = new Scanner(System.in);
        ArrayList<Plant> updatedList = new ArrayList<>();
        for (Plant k: p) {
            updatedList.add(j);
        }
        return updatedList;
    }



//REFACTORED METHOD --> SOLVED SEMANTIC COUPLING ISSUE
    public Plant maintainOptions(Plant p, Shop s) throws NegativeWaterException, NegativePesticideException,
            NegativeFertiliserException {
        effectPlant = attackPlantHelper(p);
        modifiedPlant = maintainPlant(effectPlant,s);
        return modifiedPlant;
    }

    //Code duplication
    public Plant maintainPlant(Plant p, Shop s) throws NegativeWaterException, NegativePesticideException,
            NegativeFertiliserException {
        userInput = new Scanner(System.in);
        userOption = userInput.next();
        switch (p.getPlantName()) {
            case "Eucalyptus": {
                abstractActions = new EucalytusActions();
                return maintainPlantHelper(p,s,userOption, abstractActions);
            }
            case "Cactus": {
                abstractActions = new CactusActions();
                return maintainPlantHelper(p,s,userOption, abstractActions);
            }
            case "Sunflower": {
                abstractActions = new SunflowerActions();
                return maintainPlantHelper(p,s,userOption, abstractActions);
            } default: {
                return null;
            }
        }
    }

// FIXED DUPLICATED CODE FROM HERE


    // Code duplication
    public Plant maintainPlantHelper(Plant p, Shop s, String userOption, AbstractSubject type) throws
            NegativeFertiliserException, NegativePesticideException, NegativeWaterException {
        switch (userOption) {
            case "1": {
                effectPlant = type.addWater(p,s);
                break;
            } case "2": {
                effectPlant = type.addSunlight(p);
                break;
            } case "3": {
                effectPlant = type.addFertiliser(p,s);
                break;
            } case "4": {
                effectPlant = type.addPesticides(p,s);
                break;
            }
            default: {
            }
        }
        return effectPlant;
    }


// Cohesion, Issues
    public void displayShopOptions(ArrayList<Plant> listOfPlants, Shop s)
            throws IllegalPurchaseException, TooLittlePlantsException {
        userInput = new Scanner(System.in);
        System.out.println("1. Purchase Water");
        System.out.println("2. Purchase Pesticides");
        System.out.println("3. Purchase Fertiliser");
        System.out.println("Choose an Option");
        selectOption(listOfPlants, s);
    }


    // Cohesion, Issues
    public void selectOption(ArrayList<Plant> p, Shop s) throws IllegalPurchaseException,
            TooLittlePlantsException {
        userInput = new Scanner(System.in);
        intuserOption = userInput.nextInt();
        switch (intuserOption) {
            case 1: {
                Shop.buyWater(s);
                break;
            } case 2: {
                Shop.buyFertiliser(s);
                break;
            } case 3: {
                Shop.buyPesticides(s);
                break;
            } default: {
                System.out.println("Sorry please choose another option");
                displayShopOptions(p, s);
                break;
            }
        }

    }


}






