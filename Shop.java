package plant;

import java.util.Scanner;

import exceptions.IllegalPurchaseException;

public class Shop {
    private Scanner shopOptions;
    private String input;
    private static final int waterPrice = 50;
    private static final int fertiliserPrice = 60;
    private static final int pesticidePrice = 65;

    private int shopCredits;
    private int waterAmount;
    private int fertilizerAmount;
    private int pesticideAmount;
    private int intuserOption;

    public int getShopCredits() {
        return shopCredits;
    }

    // Low Coupling, class is independet, fields are independent and do not methods in other classes at all.

    public void setShopCredits(int shopCredits) {
        this.shopCredits = shopCredits;
    }

    public int getWaterAmount() {
        return waterAmount;
    }

    public void setWaterAmount(int waterAmount) {
        this.waterAmount = waterAmount;
    }

    public int getFertilizerAmount() {
        return fertilizerAmount;
    }

    public void setFertilizerAmount(int fertilizerAmount) {
        this.fertilizerAmount = fertilizerAmount;
    }

    public int getPesticideAmount() {
        return pesticideAmount;
    }

    public void setPesticideAmount(int pesticideAmount) {
        this.pesticideAmount = pesticideAmount;
    }

    public Shop(int shopCredits, int waterAmount, int fertilizerAmount, int pesticideAmount) {
        this.fertilizerAmount = fertilizerAmount;
        this.shopCredits = shopCredits;
        this.waterAmount = waterAmount;
        this.pesticideAmount = pesticideAmount;
    }

    public Shop() {
    }


    // EFFECTS displays all the options for users to interact with the shop

    public static void buyWater(Shop s) throws IllegalPurchaseException {
        if (s.getShopCredits() < waterPrice) {
            throw new IllegalPurchaseException();
        }
        s.setShopCredits(s.getShopCredits() - waterPrice);
        s.setWaterAmount(s.getWaterAmount() + 100);
    }

    public static void buyFertiliser(Shop s) throws IllegalPurchaseException {
        if (s.getShopCredits() < fertiliserPrice) {
            throw new IllegalPurchaseException();
        }
        s.setShopCredits(s.getShopCredits() - fertiliserPrice);
        s.setFertilizerAmount(s.getWaterAmount() + 100);
    }

    public static void buyPesticides(Shop s) throws IllegalPurchaseException {
        if (s.getShopCredits() < pesticidePrice) {
            throw new IllegalPurchaseException();
        }
        s.setShopCredits(s.getShopCredits() - pesticidePrice);
        s.setPesticideAmount(s.getPesticideAmount() + 100);
    }

}


