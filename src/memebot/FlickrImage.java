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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Basil Nikolopoulos <nikolopoulosbasil.com>
 */
public class FlickrImage {

    private String id;
    private String farm;
    private String secret;
    private String originalSecret;
    private String server;
    private int width;
    private int height;

    public FlickrImage(String id, String farm, String secret, String server) {
        try {
            this.id = id;
            this.farm = farm;
            this.secret = secret;
            this.server = server;
            
            String searchURL = "https://api.flickr.com/services/rest/?method=flickr.photos.getInfo&api_key=e5e3370bb8fa5f09427114afa63280af&photo_id="+id+"&format=json&nojsoncallback=1";
            
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
            //System.out.println(JSONText);
            reader.close();
            
            JsonObject photo = new JsonParser().parse(JSONText).getAsJsonObject().get("photo").getAsJsonObject();
            this.originalSecret = photo.get("originalsecret").getAsString();
            
            
            searchURL = "https://api.flickr.com/services/rest/?method=flickr.photos.getSizes&api_key=e5e3370bb8fa5f09427114afa63280af&photo_id="+id+"&format=json&nojsoncallback=1";
            
            
            url = new URL(searchURL);
            conn = url.openConnection();
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-GB;     rv:1.9.2.13) Gecko/20101203 Firefox/3.6.13 (.NET CLR 3.5.30729)");
            is = conn.getInputStream();
            
            reader = new BufferedReader(new InputStreamReader(is));
            
            line = null;
            JSONText = "";
            
            while ((line = reader.readLine()) != null) {
                JSONText += line;
            }
            //System.out.println(JSONText);
            reader.close();
            
            JsonArray sizes = new JsonParser().parse(JSONText).getAsJsonObject().get("sizes").getAsJsonObject().get("size").getAsJsonArray();
            Iterator <JsonElement> it = sizes.iterator();
            
            while(it.hasNext()){
                JsonObject el = it.next().getAsJsonObject();
                if(el.get("label").getAsString().equalsIgnoreCase("original")){
                    this.setHeight(el.get("height").getAsInt());
                    this.setWidth(el.get("width").getAsInt());
                }
            }
                        
        } catch (Exception ex) {
            //Logger.getLogger(FlickrImage.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getFarm() {
        return farm;
    }

    public void setFarm(String farm) {
        this.farm = farm;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getOriginalSecret() {
        return originalSecret;
    }

    public void setOriginalSecret(String originalSecret) {
        this.originalSecret = originalSecret;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }
    
    public String getOriginalSizeURL(){
        return "https://farm"+farm+".staticflickr.com/"+server+"/"+id+"_"+originalSecret+"_o.jpg";
    }

}
