import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

import com.sun.jdi.Location;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.regex.*;
import java.util.ArrayList;

/**
 * This class instantiates a ConnectFive object, which is the model for the
 * game. As the user clicks the game board, the model is updated. Whenever the
 * model is updated, the game board repaints itself and updates its status
 * JLabel to reflect the current state of the model.
 * 
 * This game adheres to a Model-View-Controller design framework. This framework
 * is very effective for turn-based games. We STRONGLY recommend you review
 * these lecture slides, starting at slide 8, for more details on
 * Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec37.pdf
 * 
 * In a Model-View-Controller framework, GameBoard stores the model as a field
 * and acts as both the controller (with a MouseListener) and the view (with its
 * paintComponent method and the status JLabel).
 */
@SuppressWarnings("serial")
public class Backend extends JPanel{

    private static final String[] FOOD_CATEGORIES = {"Thai", "Mexican"};
    private static final String[] ACTIVITY_CATEGORIES = {"Arcade", "Taking Photos"};
    private ArrayList<Integer> userFood;
    private int userBudget;
    private ArrayList<Integer> userActivity;

    private ArrayList<Location> finalLocations;

    // web scraping tools
    private String baseURL; 
    private Document currentDoc;
    
    public Backend (){
        userFood = new ArrayList<Integer>();
        userActivity = new ArrayList<Integer>();
        finalLocations = new ArrayList<Location>();
    }
    
    public void setUserFood (int foodCategory) {
        userFood.add(foodCategory);
    }
    
    public void setUserActivity (int activityCategory) {
        userActivity.add(activityCategory);
    }
    
    public void setUserBudget (int budget) {
        userBudget = budget;
    }
    
    public void calculate() {

        ArrayList<String> foodAddresses = getFoodAddresses();
        // foodAddresses.addAll(getActivityAddresses());        

        // for (String address : foodAddresses) {
        //     Double[] coordinateArr = getCoordinates(address);
        //     Location loc = new Location(coordinateArr[1], coordinateArr[0], address);
        //     finalLocations.add(loc);
        // }

    }

    /**
     * Assumptions:
     * @return An ArrayList with the top two food addresses based on preference
     */
    public ArrayList<String> getFoodAddresses() {

        ArrayList<String> foodAddresses = new ArrayList<String>();

        ArrayList<String> foodPreferences = new ArrayList<String>();
        for (int i : userFood) {
            foodPreferences.add(FOOD_CATEGORIES[i - 1]);
            System.out.println(FOOD_CATEGORIES[i - 1]);
        }

        for (String foodPreference : foodPreferences) {

            // this.baseURL = "https://www.yelp.com/search?find_desc=" + foodPreference + "&find_near=new-york-city-new-york-14&ns=1";
            this.baseURL = "https://www.yelp.com/search?find_desc=thai&find_near=new-york-city-new-york-14&ns=1";
            try {
                this.currentDoc = Jsoup.connect(this.baseURL).get();
            } catch (IOException e) {
                System.out.println("Could not get yelp website for " + foodPreference + " query.");
            }
    
            Element address1 = currentDoc.select("address").get(1);
            // Element section = currentDoc.select("div. container__09f24__21w3G hoverable__09f24__2nTf3 margin-t3__09f24__5bM2Z margin-b3__09f24__1DQ9x padding-t3__09f24__-R_5x padding-r3__09f24__1pBFG padding-b3__09f24__1vW6j padding-l3__09f24__1yCJf border--top__09f24__8W8ca border--right__09f24__1u7Gt border--bottom__09f24__xdij8 border--left__09f24__rwKIa border-color--default__09f24__1eOdn").first();
            System.out.println(address1.text());

            Element address2 = address1.nextElementSibling();
            System.out.println(address2.text());

            String str1 = address1 + ", " + address2;

            Double[] test = getCoordinates(str1);
            System.out.println(test[0]);
            System.out.println(test[1]);

            Element address3 = currentDoc.select("address").get(2);
            System.out.println(address3.text());

            Element address4 = address3.nextElementSibling();
            System.out.println(address4.text());

        }


        return foodAddresses;
    }

    public ArrayList<String> getActivityAddresses() {
        return null;
    }
    
    public Double[] getCoordinates(String address) {
        String URL = "http://mapquestapi.com/geocoding/v1/address?"
                + "key=OeyNs9RlSfU1pePd8vJEfAA3mJMSTvU9&location=";
        try {
            Document doc = Jsoup.connect(URL + address + "&outFormat=xml").get();
            String text = doc.toString();
            String regexOne = "<lat>\n(.*)";
            String regexTwo = "<lng>\n(.*)";
            Pattern patternOne = Pattern.compile(regexOne);
            Pattern patternTwo = Pattern.compile(regexTwo);
            Matcher matchOne = patternOne.matcher(text);
            String lat = "";
            if (matchOne.find()) {
                lat = matchOne.group(1);
            }
            Matcher matchTwo = patternTwo.matcher(text);
            String lon = "";
            if (matchTwo.find()) {
                lon = matchTwo.group(1);
            }
            Double[] output = {Double.parseDouble(lat),Double.parseDouble(lon)};
            return output;
        } catch (IOException e) {
            System.out.println(e);
            System.out.println("ERROR");
            Double[] output = {-1.0,-1.0};
            return output;
        }
    }
}
