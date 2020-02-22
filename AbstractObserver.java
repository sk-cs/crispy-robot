package plant;

import exceptions.IllegalPurchaseException;
import exceptions.NegativeFertiliserException;
import exceptions.NegativePesticideException;
import exceptions.NegativeWaterException;
import plant.Plant;
import plant.Shop;

import java.util.ArrayList;
import java.util.Observer;

public interface AbstractObserver {
    // This is an abstract observer, which contains the methods of each plant, but contains of Sunflower, Cactus, and
    // Eucalyptus in the observers class.

    int waterUsed = 20;
    int fertiliserUsed = 30;
    int pesticideUsed = 15;

    Plant healfactorSun(Plant p);

    Plant healfactorWater(Plant p, Shop s) throws NegativeWaterException;

    Plant healfactorFertiliser(Plant p, Shop s) throws NegativeFertiliserException;

    Plant healfactorPesticides(Plant p, Shop s) throws NegativePesticideException;

    ArrayList<Plant> purchasePlant(ArrayList<Plant> existingArray, Shop s) throws IllegalPurchaseException;

    void update(String s);

}
