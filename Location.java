public class Location {

    private double longitude;
    private double latitude;
    private String address;
    private String name;
    private boolean isFood;
    private String activityName;

    public Location(double longitude, double latitude, String address, String name, boolean isFood, String activityName) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.name = name;
        this.isFood = isFood;
        this.activityName = activityName;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public String getAddress() {
        return this.address;
    }

    public String getName() {
        return this.name;
    }

    public boolean isFood() {
        return this.isFood;
    }

    public String getActivityName() {
        return this.activityName;
    }
}
