import java.io.IOException;

import javax.swing.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.util.regex.*;
import java.util.ArrayList;

/**
 * Backend code for the GUI.
 */
@SuppressWarnings("serial")
public class Backend extends JPanel{

    private static final String[] FOOD_CATEGORIES = {"Thai", "Mexican", "Italian", "Korean", "Chinese", "American"};
    private static final String[] ACTIVITY_CATEGORIES = {"Arcade", "Karaoke", "Taking Photos", "Bowling"};
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

        // debugging
        for (Location l : locations) {
            String isFood = "";
            if (l.isFood()) {
                isFood = " is food ";
            } else {
                isFood = "is activity";
            }

            System.out.print("Activity: " + l.getActivityName() + " " + l.getName() + isFood +
                    l.getAddress() + " at latitude = " + l.getLatitude() + " and longitude = "
                    + l.getLongitude() + "\n");
        }

    }

    /**
     * Assumptions: 
     *      We assume that Yelp won't change the layout of their HTML tags in future website updates. Specifically:
     * 
     *      The second address tag will always contain the street address of the top Yelp recommendation
     *      The third address tag will always contain the street address of the second best Yelp recommendation
     *      The sibling element of the address tag will always contain the area/county name
     *      The name of the venue will always be in the previous sibling element of the grand parent of the address tag
     *      
     *      We will also only consider locations whose address is in an address HTML tag
     * 
     * @return An ArrayList with the top two food addresses based on preference
     */
    public ArrayList<Location> getLocations() {

        ArrayList<Location> l = new ArrayList<Location>();

        ArrayList<String> userPreferences = new ArrayList<String>();
        for (int i : userFood) {
            userPreferences.add(FOOD_CATEGORIES[i]);
            System.out.println(FOOD_CATEGORIES[i]);
        }
        for (int i : userActivity) {
            userPreferences.add(ACTIVITY_CATEGORIES[i]);
            System.out.println(ACTIVITY_CATEGORIES[i]);
        }

        for (String preference : userPreferences) {

            this.baseURL = "https://www.yelp.com/search?find_desc=" + preference + "&find_loc=New%20York%2C%20NY%2010035";
            try {
                this.currentDoc = Jsoup.connect(this.baseURL).get();
            } catch (IOException e) {
                System.out.println("Could not get yelp website for " + preference + " query.");
            }

            boolean isFood = false;

            // check if this preference is a food preference:
            for (String s : FOOD_CATEGORIES) {
                if (preference.equals(s)) {
                    isFood = true;
                }
            }

            // second occurence of address tag contains the street name of the first recommendation
            Element address1 = currentDoc.select("address").get(1);
            // neighbor of address tag contains the area name
            Element address2 = address1.nextElementSibling();

            // assumption — the name of the venue is in the neighboring div tag to the address tag's grandparent element
            Element parentDiv1 = address1.parent();
            Element parentDiv2 = parentDiv1.parent();
            Element firstVenueLocation = parentDiv2.previousElementSibling();

            String firstAddress = address1.text() + ", " + address2.text();

            // call helper function to get array of lat and long values
            Double[] firstCoordinate = getCoordinates(firstAddress);

            // create new location object based on longitude, latitude, and address name
            Location firstLocation = new Location(firstCoordinate[1], firstCoordinate[0], firstAddress, firstVenueLocation.text(), isFood, preference);


            // third occurrence of address tag contians the street name of the second recommendation
            Element address3 = currentDoc.select("address").get(2);
            // neighbor of address tag contains area name
            Element address4 = address3.nextElementSibling();

            Element parentDiv3 = address3.parent();
            Element parentDiv4 = parentDiv3.parent();
            Element secondVenueLocation = parentDiv4.previousElementSibling();

            String secondAddress = address3.text() + ", " + address4.text();
            Double[] secondCoordinate = getCoordinates(secondAddress);

            Location secondLocation = new Location(secondCoordinate[1], secondCoordinate[0], secondAddress, secondVenueLocation.text(), isFood, preference);

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

    /**
     * Creates the itinerary. It iterates through the food and activity, adding them to a graph
     * such that only food and activities are neighbors. Then, it runs Dijkstra's algorithm
     * to find the shortest itinerary.
     */
    public ArrayList<String> getItinerary() {
        ArrayList<String> output = new ArrayList<>();
        int graphSize = locations.size();
        int[][] g = new int[locations.size()][locations.size()];
        for (Location a: locations) {
            for (Location b: locations) {
                if ((a.isFood ^ b.isFood) && !(a.isEquals(b))) {
                    g[locations.get(a)][locations.get(b)] = getDistance(a, b);
                    g[locations.get(b)][locations.get(a)] = getDistance(b, a);
                }
            }
        }
        ArrayList<Integer> dijkOutput = dijkstra(g);
        for (Integer i : dijkOutput) {
            output.add(locations.get(i));
        }
        return output;
    }


    /**
     * Takes in a graph and applies Dijkstra's algorithm.
     * @param graph
     */
    public ArrayList<Integer> dijkstra(Graph g) {
        ArrayList<Integer> output = new ArrayList<>();
        int gSize = locations.size();
        Integer[] dist = new Integer[gSize];
        PriorityQueue<Integer, Integer> q = new PriorityQueue<>();

        for (int i = 0; i < gSize; i++) {
            dist[i] = Integer.MAX_VALUE;
            q.add(dist[i], i);
        }

        dist[0] = 0;
        q.decreaseKey(0, 0);
        output.add(0);

        while (!q.isEmpty()) {
            int u = q.extractMin().value;
            output.add(u);
            for (Integer v : g.outNeighbors(u)) {
                if (dist[v] > (dist[u] + g.getWeight(u, v)) && dist[u] != Integer.MAX_VALUE ) {
                    dist[v] = dist[u] + g.getWeight(u, v);
                    q.decreaseKey(v, dist[v]);
                }
            }
        }
        return output;
    }

    public void
    /**
     * Returns the distance between two coordinates by using the haversine formula
     * which takes into account the sphreical nature of the earth. This allows for a more
     * accurate distance, even at small distances. The haversine formula is 2 * radius * arcsine
     * of haversine function.
     * We make the assumption that travel distance in general is correlated to absolute distance.
     * @param origin
     * @param destination
     */
    public int getDistance(Location src, Location tgt) {
        int radius = 3958 //approximate radius in miles of earth near new york
        double srcLat = Math.toRadians(src.getLatitude());
        double srcLong = Math.toRadians(src.getLongitude());
        double tgtLat = Math.toRadians(tgt.getLatitude());
        double tgtLong = Math.toRadians(tgt.getLongitude());
        double latTheta = srcLat - tgtLat;
        double longTheta = srcLong - tgtLong;
        double havLat = Math.pow(Math.sin(latTheta / 2), 2);
        double havLong = Math.pow(Math.sin(longTheta / 2), 2);
        double haversine = Math.sqrt(havLat + Math.cos(srcLat) * Math.cost(tgtLat) * havLong);
        int distance = 2 * radius * Math.asin(haversine);
        return distance;
    }
}
