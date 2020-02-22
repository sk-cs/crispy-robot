package ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import plant.Plant;
import ui.PlantUI.*;

import javax.swing.*;

import static ui.PlantUI.*;

public class MoreActions implements ActionListener {
    private int nooftimesremaining = 3;

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == modifyPlant) {
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

}

//    public void getNextround() {
//        nextRound = new JButton("next round");
//        mainMenu.remove(addOption);
//        mainMenu.remove(atextfield);
//        mainMenu.add(nextRound);
//        nextRound.addActionListener(this);
//        mainMenu.setVisible(true);
//    }
//
//
//    public void getPlants() {
//        mainMenu.setLayout(new FlowLayout(FlowLayout.LEFT));
//        mainMenu.remove(addPlant);
//        mainMenu.remove(plantOptions);
//        mainMenu.remove(modifyPlant);
//        addOption = new JButton("Submit Option");
//        addOption.addActionListener(this);
//        atextfield = new JTextField("Enter the plant you want to add here");
//        mainMenu.add(atextfield);
//        mainMenu.add(addOption);
//        mainMenu.setVisible(true);
//
//    }
//
//    public void addOptions() {
//        addPlant = new JButton("Add Plant");
//        addPlant.addActionListener(this);
//        mainMenu.add(addPlant);
//        modifyPlant = new JButton("Modify Plant");
//        modifyPlant.addActionListener(this);
//        mainMenu.add(modifyPlant);
//        mainMenu.setVisible(true);
//    }
//
//    public void nextRound() {
//        mainMenu.add(plantOptions);
//        mainMenu.remove(nextRound);
//        mainMenu.remove(atextfield);
//        try {
//            ui.getNextRound(listOfPlants, aShop);
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//        mainMenu.setVisible(true);
//    }


