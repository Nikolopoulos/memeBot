/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package memebot;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Basil Nikolopoulos <nikolopoulosbasil.com>
 */
public class getImage {

    static final ArrayList<String> imageURLS = new ArrayList<String>();
    static final ArrayList<FlickrImage> fimages = new ArrayList<FlickrImage>();
    static final Semaphore sema = new Semaphore(1);

    public static void loadPersona(String name) {
        try {
            String searchURL = "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=e5e3370bb8fa5f09427114afa63280af&text=" + URLEncoder.encode(name) + "&format=json";

            URL url = new URL(searchURL);
            URLConnection conn = url.openConnection();
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-GB;     rv:1.9.2.13) Gecko/20101203 Firefox/3.6.13 (.NET CLR 3.5.30729)");
            InputStream is = conn.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String line = null;
            String JSONText = "";

            while ((line = reader.readLine()) != null) {
                JSONText += line;
            }
            reader.close();

            JSONText = JSONText.substring(14, JSONText.length() - 1);
            JsonObject parsedObject = new JsonParser().parse(JSONText).getAsJsonObject();
            JsonArray photos = parsedObject.getAsJsonObject("photos").getAsJsonArray("photo");
            Iterator<JsonElement> it = photos.iterator();
            while (it.hasNext()) {
                JsonElement el = (JsonElement) it.next();
                String farmId = el.getAsJsonObject().get("farm").getAsString();
                String serverId = el.getAsJsonObject().get("server").getAsString();
                String id = el.getAsJsonObject().get("id").getAsString();
                String secret = el.getAsJsonObject().get("secret").getAsString();
                Thread t = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        FlickrImage f = new FlickrImage(id, farmId, secret, serverId);
                        try {
                            sema.acquire();
                        } catch (InterruptedException ex) {
                            Logger.getLogger(getImage.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        fimages.add(f);
                        sema.release();
                        //System.out.println(f.getOriginalSizeURL());
                    }
                });
                t.start();
            }

            //for()
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static FlickrImage getImage() {
        Random rng = new Random();
        rng.setSeed(System.currentTimeMillis());
        boolean found = false;
        int randomInt = -1;
        while (!found) {
            randomInt = rng.nextInt(fimages.size());
            if (fimages.get(randomInt).getHeight() > 200 && fimages.get(randomInt).getWidth() > 200) {
                found = true;
            }
        }

        return fimages.get(randomInt);
    }
}
