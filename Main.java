package ui;

import exceptions.*;
import network.WeatherAPI;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Main {


    //EFFECTS starts the ui.game

    public static void main(String[] args) throws IOException, ClassNotFoundException, ParseException,
            IllegalPurchaseException, NegativeFertiliserException, NegativeWaterException, NegativePesticideException,
            TooLittlePlantsException {


        GameMenu newG = new GameMenu();
        newG.getMenuOption();
        System.out.println("END of Main method");


    }
}

