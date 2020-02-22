package ui;

import exceptions.*;
import game.AbstractSubject;
import game.CactusActions;
import game.EucalytusActions;
import game.SunflowerActions;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import plant.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class PlantUI implements ActionListener {
    static JFrame mainMenu;
    static JButton playGame;
    static JButton aboutGame;
    static JButton quit;
 //   static JButton loadGame;
    static JLabel about;
    static JButton back;


    private Plant newPlant;
    static ArrayList<Plant> listOfPlants = new ArrayList<>();
    private JLabel label;
    private JLabel newPlantLabel;
    private final int minNoOfPlants = 1;
    private final int maxRoundNumber = 2;
    private final Random bugAttack = new Random();
    private final Shop gameShop = new Shop();


    private AbstractObserver abstractPlant;
    private AbstractSubject abstractActions;
    private Shop mutateShop = new Shop(0, 0, 0, 0);
    static Shop aShop = new Shop(1000,0,0,0);

    private MoreActions ui = new MoreActions();


    //FIELDS
    private Plant modifiedPlant;
    private Plant effectPlant;
    private String userOption;
    private int intuserOption;
    private static int roundNumber;
    private Scanner userInput;
    private int spacing = 100;
    private JLabel showCredits;
    private int nooftimesremaining = 3;


    private int minDamageBugs;
    private int maxDamageBugs;

    static JLabel showPlant;
    static JLabel showHealth;

    static JLabel showPlant1;
    static JLabel showHealth1;

    static JLabel showPlant2;
    static JLabel showHealth2;

    static JLabel showPlant3;
    static JLabel showHealth3;

    static JLabel showPlant4;
    static JLabel showHealth4;

    static JButton addOption;

    static JButton plantOptions;
    static JButton shop;
    static JButton addPlant;
    static JButton modifyPlant;
    static JTextField atextfield;
    static JButton nextRound;


    public void setUi() throws IOException, ParseException {
        mainMenu = new JFrame();
        backUi();
    }

    public void backUi() throws IOException, ParseException {
        mainMenu.setSize(450, 65);
        mainMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainMenu.setResizable(false);
        playGame = new JButton("PlayGame");
        aboutGame = new JButton("About Game");
        quit = new JButton("Quit");
  //      loadGame = new JButton("Load Game");
        playGame.addActionListener(this);
        aboutGame.addActionListener(this);
        quit.addActionListener(this);
   //     loadGame.addActionListener(this);
        mainMenu.add(playGame);
        mainMenu.add(aboutGame);
        mainMenu.add(quit);
    //    mainMenu.add(loadGame);
        mainMenu.setLayout(new FlowLayout());
        mainMenu.setVisible(true);
    }

    public void playGame() throws IOException, ParseException, FontFormatException {
        about = new JLabel("");
        mainMenu.setSize(800, 800);
      //  mainMenu.remove(loadGame);
        mainMenu.remove(playGame);
        mainMenu.remove(quit);
        mainMenu.remove(aboutGame);
        mainMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startNewGame();

    }

    public void aboutGame() {
        about = new JLabel("A game of chance, either heal all your plants or add a new plant, if even"
                + " one of your plant's health falls to zero, you lose");
        mainMenu.add(about);
        mainMenu.setSize(800, 800);
     //   mainMenu.remove(loadGame);
        mainMenu.remove(playGame);
        mainMenu.remove(quit);
        mainMenu.remove(aboutGame);
        mainMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playGame) {
            tryCatchPlayGame();
        } else if (e.getSource() == aboutGame) {
            aboutGame();
        } else if (e.getSource() == quit) {
            System.exit(0);
        } else if (e.getSource() == plantOptions) {
            addOptions();
        } else if (e.getSource() == addPlant) {
            getPlants();
        } else if (e.getSource() == addOption) {
            nextRound2();
        } else if (e.getSource() == nextRound) {
            nextRound();
        } else if (e.getSource() == modifyPlant) {
            modifyPlant();
        }
    }

    public void modifyPlant() {
        mainMenu.remove(modifyPlant);
        mainMenu.remove(addPlant);
        if (nooftimesremaining > 0) {
            for (Plant p : listOfPlants) {
                p.setPlantHealth(p.getPlantHealth() + 100);
            }
            nooftimesremaining--;
            mainMenu.add(plantOptions);
            mainMenu.setVisible(true);
        }
    }

    public void tryGameAgain() {
        mainMenu.remove(back);
        tryCatchBack();
        mainMenu.remove(about);
    }

    public void nextRound2() {
        try {
            getNextround();
        } catch (IllegalPurchaseException ex) {
            ex.printStackTrace();
        }
    }

    public void nextRound() {
        mainMenu.add(plantOptions);
        mainMenu.remove(nextRound);
        mainMenu.remove(atextfield);
        try {
            getNextRound(listOfPlants, aShop);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        mainMenu.setVisible(true);
    }

    public void getNextround() throws IllegalPurchaseException {
        nextRound = new JButton("next round");
        mainMenu.remove(addOption);
        mainMenu.remove(atextfield);
        mainMenu.add(nextRound);
        nextRound.addActionListener(this);
        mainMenu.setVisible(true);
        addPlant(listOfPlants,aShop);
    }


    public void getPlants() {
        mainMenu.setLayout(new FlowLayout(FlowLayout.LEFT));
        mainMenu.remove(addPlant);
        mainMenu.remove(plantOptions);
        mainMenu.remove(modifyPlant);
        addOption = new JButton("Submit Option");
        addOption.addActionListener(this);
        atextfield = new JTextField("Enter Eucalpytus,Sunflower, and Cactus");
        mainMenu.add(atextfield);
        mainMenu.add(addOption);
        mainMenu.setVisible(true);

    }

    public void addOptions() {
        addPlant = new JButton("Add Plant");
        addPlant.addActionListener(this);
        mainMenu.add(addPlant);
        modifyPlant = new JButton("Heal Plants");
        modifyPlant.addActionListener(this);
        mainMenu.add(modifyPlant);
        mainMenu.setVisible(true);
    }

    public void tryCatchPlayGame() {
        try {
            playGame();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ParseException ex) {
            ex.printStackTrace();
        } catch (FontFormatException ex) {
            ex.printStackTrace();
        }
    }

    public void tryCatchBack() {
        try {
            backUi();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }


    public void startNewGame() throws IOException, ParseException, FontFormatException {
        // This will have to be replaced with onclick, instead of
     //   mainMenu.setLayout(null);
        Plant newP = new Plant(100, 0.0, 0.0, "", "");
        System.out.println("Choosing Plant based on weather");
        System.out.println("Loading...");
        System.out.println(listOfPlants.size());
        newPlant = newP.chooseSP(); // Need to change this --------------->> ALERT/ALERT
        listOfPlants.add(newPlant); // Need to change this --------->> ALERT/ALERT
        System.out.println(listOfPlants.size());
        settingLabels();
        newRound(listOfPlants, mutateShop);
    }

    public void settingLabels() {
        label = new JLabel("Choosing a plant...");
        newPlantLabel = new JLabel("The plant chosen is" + " " + newPlant.getPlantName());
        label.setBounds(101, 62, 800, 40);
        label.setFont(new Font("Courier New", Font.PLAIN, 15));
        label.setBackground(Color.green);
        mainMenu.getContentPane().add(label);
        newPlantLabel.setBounds(101, 120, 800, 21);
        mainMenu.getContentPane().add(newPlantLabel);
        newPlantLabel.setFont(new Font("Courier New", Font.PLAIN, 15));
        newPlantLabel.setBackground(Color.green);
        plantOptions = new JButton("Plant Options");
        mainMenu.add(plantOptions);
        plantOptions.addActionListener(this);
        mainMenu.setVisible(true);
    }

    public void newRound(ArrayList<Plant> p, Shop s) throws IOException {
        try {
            newRoundHelper(p, s);
        } catch (NegativeWaterException e) {
            newRound(p, s);
        } catch (NegativeFertiliserException e) {
            newRound(p, s);
        } catch (NegativePesticideException e) {
            newRound(p, s);
        } catch (TooLittlePlantsException e) {
            System.out.println("Sorry you need at least one Plant to countinue Playing");
            newRound(p, s);
        } catch (IllegalPurchaseException e) {
            System.out.println("Sorry you do not have enough shop credits to purchase Plant");
            newRound(p, s);
        } catch (NullPointerException e) {
            System.out.println("Oh noo all your plants died Better luck next time");
        }
    }

    public void newRoundHelper(ArrayList<Plant> p, Shop s) throws NegativeWaterException, IllegalPurchaseException,
            IOException, NegativeFertiliserException, NegativePesticideException, TooLittlePlantsException {
        endGame(p, s);
        if (roundNumber <= maxRoundNumber && p.size() >= minNoOfPlants && p.size() <= 5) {
            update(s);
            addPlants(p);
            roundNumber++;
        } else if (p.size() == 0) {
            deleteplants();
        } else {
            endingScreen();
        }
        listOfPlants = p;
        aShop = s;
    }

    public void addPlants(ArrayList<Plant> p) {
        if (p.size() == 1) {
            displayPlants(20, p.get(0));
        } else if (p.size() == 2) {
            displayPlants1(40, p.get(1));
        } else if (p.size() == 3) {
            displayPlants2(60, p.get(2));
        } else if (p.size() == 4) {
            displayPlants3(80, p.get(3));
        } else if (p.size() == 5) {
            displayPlants4(100, p.get(4));
        }
    }

    public void deleteplants() {
        mainMenu.remove(label);
        mainMenu.remove(playGame);
        mainMenu.remove(aboutGame);
        //    mainMenu.remove(loadGame);
        mainMenu.remove(quit);
        mainMenu.remove(plantOptions);
        mainMenu.remove(newPlantLabel);
        JLabel ending = new JLabel("All your plants died try again some other time");
        mainMenu.add(ending);
        ending.setFont(new Font("Courier New", Font.PLAIN, 35));
        mainMenu.setVisible(true);
        System.exit(0);
    }

    public void endGame(ArrayList<Plant> p, Shop s) {
        int x = 500;
        s.setShopCredits(x + 360);
        for (Plant j: p) {
            if (j.getPlantHealth() == 0) {
                madeAnotherLabel();
            }
        }
    }

    public void madeAnotherLabel() {
        mainMenu.add(new JLabel("Wrong Choice, your Plant died, you LOSE!"));
        mainMenu.add(quit);
        mainMenu.remove(plantOptions);
        mainMenu.remove(addPlant);
        mainMenu.remove(modifyPlant);
        mainMenu.remove(addOption);
        mainMenu.remove(atextfield);
        mainMenu.setVisible(true);
    }

    public void displayPlants1(int x, Plant l) {
        showPlant1 = new JLabel("Name: " + l.getPlantName());
        showPlant1.setBounds(x, 400, 800, 21);
        mainMenu.getContentPane().add(showPlant1);
        showPlant1.setFont(new Font("Courier New", Font.PLAIN, 15));
        Integer i = l.getPlantHealth();
        showHealth1 = new JLabel("Plant Health: " + i.toString());
        showHealth1.setBounds(x, 450, 800, 21);
        mainMenu.getContentPane().add(showHealth1);
        showHealth1.setFont(new Font("Courier New", Font.PLAIN, 15));
    }

    public void displayPlants2(int x, Plant l) {
        showPlant2 = new JLabel("Name: " + l.getPlantName());
        showPlant2.setBounds(x, 400, 800, 21);
        mainMenu.getContentPane().add(showPlant2);
        showPlant2.setFont(new Font("Courier New", Font.PLAIN, 15));
        Integer i = l.getPlantHealth();
        showHealth2 = new JLabel("Plant Health: " + i.toString());
        showHealth2.setBounds(x, 450, 800, 21);
        mainMenu.getContentPane().add(showHealth2);
        showHealth2.setFont(new Font("Courier New", Font.PLAIN, 15));
    }

    public void displayPlants3(int x, Plant l) {
        showPlant3 = new JLabel("Name: " + l.getPlantName());
        showPlant3.setBounds(x, 400, 800, 21);
        mainMenu.getContentPane().add(showPlant3);
        showPlant3.setFont(new Font("Courier New", Font.PLAIN, 15));
        Integer i = l.getPlantHealth();
        showHealth3 = new JLabel("Plant Health: " + i.toString());
        showHealth3.setBounds(x, 450, 800, 21);
        mainMenu.getContentPane().add(showHealth3);
        showHealth3.setFont(new Font("Courier New", Font.PLAIN, 15));
    }

    public void displayPlants4(int x, Plant l) {
        showPlant4 = new JLabel("Name: " + l.getPlantName());
        showPlant4.setBounds(x, 400, 800, 21);
        mainMenu.getContentPane().add(showPlant4);
        showPlant4.setFont(new Font("Courier New", Font.PLAIN, 15));
        Integer i = l.getPlantHealth();
        showHealth4 = new JLabel("Plant Health: " + i.toString());
        showHealth4.setBounds(x, 450, 800, 21);
        mainMenu.getContentPane().add(showHealth4);
        showHealth4.setFont(new Font("Courier New", Font.PLAIN, 15));
    }

    public void update(Shop s) {
        showCredits = new JLabel("Shop Credits: " + s.getShopCredits());
        showCredits.setBounds(680, 80, 800, 21);
        mainMenu.getContentPane().add(showCredits);
        showCredits.setFont(new Font("Courier New", Font.PLAIN, 1));
    }

    public void displayPlants(int x, Plant l) {
        showPlant = new JLabel("Name: " + l.getPlantName());
        showPlant.setBounds(x, 400, 800, 21);
        mainMenu.getContentPane().add(showPlant);
        showPlant.setFont(new Font("Courier New", Font.PLAIN, 15));
        Integer i = l.getPlantHealth();
        showHealth = new JLabel("Plant Health: " + i.toString());
        showHealth.setBounds(x, 450, 800, 21);
        mainMenu.getContentPane().add(showHealth);
        showHealth.setFont(new Font("Courier New", Font.PLAIN, 15));
    }

//    private void modifyUserInput(ArrayList<Plant> p, Shop s) throws IllegalPurchaseException, TooLittlePlants
//    Exception,
//            NegativeWaterException, NegativePesticideException, NegativeFertiliserException, IOException {
//        System.out.println("The next round has started" + "1: Maintain Plants " + "2. Shop Items");
//        intuserOption = userInput.nextInt();
//        roundNumber++;
//
//        if (intuserOption == 1) {
//            System.out.println(p.get(0).getPlantHealth());
//            sg.makePlantChanges(p, mutateShop);
//
//        } else if (intuserOption == 2) {
//            System.out.println(p.get(0).getPlantHealth());
//            sg.displayShopOptions(p, mutateShop);
//        }
//
//    }
    //____________________________________________________

    public void endingScreen() {
        System.out.println("Congratulations, you win this Game \n Thanks for playing!");
        JLabel jj = new JLabel("Congratulations, you win this Game \n Thanks for playing!");
        mainMenu.setVisible(true);

        JFrame frame = new JFrame();
        frame.setSize(800,800);
        frame.add(jj);
        frame.setVisible(true);
       // System.exit(0);
    }

    void getNextRound(ArrayList<Plant> p, Shop s) throws IOException {
        listOfPlants = bugsAttack(p); // This keeps on increasing damage, as I haven't added a healing;
        System.out.println(listOfPlants.size());
        Save.saveFile(p);
        Save.saveShop(s);
        newRound(listOfPlants, s);
    }

//    void dosomething() {
//        if (listOfPlants.size() == 1) {
//            Integer i = listOfPlants.get(0).getPlantHealth();
//            showHealth.setText(i.toString());
//            mainMenu.setVisible(true);
//        } else if (listOfPlants.size() == 2) {
//            Integer i = listOfPlants.get(1).getPlantHealth();
//            showHealth1.setText(i.toString());
//            mainMenu.setVisible(true);
//        } else if (listOfPlants.size() == 3) {
//            Integer i = listOfPlants.get(2).getPlantHealth();
//            showHealth2.setText(i.toString());
//            mainMenu.setVisible(true);
//        } else if (listOfPlants.size() == 4) {
//            Integer i = listOfPlants.get(3).getPlantHealth();
//            showHealth3.setText(i.toString());
//            mainMenu.setVisible(true);
//        } else if (listOfPlants.size() == 5) {
//            Integer i = listOfPlants.get(4).getPlantHealth();
//            showHealth4.setText(i.toString());
//            mainMenu.setVisible(true);
//        }
//    }



    public ArrayList<Plant> bugsAttack(ArrayList<Plant> listOfPlants) {
        userInput = new Scanner(System.in);
        minDamageBugs = 5;
        maxDamageBugs = 15;
        int nextBugAttack = bugAttack.nextInt((maxDamageBugs - minDamageBugs) + minDamageBugs);
        for (int i = 0; i < listOfPlants.size(); i++) {
            if (listOfPlants.get(i).getPlantHealth() == 0) {
                listOfPlants.remove(i);
            } else {
                listOfPlants.set(i, changeAttack(listOfPlants.get(i), nextBugAttack));
            }
        }

        minDamageBugs = minDamageBugs + 2;
        maxDamageBugs = maxRoundNumber + 2;
        return listOfPlants;
    }


    public Plant changeAttack(Plant p, int anotherAttack) {
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
        return p;
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
  //      userOption = userInput.next();
        ArrayList<Plant> updatedList = new ArrayList<>();
        for (Plant j: p) {
            updatedList.add(j);
        }
        return updatedList;
    }

    public ArrayList<Plant> addPlant(ArrayList<Plant> p, Shop s) throws
            IllegalPurchaseException {
        ArrayList<Plant> updatedList = addPlantHelper(p, s);
        switch (atextfield.getText()) {
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




}
