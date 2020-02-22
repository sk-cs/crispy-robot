package game;

import exceptions.NegativeFertiliserException;
import plant.*;

public class EucalytusActions extends AbstractSubject {
    private final int damageMultiplier = 9;
    private final AbstractObserver eucalyptus = new Eucalyptus();


    @Override
    public Plant reducePlantHealth(Plant p, int damage) {
        if ((p.getPlantHealth() - (damageMultiplier * damage)) <  0) {
            p.setPlantHealth(0);
        } else {
            p.setPlantHealth(p.getPlantHealth() - (damageMultiplier * damage));
        }
        if (p.getPlantHealth() == 0) {
            System.out.println("Oh noo, your Eucalyptus died");
            return null;
        }

        return p;
    }

    @Override
    public Plant addFertiliser(Plant p, Shop s) throws NegativeFertiliserException {
        notifyObserver();
        return eucalyptus.healfactorFertiliser(p, s);

    }

    @Override
    public Plant addPesticides(Plant p, Shop s) throws NegativeFertiliserException {
        notifyObserver();
        return eucalyptus.healfactorFertiliser(p, s);
    }

    @Override
    public Plant addWater(Plant p, Shop s) throws NegativeFertiliserException {
        notifyObserver();
        return eucalyptus.healfactorFertiliser(p, s);
    }


    @Override
    public Plant addSunlight(Plant p) {
        notifyObserver();
        return eucalyptus.healfactorSun(p);

    }



}
