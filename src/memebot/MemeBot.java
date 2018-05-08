/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package memebot;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 *
 * @author Basil Nikolopoulos <nikolopoulosbasil.com>
 */
public class MemeBot {

    /**
     * @param args the command line arguments
     */
    private static String[] people = {"Γιώργος","Michael Jackson","Ισίδορος","Κάρολος παπούλιας", "Κωνσταντίνος Καραμανλής", "Αλέξης Τσίπρας", "Αντώνης Σαμαράς", "Μιλόσεβιτς", "Ιβάν Ντράγκο", "Κυριάκος Μιτσοτάκης", "Ηλίας Μαμαλάκης", "Έκτορας Μποτρίνι", "Στέφανος Χίος", "Μάκης Τριανταφυλλόπουλος", "Φώτης Κουβέλης"};

    public static void main(String[] args) throws IOException {

        for (String name : people) {
            getImage.loadPersona(name);
        }
        while (true) {
            String[] lyrics = getLyrics.getLyrics(); 
            textAdder.addText(getImage.getImage(), lyrics);
        }
    }

}
