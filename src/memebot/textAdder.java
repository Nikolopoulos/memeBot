/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package memebot;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Basil Nikolopoulos <nikolopoulosbasil.com>
 */
public class textAdder {

    public static File addText(FlickrImage fimage, String[] text) throws MalformedURLException, IOException {

        BufferedImage image;
        try{
        image = ImageIO.read(new URL(fimage.getOriginalSizeURL()));
        }
        catch(Exception e){
            return null;
        }
        int width = image.getWidth();
        int height = image.getHeight();
        Graphics g = image.getGraphics();
        
        int fontSize = fimage.getHeight()/20;
        System.out.println("FImage height = "+fimage.getHeight()+" resulting font is "+fontSize);
        g.setFont(g.getFont().deriveFont((float)fontSize));
        FontMetrics metrics = g.getFontMetrics(g.getFont());
        int adv1 = metrics.stringWidth(text[0]);
        int adv2 = metrics.stringWidth(text[1]);
        int adv3 = metrics.stringWidth(text[2]);
        int adv = max(adv1, adv2, adv3);
        
        
        

        /*g.drawString(text[0], (int) width - adv, (int) (height * 0.5));
        g.drawString(text[1], (int) width - adv, (int) (height * 0.5) + fontSize);
        g.drawString(text[2], (int) width - adv, (int) (height * 0.5) + fontSize*2);
        */
        g.setColor(Color.black);
        g.drawString(text[0], ShiftWest((int) width - adv, 1), ShiftNorth((int) (height-fontSize*3+5), 1));
        g.drawString(text[0], ShiftWest((int) width - adv, 1), ShiftSouth((int) (height -fontSize*3+5), 1));
        g.drawString(text[0], ShiftEast((int) width - adv, 1), ShiftNorth((int) (height -fontSize*3+5), 1));
        g.drawString(text[0], ShiftEast((int) width - adv, 1), ShiftSouth((int) (height -fontSize*3+5), 1));
        
        g.drawString(text[1], ShiftWest((int) width - adv, 1), ShiftNorth((int) (height -fontSize*3+5)+fontSize, 1));
        g.drawString(text[1], ShiftWest((int) width - adv, 1), ShiftSouth((int) (height -fontSize*3+5)+fontSize, 1));
        g.drawString(text[1], ShiftEast((int) width - adv, 1), ShiftNorth((int) (height -fontSize*3+5)+fontSize, 1));
        g.drawString(text[1], ShiftEast((int) width - adv, 1), ShiftSouth((int) (height -fontSize*3+5)+fontSize, 1));
        
        g.drawString(text[2], ShiftWest((int) width - adv, 1), ShiftNorth((int) (height -fontSize*3+5)+fontSize*2, 1));
        g.drawString(text[2], ShiftWest((int) width - adv, 1), ShiftSouth((int) (height -fontSize*3+5)+fontSize*2, 1));
        g.drawString(text[2], ShiftEast((int) width - adv, 1), ShiftNorth((int) (height -fontSize*3+5)+fontSize*2, 1));
        g.drawString(text[2], ShiftEast((int) width - adv, 1), ShiftSouth((int) (height -fontSize*3+5)+fontSize*2, 1));
        
        g.setColor(Color.white);
        g.drawString(text[0], (int) width - adv, (int) (height -fontSize*3+5));
        g.drawString(text[1], (int) width - adv, (int) (height -fontSize*3+5) + fontSize);
        g.drawString(text[2], (int) width - adv, (int) (height -fontSize*3+5) + fontSize*2);
        g.setColor(Color.black);

        g.dispose();

        File f = new File(text[0] + ".png");
        try {
            ImageIO.write(image, "jpg", f);
        } catch (Exception ex) {
            Logger.getLogger(textAdder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return f;
    }

    private static int ShiftNorth(int p, int distance) {
        return (p - distance);
    }

    private static int ShiftSouth(int p, int distance) {
        return (p + distance);
    }

    private static int ShiftEast(int p, int distance) {
        return (p + distance);
    }

    private static int ShiftWest(int p, int distance) {
        return (p - distance);
    }

    private static int max(int a, int b, int c) {

        int max = a;
        if (a < b) {
            max = b;
        }
        if (c > max) {
            max = c;
        }
        return max;
    }
}
