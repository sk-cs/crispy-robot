package game;

import exceptions.NegativeFertiliserException;
import plant.*;

public class CactusActions extends AbstractSubject {
    private final int damageMultiplier = 8;
    private final AbstractObserver cactus = new Cactus();

// Semantic Coupling Issues

    @Override
    public Plant reducePlantHealth(Plant p, int damage) {
        if ((p.getPlantHealth() - (damageMultiplier * damage)) <  0) {
            p.setPlantHealth(0);
        } else {
            p.setPlantHealth(p.getPlantHealth() - (damageMultiplier * damage));
        }
        if (p.getPlantHealth() <= 0) {
            System.out.println("On noo, your Cactus died");
        }

        return p;
    }

    @Override
    public Plant addFertiliser(Plant p, Shop s) throws NegativeFertiliserException {
        notifyObserver();
        return cactus.healfactorFertiliser(p, s);
    }

    @Override
    public Plant addPesticides(Plant p, Shop s) throws NegativeFertiliserException {
        notifyObserver();
        return cactus.healfactorFertiliser(p, s);
    }

    @Override
    public Plant addWater(Plant p, Shop s) throws NegativeFertiliserException {
        notifyObserver();
        return cactus.healfactorFertiliser(p, s);
    }


    @Override
    public Plant addSunlight(Plant p) {
        notifyObserver();
        return cactus.healfactorSun(p);
    }
}
