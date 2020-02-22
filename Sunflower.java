package plant;

import exceptions.IllegalPurchaseException;
import exceptions.NegativeFertiliserException;
import exceptions.NegativePesticideException;
import exceptions.NegativeWaterException;

import java.util.ArrayList;

public class Sunflower implements AbstractObserver {
    private final int sunflowerPrice = 30;

    // High amounts of code duplication, the only difference is the health added, by each method.
    // Semantic Coupling

    public Plant healfactorSun(Plant p) {
        p.setPlantHealth(p.getPlantHealth() + 40);
        return p;
    }

    public Plant healfactorWater(Plant p, Shop s) throws NegativeWaterException {
        if ((s.getFertilizerAmount() - waterUsed) < 0) {
            update("You do not have enough water left");
            throw new NegativeWaterException("You do not have enough water left");
        }
        p.setPlantHealth(p.getPlantHealth() + 30);
        s.setWaterAmount(s.getWaterAmount() - waterUsed);
        return p;
    }

    public Plant healfactorFertiliser(Plant p, Shop s) throws NegativeFertiliserException {
        if ((s.getFertilizerAmount() - fertiliserUsed) < 0) {
            update("You do not have enough fertiliser left");
            throw new NegativeFertiliserException("You do not have enough fertiliser left");
        }

        p.setPlantHealth(p.getPlantHealth() + 20);
        s.setFertilizerAmount(s.getFertilizerAmount() - fertiliserUsed);
        return p;
    }

    public Plant healfactorPesticides(Plant p, Shop s) throws NegativePesticideException {
        if ((s.getFertilizerAmount() - pesticideUsed) < 0) {
            update("You do not have enough Pesticides left");
            throw new NegativePesticideException("You do not have enough pesticides left");
        }
        p.setPlantHealth(p.getPlantHealth() + 10);
        s.setPesticideAmount(s.getPesticideAmount() - pesticideUsed);
        return p;
    }

    public ArrayList<Plant> purchasePlant(ArrayList<Plant> p, Shop s) throws
            IllegalPurchaseException {
        if (s.getShopCredits() < sunflowerPrice) {
            throw new IllegalPurchaseException();
        }
        p.add(new Plant(100, 7.5, 200, "Sunflower",
                Plant.randomIDGenerator(Plant.random, "Sunflower")));
        s.setShopCredits(s.getShopCredits() - sunflowerPrice);
        update("Thank you for purchasing a Sunflower Plant");
        update("Your remaining balance is" + s.getShopCredits());
        return p;
    }



    public void update(String s) {
        System.out.println("You made a change to a plant");
        System.out.println(s);

    }
}


