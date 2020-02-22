package ui;

import exceptions.*;
import org.json.simple.parser.ParseException;
import plant.Plant;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameMenu {
  //  private Scanner input = new Scanner(System.in);
    private String userInput;
    private final GamePlay gamePlay = new GamePlay();
    private  PlantUI pi = new PlantUI();

//Getters
    //MODIFIES userInput
    public void getMenuOption() throws IOException, ParseException, ClassNotFoundException,
            IllegalPurchaseException, TooLittlePlantsException, NegativeWaterException,
            NegativePesticideException, NegativeFertiliserException {
      //  onClickOptions(mainMenu);
        pi.setUi();

    }

    //MODIFIES this

//    public void onClickOptions(JFrame menu) throws IOException, ParseException, ClassNotFoundException,
//            IllegalPurchaseException, TooLittlePlantsException, NegativeWaterException, NegativePesticideException,
//            NegativeFertiliserException {
//
//          //  gamePlay.startGame();
//        }


//        switch (userInput) {
//            case "1": {
//                gamePlay.startGame();
//                break;
//            } case "2": {
//                aboutGame();
//                break;
//            } case "3": {
//                exitGame();
//                break;
//            } case "4": {
//                gamePlay.newRound(Save.loadFile(), Save.loadShopFile());
//                break;
//            } default: {
//                getMenuOption();
//                break;
//            } }

    //EFFECTS displays information about ui.game

    private void aboutGame() {
        System.out.println("Welcome to Plant Quick! \n this is a ui.game where you grow as many plants as you can under"
                + "a specific time while trying to stop bugs from eating your plants");
        System.out.println("Each round there is a random time limit that you have to survive, and not let any of your"
                + "plants die");
        System.out.println("If even one of your plants die, you lose the ui.game");
        System.out.println("Good luck! May be the best Gardener in the wordl!");
    }
    // EFFECTS: exits the ui.game;

    private void exitGame() {
        System.out.println("Cya again!");
        System.exit(0);

    }



}

