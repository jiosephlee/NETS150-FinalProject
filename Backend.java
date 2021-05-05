import java.io.IOException;

import javax.swing.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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
    private ArrayList<Integer> userActivity;

    private ArrayList<Location> locations;

    // web scraping tools
    private String baseURL; 
    private Document currentDoc;
    
    public Backend (){
        userFood = new ArrayList<Integer>();
        userActivity = new ArrayList<Integer>();
    }
    
    public void setUserFood (int foodCategory) {
        userFood.add(foodCategory);
    }
    
    public void setUserActivity (int activityCategory) {
        userActivity.add(activityCategory);
    }
    
    public void calculate() {

        this.locations = getLocations();

        for (Location l : locations) {
            System.out.print(l.getAddress() + " at latitude = " + l.getLatitude() + " and longitude = " + l.getLongitude() + "\n");
        }

    }

    /**
     * Assumptions: 
     *      The second address tag will always contain the address of the top Yelp recommendation
     *      Only consider locations whose address is in an address html tag
     * @return An ArrayList with the top two food addresses based on preference
     */
    public ArrayList<Location> getLocations() {

        ArrayList<Location> l = new ArrayList<Location>();

        ArrayList<String> userPreferences = new ArrayList<String>();
        for (int i : userFood) {
            userPreferences.add(FOOD_CATEGORIES[i - 1]);
            System.out.println(FOOD_CATEGORIES[i - 1]);
        }
        for (int i : userActivity) {
            userPreferences.add(ACTIVITY_CATEGORIES[i - 1]);
            System.out.println(ACTIVITY_CATEGORIES[i - 1]);
        }

        for (String preference : userPreferences) {

            this.baseURL = "https://www.yelp.com/search?find_desc=" + preference + "&find_loc=New%20York%2C%20NY%2010035";
            try {
                this.currentDoc = Jsoup.connect(this.baseURL).get();
            } catch (IOException e) {
                System.out.println("Could not get yelp website for " + preference + " query.");
            }
    
            // second occurence of address tag contains the street name of the first recommendation
            Element address1 = currentDoc.select("address").get(1);
            // neighbor of address tag contains the area name
            Element address2 = address1.nextElementSibling();

            String firstAddress = address1.text() + ", " + address2.text();

            // call helper function to get array of lat and long values
            Double[] firstCoordinate = getCoordinates(firstAddress);

            // create new location object based on longitude, latitude, and address name
            Location firstLocation = new Location(firstCoordinate[1], firstCoordinate[0], firstAddress);


            // third occurrence of address tag contians the street name of the second recommendation
            Element address3 = currentDoc.select("address").get(2);
            // neighbor of address tag contains area name
            Element address4 = address3.nextElementSibling();

            String secondAddress = address3.text() + ", " + address4.text(); 
            Double[] secondCoordinate = getCoordinates(secondAddress);

            Location secondLocation = new Location(secondCoordinate[1], secondCoordinate[0], secondAddress);

            l.add(firstLocation);
            l.add(secondLocation);

        }

        return l;

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
