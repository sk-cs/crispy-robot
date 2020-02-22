package game;

import exceptions.NegativeFertiliserException;
import exceptions.NegativePesticideException;
import exceptions.NegativeWaterException;
import plant.*;

public class SunflowerActions extends AbstractSubject {
    private final int damageMultiplier = 7;
    private final AbstractObserver sunflower = new Sunflower();

// Semantic Coupling Issues

    @Override
    public Plant reducePlantHealth(Plant p, int damage) {
        if ((p.getPlantHealth() - (damageMultiplier * damage)) <  0) {
            p.setPlantHealth(0);
        } else {
            p.setPlantHealth(p.getPlantHealth() - (damageMultiplier * damage));
        }
        if (p.getPlantHealth() <= 0) {
            System.out.println("On noo, your sunflower died");
            return null;
        }
        return p;
    }


    @Override
    public Plant addFertiliser(Plant p, Shop s) throws NegativeFertiliserException {
        notifyObserver();
        return sunflower.healfactorFertiliser(p, s);
    }

    @Override
    public Plant addPesticides(Plant p, Shop s) throws NegativePesticideException {
        notifyObserver();
        return sunflower.healfactorPesticides(p, s);
    }

    @Override
    public Plant addWater(Plant p, Shop s) throws NegativeWaterException {
        notifyObserver();
        return sunflower.healfactorWater(p, s);
    }


    @Override
    public Plant addSunlight(Plant p) {
        notifyObserver();
        System.out.println("TEST");
        return sunflower.healfactorSun(p);
    }
}
